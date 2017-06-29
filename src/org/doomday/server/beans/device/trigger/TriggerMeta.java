package org.doomday.server.beans.device.trigger;

public class TriggerMeta {
	private TriggerParam[] params;
	private String name;
	
	public TriggerMeta(String name, TriggerParam[] params) {
		this.name = name;
		this.params = params;
	}
	
	public String getName() {
		return name;
	}

	public boolean validate(String[] params) {
		if (params.length!=this.params.length)
			return false;
		
		for (int i=0;i<params.length;i++){
			if (!this.params[i].validate(params[i]))
				return false;
		}
		return true;
	}

	public TriggerParam[] getParams() {
		return params;
	}

}
