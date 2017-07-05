package org.doomday.server.protocol;

import org.doomday.server.beans.device.Device;
import org.doomday.server.beans.device.DeviceProfile;
import org.doomday.server.beans.device.sensor.BoolSensorMeta;
import org.doomday.server.beans.device.sensor.FlagSensorMeta;
import org.doomday.server.beans.device.sensor.FloatSensorMeta;
import org.doomday.server.beans.device.sensor.IntSensorMeta;
import org.doomday.server.beans.device.sensor.SensorMeta;
import org.doomday.server.beans.device.sensor.StrSensorMeta;
import org.doomday.server.beans.device.sensor.ValSensorMeta;
import org.junit.Test;


import static org.junit.Assert.*;

public class TestProtocolParser_Sensors {

	Device device = new Device("","");
	ProtocolProcessor pp = new ProtocolProcessor(device);
	@Test
	public void testSensorIntMsg(){
		pp.read("ACCEPT");
		pp.read("SENSOR INT SENSOR1 (10,30)");
		DeviceProfile meta = device.getProfile();
		assertNotNull(meta);
		SensorMeta sm = meta.getSensor("SENSOR1");				
		assertNotNull(sm);
		assertEquals(IntSensorMeta.class, sm.getClass());
		IntSensorMeta ism = (IntSensorMeta) sm;
		assertEquals(Integer.valueOf(10), ism.getMin());
		assertEquals(Integer.valueOf(30), ism.getMax());			
	}
	
	@Test
	public void testSensorFloatMsg(){
		pp.read("ACCEPT");
		pp.read("SENSOR FLOAT SENSOR1 (3.14,5.15)");
		DeviceProfile meta = device.getProfile();
		assertNotNull(meta);
		SensorMeta sm = meta.getSensor("SENSOR1");				
		assertNotNull(sm);
		assertEquals(FloatSensorMeta.class, sm.getClass());
		FloatSensorMeta ism = (FloatSensorMeta) sm;
		assertEquals(new Float(3.14), ism.getMin());
		assertEquals(new Float(5.15), ism.getMax());
		
	}
	
	@Test
	public void testSensorStrMsg(){
		pp.read("ACCEPT");
		pp.read("SENSOR STR SENSOR1");
		DeviceProfile meta = device.getProfile();
		assertNotNull(meta);
		SensorMeta sm = meta.getSensor("SENSOR1");				
		assertNotNull(sm);
		assertEquals(StrSensorMeta.class, sm.getClass());			
	}
	
	@Test
	public void testSensorBoolMsg(){
		pp.read("ACCEPT");
		pp.read("SENSOR BOOL SENSOR1");
		DeviceProfile meta = device.getProfile();
		assertNotNull(meta);
		SensorMeta sm = meta.getSensor("SENSOR1");				
		assertNotNull(sm);
		assertEquals(BoolSensorMeta.class, sm.getClass());				
	}
	
	@Test
	public void testSensorValMsg(){
		pp.read("ACCEPT");
		pp.read("SENSOR VAL SENSOR1 (OPTION1,OPTION2,OPTION3)");
		DeviceProfile meta = device.getProfile();
		assertNotNull(meta);
		SensorMeta sm = meta.getSensor("SENSOR1");				
		assertNotNull(sm);
		assertEquals(ValSensorMeta.class, sm.getClass());
		ValSensorMeta vsm = (ValSensorMeta) sm;
		assertTrue(vsm.getOptions().contains("OPTION1"));
		assertTrue(vsm.getOptions().contains("OPTION2"));
		assertTrue(vsm.getOptions().contains("OPTION3"));
		assertFalse(vsm.getOptions().contains("OPTION5"));				
	}
	
	@Test
	public void testSensorFlagMsg(){
		pp.read("ACCEPT");
		pp.read("SENSOR FLAG SENSOR1 (OPTION1,OPTION2,OPTION3)");
		DeviceProfile meta = device.getProfile();
		assertNotNull(meta);
		SensorMeta sm = meta.getSensor("SENSOR1");				
		assertNotNull(sm);
		assertEquals(FlagSensorMeta.class, sm.getClass());
		FlagSensorMeta vsm = (FlagSensorMeta) sm;
		assertTrue(vsm.getFlags().contains("OPTION1"));
		assertTrue(vsm.getFlags().contains("OPTION2"));
		assertTrue(vsm.getFlags().contains("OPTION3"));
		assertFalse(vsm.getFlags().contains("OPTION5"));				
	}
	
}
