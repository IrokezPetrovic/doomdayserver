package org.doomday.server.plugin.admin.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.doomday.server.beans.device.Device;
import org.doomday.server.event.DeviceDiscoveredEvent;
import org.doomday.server.event.DeviceProfileUpdateEvent;
import org.doomday.server.event.DeviceSensorEvent;
import org.doomday.server.event.dashboard.DashboardRemoveEvent;
import org.doomday.server.event.dashboard.DashboardSavedEvent;
import org.doomday.server.eventbus.IEventBus;
import org.doomday.server.model.IDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.Observable;

@Component
public class AdminWebSocket extends TextWebSocketHandler{
	Set<WebSocketSession> sessions = new HashSet<>();
	
	@Autowired
	IEventBus eventBus;
	
	@Autowired
	IDeviceRepository deviceRepository;
	
	@Autowired
	ObjectMapper mapper;
	
	private void sendMessage(String msg){
		sessions.forEach(s->{
			try {
				s.sendMessage(new TextMessage(msg.getBytes()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	@PostConstruct
	public void init(){
		Observable<Object> bus = eventBus.get("/device");
		
		bus.ofType(DeviceDiscoveredEvent.class)		
		.subscribe(e->{
			WebSocketEventMessage msg = new WebSocketEventMessage("/device/discovered",e.getDevice());					
			sendMessage(mapper.writeValueAsString(msg));
		});
		
		bus.ofType(DeviceSensorEvent.class)
		.subscribe(e->{
			WebSocketEventMessage msg = new WebSocketEventMessage("/device/sensor/value", e);
			sendMessage(mapper.writeValueAsString(msg));
		});
		
		eventBus.get("/device")
		.ofType(DeviceProfileUpdateEvent.class)
		.subscribe(e->{			
			Device d = deviceRepository.getDevice(e.getDeviceId());
			WebSocketEventMessage msg = new WebSocketEventMessage("/device/profile/updated", d);
			sendMessage(mapper.writeValueAsString(msg));
		});
		
		Observable<Object> dashboardBus = eventBus.get("/dashboard");
		dashboardBus.ofType(DashboardSavedEvent.class)
		.subscribe(e->{
			WebSocketEventMessage msg = new WebSocketEventMessage("/dashboard/saved", e.getDashboard());
			sendMessage(mapper.writeValueAsString(msg));
		});
		
		dashboardBus.ofType(DashboardRemoveEvent.class)
		.subscribe(e->{
			WebSocketEventMessage msg = new WebSocketEventMessage("/dashboard/removed", e.getDashboard());
			sendMessage(mapper.writeValueAsString(msg));
		});
				
						
	}
	
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("AdminWebSocket connected");				
		sessions.add(session);		
		super.afterConnectionEstablished(session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub		
		sessions.remove(session);
		super.afterConnectionClosed(session, status);
	}
}
