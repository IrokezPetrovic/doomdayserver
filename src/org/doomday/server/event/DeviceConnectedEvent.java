package org.doomday.server.event;

public class DeviceConnectedEvent {
	private final String devClass;
	private final String devName;
	private final String devSerial;
	public DeviceConnectedEvent(String devClass, String devName, String devSerial) {
		super();
		this.devClass = devClass;
		this.devName = devName;
		this.devSerial = devSerial;
	}
	public String getDevClass() {
		return devClass;
	}
	public String getDevName() {
		return devName;
	}
	public String getDevSerial() {
		return devSerial;
	}
	
	
}
