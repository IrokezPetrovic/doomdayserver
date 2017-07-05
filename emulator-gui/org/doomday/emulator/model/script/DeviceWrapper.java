package org.doomday.emulator.model.script;

import org.doomday.emulator.model.DeviceModel;

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

}
