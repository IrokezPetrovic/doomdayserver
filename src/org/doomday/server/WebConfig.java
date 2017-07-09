package org.doomday.server;

import org.doomday.server.plugin.webclient.websocket.WebClientWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@ComponentScan("org.doomday.server")
@EnableWebSocket
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{
	@Autowired
	ApplicationContext ctx;
	
	@Bean
	@Qualifier("discover.mcastgroup")
	public String macastGroup(){
//		return "239.12.13.14";
		return "239.141.12.12";
	}
	
	@Bean
	@Qualifier("discover.mcastport")
	public Integer mcastPort(){
		return 27015;
	}
	
	@Bean
	@Qualifier("tcp.port")
	public Integer tcpPort(){
		return 27015;
	}

	@Bean
	@Scope("prototype")
	public ObjectMapper jsonMapper(){		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		mapper.configure(Feature.IGNORE_UNKNOWN, true);
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		return mapper;
	}
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		registry.addHandler(ctx.getBean(WebClientWebSocket.class), "/webclient/event.ws");
	}
	
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver multipartResolver(){
		return new CommonsMultipartResolver();
	}
	
}
