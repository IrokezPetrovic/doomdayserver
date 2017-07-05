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
		String src = ""
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
	public void testAddIntSensor() throws ScriptException{
		String src = "var sensor = device.sensor(IntSensor('INT',0,10));sensor.set(1);";
		factory.build(src, logger);					
	}
	
	@Test
	public void testAddFloatSensor() throws ScriptException{
		String src = "var sensor = device.sensor(FloatSensor('FLOAT',0.0,10.0));sensor.set(1);";
		factory.build(src, logger);
	}
	
	@Test
	public void testAddStrSensor() throws ScriptException{
		String src = "var sensor = device.sensor(StrSensor('STR'));sensor.set('TEST');";
		factory.build(src, logger);
	}
	@Test
	public void testAddBoolSensor() throws ScriptException{
		String src = "var sensor = device.sensor(BoolSensor('BOOL'));sensor.set(true);";
		factory.build(src, logger);
	}
	
}
