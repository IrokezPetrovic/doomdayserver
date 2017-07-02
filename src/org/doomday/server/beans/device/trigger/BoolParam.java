package org.doomday.server.beans.device.trigger;

public class BoolParam extends TriggerParam {

	public BoolParam(String name) {
		super(name);
	}

	@Override
	public boolean validate(String v) {
		return v.equalsIgnoreCase("TRUE")||v.equalsIgnoreCase("FALSE");
	}

	@Override
	public String getType() {
		return "bool";
	}
	
	@Override
	public String toString() {
		return "BOOL "+getName();
	}

}
