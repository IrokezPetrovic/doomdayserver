package org.doomday.server.service.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.doomday.server.beans.device.Device;
import org.doomday.server.protocol.IProtocolProcessor;
import org.doomday.server.protocol.IProtocolProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TcpWorker implements ITcpWorker,Runnable{
	private Map<SelectionKey, IProtocolProcessor> processors = new HashMap<>();
	
	@Autowired
	IProtocolProcessorFactory ppf;
	
	@Autowired
	@Qualifier("tcp.port")
	Integer tcpPort;
	
	@PostConstruct
	public void init() throws IOException{
		
		selector = SelectorProvider.provider().openSelector();
		
		Thread t = new Thread(this);
		t.start();
	}
	
	Selector selector = null;
	
	@Override
	public void run() {
		try {
			//System.out.println("RUN");
			//selector = SelectorProvider.provider().openSelector();
			while(selector.select(1)>-1){
//				System.out.println("SELECT");
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while(iterator.hasNext()){
					SelectionKey key = iterator.next();
					iterator.remove();					
					try{
						if (key.isValid()){
							if (key.isReadable()){
//								System.out.println("READ");
								readChan(key);
							}
							if (key.isWritable()){
//								System.out.println("WRITE");
								writeChan(key);
							}
						}
					} catch (IOException e){
		
						closeChan(key);
					}
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	

	private void closeChan(SelectionKey key) throws IOException{
		IProtocolProcessor pp = processors.get(key);
		if (pp==null)
			return;
		Device d = pp.getDevice();
		d.setConnectionStatus(Device.ConnectionStatus.OFFLINE);
		SocketChannel chan = (SocketChannel) key.channel();
		chan.close();
	}



	private void writeChan(SelectionKey key) throws IOException{

		IProtocolProcessor pp = processors.get(key);
		if (pp==null)
			return;
		Queue<String> q = pp.getWriteQueue();
		
		if (q.size()==0){
			return;
		}
		
		String payload = q.poll();
		byte[] bytes = payload.getBytes();
		ByteBuffer buff = ByteBuffer.allocate(bytes.length);
		buff.clear();
		buff.put(bytes);
		buff.flip();
		SocketChannel chan = (SocketChannel) key.channel();
		chan.write(buff);
		
	}



	private void readChan(SelectionKey key) throws IOException{
		IProtocolProcessor pp = processors.get(key);
		if (pp==null)
			return;
		SocketChannel chan = (SocketChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocate(4096);
		int readed = chan.read(buf);
		if (readed>0){
			String payload = new String(buf.array());			
			Stream.of(payload.trim().split("\n"))
			.map(String::trim)
			.forEach(pp::read);				
		} else {
			closeChan(key);
		}
	}



	@Override
	public void appendDevice(String ipAddr,Device device) {	
		System.out.println("Append device");
		try {
			SocketChannel chan = SocketChannel.open(new InetSocketAddress(ipAddr, tcpPort));						
			chan.configureBlocking(false);			
			SelectionKey key = chan.register(selector, chan.validOps());			
			processors.put(key, ppf.createProcessor(device));			
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
		
	
	

}
