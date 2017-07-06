package org.doomday.emulator.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValSensor extends Sensor{
	DeviceModel model = null;
	String value;
	String def;
	Set<String> options;
	public ValSensor(String name,String[] options) {
		super(name);
		this.options = new HashSet<String>(Arrays.asList(options));
		this.value = options[0];
		setDef(String.format("VAL %s (%s)", name, String.join(",", options)));		
	}
	
	public void setModel(DeviceModel model) {
		this.model = model;
	}



	@Override
	public boolean validate(String value) {
		return value!=null&&options.contains(value);
	}
}
