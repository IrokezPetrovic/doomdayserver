package org.doomday.emulator.model.script;

import org.doomday.emulator.model.trigger.FloatArg;

import jdk.nashorn.api.scripting.AbstractJSObject;
@SuppressWarnings("restriction")
public class FloatArgFactory extends AbstractJSObject {
	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public Object call(Object thiz, Object... args) {
		if (args.length==3){
			String name = args[0].toString();
			Float min = Float.valueOf(args[1].toString());
			Float max = Float.valueOf(args[2].toString());
			return new FloatArg(name, min, max);
		}
		return null;
	}
}
