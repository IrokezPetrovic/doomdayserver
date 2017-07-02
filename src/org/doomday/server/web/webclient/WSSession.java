package org.doomday.server.web.webclient;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.doomday.server.eventbus.rx.IDisposableStorage;
import org.doomday.server.eventbus.rx.IEventBus;
import org.doomday.server.model.IDeviceRepositoryListener;
import org.doomday.server.model.event.DeviceSensorEvent;
import org.doomday.server.model.event.DeviceStatusEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class WSSession implements IDeviceRepositoryListener{
	
	@Autowired
	IEventBus eventbus;
		
	@Autowired
	IDisposableStorage disposableStorage;
	
	public WSSession(String userId,WebSocketSession session) {
		
	}
		
	@PostConstruct
	public void init(){
		disposableStorage.add(
			eventbus.get("/device")
			.ofType(DeviceSensorEvent.class)
			.subscribe(e->{
				
			})
		);
		
		disposableStorage.add(
			eventbus.get("/device")
			.ofType(DeviceStatusEvent.class)
			.subscribe(e->{
				
			})
		);
		
	}
	
		
}
