import org.doomday.server.beans.device.Device;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTest {

	@Test
	public void testDeviceSerialize() throws JsonProcessingException{
		ObjectMapper m = new ObjectMapper();
		//m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		Device d = new Device("DOOMDAYDIY:TEAPOT:0.0.1", "12345");
		DeviceProfile meta = new DeviceProfile();
		
		meta.addSensor(new IntSensorMeta("INTSENSOR", 10, 20));
		meta.addSensor(new FloatSensorMeta("FLOATSENSOR", 3.14f, 5.15f));
		meta.addSensor(new StrSensorMeta("STRSENSOR"));
		meta.addSensor(new BoolSensorMeta("BOOLSENSOR"));
		meta.addSensor(new ValSensorMeta("VALSENSOR", new String[]{"OPTION1","OPTION2","OPTION3"}));
		meta.addSensor(new FlagSensorMeta("FLAGSENSOR", new String[]{"FLAG1","FLAG2","FLAG3"}));		
		d.setProfile(meta);
		
		TriggerMeta t = new TriggerMeta("TRIGGER1", new TriggerParam[]{
				new IntParam("INTPARAM",10,100),
				new FloatParam("FLOATPARAM",3.14f,5.15f),
				new StrParam("STRPARAM"),
				new BoolParam("BOOLPARAM"),
				new ValParam("VALPARAM", new String[]{"OPTION1","OPTION2","OPTION3"}),
				new FlagParam("FLAGPARAM", new String[]{"FLAG1","FLAG2","FLAG3"})
				});
		meta.addTrigger(t);
		System.out.println(m.writeValueAsString(t));
		System.out.println(m.writeValueAsString(d));
		System.out.println(m.writeValueAsString(meta));
	}
}
