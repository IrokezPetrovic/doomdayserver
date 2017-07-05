package org.doomday.emulator.model;

public class DeviceModel {

	private String devClass;
	private String devSerial;

	

	public String getDevSerial() {
		return this.devSerial;
	}

	public void process(String s) {
		// TODO Auto-generated method stub
		
	}

	public String getWritrable() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasWritable() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setDevClass(String s) {
		this.devClass = s;		
	}
	public String getDevClass() {
		return devClass;
	}

	public void setDevSerial(String serial) {
		this.devSerial = serial;
		
	}

}
