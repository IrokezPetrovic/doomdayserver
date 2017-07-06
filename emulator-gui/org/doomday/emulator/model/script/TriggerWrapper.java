package org.doomday.emulator.model.script;

import org.doomday.emulator.model.trigger.Trigger;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
@SuppressWarnings("restriction")
public class TriggerWrapper {
	private ScriptObjectMirror callback;
	private Trigger trigger;
		
	public Trigger getTrigger() {
		return trigger;
	}
	
	public TriggerWrapper(Trigger t) {
		this.trigger = t;
		t.setCallback(this::invoke);
	}

	public void on(ScriptObjectMirror c){
		this.callback = c;		
	}
	
	public void invoke(Object... args){
		if (callback!=null){			
			callback.call(this,args);
		}
	}
}
