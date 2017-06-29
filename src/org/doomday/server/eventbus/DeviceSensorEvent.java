package org.doomday.server.eventbus;

public class DeviceSensorEvent extends DeviceEvent {
	private final String name;
	private final String value;
	public DeviceSensorEvent(String sensorName, String sensorValue) {
		this.name = sensorName;
		this.value = sensorValue;
	}
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
	
	

}
