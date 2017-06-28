package org.doomday.server.beans.device.sensor;

public class BoolSensorMeta extends SensorMeta{

	public BoolSensorMeta(String name) {
		super(name);
	}

	@Override
	public boolean validate(String sensorValue) {
		return sensorValue!=null&&(sensorValue.equalsIgnoreCase("TRUE")||sensorValue.equalsIgnoreCase("FALSE"));
	}

}
