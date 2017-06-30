package org.doomday.server.model;

import java.util.Collection;

import org.doomday.server.beans.device.Device;

public interface IDeviceRepository {
	
	Device getDevice(String devClass, String devSerial);

	Collection<Device> listDevices();

	void updateDevice(Device d);

}
