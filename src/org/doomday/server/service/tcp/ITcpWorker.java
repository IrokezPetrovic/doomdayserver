package org.doomday.server.service.tcp;

import org.doomday.server.beans.device.Device;

public interface ITcpWorker {

	void appendDevice(String ipAddr, Device device);

}
