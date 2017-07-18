package org.doomday.server.plugin.webclient.websocket;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.doomday.server.event.SensorEvent;
import org.doomday.server.eventbus.IEventBus;
import org.doomday.server.plugin.admin.websocket.WebSocketEventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.Observable;

@Component
@Scope("prototype")
public class SocketSession {
	private final WebSocketSession session;

	public SocketSession(WebSocketSession session) {
		super();
		this.session = session;
	}

	@Autowired
	IEventBus eventBus;

	@Autowired
	ObjectMapper mapper;

	private void sendMessage(String msg) {

		try {
			session.sendMessage(new TextMessage(msg.getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendEvent(WebSocketEventMessage msg) {
		try {
			String val = mapper.writeValueAsString(msg);
			sendMessage(val);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@PostConstruct
	public void init() {
		Observable<Object> sensorBus = eventBus.get("/sensor");
		sensorBus.ofType(SensorEvent.class).subscribe(e -> {
			sendEvent(new WebSocketEventMessage("/device/sensor/value", e));
		});

	}

}
