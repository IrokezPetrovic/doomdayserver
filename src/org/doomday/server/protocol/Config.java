package org.doomday.server.protocol;

import java.util.function.BiFunction;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.doomday.server.ITransport;
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
	IProtocolProcessor protocolProtocolProcessor(Device d,ITransport transport){
		return new ProtocolProcessor(d,transport);
	}
	
	@PostConstruct
	public void init(){
		System.out.println("CfgInit()");
	}
	@Bean
	Function<Device, IProtocolProcessor> _protocolProcessorSupplier(){		
		return new Function<Device, IProtocolProcessor>() {
			@Override
			public IProtocolProcessor apply(Device d) {
				return ctx.getBean(IProtocolProcessor.class,d);
			}
		};
	}
	
	@Bean
	BiFunction<Device, ITransport, IProtocolProcessor> protocolProcessorSupplier(){
		return new BiFunction<Device,ITransport, IProtocolProcessor>() {

			@Override
			public IProtocolProcessor apply(Device t, ITransport u) {				
				return ctx.getBean(IProtocolProcessor.class,t,u);
			}
			
		};
	}

}
