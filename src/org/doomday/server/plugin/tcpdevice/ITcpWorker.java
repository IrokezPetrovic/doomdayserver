package org.doomday.server.plugin.tcpdevice;

import org.doomday.server.beans.device.Device;

public interface ITcpWorker {

	void appendDevice(String ipAddr, Device device);

}
