package org.doomday.server.web.webclient.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ApiWebSocket extends TextWebSocketHandler{
	Map<WebSocketSession, Session> sessions = new HashMap<>();
	
		
	@Autowired
	Function<Session, WebSocketSession> sessionProducer;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {		
		super.afterConnectionEstablished(session);	
		
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
