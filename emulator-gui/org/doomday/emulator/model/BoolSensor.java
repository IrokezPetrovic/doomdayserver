package org.doomday.emulator.model;

public class BoolSensor extends Sensor{
	DeviceModel model = null;
	Boolean value;
	String def;
	public BoolSensor(String name) {
		super(name);
		
		this.value = false;
		setDef(String.format("BOOL %s", name));		
	}
	
	public void setModel(DeviceModel model) {
		this.model = model;
	}


	
	@Override
	public void set(Object value) {
		this.value = Boolean.valueOf(value.toString());
	}

	@Override
	public String get() {
		return value.toString();
	}
}
