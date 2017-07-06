package org.doomday.emulator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.doomday.emulator.model.DeviceModel;
import org.doomday.emulator.model.DeviceModel.State;

public class DeviceWorker implements Runnable {
	private Consumer<Boolean> onConnect = null;
	private Consumer<String> logger;

	public void onConnect(Consumer<Boolean> onConnect) {
		this.onConnect = onConnect;
	}

	public DeviceWorker(DeviceModel model, Consumer<String> logger) {
		this.model = model;
		this.logger = logger;
	}

	public boolean isConnected() {
		return clientKey != null;
	}

	private Thread t;

	public void start(String mcastGroup, Integer mcastPort, Integer ipPort) {
		this.ipPort = ipPort;
		mcastGroupAddr = new InetSocketAddress(mcastGroup, mcastPort);
		t = new Thread(this);
		t.start();

	}

	public void disconnect() {
		if (clientKey != null) {
			try {
				clientKey.channel().close();
				clientKey = null;
				if (onConnect != null) {
					onConnect.accept(false);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		disconnect();
		if (discoverKey != null) {
			try {
				discoverKey.channel().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (serverKey != null) {
			try {
				serverKey.channel().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private DeviceModel model;
	private Integer ipPort;
	private InetSocketAddress mcastGroupAddr;
	private SelectionKey discoverKey;
	private SelectionKey serverKey;
	private SelectionKey clientKey = null;
	private boolean canSendDiscover = false;
	private Selector selector;

	@Override
	public void run() {
		try {
			selector = SelectorProvider.provider().openSelector();
			DatagramChannel discoverChan = DatagramChannel.open();
			discoverChan.configureBlocking(false);
			discoverKey = discoverChan.register(selector, SelectionKey.OP_WRITE);

			ServerSocketChannel serverChan = ServerSocketChannel.open();
			serverChan.bind(new InetSocketAddress("0.0.0.0", ipPort));
			serverChan.configureBlocking(false);
			serverKey = serverChan.register(selector, SelectionKey.OP_ACCEPT);
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				@Override
				public void run() {
					if (!isConnected())
						canSendDiscover = true;

				}
			}, 1000, 1000);

			while (selector.selectNow() > -1) {
				Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
				while (keys.hasNext()) {
					SelectionKey key = keys.next();
					keys.remove();
					if (key.equals(discoverKey)) {
						processDiscover(key);
					}

					if (key.equals(serverKey)) {
						processServer(key);
					}

					if (key.equals(clientKey)) {
						processClient(key);
					}
				}
				selector.selectedKeys().clear();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void processClient(SelectionKey key) {
		if (!key.isValid())
			return;
		try {
			if (key.isReadable()) {
				readFromClient(key);
			}
			if (key.isWritable()) {
				writeToClient(key);
			}
		} catch (IOException e) {
			logger.accept("Close connection");
			closeClient(key);
		} catch (CancelledKeyException e) {

		}

	}

	void readFromClient(SelectionKey key) throws IOException {
		SocketChannel chan = (SocketChannel) key.channel();
		ByteBuffer readBuffer = ByteBuffer.allocate(4096);
		readBuffer.clear();
		int readed = chan.read(readBuffer);
		if (readed > -1) {
			String readString = new String(readBuffer.array()).trim();
			Stream.of(readString.split("\n")).map(s -> s.trim()).forEach(s -> {
				logger.accept("Readed " + s);
				model.process(s);
			});
		} else {
			closeClient(key);
		}

	}

	void writeToClient(SelectionKey key) throws IOException {
		SocketChannel chan = (SocketChannel) key.channel();
		while (model.hasWritable()) {			
			String str = model.getWritrable();
			logger.accept("Writed " + str);
			String s = str + "\n";
			chan.write(ByteBuffer.wrap(s.getBytes()));
		}
	}

	void closeClient(SelectionKey key) {
		clientKey = null;
		model.setState(State.DISCONNECTED);
		logger.accept("Close connection");
		try {
			if (onConnect != null) {
				onConnect.accept(false);
			}
			key.channel().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processServer(SelectionKey key) {
		
		ServerSocketChannel chan = (ServerSocketChannel) key.channel();
		try {
			SocketChannel clientChan = chan.accept();

			if (isConnected()) {
				clientChan.close();
			} else {
				clientChan.configureBlocking(false);
				clientKey = clientChan.register(selector, clientChan.validOps());
				logger.accept("Accept connection from " + clientChan.getRemoteAddress().toString());
				model.setState(State.CONNECTED);
				if (onConnect != null) {
					onConnect.accept(true);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void processDiscover(SelectionKey key) {
		if (!canSendDiscover)
			return;
		canSendDiscover = false;
		byte[] discoverString = (model.getDevClass() + " " + model.getDevSerial()).getBytes();
		ByteBuffer buf = ByteBuffer.allocate(discoverString.length);
		buf.clear();
		buf.put(discoverString);
		buf.flip();
		DatagramChannel chan = (DatagramChannel) key.channel();
		try {
			logger.accept("Send discovery");
			chan.send(buf, mcastGroupAddr);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
