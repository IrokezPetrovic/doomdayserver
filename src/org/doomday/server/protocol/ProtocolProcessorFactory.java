package org.doomday.server.protocol;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.doomday.server.ITransport;
import org.doomday.server.beans.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProtocolProcessorFactory implements IProtocolProcessorFactory{

	@Autowired
	private BiFunction<Device,ITransport, IProtocolProcessor> protocolProcessorSupplier;
	
	@Override
	public IProtocolProcessor createProcessor(Device device,ITransport transport) {
		return protocolProcessorSupplier.apply(device,transport);
	}

}
