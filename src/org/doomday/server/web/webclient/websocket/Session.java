package org.doomday.server.web.webclient.websocket;

import javax.annotation.PostConstruct;

import org.doomday.server.eventbus.rx.IDisposableStorage;
import org.doomday.server.eventbus.rx.IEventBus;
import org.doomday.server.model.IDeviceRepositoryListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@Scope("prototype")
public class Session implements IDeviceRepositoryListener{
	
	@Autowired
	IEventBus eventbus;
		
	@Autowired
	IDisposableStorage disposableStorage;
	
	public Session(WebSocketSession session) {
		
	}
		
	@PostConstruct
	public void init(){
		
		
	}
	
		
}
