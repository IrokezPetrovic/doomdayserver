package org.doomday.emulator.model;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.script.ScriptException;

import org.doomday.emulator.model.DeviceModel.State;
import org.doomday.emulator.model.trigger.Trigger;
import org.junit.Ignore;
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
		String src = "var sensor = IntSensor('INT',0,10);sensor.value = 1;";
		factory.build(src, logger);					
	}
	
	@Test
	public void testAddFloatSensor() throws ScriptException{
		String src = "var sensor = FloatSensor('FLOAT',0.0,10.0);sensor.value = 1.14;";
		factory.build(src, logger);
	}
	
	@Test
	public void testAddStrSensor() throws ScriptException{
		String src = "var sensor = StrSensor('STR');sensor.value = 'TEST';";
		factory.build(src, logger);
	}
	@Test
	public void testAddBoolSensor() throws ScriptException{
		String src = "var sensor = BoolSensor('BOOL');sensor.value = true;";
		factory.build(src, logger);
	}
	
	@Test
	public void testAddValSensor() throws ScriptException{
		String src = "var sensor = ValSensor('VALSENS','OPTION1','OPTION2','OPTION3');sensor.value = 'OPTION1';";
		factory.build(src, logger);
	}
	
	@Test
	public void testAddFlagSensor() throws ScriptException{
		String src = "var sensor = FlagSensor('FLAGSENS','FLAG1','FLAG2','FLAG3');sensor.value = 'FLAG1,FLAG3';";
		factory.build(src, logger);
	}
	
	@Test
	public void testAddTrigger() throws ScriptException{
		String src = "var trigger = Trigger('TRIGG');"
				+ "trigger.on(function(){logger.log('FCALL')});"
				+ "device.trigger(trigger);";
		factory.build(src, logger);
	}
	
	@Test
	public void testAddTriggerIntArg() throws ScriptException{
		String src = "var trigger = Trigger('TRIGG',IntArg('INT',10,100));"
				+ "trigger.on(function(val){logger.log('FCALL');logger.log(val);});"
				+ "device.trigger(trigger);"
				+ "trigger.invoke(-10);";
		factory.build(src, logger);
	}
	
	
	@Test	
	public void testSetInterval() throws ScriptException, InterruptedException{
		String src = "var count = 0;"
				+ "var f = function(){"
				+ "logger.log('TIMER');"
				+ "count++;"
				+ "if(count==4){this.cancel()};"
				+ "};"
				+ "setInterval(f,100,100);";
				
		factory.build(src, logger);
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void testTriggerCall() throws ScriptException{
		String src = "var t = Trigger('TRIG',IntArg('TARGET',0,100));"
				+ "device.trigger(t);"
				+ "t.on(function(a){logger.log('CALL');logger.log(a)});";
		
		DeviceModel m = factory.build(src, logger);
		Trigger trigger = m.getTrigger("TRIG");
		trigger.invoke(new String[]{"30"});
		trigger.invoke(new String[]{"130"});
				
	}
	
	@Test
	public void testSensorSet() throws ScriptException{
		String src = "var t = Trigger('TRIG');"
				+ "var s = IntSensor('SENS',10,100);"
				+ "t.on(function(){logger.log('INVOKE');s.value = 150;});"
				+ "device.trigger(t);"
				+ "device.sensor(s);";
		
		DeviceModel m = factory.build(src, logger);
		m.setState(State.ARMED);
		Trigger trigger = m.getTrigger("TRIG");
		trigger.invoke(new String[]{});
		
		System.out.println(m.getWritrable());
	}
	
	
	
	
}
