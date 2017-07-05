package org.doomday.server.plugin.tcpdevice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.nio.channels.Selector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.doomday.server.beans.device.Device;
import org.doomday.server.event.DeviceDiscoveredEvent;
import org.doomday.server.eventbus.EventBus;
import org.doomday.server.eventbus.IEventBus;
import org.doomday.server.model.IDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TcpDiscoverService implements Runnable{

	@Autowired
	@Qualifier("discover.mcastgroup")
	String mcastGroup;
	
	@Autowired
	@Qualifier("discover.mcastport")
	Integer mcastPort;
	
	@Autowired
	ITcpWorker tcpWorker;
			
	@Autowired
	IDeviceRepository deviceRepository;
	
	@Autowired
	IEventBus eventBus;
	
	private MulticastSocket mcastSock;
	Thread t;	
	@PostConstruct
	public void init() throws IOException{				
		InetAddress mcastAddr = InetAddress.getByName(mcastGroup);			
		mcastSock = new MulticastSocket(new InetSocketAddress(mcastPort));				
		mcastSock.joinGroup(mcastAddr);					
										
		t = new Thread(this);
		t.start();
	}
		
	@PreDestroy	
	public void destroy(){
		t.interrupt();
		mcastSock.close();	
		
	}
		
	@Override
	public void run() {	
		while(!mcastSock.isClosed()&&!t.isInterrupted()){
			byte[] buff = new byte[1024];
			DatagramPacket dp = new DatagramPacket(buff, buff.length);		
			try {
				mcastSock.receive(dp);
				parsePacket(dp);
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
				
	}
	
	private void parsePacket(DatagramPacket dp) {
		String localAddr = dp.getAddress().getHostAddress();		
		String[] discoveryString = new String(dp.getData()).trim().split(" ");
		System.out.println("Recieved "+new String(dp.getData())+" from "+localAddr);
		if (discoveryString.length!=2){
			return;
		}
		String devClass = discoveryString[0];
		String devSerial = discoveryString[1];
		
		onDiscover(localAddr, devClass, devSerial);
		
	}

	private void onDiscover(String ipAddr,String devClass, String devSerial){		
		Device device = deviceRepository.getDevice(devClass,devSerial);		
		eventBus.pub("/device", new DeviceDiscoveredEvent(device));
		if (device.getPincode()!=null){
			tcpWorker.appendDevice(ipAddr,device);
		}
	}

	
	

	
}
