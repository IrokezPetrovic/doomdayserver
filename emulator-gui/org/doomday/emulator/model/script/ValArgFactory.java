package org.doomday.emulator.model.script;
import org.doomday.emulator.model.DeviceModel;
import org.doomday.emulator.model.trigger.ValArg;

import jdk.nashorn.api.scripting.AbstractJSObject;
@SuppressWarnings("restriction")
public class ValArgFactory extends AbstractJSObject{
	
	

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
			
			return new ValArg(name, options);
		}
		return null;
				
	}
	
}
