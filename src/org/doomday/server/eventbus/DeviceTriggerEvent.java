package org.doomday.server.eventbus;

public class DeviceTriggerEvent extends DeviceEvent{
	private final String[] params;
	private final String trigger;
	public DeviceTriggerEvent(String trigger,String[] params) {
		this.trigger = trigger;
		this.params = params;
	}
	
	public String getTrigger() {
		return trigger;
	}
	
	public String[] getParams() {
		return params;
	}
}
