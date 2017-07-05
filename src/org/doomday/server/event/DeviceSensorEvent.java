package org.doomday.server.event;

public class DeviceSensorEvent {
	private final String devSerial;
	private final String sensor;
	private final String value;
	public DeviceSensorEvent(String devSerial, String sensor, String value) {
		super();
		this.devSerial = devSerial;
		this.sensor = sensor;
		this.value = value;
	}
	public String getDevSerial() {
		return devSerial;
	}
	public String getSensor() {
		return sensor;
	}
	public String getValue() {
		return value;
	}
	
	
}
