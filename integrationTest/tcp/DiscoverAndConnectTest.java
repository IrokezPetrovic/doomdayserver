package tcp;

import java.util.concurrent.TimeUnit;

import org.doomday.server.beans.device.Device;
import org.doomday.server.eventbus.EventBus;
import org.doomday.server.eventbus.IDeviceEventBus;
import org.doomday.server.model.IDeviceRepository;
import org.doomday.server.protocol.IProtocolProcessor;
import org.doomday.server.protocol.IProtocolProcessorFactory;
import org.doomday.server.protocol.ProtocolProcessor;
import org.doomday.server.service.DiscoveryWorker;
import org.doomday.server.service.IDiscoverService;
import org.doomday.server.service.tcp.ITcpWorker;
import org.doomday.server.service.tcp.TcpDiscoverService;
import org.doomday.server.service.tcp.TcpWorker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import emulator.Emulator;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={DiscoverAndConnectTest.Config.class})
public class DiscoverAndConnectTest {	
	public Emulator emulator;
	
	@Autowired
	DiscoveryWorker discoveryWorker;
	
	@Autowired
	@Qualifier("discover.mcastgroup")
	String mcastAddr;
	
	@Autowired
	@Qualifier("discover.mcastport")
	Integer mcastPort; 
	
	@Autowired
	@Qualifier("tcp.port")
	Integer tcpPort;
	
	@Before
	public void before(){
		emulator = new Emulator("TEST1234","DOOMDAYDIY:TEST:1",mcastAddr,mcastPort,tcpPort);		
	}
	
	
	@Test
	public void testDiscover() throws InterruptedException{
		TimeUnit.SECONDS.sleep(1);
		emulator.begin();
		TimeUnit.SECONDS.sleep(3);
		
	}
		
	
	
	@Import(tcp.Config.class)
	public static class Config{
		@Bean
		DiscoveryWorker discoveryWorker(){
			return new DiscoveryWorker();
		}
		
		@Bean
		IDiscoverService tcpDiscoverService(){
			return new TcpDiscoverService();
		}
		
		@Bean
		ITcpWorker tcpWorker(){
			return new TcpWorker();
		}
		
		@Autowired
		ApplicationContext ctx;
		
		@Bean
		@Scope("prototype")
		IProtocolProcessor protocolProcessor(Device device){			
			return new ProtocolProcessor(device);
		}
				
		@Bean
		IProtocolProcessorFactory protocolProcessorFactory(){			
			return new IProtocolProcessorFactory() {				
				@Override
				public IProtocolProcessor createProcessor(Device device) {	
					return ctx.getBean(IProtocolProcessor.class, device);
					
				}
			};
			
		}
		
		@Bean
		IDeviceRepository deviceRepository(){
			return new IDeviceRepository() {				
				@Override
				public Device getDevice(String devClass, String devSerial) {
					Device d = new Device(devClass,devSerial);
					d.setPincode("1234");
					return d;
				}
			};
		}
		
		
		@Bean
		IDeviceEventBus eventBus(){
			return new EventBus();
		}
	}
	
	
}
