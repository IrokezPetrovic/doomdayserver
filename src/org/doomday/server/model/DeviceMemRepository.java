package org.doomday.server.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.doomday.server.beans.device.Device;
import org.doomday.server.event.DeviceForgetEvent;
import org.doomday.server.event.DeviceProfileUpdateEvent;
import org.doomday.server.eventbus.IEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DeviceMemRepository implements IDeviceRepository{
	Map<String, Device> devices = new HashMap<>();
	
	@Autowired
	IEventBus eventBus;
	
	@PostConstruct
	public void init(){
							
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
