package org.doomday.emulator.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class FlagSensor extends Sensor{
	DeviceModel model = null;
	Set<String> value;
	String def;
	Set<String> flags;
	public FlagSensor(String name,String[] flags) {
		super(name);
		this.flags = new HashSet<String>(Arrays.asList(flags));
		this.value = new HashSet<>();
		setDef(String.format("%s FLAG (%s)", name, String.join(",", flags)));		
	}
	
	public void setModel(DeviceModel model) {
		this.model = model;
	}

	@Override
	public boolean validate(String value) {
		
		return Stream.of(value.split(","))
		.allMatch(flags::contains);
		
	}


	
	
}
