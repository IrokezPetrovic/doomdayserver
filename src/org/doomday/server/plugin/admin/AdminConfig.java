package org.doomday.server.plugin.admin;

import javax.annotation.PostConstruct;

import org.doomday.server.beans.Widget;
import org.doomday.server.plugin.admin.websocket.AdminWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
public class AdminConfig implements WebSocketConfigurer,FormatterRegistrar{
	@Autowired
	ApplicationContext ctx;

	@PostConstruct
	public void init(){
		DefaultConversionService srvConverters = (DefaultConversionService) ctx.getBean("conversionService");
		srvConverters.addConverter(String.class, Widget.class, new Converter<String, Widget>() {

			@Override
			public Widget convert(String source) {
				return new Widget("", "", "", "");
			}
		});
	}
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		System.out.println("WS COnfig");
		registry.addHandler(ctx.getBean(AdminWebSocket.class), "/admin/event.ws").setAllowedOrigins("*");		
	}

	@Override
	public void registerFormatters(FormatterRegistry registry) {
		System.out.println("REgister converter");
		registry.addConverter(new Converter<String, Widget>() {

			@Override
			public Widget convert(String source) {
				return new Widget("", "", "", "");
			}
		});
		
		registry.addConverter(String.class, Widget.class, new Converter<String, Widget>() {

			@Override
			public Widget convert(String source) {
				return new Widget("", "", "", "");
			}
		});
		
	}

}
