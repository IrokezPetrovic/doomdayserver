package org.doomday.server.plugin.admin.ctrl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.doomday.server.beans.device.Device;
import org.doomday.server.model.IDeviceRepository;
import org.doomday.server.model.ISensorValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/admin/device/")
@CrossOrigin(origins="*")
public class DeviceCtrl {
	
	@Autowired
	IDeviceRepository deviceRepository;
	
	@Autowired
	ISensorValueRepository sensorValues;
	
	@RequestMapping(method=RequestMethod.GET,path="list")
	@ResponseBody
	Collection<Device> listDevices(){
		Collection<Device> devices = deviceRepository.listDevices();
		devices.forEach(device->{
			if (device.getProfile()!=null){
				Map<String,String> values = new HashMap<>();
				
				device.getProfile().getSensors().
				forEach(s->{
					values.put(s.getName(), sensorValues.getValue(device.getId(),s.getName()));
				});
				device.setValues(values);				
			}
		});
		return devices;
	}
	
	
	@RequestMapping(method=RequestMethod.POST,path="update")
	@ResponseBody
	Device updateDevice(@RequestBody Device d){			
		deviceRepository.updateDevice(d);
		return d;
	}
	
	@RequestMapping(method=RequestMethod.POST,path="save")
	@ResponseBody
	void saveDevice(Device device){
		
	}
	
	@RequestMapping(method=RequestMethod.POST,path="forget")	
	@ResponseBody
	Boolean forgetDevice(@RequestBody Device d){
		deviceRepository.forget(d);
		return true;
	}
}
