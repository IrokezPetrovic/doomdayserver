package org.doomday.emulator.model.trigger;

public class StrArg extends TriggerArg{
	
	private final String def;
	public StrArg(String name) {
		super();			
		this.def = String.format("STR %s", name);
	}

	@Override
	public String toString() {
		return def;
	}


	@Override
	public boolean validate(String strValue) {
		return strValue!=null&&!strValue.isEmpty();
	}

}
