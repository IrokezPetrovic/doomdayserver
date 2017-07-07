package org.doomday.server.plugin.tcpdevice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
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
import javax.annotation.PreDestroy;

import org.doomday.server.ITransport;
import org.doomday.server.beans.device.Device;
import org.doomday.server.event.DeviceForgetEvent;
import org.doomday.server.eventbus.IEventBus;
import org.doomday.server.protocol.IProtocolProcessor;
import org.doomday.server.protocol.IProtocolProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TcpWorker implements ITcpWorker,Runnable,ITransport{
	private Map<SelectionKey, IProtocolProcessor> processors = new HashMap<>();
	
	@Autowired
	IProtocolProcessorFactory ppf;
	
	@Autowired
	@Qualifier("tcp.port")
	Integer tcpPort;
	
	
	@Autowired
	IEventBus eventBus;
	
	Thread t;
	@PostConstruct
	public void init() throws IOException{
		eventBus.get("/device")
		.ofType(DeviceForgetEvent.class)
		.subscribe(e->{
			String id = e.getDevice().getId();
			for (SelectionKey k:processors.keySet()){
				IProtocolProcessor p = processors.get(k);
				if (p.getDevice().getId().equals(id)){
					System.out.println("Close chan for "+id);
					k.channel().close();
					processors.remove(k);
					break;
				}
			}
		});
		selector = SelectorProvider.provider().openSelector();	
		t = new Thread(this);
		t.start();
	}
	
	@PreDestroy
	public void destroy(){
		processors.keySet().forEach(s->{
			try {
				s.channel().close();
			} catch (IOException|CancelledKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		});
		
		t.stop();
	}
	Selector selector = null;
	
	@Override
	public void run() {
		try {			
			while(!t.isInterrupted()&&selector.selectNow()>-1){				
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
					} catch (CancelledKeyException e){
						e.printStackTrace();
					} 
				}
			}
		} catch (IOException|ClosedSelectorException e) {
			e.printStackTrace();
		}
		
	}
	
	

	private void closeChan(SelectionKey key) throws IOException{
		IProtocolProcessor pp = processors.get(key);		
		if (pp==null)
			return;
		processors.remove(key);
		Device d = pp.getDevice();
		d.setConnectionStatus(Device.ConnectionStatus.OFFLINE);
		SocketChannel chan = (SocketChannel) key.channel();
		chan.close();			
		System.out.println("Device closed");		
	}



	private void writeChan(SelectionKey key) throws IOException,CancelledKeyException{

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
		int writed = chan.write(buff);
		if (writed==0){
			closeChan(key);
		}
		
	}



	private void readChan(SelectionKey key) throws IOException,CancelledKeyException{
		IProtocolProcessor pp = processors.get(key);
		if (pp==null)
			return;
		SocketChannel chan = (SocketChannel) key.channel();
		ByteBuffer buf = ByteBuffer.allocate(4096);
		int readed = chan.read(buf);
		if (readed>-1){
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
			processors.put(key, ppf.createProcessor(device,this));			
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}

	@Override
	public void disconnect(IProtocolProcessor protocolProcessor) {
		for (SelectionKey k:processors.keySet()){
			if (processors.get(k).equals(protocolProcessor)){
				processors.remove(k);
				try {
					k.channel().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		
	}
		
	
	

}
