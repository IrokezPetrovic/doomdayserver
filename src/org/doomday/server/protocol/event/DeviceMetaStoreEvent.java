package org.doomday.server.protocol.event;

import org.doomday.server.beans.device.DeviceMeta;

public class DeviceMetaStoreEvent {
	private final DeviceMeta meta;
	private final String deviceId;
	

	public DeviceMetaStoreEvent(DeviceMeta meta, String deviceId) {
		super();
		this.meta = meta;
		this.deviceId = deviceId;
	}


	public DeviceMeta getMeta() {
		return meta;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	
}
