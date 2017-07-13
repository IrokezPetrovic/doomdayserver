package org.doomday.server.beans.device.sensor;

public abstract class SensorMeta {
	
	private String name;
	public SensorMeta(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}

	public abstract boolean validate(String sensorValue);
	public abstract String getType();
	public abstract String getDef();
}
