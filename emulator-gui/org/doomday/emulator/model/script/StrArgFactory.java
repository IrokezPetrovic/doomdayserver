package org.doomday.emulator.model.script;

import org.doomday.emulator.model.trigger.StrArg;

import jdk.nashorn.api.scripting.AbstractJSObject;
@SuppressWarnings("restriction")
public class StrArgFactory extends AbstractJSObject {
	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public Object call(Object thiz, Object... args) {
		if (args.length==1){
			String name = args[0].toString();			
			return new StrArg(name);
		}
		return null;
	}
}
