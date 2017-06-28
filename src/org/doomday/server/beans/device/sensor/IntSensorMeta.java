package org.doomday.server.beans.device.sensor;

public class IntSensorMeta extends SensorMeta{	
	private Integer min;
	private Integer max;
	
	public IntSensorMeta(String name,Integer min,Integer max) {
		super(name);
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean validate(String sensorValue) {
		try{
			Integer v = Integer.parseInt(sensorValue);
			return (v>=min&&v<=max);
		} catch (NumberFormatException e){
			return false;
		}
		
		
	}
		
	
	
}
