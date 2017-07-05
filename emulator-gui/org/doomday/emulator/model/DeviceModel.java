package org.doomday.emulator.model;

import java.util.HashMap;
import java.util.Map;

public class DeviceModel {

	private String devClass;
	private String devSerial;
	private Map<String, Sensor> sensors = new HashMap<String, Sensor>();

	

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

	public void addSensor(Sensor sensor) {
		this.sensors.put(sensor.getName(),sensor);
		
	}

}
