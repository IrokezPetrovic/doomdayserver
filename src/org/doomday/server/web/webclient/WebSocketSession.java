package org.doomday.server.web.webclient;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.doomday.server.model.IAclResolver;
import org.doomday.server.model.IDeviceRepository;
import org.doomday.server.model.IDeviceRepositoryListener;
import org.springframework.beans.factory.annotation.Autowired;

public class WebSocketSession implements IDeviceRepositoryListener{
	private Set<String> devicesAvailable = new HashSet<>();
	
	@Autowired
	IDeviceRepository deviceRepository;
		
	@Autowired
	IAclResolver aclResolver;
	
	public WebSocketSession(String userId) {
		
	}
		
	@PostConstruct
	public void init(){
		devicesAvailable.addAll(aclResolver.getDevices());
	}
	
	@PreDestroy
	public void destroy(){
		
	}
	
}
