package org.doomday.server.beans.device.trigger;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FlagParam extends TriggerParam {
	private Set<String> flags;
	public FlagParam(String name,String[] flags) {
		super(name);
		this.flags = new HashSet<>(Arrays.asList(flags));
	}
	@Override
	public boolean validate(String v) {
		String[] targetFlags = v.split(",");
		return flags.containsAll(Arrays.asList(targetFlags));
		
	}
	public Set<String> getFlags() {
		return Collections.unmodifiableSet(flags);
	}

}
