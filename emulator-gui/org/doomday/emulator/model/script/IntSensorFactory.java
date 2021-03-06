package org.doomday.emulator.model.script;
import org.doomday.emulator.model.DeviceModel;
import org.doomday.emulator.model.IntSensor;

import jdk.nashorn.api.scripting.AbstractJSObject;
@SuppressWarnings("restriction")
public class IntSensorFactory extends AbstractJSObject{
	
	private DeviceModel model;

	public IntSensorFactory(DeviceModel model) {
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
			Integer min = (Integer) args[1];
			Integer max = (Integer) args[2];
			
			return new SensorWrapper(new IntSensor(name,min,max),model);
		}
		return null;
				
	}
	
}
