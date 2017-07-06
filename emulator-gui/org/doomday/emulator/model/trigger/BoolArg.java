package org.doomday.emulator.model.trigger;

public class BoolArg extends TriggerArg{
	
	private final String def;
	public BoolArg(String name) {
		super();			
		this.def = String.format("BOOL %s", name);
	}

	@Override
	public String toString() {
		return def;
	}


	@Override
	public boolean validate(String strValue) {
		return strValue!=null&&(strValue.equalsIgnoreCase("TRUE")||strValue.equalsIgnoreCase("FALSE"));
	}

}
