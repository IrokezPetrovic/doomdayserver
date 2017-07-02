package org.doomday.server.service.tcp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.doomday.server.beans.device.Device;
import org.doomday.server.model.IDeviceRepository;
import org.doomday.server.service.IDiscoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TcpDiscoverService implements IDiscoverService,Runnable{

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
	
	private MulticastSocket mcastSock;
			
	Thread t;
	
	@PostConstruct
	public void init() throws IOException{				
		InetAddress mcastAddr = InetAddress.getByName(mcastGroup);		
		mcastSock = new MulticastSocket(mcastPort);		
		mcastSock.joinGroup(mcastAddr);					
		System.out.println("Discovery started, listen "+mcastGroup+":"+mcastPort);
		t = new Thread(this);
		t.start();
	}
	
	
	@PreDestroy	
	public void destroy(){		
		System.out.println("TCP DISCOVER DESTROY");		
		mcastSock.close();	
		t.interrupt();
	}
	
	public void discover(){
		
	}
	
	public void $discover() {
		System.out.println("DISCOVER...");
		byte[] buff = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buff, buff.length);		
		try {
			mcastSock.receive(dp);
			parsePacket(dp);
		} catch (IOException e) {
			e.printStackTrace();
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
		if (device.getPincode()!=null){
			tcpWorker.appendDevice(ipAddr,device);
		}
	}


	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			$discover();
		}
		
	}
	

	
}
