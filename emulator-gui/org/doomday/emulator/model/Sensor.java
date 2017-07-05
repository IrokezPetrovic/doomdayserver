package org.doomday.emulator.model;

public abstract class Sensor {
	
	private final String name;
	private String def;	
	public Sensor(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	protected void setDef(String def){
		this.def = def;
	}
	public String getDef() {
		return def;
	}
	
		
	public abstract void set(Object value);
	public abstract String get();
	
}
