package org.doomday.server.beans.device.trigger;

public class FloatParam extends TriggerParam {
	private Float min,max;
	public FloatParam(String name,Float min,Float max) {
		super(name);
		this.min = min;
		this.max = max;
	}
	@Override
	public boolean validate(String v) {
		try{
			Float val = Float.parseFloat(v);
			return val>=min&&val<=max;
		} catch (NumberFormatException e){
			return false;
		}
	}
	public Float getMin() {
		return min;
	}
	public Float getMax() {
		return max;
	}
	@Override
	public String getType() {
		return "float";
	}
	
	@Override
	public String toString() {
		return String.format("FLOAT %s (%f,%f)", getName(),min,max);
	}
	
	

}
