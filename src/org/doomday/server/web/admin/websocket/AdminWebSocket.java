package org.doomday.server.web.admin.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.doomday.server.eventbus.rx.IEventBus;
import org.doomday.server.model.event.DeviceDiscoveredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AdminWebSocket extends TextWebSocketHandler{
	Set<WebSocketSession> sessions = new HashSet<>();
	
	@Autowired
	IEventBus eventBus;
	@Autowired
	ObjectMapper mapper;
	
	
	@PostConstruct
	public void init(){
		eventBus.get("/device")
		.ofType(DeviceDiscoveredEvent.class)		
		.subscribe(e->{
			WebSocketEventMessage msg = new WebSocketEventMessage("device", "discover",e.getDevice());		
			String stringMsg = mapper.writeValueAsString(msg);
			sessions.forEach(s->{
				try {
					s.sendMessage(new TextMessage(stringMsg.getBytes()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		});
				
	}
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
		sessions.add(session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
		sessions.remove(session);
	}
}
