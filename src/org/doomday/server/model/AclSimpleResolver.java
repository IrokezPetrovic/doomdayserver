package org.doomday.server.model;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

public class AclSimpleResolver implements IAclResolver{

	@Autowired
	IDeviceRepository deviceRepository;
	
	@Override
	public Set<String> getDevices() {
		return deviceRepository.listDevices().stream().map((d)->d.getDevClass()+":"+d.getDevSerial()).collect(Collectors.toSet());
	}
	
}
