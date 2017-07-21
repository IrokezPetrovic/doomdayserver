package org.doomday.server.protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.doomday.server.ITransport;
import org.doomday.server.beans.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProtocolProcessorFactory implements IProtocolProcessorFactory{
	Map<String,IProtocolProcessor> processors = new HashMap<>();
	@Autowired
	private BiFunction<Device,ITransport, IProtocolProcessor> protocolProcessorSupplier;
	
	@Override
	public IProtocolProcessor createProcessor(Device device,ITransport transport) {
		IProtocolProcessor protocolProcessor = protocolProcessorSupplier.apply(device,transport);
		processors.put(device.getId(), protocolProcessor);
		return protocolProcessor;
	}

	@Override
	public IProtocolProcessor getExistsProcessor(String deviceId) {
		return processors.get(deviceId);
	}
	
	

}
