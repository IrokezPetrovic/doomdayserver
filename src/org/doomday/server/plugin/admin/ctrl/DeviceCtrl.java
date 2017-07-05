package org.doomday.server.plugin.admin.ctrl;

import java.util.Collection;

import org.doomday.server.beans.device.Device;
import org.doomday.server.model.IDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(path="/admin/device/")
@CrossOrigin(origins="*")
public class DeviceCtrl {
	
	@Autowired
	IDeviceRepository deviceRepository;
	
	
	@RequestMapping(method=RequestMethod.GET,path="list")
	@ResponseBody
	Collection<Device> listDevices(){
		return deviceRepository.listDevices();
	}
	
	
	@RequestMapping(method=RequestMethod.POST,path="update")
	@ResponseBody
	Device updateDevice(@RequestBody Device d){			
		deviceRepository.updateDevice(d);
//		Device dev = deviceRepository.getDevice(d.getId());
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
