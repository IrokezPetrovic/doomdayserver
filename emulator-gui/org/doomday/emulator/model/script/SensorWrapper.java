package org.doomday.emulator.model.script;

import org.doomday.emulator.model.DeviceModel;
import org.doomday.emulator.model.Sensor;

public class SensorWrapper {
	private final Sensor sensor;
	private final DeviceModel model;

	public SensorWrapper(Sensor sensor,DeviceModel model) {
		super();
		this.sensor = sensor;
		this.model = model;
	}
	
	public Sensor getSensor() {
		return sensor;
	}
	
	public void setValue(String v){
		if (sensor.validate(v)){
			model.send(String.format("SET %s %s", sensor.getName(),v));
		}
	}
	
	public String getValue(){
		return "";
	}
	

	
	
}
