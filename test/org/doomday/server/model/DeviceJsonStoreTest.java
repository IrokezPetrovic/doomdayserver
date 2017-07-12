package org.doomday.server.model;

import java.io.IOException;

import org.doomday.server.beans.device.DeviceProfile;
import org.doomday.server.beans.device.sensor.IntSensorMeta;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DeviceJsonStoreTest.Config.class})

public class DeviceJsonStoreTest {
	@Autowired
	ObjectMapper mapper;
	
	@Test
	public void testStore() throws IOException{
		DeviceProfile profile = new DeviceProfile();
		profile.addSensor(new IntSensorMeta("INSTSE", 10, 100));		
		String src = mapper.writeValueAsString(profile);
		System.out.println(src);
		DeviceProfile dst = mapper.readValue(src, DeviceProfile.class);
	}
	
	
	
	@Import(org.doomday.server.misc.JsonConfig.class)
	public static class Config{
		
	}
}
