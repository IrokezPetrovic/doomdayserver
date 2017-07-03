package org.doomday.server.protocol;

import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.doomday.server.beans.device.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {
	@Autowired
	ApplicationContext ctx;
	
	
	@Bean
	@Scope("prototype")
	IProtocolProcessor protocolProtocolProcessor(Device d){
		return new ProtocolProcessor(d);
	}
	
	@PostConstruct
	public void init(){
		System.out.println("CfgInit()");
	}
	@Bean
	Function<Device, IProtocolProcessor> protocolProcessorSupplier(){
		return new Function<Device, IProtocolProcessor>() {
			@Override
			public IProtocolProcessor apply(Device d) {
				return ctx.getBean(IProtocolProcessor.class,d);
			}
		};
	}

}
