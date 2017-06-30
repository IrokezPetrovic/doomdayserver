package org.doomday.server.beans.device.trigger;

public class StrParam extends TriggerParam {

	public StrParam(String name) {
		super(name);
	}

	@Override
	public boolean validate(String v) {
		return true;
	}

	@Override
	public String getType() {
		return "str";
	}

}
