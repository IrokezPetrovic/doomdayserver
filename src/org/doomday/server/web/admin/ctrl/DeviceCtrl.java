package org.doomday.server.web.admin.ctrl;

import java.util.Collection;
import org.doomday.server.beans.device.Device;
import org.doomday.server.model.IDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path="/device")
public class DeviceCtrl {

	@Autowired
	IDeviceRepository deviceRepository;
	
	
	@RequestMapping(method=RequestMethod.GET,path="/list")
	Collection<Device> listDevices(){
		return deviceRepository.listDevices();
	}
}
