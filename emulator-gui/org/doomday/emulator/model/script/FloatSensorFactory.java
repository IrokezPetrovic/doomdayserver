package org.doomday.emulator.model.script;
import org.doomday.emulator.model.DeviceModel;
import org.doomday.emulator.model.FloatSensor;

import jdk.nashorn.api.scripting.AbstractJSObject;
@SuppressWarnings("restriction")
public class FloatSensorFactory extends AbstractJSObject{
	
	private DeviceModel model;

	public FloatSensorFactory(DeviceModel model) {
		this.model = model;
	}

	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public Object call(Object thiz, Object... args) {
		if (args.length==3){
			String name = (String) args[0];
			Float min = Float.valueOf(args[1].toString());
			Float max = Float.valueOf(args[2].toString());
			return new SensorWrapper(new FloatSensor(name,min,max),model);
		}
		return null;
				
	}
	
}
