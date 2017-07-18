package org.doomday.server.plugin.webclient.ctrl;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.doomday.server.model.ISensorValueRepository;
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

@Controller("webclient-device-ctrl")
@RequestMapping("/webclient/devices")
@CrossOrigin(origins="*")
public class DeviceCtrl {
	public static class SensorValue{		
	}
	@Autowired
	ISensorValueRepository sensorValueRepo;
	
	@Autowired
	ObjectMapper mapper;
	
	@RequestMapping(method=RequestMethod.GET,path="/values")
	@ResponseBody 
	public Collection<String> getValues(@RequestParam String sensors) throws JsonParseException, JsonMappingException, IOException{
		String[] sensorList = mapper.readValue(sensors, String[].class);	
		for (String sensor:sensorList){
			System.out.println("Get sensor "+sensor);
		}
		
		return Stream.of(sensorList)
		
		.map(sensor->{
			System.out.println("Get sensor "+sensor);
			try {
				return mapper.readTree(sensor);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		})
		.filter(s->null!=s)
		.map(sensor->{
			return sensorValueRepo.getValue(sensor.get("device").asText(), sensor.get("sensor").asText());
		})
		.collect(Collectors.toList());
		
		
								
	}
}
