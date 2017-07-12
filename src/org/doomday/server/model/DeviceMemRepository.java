package org.doomday.server.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.doomday.server.beans.Dashboard;
import org.doomday.server.beans.device.Device;
import org.doomday.server.event.DeviceForgetEvent;
import org.doomday.server.eventbus.IEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DeviceMemRepository implements IDeviceRepository{
	Map<String, Device> devices = new HashMap<>();
	
	@Autowired
	IEventBus eventBus;
	
	@Autowired
	ObjectMapper mapper;
	
	@PostConstruct
	public void init(){
		try{
			FileReader fr = new FileReader("/tmp/devices.conf");
			BufferedReader br = new BufferedReader(fr);		
			String line = "";
			while((line=br.readLine())!=null){
				Device d = mapper.readValue(line, Device.class);
				//dashboards.add(d);
				devices.put(d.getId(), d);
			}
			br.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
		
	@PreDestroy
	public void destroy() {
		try{
			FileWriter fw = new FileWriter("/tmp/devices.conf");
			devices.values()
			.forEach(d->{
				try {
					fw.write(mapper.writeValueAsString(d)+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});		
			fw.flush();
			fw.close();
		} catch(IOException e){
			
		}
	}
	
	@Override
	public Collection<Device> listDevices(){
		return devices.values();
	}
	
	@Override
	public void updateDevice(Device d){		
		Device target = devices.get(d.getId());	
		if (target!=null){
			target.merge(d);
		}
		
	}
	
	@Override
	public Device getDevice(String devClass, String devSerial) {		
		Device d = devices.get(devClass+":"+devSerial);
		if (d==null){
			d = new Device(devClass, devSerial);
			d.setName(d.getId());
			devices.put(d.getId(), d);
		}
		//d.setPincode("1234");
		return d;
	}
	
	@Override
	public Device getDevice(String deviceId) {
		return devices.get(deviceId);
	}

	@Override
	public void forget(Device d) {
		if (devices.remove(d.getId())!=null){
			eventBus.pub("/device", new DeviceForgetEvent(d));
		}
		
	}
	
}
