package org.doomday.emulator.model.trigger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValArg extends TriggerArg{
	
	private final String def;
	private Set<String> options;
	public ValArg(String name,String[] options) {
		super();
		this.options = new HashSet<>(Arrays.asList(options));		
		this.def = String.format("VAL %s (%s)", name, String.join(",", options));		
	}

	@Override
	public String toString() {
		return def;
	}


	@Override
	public boolean validate(String strValue) {
		return strValue!=null&&!strValue.isEmpty()&&options.contains(strValue);
	}

}
