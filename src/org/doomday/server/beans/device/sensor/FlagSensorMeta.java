package org.doomday.server.beans.device.sensor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlagSensorMeta extends SensorMeta{

	//private Set<String> flags;
	private List<String> flags;
	public FlagSensorMeta(String name, String[] flags) {
		super(name);
		//this.flags = new HashSet<>(Arrays.asList(flags));
		this.flags = Arrays.asList(flags);
	}
	@Override
	public boolean validate(String sensorValue) {
		String[] values = sensorValue.split(",");
		return flags.containsAll(Arrays.asList(values));
	}
	public List<String> getFlags() {
		//return Collections.unmodifiableSet(flags);
		return Collections.unmodifiableList(this.flags);
	}
	
	@Override
	public String getType() {
		return "flag";
	}
	@Override
	public String getDef() {
		return "FLAG ("+String.join(",", flags)+")";
	}

}
