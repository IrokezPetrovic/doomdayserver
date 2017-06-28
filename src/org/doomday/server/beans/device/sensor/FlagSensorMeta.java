package org.doomday.server.beans.device.sensor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FlagSensorMeta extends SensorMeta{

	private Set<String> flags;
	public FlagSensorMeta(String name, String[] flags) {
		super(name);
		this.flags = new HashSet<>(Arrays.asList(flags));
	}
	@Override
	public boolean validate(String sensorValue) {
		String[] values = sensorValue.split(",");
		return flags.containsAll(Arrays.asList(values));
	}

}
