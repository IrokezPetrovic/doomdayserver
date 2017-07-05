package org.doomday.server.beans.device.sensor;

public class FloatSensorMeta extends SensorMeta{

	private Float min;
	private Float max;
	
	public FloatSensorMeta(String name, Float min, Float max) {
		super(name);
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean validate(String sensorValue) {
		try{
			Float v = Float.parseFloat(sensorValue);
			return (v>=min&&v<=max);
		} catch(NumberFormatException e){
			return false;
		}
	}

	public Float getMax() {
		return max;
	}
	
	public Float getMin() {
		return min;
	}

	@Override
	public String getType() {
		return "float";
	}

	@Override
	public String getDef() {
		return String.format("FLOAT (%f,%f)", min,max);
	}

}
