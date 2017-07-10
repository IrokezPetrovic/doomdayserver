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
		setDef(String.format("%s INT (%d,%d)", name,min,max));		
	}
	
	public void setModel(DeviceModel model) {
		this.model = model;
	}


	@Override
	public boolean validate(String value) {
		if (value==null||value.isEmpty()) return false;
		try{
			Integer.valueOf(value);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}
}
