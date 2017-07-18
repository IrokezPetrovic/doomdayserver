package org.doomday.server.plugin.webclient;

import java.util.function.Function;

import org.doomday.server.plugin.webclient.websocket.SocketSession;
import org.doomday.server.plugin.webclient.websocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class WebClientConfig implements WebSocketConfigurer{
	@Autowired
	ApplicationContext ctx;
				
	
	@Bean
	Function<WebSocketSession, SocketSession> sessionSupplier(){
		return new Function<WebSocketSession, SocketSession>(){
			@Override
			public SocketSession apply(WebSocketSession t) {
				return ctx.getBean(SocketSession.class,t);
			}		
		};			
		
	}


	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(ctx.getBean(WebSocket.class), "/webclient/event.ws").setAllowedOrigins("*");
		
	}

	
	
	
}
