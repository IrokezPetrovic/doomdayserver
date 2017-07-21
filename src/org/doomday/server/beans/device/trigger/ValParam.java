package org.doomday.server.beans.device.trigger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ValParam extends TriggerParam {
	
	private List<String> values;
	public ValParam(String name,String[] values) {
		super(name);
		this.values = Arrays.asList(values);
	}
	@Override
	public boolean validate(String v) {
		return values.contains(v);
	}
	
	public List<String> getOptions() {
		return Collections.unmodifiableList(values);
	}
	@Override
	public String getType() {
		return "val";
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("VAL ");
		sb.append(getName())
		.append(" (");
		Stream.of(values).forEach(sb::append);
		sb.append(")");
		return sb.toString();
	}

}
