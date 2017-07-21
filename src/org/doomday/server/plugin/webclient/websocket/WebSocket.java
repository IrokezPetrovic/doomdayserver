package org.doomday.server.plugin.webclient.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Component("webclient-event-websocket")
public class WebSocket extends TextWebSocketHandler{
	
	Map<WebSocketSession, SocketSession> sessions = new HashMap<>();
	
	@Autowired
	Function<WebSocketSession, SocketSession> sessionSupplier;
	
	
	@PostConstruct
	public void init(){
		
	}
			
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		String msg = message.getPayload();
		System.out.println("Process msg "+msg);
		if (sessions.containsKey(session)){
			sessions.get(session).processMessage(msg);
		}		
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {		
		sessions.put(session, sessionSupplier.apply(session));
		super.afterConnectionEstablished(session);
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
		super.afterConnectionClosed(session, status);
	}
	 
}
