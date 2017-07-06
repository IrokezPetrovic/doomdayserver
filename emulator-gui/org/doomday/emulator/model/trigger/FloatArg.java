package org.doomday.emulator.model.trigger;

public class FloatArg extends TriggerArg{

	private final Float max,min;
	private final String def;
	public FloatArg(String name,Float min, Float max) {
		super();
		this.min = min;
		this.max = max;		
		this.def = String.format("FLOAT %s (%f,%f)", name,min,max);
	}

	@Override
	public String toString() {
		return def;
	}


	@Override
	public boolean validate(String strValue) {
		try{
			Float v = Float.valueOf(strValue);
			return v>=min&&v<=max;
		} catch (NumberFormatException e){
			return false;
		}
	}

}
