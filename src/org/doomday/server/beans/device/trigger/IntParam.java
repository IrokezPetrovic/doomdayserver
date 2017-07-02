package org.doomday.server.beans.device.trigger;

public class IntParam extends TriggerParam{
	private Integer min,max;
	
	public IntParam(String name,Integer min,Integer max) {
		super(name);
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean validate(String v) {
		try{
			Integer val = Integer.parseInt(v);
			return val>=min&&val<=max;
		} catch (NumberFormatException e){
			return false;
		}
		
	}

	public Integer getMin() {
		return min;
	}

	public Integer getMax() {
		return max;
	}

	@Override
	public String getType() {
		return "int";
	}
	
	@Override
	public String toString() {
		return String.format("INT %s (%d,%d)", getName(),min,max);
	}
	
	

}
