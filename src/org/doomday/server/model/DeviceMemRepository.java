package org.doomday.server.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.doomday.server.beans.device.Device;
import org.doomday.server.eventbus.rx.IEventBus;
import org.doomday.server.protocol.event.DeviceMetaStoreEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceMemRepository implements IDeviceRepository{
	Map<String, Device> devices = new HashMap<>();
	
	@Autowired
	IEventBus eventBus;
	
	@PostConstruct
	public void init(){
		eventBus.get("/device")
		.ofType(DeviceMetaStoreEvent.class)
		.subscribe(m->{
			
		});					
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
			devices.put(d.getId(), d);
		}
		d.setPincode("1234");
		return d;
	}
	
}
