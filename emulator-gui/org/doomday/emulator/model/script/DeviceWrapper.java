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
	
	public void sensor(SensorWrapper sensor){
		model.addSensor(sensor.getSensor());		
	}
	
	public void trigger(TriggerWrapper t){
		model.addTrigger(t.getTrigger());
	}
	
	public String getPin(){
		return model.getPincode();
	}
	public void setPin(String pin){
		model.setPincode(pin);
	}

}
