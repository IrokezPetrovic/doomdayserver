package org.doomday.server;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

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

		
	
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
//		registry.addHandler(ctx.getBean(WebClientWebSocket.class), "/webclient/event.ws");
	}
	
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver multipartResolver(){
		return new CommonsMultipartResolver();
	}
	
	@SuppressWarnings("rawtypes")
	@Autowired
	List<Converter> convList;
	
	@Bean
    ConversionServiceFactoryBean conversionService() {
		System.out.println("Converters:"+convList.size());
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();        
        @SuppressWarnings("rawtypes")
		Set<Converter> converters = new HashSet<>();               
        convList.forEach(converters::add);
        bean.setConverters(converters);        
        System.out.println("REgister converters");
        return bean;
    }
	
}
