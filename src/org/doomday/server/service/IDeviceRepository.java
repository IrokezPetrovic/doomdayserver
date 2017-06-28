package org.doomday.server.service;

import org.doomday.server.beans.device.Device;

public interface IDeviceRepository {
	Device getDevice(String devClass, String devSerial);

}
