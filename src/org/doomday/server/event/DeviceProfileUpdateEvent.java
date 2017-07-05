package org.doomday.server.event;

import org.doomday.server.beans.device.DeviceProfile;

public class DeviceProfileUpdateEvent {
	private final DeviceProfile meta;
	private final String deviceId;
	

	public DeviceProfileUpdateEvent(DeviceProfile meta, String deviceId) {
		super();
		this.meta = meta;
		this.deviceId = deviceId;
	}


	public DeviceProfile getProfile() {
		return meta;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	
}
