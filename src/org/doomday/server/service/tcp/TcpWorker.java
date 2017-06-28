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
import org.doomday.server.protocol.IProtocolProcessorFactory;
import org.doomday.server.protocol.ProtocolProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class TcpWorker implements ITcpWorker,Runnable{
	private Map<SelectionKey, ProtocolProcessor> processors = new HashMap<>();
	
	@Autowired
	IProtocolProcessorFactory ppf;
	
	@PostConstruct
	public void init(){
		Thread t = new Thread(this);
		t.start();
	}
	
	Selector selector;
	
	@Override
	public void run() {
		try {
			selector = SelectorProvider.provider().openSelector();
			while(selector.select()>-1){
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while(iterator.hasNext()){
					SelectionKey key = iterator.next();
					iterator.remove();
					
					try{
						if (key.isValid()){
							if (key.isReadable()){
								readChan(key);
							}
							if (key.isWritable()){
								writeChan(key);
							}
						}
					} catch (IOException e){
						closeChan(key);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	private void closeChan(SelectionKey key) throws IOException{
		ProtocolProcessor pp = processors.get(key);
				
	}



	private void writeChan(SelectionKey key) throws IOException{

		ProtocolProcessor pp = processors.get(key);
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
		ProtocolProcessor pp = processors.get(key);
		SocketChannel chan = (SocketChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocate(4096);
		int readed = chan.read(buf);
		if (readed>0){
			String payload = new String(buf.array());
			System.out.println(payload);
			Stream.of(payload.trim().split("\n"))
			.map(String::trim)
			.forEach(pp::read);				
		} else {
			closeChan(key);
		}
	}



	@Override
	public void appendDevice(Device device) {		
		try {
			SocketChannel chan = SocketChannel.open(new InetSocketAddress(device.getLocalAddr(), 22351));
			chan.configureBlocking(false);
			SelectionKey key = chan.register(selector, chan.validOps());
			processors.put(key, ppf.createProcessor(device));			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	
	private void onRead(SelectionKey key,String content){
		
	}
	
	private String onWrite(SelectionKey key){
		return null;		
	}

}
