package org.doomday.server.service.tcp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.doomday.server.beans.device.Device;
import org.doomday.server.service.IDeviceRepository;
import org.doomday.server.service.IDiscoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TcpDiscoverService implements IDiscoverService{

	@Autowired
	@Qualifier("discover.mcastgroup")
	String mcastGroup;
	
	@Autowired
	ITcpWorker tcpWorker;
	
	
	
	@Autowired
	IDeviceRepository deviceRepository;

	
	private MulticastSocket mcastSock;
			
	@PostConstruct
	public void init() throws IOException{
		InetAddress mcastAddr = InetAddress.getByName(mcastGroup);
				
		mcastSock = new MulticastSocket(27015);
		//MulticastSocket mcastSock = new MulticastSocket(new InetSocketAddress("192.168.0.125", 27015));
		mcastSock.joinGroup(mcastAddr);					
		System.out.println("Discovery started");
	}
	

	@PreDestroy
	public void destroy(){
		mcastSock.close();
	}
	
	@Override
	public void discover() {
		byte[] buff = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buff, buff.length);
		try {
			mcastSock.receive(dp);
			parsePacket(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			tcpWorker.appendDevice(device);
		}
	}
	

	
}
