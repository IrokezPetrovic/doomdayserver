package org.doomday.emulator.model;

public class BoolSensor extends Sensor{
	DeviceModel model = null;
	Boolean value;
	String def;
	public BoolSensor(String name) {
		super(name);
		
		this.value = false;
		setDef(String.format("%s BOOL", name));		
	}
	
	public void setModel(DeviceModel model) {
		this.model = model;
	}


	
	@Override
	public boolean validate(String value) {
		return value!=null&&(value.equalsIgnoreCase("true")||value.equalsIgnoreCase("false"));
	}
}
