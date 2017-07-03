package org.doomday.server.model.event;

import org.doomday.server.beans.device.Device;

public class DeviceDiscoveredEvent {
	private final Device device;
	
	public DeviceDiscoveredEvent(Device device) {
		super();
		this.device = device;
	}


	public Device getDevice() {
		return device;
	}
	
	
	
	
}
