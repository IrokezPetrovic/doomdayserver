package org.doomday.server.beans.device.trigger;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

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
	@Override
	public String getType() {
		return "flag";
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("FLAG ");
		sb.append(getName())
		.append(" (");
		Stream.of(flags)
		.forEach(sb::append);
		sb.append(")");
		return sb.toString();
		
	}

}
