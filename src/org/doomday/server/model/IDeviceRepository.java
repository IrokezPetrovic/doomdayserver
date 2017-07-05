package org.doomday.server.model;

import java.util.Collection;

import org.doomday.server.beans.device.Device;

public interface IDeviceRepository {
	
	Device getDevice(String devClass, String devSerial);
	Device getDevice(String deviceId);

	Collection<Device> listDevices();

	void updateDevice(Device d);
	void forget(Device d);

}
