package org.doomday.server.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.doomday.server.beans.device.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMemRepository implements IDeviceRepository{
	Map<String, Device> devices = new HashMap<>();
	
	@Override
	public Collection<Device> listDevices(){
		return devices.values();
	}
	@Override
	public void updateDevice(Device d){
		String _id = d.getDevClass()+":"+d.getDevSerial();
		Device target = devices.get(_id);
		if (target!=null){
			target.merge(d);
		}
		
	}
	@Override
	public Device getDevice(String devClass, String devSerial) {
		
		Device d = devices.get(devClass+":"+devSerial);
		if (d==null){
			d = new Device(devClass, devSerial);
			devices.put(devClass+":"+devSerial, d);
		}
		return d;
	}
	
}
