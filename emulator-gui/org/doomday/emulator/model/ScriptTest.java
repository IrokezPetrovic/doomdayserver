package org.doomday.emulator.model;

import java.util.function.Consumer;

import javax.script.ScriptException;

import org.junit.Test;
import static org.junit.Assert.*;

public class ScriptTest {

	private ScriptedDeviceModelFactory factory = new ScriptedDeviceModelFactory();
	private Consumer<String> logger = new Consumer<String>() {
		
		@Override
		public void accept(String t) {
			System.out.println(t);
			
		}
	};
	@Test
	public void testDeviceSetClass() throws ScriptException{
		String src = "logger.log(IntSensor);"
				+ "device.devClass='CLASS'";
		DeviceModel model = factory.build(src, logger);
		assertEquals("CLASS", model.getDevClass());		
	}
	
	@Test
	public void testDeviceSetSerial() throws ScriptException{
		String src= "device.devSerial = 'SERIAL'";
		DeviceModel m = factory.build(src, logger);
		assertEquals("SERIAL", m.getDevSerial());
	}
	
	@Test
	public void testAddSensor() throws ScriptException{
		String src = "var sensor = device.sensor(new IntSensor('INT',0,10));";
		DeviceModel m = factory.build(src, logger);
		
			
	}
	
}
