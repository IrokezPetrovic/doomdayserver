package org.doomday.server.plugin.admin.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.doomday.server.beans.device.Device;
import org.doomday.server.beans.device.DeviceProfile;
import org.doomday.server.event.DeviceDiscoveredEvent;
import org.doomday.server.event.DeviceForgetEvent;
import org.doomday.server.event.DeviceProfileUpdateEvent;
import org.doomday.server.event.DeviceSensorEvent;
import org.doomday.server.event.DeviceUpdatedEvent;
import org.doomday.server.event.dashboard.DashboardRemoveEvent;
import org.doomday.server.event.dashboard.DashboardSavedEvent;
import org.doomday.server.eventbus.IEventBus;
import org.doomday.server.model.IDeviceRepository;
import org.doomday.server.model.IProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.reactivex.Observable;

@Component
public class AdminWebSocket extends TextWebSocketHandler{
	Set<WebSocketSession> sessions = new HashSet<>();
	
	@Autowired
	IEventBus eventBus;
	
	@Autowired
	IDeviceRepository deviceRepository;
	
	@Autowired
	IProfileRepository profileRepository;
	
	@Autowired
	ObjectMapper mapper;
	
	private void sendMessage(String msg){
		sessions.forEach(s->{
			try {
				s.sendMessage(new TextMessage(msg.getBytes()));				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch(IllegalStateException e){
				e.printStackTrace();
			}
		});
	}
	
	private void sendEvent(WebSocketEventMessage msg){
		try {
			String val = mapper.writeValueAsString(msg);
			sendMessage(val);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@PostConstruct
	public void init(){
		Observable<Object> devicesBus = eventBus.get("/device");
		devicesBus
		.ofType(DeviceForgetEvent.class)
		.subscribe(e->{
			WebSocketEventMessage msg = new WebSocketEventMessage("/device/removed", e.getDevice());
			sendEvent(msg);
		});

		devicesBus.ofType(DeviceUpdatedEvent.class)
		.subscribe(e->{
			Device d = e.getDevice();
			ObjectNode node = mapper.valueToTree(d);
			DeviceProfile profile = profileRepository.getProfile(d.getDevClass());
			node.set("profile", mapper.valueToTree(profile));
			WebSocketEventMessage msg = new WebSocketEventMessage("/device/updated", node);
			sendEvent(msg);
		});
		
		/*
		 *
		 *
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
		*/
		Observable<Object> dashboardBus = eventBus.get("/dashboard");
		dashboardBus.ofType(DashboardSavedEvent.class)
		.subscribe(e->{
			WebSocketEventMessage msg = new WebSocketEventMessage("/dashboard/saved", e.getDashboard());
			sendEvent(msg);
		});
		
		dashboardBus.ofType(DashboardRemoveEvent.class)
		.subscribe(e->{
			WebSocketEventMessage msg = new WebSocketEventMessage("/dashboard/removed", e.getDashboard());
			sendEvent(msg);
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
