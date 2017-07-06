package org.doomday.emulator.model;

public class FloatSensor extends Sensor{
	DeviceModel model = null;
	Float min,max,value;
	String def;
	public FloatSensor(String name, Float min, Float max) {
		super(name);
		this.min = min;
		this.max = max;
		this.value = min;
		setDef(String.format("FLOAT %s (%f,%f)", name,min,max));		
	}
	
	public void setModel(DeviceModel model) {
		this.model = model;
	}

	@Override
	public boolean validate(String value) {
		if (value==null||value.isEmpty()) return false;
		try{
			Float.valueOf(value);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
		
	}


	
	
}
