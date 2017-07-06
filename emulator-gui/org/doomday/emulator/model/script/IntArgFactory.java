package org.doomday.emulator.model.script;

import org.doomday.emulator.model.trigger.IntArg;

import jdk.nashorn.api.scripting.AbstractJSObject;
@SuppressWarnings("restriction")
public class IntArgFactory extends AbstractJSObject {
	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public Object call(Object thiz, Object... args) {
		if (args.length==3){
			String name = args[0].toString();
			Integer min = Integer.valueOf(args[1].toString());
			Integer max = Integer.valueOf(args[2].toString());
			return new IntArg(name, min, max);
		}
		return null;
	}
}
