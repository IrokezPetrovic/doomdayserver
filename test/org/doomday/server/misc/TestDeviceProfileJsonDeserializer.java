package org.doomday.server.misc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.doomday.server.beans.device.DeviceProfile;
import org.doomday.server.beans.device.sensor.BoolSensorMeta;
import org.doomday.server.beans.device.sensor.FlagSensorMeta;
import org.doomday.server.beans.device.sensor.FloatSensorMeta;
import org.doomday.server.beans.device.sensor.IntSensorMeta;
import org.doomday.server.beans.device.sensor.StrSensorMeta;
import org.doomday.server.beans.device.sensor.ValSensorMeta;
import org.doomday.server.beans.device.trigger.BoolParam;
import org.doomday.server.beans.device.trigger.FlagParam;
import org.doomday.server.beans.device.trigger.FloatParam;
import org.doomday.server.beans.device.trigger.IntParam;
import org.doomday.server.beans.device.trigger.StrParam;
import org.doomday.server.beans.device.trigger.TriggerMeta;
import org.doomday.server.beans.device.trigger.TriggerParam;
import org.doomday.server.beans.device.trigger.ValParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestDeviceProfileJsonDeserializer.Config.class})
public class TestDeviceProfileJsonDeserializer {

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	DeviceProfileJsonDeserializer deserializer;
	
	@Test
	public void deserializeTest() throws JsonProcessingException{
		DeviceProfile profile = new DeviceProfile();
		profile.addSensor(new IntSensorMeta("INTSENSOR", 10, 20));
		profile.addSensor(new FloatSensorMeta("FLOATSENSOR", 3.14f, 5.15f));
		profile.addSensor(new StrSensorMeta("STRSENSOR"));
		profile.addSensor(new BoolSensorMeta("BOOLSENSOR"));
		profile.addSensor(new ValSensorMeta("VALSENSOR", new String[]{"OPTION1","OPTION2","OPTION3"}));
		profile.addSensor(new FlagSensorMeta("FLAGSENSOR", new String[]{"FLAG1","FLAG2","FLAG3"}));
		
		String json = mapper.writeValueAsString(profile);
		System.out.println(json);
		
		DeviceProfile fromJson = deserializer.parse(json);
		assertEquals(profile.getSensors().size(), fromJson.getSensors().size());	
		String json2 = mapper.writeValueAsString(fromJson);
		assertEquals(json, json2);
	}
	
	@Test
	public void triggerParseTest() throws IOException{
		DeviceProfile profile = new DeviceProfile();
		profile.addTrigger(new TriggerMeta("TRIGGER1", new TriggerParam[]{
				new IntParam("INTPARAM", 10, 20),
				new FloatParam("FLOATPARA", 3.14f, 5.15f),
				new StrParam("STRPARAM"),
				new BoolParam("BOOLPARAM"),
				new ValParam("VALPARAM", new String[]{"OPTION1","OPTION2","OPTION3"}),
				new FlagParam("FLAGPARAM", new String[]{"FLAG1","FALG2","FLAG3"})
		}));
		
		String json = mapper.writeValueAsString(profile);
		
		System.out.println(json);
		DeviceProfile fromJson = deserializer.parse(json);
		
		assertEquals(profile.getTriggers().size(), fromJson.getTriggers().size());
		String json2 = mapper.writeValueAsString(fromJson);
		assertEquals(json, json2);
	}
	
	public static class Config{
		@Bean
		public DeviceProfileJsonDeserializer deserializer(){
			return new DeviceProfileJsonDeserializer();
		}
		
		@Bean
		public ObjectMapper objectMapper(){
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
			mapper.configure(Feature.IGNORE_UNKNOWN, true);
			mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_MISSING_VALUES, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);		
			return mapper;
		}
	}
}
