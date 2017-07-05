package org.doomday.emulator.model;

public class IntSensor extends Sensor{
	DeviceModel model = null;
	Integer min,max,value;
	String def;
	public IntSensor(String name, Integer min, Integer max) {
		super(name);
		this.min = min;
		this.max = max;
		this.value = min;
		setDef(String.format("INT %s (%d,%d)", name,min,max));		
	}
	
	public void setModel(DeviceModel model) {
		this.model = model;
	}


	
	@Override
	public void set(Object value) {
		Integer v = (Integer) value;		
		this.value = v;
	}

	@Override
	public String get() {
		return String.valueOf(value);
	}
}
