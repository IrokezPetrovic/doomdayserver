package org.doomday.server.plugin.admin;

import java.io.IOException;

import org.doomday.server.beans.User;
import org.doomday.server.beans.Widget;
import org.doomday.server.plugin.admin.websocket.AdminWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AdminConfig implements WebSocketConfigurer{
	@Autowired
	ApplicationContext ctx;

	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("WS COnfig");
		registry.addHandler(ctx.getBean(AdminWebSocket.class), "/admin/event.ws").setAllowedOrigins("*");		
	}

	@Autowired
	ObjectMapper mapper;
	
	@Bean
	Converter<String,Widget> stringToWidgetConverter(){
		return new Converter<String, Widget>() {

			@Override
			public Widget convert(String source) {
				try {
					return mapper.readValue(source, Widget.class);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		};
		 
	}
	
//	@Bean
	Converter<String, User> jsonToUserConverer(){
		return source -> {
			try {
				return mapper.readValue(source, User.class);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		};
	}
	
	
	
	

}
