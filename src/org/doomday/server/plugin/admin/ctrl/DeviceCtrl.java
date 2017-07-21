package org.doomday.server.plugin.admin.ctrl;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.doomday.server.beans.device.Device;
import org.doomday.server.beans.device.DeviceProfile;
import org.doomday.server.model.IDeviceRepository;
import org.doomday.server.model.IProfileRepository;
import org.doomday.server.model.ISensorValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping(path="/admin/devices")
@CrossOrigin(origins="*")
public class DeviceCtrl {
	
	@Autowired
	IDeviceRepository deviceRepository;
	
	@Autowired
	IProfileRepository profileRepo;
	
	@Autowired
	ISensorValueRepository sensorValues;
	
	@Autowired
	ObjectMapper mapper;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	Collection<JsonNode> listDevices(){
		
		Collection<Device> devices = deviceRepository.listDevices();
		return devices.stream()
		.map(device->{
			ObjectNode node = mapper.valueToTree(device);
			DeviceProfile profile = profileRepo.getProfile(device.getDevClass());
			node.set("profile", mapper.valueToTree(profile));			
			if (profile!=null){
				Map<String, String> values = profile.getSensors().values().stream()
				.collect(Collectors.toMap(sensor->{
					return sensor.getName();
				}, sensor->{
					return sensorValues.getValue(device.getId(), sensor.getName());					
				}));
				node.set("values", mapper.valueToTree(values));
			}
			return node;			
			
		}).collect(Collectors.toSet());						
	}
	
	@RequestMapping(method=RequestMethod.GET,path="/{deviceId}")
	@ResponseBody
	Device getDevice(@PathVariable String deviceId){
		Device d = deviceRepository.getDevice(deviceId);
		if (d==null) return null;
		return null;
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	Device updateDevice(@RequestBody Device device){
		return deviceRepository.updateDevice(device);
	}
		
	
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	boolean removeDevice(@RequestParam String id){
		return false;
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE,path="/{deviceId:.+}")
	@ResponseBody
	boolean removeDeviceById(@PathVariable String deviceId){
		return deviceRepository.removeDevice(deviceId);		
	}
	/*
	
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
	*/
}
