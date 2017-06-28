package org.doomday.server.beans.device.sensor;

public class StrSensorMeta extends SensorMeta{

	public StrSensorMeta(String name) {
		super(name);
	}

	@Override
	public boolean validate(String sensorValue) {
		return sensorValue!=null&&!sensorValue.isEmpty();
	}

}
