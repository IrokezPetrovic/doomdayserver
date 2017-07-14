package org.doomday.server.event;

public class SensorEvent {
	private final String deviceId;
	private final String sensorId;
	private final String value;
	public SensorEvent(String deviceId, String sensorId, String value) {
		super();
		this.deviceId = deviceId;
		this.sensorId = sensorId;
		this.value = value;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public String getSensorId() {
		return sensorId;
	}
	public String getValue() {
		return value;
	}
	
	
}
