package org.doomday.server.event;

public class DeviceSensorEvent {
	private final String devId;
	private final String sensor;
	private final String value;
	public DeviceSensorEvent(String devSerial, String sensor, String value) {
		super();
		this.devId = devSerial;
		this.sensor = sensor;
		this.value = value;
	}
	public String getDevId() {
		return devId;
	}
	public String getSensor() {
		return sensor;
	}
	public String getValue() {
		return value;
	}
	
	
}
