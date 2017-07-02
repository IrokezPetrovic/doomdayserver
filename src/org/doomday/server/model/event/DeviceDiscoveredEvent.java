package org.doomday.server.model.event;

public class DeviceDiscoveredEvent {
	private final String devAddr;
	private final String devClass;
	private final String devSerial;
	public DeviceDiscoveredEvent(String devAddr, String devClass, String devSerial) {
		super();
		this.devAddr = devAddr;
		this.devClass = devClass;
		this.devSerial = devSerial;
	}
	public String getDevAddr() {
		return devAddr;
	}
	public String getDevClass() {
		return devClass;
	}
	public String getDevSerial() {
		return devSerial;
	}
	
	
	
}
