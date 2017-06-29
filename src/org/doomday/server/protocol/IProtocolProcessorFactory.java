package org.doomday.server.protocol;

import org.doomday.server.beans.device.Device;

public interface IProtocolProcessorFactory {

	IProtocolProcessor createProcessor(Device device);

}
