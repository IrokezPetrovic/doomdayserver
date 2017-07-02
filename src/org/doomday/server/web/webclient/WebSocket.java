package org.doomday.server.web.webclient;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocket extends TextWebSocketHandler{
	Map<WebSocketSession, WSSession> sessions = new HashMap<>();
	
	@Autowired
	Function<WebSocketSession, WSSession> wsSupplier;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {		
		super.afterConnectionEstablished(session);
		sessions.put(session, wsSupplier.apply(session));		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub		
		super.afterConnectionClosed(session, status);		
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
		
	}
		
}
