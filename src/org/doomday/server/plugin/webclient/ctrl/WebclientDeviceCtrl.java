package org.doomday.server.plugin.webclient.ctrl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.doomday.server.beans.device.DeviceProfile;
import org.doomday.server.model.IDeviceRepository;
import org.doomday.server.model.ISensorValueRepository;
import org.doomday.server.plugin.webclient.beans.DeviceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/webclient/device")
@CrossOrigin(origins="*")
public class WebclientDeviceCtrl {
	
	@Autowired
	IDeviceRepository deviceRepo;
	
	@Autowired
	ISensorValueRepository valuesRepo;
	
	@RequestMapping(method=RequestMethod.GET,path="/list")
	@ResponseBody
	Collection<DeviceBean> listDevices(){
		return deviceRepo.listDevices().stream()
		.filter(d->d.getProfile()!=null)
		.map(d->{			
			DeviceBean bean = new DeviceBean(d.getName(), "", d.getProfile().getSensors(), d.getProfile().getTriggers());
			bean.getSensors().forEach(sensor->{
				String value = valuesRepo.getValue(d.getId(), sensor.getName());
				bean.getValues().put(sensor.getName(), value);
			});
			return bean;
		})		
		.collect(Collectors.toSet());
	}
	
	

}
