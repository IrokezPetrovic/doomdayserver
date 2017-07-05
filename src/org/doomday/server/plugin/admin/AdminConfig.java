package org.doomday.server.plugin.admin;

import org.doomday.server.plugin.admin.websocket.AdminWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class AdminConfig implements WebSocketConfigurer{
	@Autowired
	ApplicationContext ctx;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("WS COnfig");
		registry.addHandler(ctx.getBean(AdminWebSocket.class), "/admin/event.ws").setAllowedOrigins("*");		
	}

}
