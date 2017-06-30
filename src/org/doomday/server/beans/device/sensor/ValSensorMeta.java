package org.doomday.server.beans.device.sensor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ValSensorMeta extends SensorMeta{
	private Set<String> values;
	
	public ValSensorMeta(String name,String[] values) {
		super(name);		
		this.values = new HashSet<>(Arrays.asList(values)); 
	}
	
	@Override
	public boolean validate(String sensorValue) {
		return values.contains(sensorValue);
	}

	public Set<String> getOptions() {
		return Collections.unmodifiableSet(values);
	}

	@Override
	public String getType() {
		return "val";
	}

}
