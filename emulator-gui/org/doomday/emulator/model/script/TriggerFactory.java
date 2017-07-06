package org.doomday.emulator.model.script;
import org.doomday.emulator.model.trigger.Trigger;
import org.doomday.emulator.model.trigger.TriggerArg;

import jdk.nashorn.api.scripting.AbstractJSObject;
@SuppressWarnings("restriction")
public class TriggerFactory extends AbstractJSObject{
	
	
	

	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public Object call(Object thiz, Object... args) {
		if (args.length>0){
			String name = args[0].toString();
			TriggerArg[] targs = new TriggerArg[args.length-1];
			for (int i=1;i<args.length;i++){
				targs[i-1] = (TriggerArg) args[i];
			}
			Trigger t = new Trigger(name,targs);
			return new TriggerWrapper(t);
		}
		return null;
				
	}
	
}
