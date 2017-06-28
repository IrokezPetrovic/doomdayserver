package org.doomday.server.protocol;

import org.doomday.server.beans.device.Device;

public interface IProtocolProcessorFactory {

	ProtocolProcessor createProcessor(Device device);

}
