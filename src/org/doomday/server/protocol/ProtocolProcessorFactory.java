package org.doomday.server.protocol;

import java.util.function.Function;

import org.doomday.server.beans.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProtocolProcessorFactory implements IProtocolProcessorFactory{

	@Autowired
	private Function<Device, IProtocolProcessor> protocolProcessorSupplier;
	
	@Override
	public IProtocolProcessor createProcessor(Device device) {
		return protocolProcessorSupplier.apply(device);
	}

}
