package org.doomday.server.event;

import org.doomday.server.beans.device.Device;

public class DeviceUpdatedEvent {
	private final Device device;

	public DeviceUpdatedEvent(Device device) {
		super();
		this.device = device;
	}

	public Device getDevice() {
		return device;
	}
	
	
}
