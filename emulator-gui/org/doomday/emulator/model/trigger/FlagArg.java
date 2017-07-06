package org.doomday.emulator.model.trigger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class FlagArg extends TriggerArg{
	
	private final String def;
	private Set<String> flags;
	public FlagArg(String name,String[] flags) {
		super();
		this.flags = new HashSet<>(Arrays.asList(flags));		
		this.def = String.format("FLAG %s (%s)", name, String.join(",", flags));		
	}

	@Override
	public String toString() {
		return def;
	}


	@Override
	public boolean validate(String strValue) {
		if (strValue==null||strValue.isEmpty())
			return false;
		return Stream.of(strValue.split(","))
		.allMatch(flags::contains);
	}

}
