package org.doomday.server.web.webclient.ctrl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.doomday.server.beans.device.DeviceMeta;
import org.doomday.server.model.IDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/webclient/device")
public class WebclientDeviceCtrl {
	
	@Autowired
	IDeviceRepository deviceRepo;
	
	@RequestMapping(method=RequestMethod.GET,path="/list")
	@ResponseBody
	Collection<DeviceMeta> listDevices(){
		return deviceRepo.listDevices().stream()
		.map(d->d.getMeta())
		.filter(d->d!=null)
		.collect(Collectors.toSet());
	}
	
	

}
