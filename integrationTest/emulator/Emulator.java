package emulator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

import org.doomday.server.beans.device.sensor.IntSensorMeta;
import org.doomday.server.beans.device.sensor.SensorMeta;
import org.doomday.server.beans.device.trigger.TriggerMeta;

public class Emulator implements Runnable{	
	private String devSerial;
	private String devClass;
	private InetSocketAddress discoveryAddr;
	private int tcpPort;
	
	public Emulator(String devSerial, String devClass,String mcastAddr,int mcastPort,int tcpPort) {
		super();
		this.devSerial = devSerial;
		this.devClass = devClass;
		discoveryAddr = new InetSocketAddress(mcastAddr, mcastPort);
		this.tcpPort = tcpPort;
		
	}
	
	private Thread t;
	public void begin(){
		t = new Thread(this);
		t.start();
	}
	
	public void stop(){
		t.interrupt();
	}
	
	private boolean discoverySendEnabled = false;
	private SelectionKey discoveryKey = null;
	private SelectionKey serverKey = null;
	private SelectionKey clientKey = null;
	
	
	private void processDiscoveryKey(SelectionKey key){
		if (discoverySendEnabled==false)
			return;
		if (!key.isValid()||!key.isWritable())
			return;
		System.out.println("SIM: Send discovery packet");
		discoverySendEnabled = false;
		
		byte[] discoveryStringBytes = (devClass+" "+devSerial).getBytes();
		ByteBuffer discoveryMessage = ByteBuffer.allocate(discoveryStringBytes.length);
		discoveryMessage.clear();
		discoveryMessage.put(discoveryStringBytes);	
		discoveryMessage.flip();	
		DatagramChannel chan = (DatagramChannel) key.channel();
		try {
			chan.send(discoveryMessage, discoveryAddr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void processServerKey(SelectionKey key) throws IOException{
		if (key.isValid()&&key.isAcceptable()){
			ServerSocketChannel chan = (ServerSocketChannel) key.channel();
			if (clientKey!=null){
				chan.accept().close();
			} else {				
				SocketChannel clientChan = chan.accept();
				clientChan.configureBlocking(false);
				clientKey = clientChan.register(key.selector(), SelectionKey.OP_READ|SelectionKey.OP_WRITE);
				System.out.println("ACCEPTED CONNECTION ");
			}
		}
		
	}
	
	
	private void processClientConnection(SelectionKey key){
		if (!key.isValid())
			return;
		try{
			if (key.isReadable()){
				read(key);
			}
			
			if (key.isWritable()){
				write(key);
			}
		} catch (IOException e){
			try{
				clientKey = null;
				SocketChannel chan = (SocketChannel) key.channel();
				chan.close();
			} catch (IOException e1){
				
			}
		}
	}
	
	private void read(SelectionKey key) throws IOException{
//		System.out.println("EMULATOR: Read");
		SocketChannel chan = (SocketChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocate(4096);
		int readed = chan.read(buf);
		if (readed>0){
			String payload = new String(buf.array());			
			Stream.of(payload.trim().split("\n"))
			.map(String::trim)
			.forEach((s)->{
				System.out.println("EMULATOR: "+s);
			});				
		} else {
			closeChan(key);
		}
	}
	
	
	private void write(SelectionKey key) throws IOException{
		//System.out.println("EMULATOR: WRite");
	}
	
	private void closeChan(SelectionKey key){
		clientKey = null;
		SocketChannel chan = (SocketChannel) key.channel();
		
		try {
			chan.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			Selector selector = SelectorProvider.provider().openSelector();
			DatagramChannel discoveryChannel = DatagramChannel.open();
			discoveryChannel.configureBlocking(false);
			discoveryKey = discoveryChannel.register(selector, discoveryChannel.validOps());
			
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.bind(new InetSocketAddress("0.0.0.0", tcpPort));
			serverKey = serverChannel.register(selector, serverChannel.validOps());
			
			Timer t = new Timer();
			t.schedule(new TimerTask() {
				
				@Override
				public void run() {
					if (clientKey==null)
						discoverySendEnabled = true;
					
				}
			},500,500);
			
			
			while(selector.select()>-1){
				Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
				while(keys.hasNext()){
					SelectionKey key = keys.next();
					keys.remove();
					
					if (key.equals(serverKey)){
						processServerKey(key);
					}else					
					if (key.equals(clientKey)){
						processClientConnection(key);
					}else
					if (key.equals(discoveryKey)){
						processDiscoveryKey(key);
					}
				}				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Emulator sensor(SensorMeta sensorMeta) {
		
		return this;
	}

	public Emulator trigger(TriggerMeta triggerMeta) {
		return this;
		
	}


	
	
	
}
