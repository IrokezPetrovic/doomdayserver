package emulator;

import org.doomday.server.beans.device.sensor.IntSensorMeta;
import org.doomday.server.beans.device.sensor.ValSensorMeta;
import org.doomday.server.beans.device.trigger.IntParam;
import org.doomday.server.beans.device.trigger.TriggerMeta;
import org.doomday.server.beans.device.trigger.TriggerParam;

public class EmulatorStarter {
	public static void main(String[] args){
		Emulator e = new Emulator("12345", "DOOMDAYDIY:TEST2:0.1", "1234", "239.12.13.14" , 27015, 27015);	
		e.sensor(new IntSensorMeta("TEMPEREATURE", 0, 100))
		.sensor(new IntSensorMeta("WATERLEVEL", 0, 3))
		.sensor(new ValSensorMeta("STATE", new String[]{"STANDBY","HEAT","BOIL"}))
		.trigger(new TriggerMeta("STANDBY", new TriggerParam[]{}))
		.trigger(new TriggerMeta("BOIL", new TriggerParam[]{}))
		.trigger(new TriggerMeta("HEAT",new TriggerParam[]{new IntParam("TARGET",30,95)}));
		
		
		
		e.run();
	}
}
