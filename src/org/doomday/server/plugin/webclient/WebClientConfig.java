package org.doomday.server.plugin.webclient;

import java.util.function.Function;

import org.doomday.server.beans.Widget;
import org.doomday.server.plugin.webclient.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.socket.WebSocketSession;

@Configuration
public class WebClientConfig {
	@Autowired
	ApplicationContext ctx;
				
	
	@Bean
	@Scope("prototype")
	Function<WebSocketSession, Session> wsSessionProducer(){
		return new Function<WebSocketSession, Session>() {
			
			@Override
			public Session apply(WebSocketSession wsSession) {
				return ctx.getBean(Session.class,wsSession);
			}
		};
	}
	
	
	
}
