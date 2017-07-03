package emulator;

import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;

import org.doomday.server.beans.device.sensor.BoolSensorMeta;
import org.doomday.server.beans.device.sensor.FlagSensorMeta;
import org.doomday.server.beans.device.sensor.FloatSensorMeta;
import org.doomday.server.beans.device.sensor.IntSensorMeta;
import org.doomday.server.beans.device.sensor.SensorMeta;
import org.doomday.server.beans.device.sensor.StrSensorMeta;
import org.doomday.server.beans.device.sensor.ValSensorMeta;
import org.doomday.server.beans.device.trigger.IntParam;
import org.doomday.server.beans.device.trigger.TriggerMeta;
import org.doomday.server.beans.device.trigger.TriggerParam;
import org.junit.Ignore;
import org.junit.Test;

public class TestEmulator {

	@Test
	@Ignore
	public void test() throws InterruptedException{
		Emulator emu = new Emulator("12345", "TEST", "1234","239.12.13.14",27015,27015);
		emu.sensor(new IntSensorMeta("TEMPERATURE", 0, 100))
		.sensor(new IntSensorMeta("WATERLEVEL",0,3))
		.sensor(new ValSensorMeta("MODE", new String[]{"STANDBY","HEAT","BOIL"}))
		.trigger(new TriggerMeta("BOIL",new TriggerParam[]{}))
		.trigger(new TriggerMeta("STANDBY",new TriggerParam[]{}))
		.trigger(new TriggerMeta("HEAT",new TriggerParam[]{new IntParam("TARGET_TEMPERATURE", 30, 95)}));
		emu.begin();		
		//TimeUnit.SECONDS.sleep();
	}
	
	@Test
	public void testSensorToStrings(){	
		Sensor intSensor = new Sensor(new IntSensorMeta("INTSENSOR", 10, 20));
		Sensor floatSensor = new Sensor(new FloatSensorMeta("FLOATSENSOR", 3.14f, 5.15f));
		Sensor strSensor = new Sensor(new StrSensorMeta("STRSENSOR"));
		Sensor boolSensor = new Sensor(new BoolSensorMeta("BOOLSENSOR"));
		Sensor valSensor = new Sensor(new ValSensorMeta("VALSENSOR", new String[]{"OPTION_1","OPTION_2","OPTION_3"}));
		Sensor flagSensor = new Sensor(new FlagSensorMeta("FLAGSENSOR", new String[]{"FLAG_1","FLAG_2","FLAG_3"}));
		
		assertEquals("SENSOR INT INTSENSOR (10,20)", intSensor.toString());
		assertEquals("SENSOR FLOAT FLOATSENSOR (3.14,5.15)", floatSensor.toString());
		assertEquals("SENSOR STR STRSENSOR",strSensor.toString());
		assertEquals("SENSOR BOOL BOOLSENSOR",boolSensor.toString()); 
		assertEquals("SENSOR VAL VALSENSOR (OPTION_1,OPTION_2,OPTION_3)", valSensor.toString());
		assertEquals("SENSOR FLAG FLAGSENSOR (FLAG_3,FLAG_1,FLAG_2)", flagSensor.toString());
		
	}
}
