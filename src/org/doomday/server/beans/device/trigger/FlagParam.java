package org.doomday.server.beans.device.trigger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FlagParam extends TriggerParam {
	private List<String> flags;
	public FlagParam(String name,String[] flags) {
		super(name);
		this.flags = Arrays.asList(flags);
	}
	@Override
	public boolean validate(String v) {
		String[] targetFlags = v.split(",");
		return flags.containsAll(Arrays.asList(targetFlags));
		
	}
	public List<String> getFlags() {
		return Collections.unmodifiableList(flags);
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
