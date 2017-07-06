package org.doomday.emulator.model;

public class StrSensor extends Sensor{
	DeviceModel model = null;
	String value;
	String def;
	public StrSensor(String name) {
		super(name);
		
		this.value = "";
		setDef(String.format("STR %s", name));		
	}
	
	public void setModel(DeviceModel model) {
		this.model = model;
	}

	@Override
	public boolean validate(String value) {
		return value!=null;
	}


	
}
