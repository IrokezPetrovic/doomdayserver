package org.doomday.server.protocol;

import java.util.Queue;

import org.doomday.server.beans.device.Device;

public interface IProtocolProcessor {

	Device getDevice();

	Queue<String> getWriteQueue();
	
	public void read(String content);

	void send(String string);

}
