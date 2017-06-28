package org.doomday.server.beans.device.trigger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ValParam extends TriggerParam {
	
	private Set<String> values;
	public ValParam(String name,String[] values) {
		super(name);
		this.values = new HashSet<>(Arrays.asList(values));
	}
	@Override
	public boolean validate(String v) {
		return values.contains(v);
	}

}
