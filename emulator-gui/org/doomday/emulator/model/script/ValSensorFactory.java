package org.doomday.emulator.model.script;
import org.doomday.emulator.model.DeviceModel;
import org.doomday.emulator.model.ValSensor;

import jdk.nashorn.api.scripting.AbstractJSObject;
@SuppressWarnings("restriction")
public class ValSensorFactory extends AbstractJSObject{
	
	private DeviceModel model;

	public ValSensorFactory(DeviceModel model) {
		this.model = model;
	}

	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public Object call(Object thiz, Object... args) {
		if (args.length>3){
			String name = (String) args[0];			
			String[] options = new String[args.length-1];
			for (int i=1;i<args.length;i++){
				options[i-1] = (String) args[i];
			}
			
			return new SensorWrapper(new ValSensor(name, options),model);
		}
		return null;
				
	}
	
}
