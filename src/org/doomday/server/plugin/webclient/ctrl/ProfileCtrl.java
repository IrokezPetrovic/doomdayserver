package org.doomday.server.plugin.webclient.ctrl;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.doomday.server.beans.device.DeviceProfile;
import org.doomday.server.model.IDeviceRepository;
import org.doomday.server.model.IProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller("webclient-profile-controller")
@RequestMapping("/webclient/profiles")
@CrossOrigin(origins="*")
public class ProfileCtrl {

	@Autowired
	IDeviceRepository deviceRepository;
	@Autowired
	IProfileRepository profileRepository;
	
	@Autowired
	ObjectMapper mapper;
	
	@RequestMapping(method=RequestMethod.GET,path="/byDevices")
	@ResponseBody
	public Collection<DeviceProfile> listPorfiles(@RequestParam("devices") String devices) throws JsonParseException, JsonMappingException, IOException{
		String[] deviceIds = mapper.readValue(devices, String[].class);
		return Stream.of(deviceIds)
		.sorted()
		.filter(deviceId->{
			System.out.println("DeviceId="+deviceId);
			return true;
		})
		.map(deviceId->deviceRepository.getDevice(deviceId))
		.map(device->profileRepository.getProfile(device.getDevClass()))
		.collect(Collectors.toList());
//		return new ArrayList<>();		
	}
}
