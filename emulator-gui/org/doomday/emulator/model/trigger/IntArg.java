package org.doomday.emulator.model.trigger;

public class IntArg extends TriggerArg{

	private final Integer max,min;
	private final String def;
	public IntArg(String name,Integer min, Integer max) {
		super();
		this.min = min;
		this.max = max;		
		this.def = String.format("INT %s (%d,%d)", name,min,max);
	}

	@Override
	public String toString() {
		return def;
	}


	@Override
	public boolean validate(String strValue) {
		try{
			Integer v = Integer.valueOf(strValue);
			return v>=min&&v<=max;
		} catch (NumberFormatException e){
			return false;
		}
	}

}
