package org.doomday.server.protocol.event;

public class DeviceTriggerEvent {
	private final String deviceId;
	private final String triggerId;
	private final String args;
	
	public DeviceTriggerEvent(String deviceId, String triggerId, String args) {
		super();
		this.deviceId = deviceId;
		this.triggerId = triggerId;
		this.args = args;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public String getTriggerId() {
		return triggerId;
	}
	public String getArgs() {
		return args;
	}
	
	
	
}
