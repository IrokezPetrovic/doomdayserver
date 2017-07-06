package org.doomday.emulator.model.script;
import org.doomday.emulator.model.BoolSensor;
import org.doomday.emulator.model.DeviceModel;

import jdk.nashorn.api.scripting.AbstractJSObject;
@SuppressWarnings("restriction")
public class BoolSensorFactory extends AbstractJSObject{
	
	private DeviceModel model;

	public BoolSensorFactory(DeviceModel model) {
		this.model = model;
	}

	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public Object call(Object thiz, Object... args) {
		if (args.length==1){
			String name = (String) args[0];			
			return new SensorWrapper(new BoolSensor(name),model);
		}
		return null;
				
	}
	
}
