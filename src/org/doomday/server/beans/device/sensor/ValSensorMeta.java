package org.doomday.server.beans.device.sensor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ValSensorMeta extends SensorMeta{
	private List<String> values;
	
	public ValSensorMeta(String name,String[] values) {
		super(name);		
		this.values = Arrays.asList(values); 
	}
	
	@Override
	public boolean validate(String sensorValue) {
		return values.contains(sensorValue);
	}

	public List<String> getOptions() {
		return Collections.unmodifiableList(values);
	}

	@Override
	public String getType() {
		return "val";
	}

	@Override
	public String getDef() {
		return "VAL ("+String.join(",", values)+")";
	}

}
