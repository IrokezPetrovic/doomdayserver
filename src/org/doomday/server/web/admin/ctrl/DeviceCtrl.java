package org.doomday.server.web.admin.ctrl;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.doomday.server.beans.device.Device;
import org.doomday.server.model.IDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/admin/device/")
public class DeviceCtrl {
	
	@Autowired
	IDeviceRepository deviceRepository;
	
	
	@RequestMapping(method=RequestMethod.GET,path="list")
	@ResponseBody
	Collection<Device> listDevices(){
		return deviceRepository.listDevices();
	}
	
	@RequestMapping(method=RequestMethod.POST,path="save")
	void saveDevice(Device device){
		
	}
}
