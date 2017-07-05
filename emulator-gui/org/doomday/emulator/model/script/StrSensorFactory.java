package org.doomday.emulator.model.script;
import org.doomday.emulator.model.DeviceModel;
import org.doomday.emulator.model.StrSensor;

import jdk.nashorn.api.scripting.AbstractJSObject;
@SuppressWarnings("restriction")
public class StrSensorFactory extends AbstractJSObject{
	
	public StrSensorFactory(DeviceModel model) {
		
	}

	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public Object call(Object thiz, Object... args) {
		if (args.length==1){
			String name = (String) args[0];			
			return new StrSensor(name);
		}
		return null;
				
	}
	
}
