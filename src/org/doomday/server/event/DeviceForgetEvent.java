package org.doomday.server.event;

import org.doomday.server.beans.device.Device;

public class DeviceForgetEvent {
	public final Device device;

	public DeviceForgetEvent(Device device) {
		super();
		this.device = device;
	}
	
	public Device getDevice() {
		return device;
	}
	
}
