package tcp;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.doomday.server.beans.device.Device;
import org.doomday.server.beans.device.sensor.FloatSensorMeta;
import org.doomday.server.beans.device.sensor.IntSensorMeta;
import org.doomday.server.beans.device.sensor.ValSensorMeta;
import org.doomday.server.beans.device.trigger.IntParam;
import org.doomday.server.beans.device.trigger.TriggerMeta;
import org.doomday.server.beans.device.trigger.TriggerParam;
import org.doomday.server.eventbus.EventBus;
import org.doomday.server.eventbus.IEventBus;
import org.doomday.server.model.IDeviceRepository;
import org.doomday.server.protocol.IProtocolProcessor;
import org.doomday.server.protocol.IProtocolProcessorFactory;
import org.doomday.server.protocol.ProtocolProcessor;
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
	@Qualifier("discover.mcastgroup")
	String mcastAddr;
	
	@Autowired
	@Qualifier("discover.mcastport")
	Integer mcastPort; 
	
	@Autowired
	@Qualifier("tcp.port")
	Integer tcpPort;
	
	
	@Autowired
	IDeviceRepository deviceRepository;
	
	@Before
	public void before(){
		emulator = new Emulator("TEST1234","DOOMDAYDIY:TEST:1","1234",mcastAddr,mcastPort,tcpPort);	
		emulator.sensor(new FloatSensorMeta("TEMPERATURE", 0f, 100f));
		emulator.sensor(new IntSensorMeta("WATERLEVEL", 0, 3));
		emulator.sensor(new ValSensorMeta("MODE", new String[]{"STANDBY","HEATING","BOILING"}));
		emulator.trigger(new TriggerMeta("STANDBY", new TriggerParam[]{}));
		emulator.trigger(new TriggerMeta("HEAT", new TriggerParam[]{new IntParam("TARGET", 20, 95)}));
		emulator.trigger(new TriggerMeta("BOIL", new TriggerParam[]{}));
	}
	
	
	@Test
	public void testDiscover() throws InterruptedException{
		//TimeUnit.SECONDS.sleep(1);
		emulator.begin();
		TimeUnit.SECONDS.sleep(10);				
	}
		
	
	
	@Import(tcp.Config.class)
	public static class Config{

		@Bean
		TcpDiscoverService tcpDiscoverService(){
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
		IEventBus eventBus(){
			return new EventBus();
		}
		
		@Bean
		IDeviceRepository deviceRepository(){
			return new IDeviceRepository() {
				private Device d;
				@Override
				public Device getDevice(String devClass, String devSerial) {
					d = new Device(devClass,devSerial);
					d.setPincode("1234");
					return d;
				}

				@Override
				public Collection<Device> listDevices() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void updateDevice(Device d) {
					// TODO Auto-generated method stub
					
				}
			};
		}
		
		
		
	}
	
	
}
