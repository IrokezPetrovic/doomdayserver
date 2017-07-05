package org.doomday.emulator.model.script;

import org.doomday.emulator.model.DeviceModel;
import org.doomday.emulator.model.Sensor;

public class DeviceWrapper {

	private DeviceModel model;

	public DeviceWrapper(DeviceModel model) {
		this.model = model;
	}
	
	
	public String getDevClass(){
		return model.getDevClass();		
	}	
	public void setDevClass(String s){			
		model.setDevClass(s);
	}
	
	public String getDevSerial(){
		return model.getDevSerial();
	}
	public void setDevSerial(String serial){
		model.setDevSerial(serial);
	}
	
	public Sensor sensor(Sensor sensor){
		model.addSensor(sensor);
		return sensor;
	}

}
