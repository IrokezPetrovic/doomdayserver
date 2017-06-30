package emulator;

import java.util.concurrent.TimeUnit;

import org.doomday.server.beans.device.sensor.IntSensorMeta;
import org.doomday.server.beans.device.sensor.ValSensorMeta;
import org.doomday.server.beans.device.trigger.IntParam;
import org.doomday.server.beans.device.trigger.TriggerMeta;
import org.doomday.server.beans.device.trigger.TriggerParam;
import org.junit.Test;

public class TestEmulator {

	@Test
	public void test() throws InterruptedException{
		Emulator emu = new Emulator("12345", "TEST", "235.49.49.49",27015,27015);
		emu.sensor(new IntSensorMeta("TEMPERATURE", 0, 100))
		.sensor(new IntSensorMeta("WATERLEVEL",0,3))
		.sensor(new ValSensorMeta("MODE", new String[]{"STANDBY","HEAT","BOIL"}))
		.trigger(new TriggerMeta("BOIL",new TriggerParam[]{}))
		.trigger(new TriggerMeta("STANDBY",new TriggerParam[]{}))
		.trigger(new TriggerMeta("HEAT",new TriggerParam[]{new IntParam("TARGET_TEMPERATURE", 30, 95)}));
		emu.begin();
		
		TimeUnit.SECONDS.sleep(4);
	}
}
