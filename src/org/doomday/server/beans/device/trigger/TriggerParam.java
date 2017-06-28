package org.doomday.server.beans.device.trigger;

public abstract class TriggerParam {
	private String name;
	
	public TriggerParam(String name) {
		this.name = name;		
	}
	
	public String getName() {
		return name;
	}

	public abstract boolean validate(String v);
	
}
