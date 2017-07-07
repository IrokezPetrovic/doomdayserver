package org.doomday.server.protocol;
import org.junit.*;
import static org.junit.Assert.*;

import org.doomday.server.ITransport;
import org.doomday.server.beans.device.Device;
import org.doomday.server.beans.device.DeviceProfile;
import org.doomday.server.beans.device.trigger.BoolParam;
import org.doomday.server.beans.device.trigger.FlagParam;
import org.doomday.server.beans.device.trigger.FloatParam;
import org.doomday.server.beans.device.trigger.IntParam;
import org.doomday.server.beans.device.trigger.TriggerMeta;
import org.doomday.server.beans.device.trigger.TriggerParam;
import org.doomday.server.beans.device.trigger.ValParam;

public class TestProtocolParser_TriggerCalls {
	Device device = new Device("","");
	ProtocolProcessor pp = new ProtocolProcessor(device,new ITransport() {

		@Override
		public void disconnect(IProtocolProcessor protocolProcessor) {
			// TODO Auto-generated method stub
			
		}
	});
	
	@Before
	public void before(){
		DeviceProfile meta = new DeviceProfile();
		device.setProfile(meta);				
	}
	
	@Test
	public void test_NoArg(){
		TriggerMeta tm = new TriggerMeta("TRIG1", new TriggerParam[]{});
		assertTrue(tm.validate(new String[]{}));
		assertFalse(tm.validate(new String[]{"10"}));
	}
	
	@Test
	public void test_IntArg(){
		TriggerMeta tm = new TriggerMeta("TRIG1", new TriggerParam[]{new IntParam("", 10, 20)});
		assertFalse(tm.validate(new String[]{}));
		assertFalse(tm.validate(new String[]{"1.14"}));
		assertFalse(tm.validate(new String[]{"ASD"}));
		assertFalse(tm.validate(new String[]{""}));
		assertFalse(tm.validate(new String[]{"9"}));
		assertFalse(tm.validate(new String[]{"21"}));
		assertTrue(tm.validate(new String[]{"15"}));
		assertTrue(tm.validate(new String[]{"10"}));
		assertTrue(tm.validate(new String[]{"20"}));
	}
	
	@Test
	public void test_FloatArg(){
		TriggerMeta tm = new TriggerMeta("TRIG1", new TriggerParam[]{new FloatParam("", new Float(3.14), new Float(5.15))});
		assertFalse(tm.validate(new String[]{}));		
		assertFalse(tm.validate(new String[]{"ASD"}));
		assertFalse(tm.validate(new String[]{""}));
		assertFalse(tm.validate(new String[]{"3.1399"}));
		assertFalse(tm.validate(new String[]{"5.151"}));
		assertTrue(tm.validate(new String[]{"4.14"}));
		assertTrue(tm.validate(new String[]{"3.14"}));
		assertTrue(tm.validate(new String[]{"5.15"}));
	}
	
	
	@Test
	public void test_BoolArg(){
		TriggerMeta tm = new TriggerMeta("TRIG1", new TriggerParam[]{new BoolParam("")});
		assertFalse(tm.validate(new String[]{}));		
		assertFalse(tm.validate(new String[]{"ASD"}));		
		assertFalse(tm.validate(new String[]{"3.1399"}));
		assertFalse(tm.validate(new String[]{"123"}));
		assertTrue(tm.validate(new String[]{"TRUE"}));
		assertTrue(tm.validate(new String[]{"FALSE"}));
		
	}
	
	
	@Test
	public void test_ValArg(){
		TriggerMeta tm = new TriggerMeta("TRIG1", new TriggerParam[]{new ValParam("",new String[]{"OPTION1","OPTION2","OPTION3"})});
		assertFalse(tm.validate(new String[]{}));		
		assertFalse(tm.validate(new String[]{"ASD"}));
		assertFalse(tm.validate(new String[]{""}));
		assertFalse(tm.validate(new String[]{"3.1399"}));
		assertFalse(tm.validate(new String[]{"5.151"}));
		assertFalse(tm.validate(new String[]{"OPTION"}));
		assertFalse(tm.validate(new String[]{"OPTION6"}));
		assertTrue(tm.validate(new String[]{"OPTION1"}));
		assertTrue(tm.validate(new String[]{"OPTION2"}));
		assertTrue(tm.validate(new String[]{"OPTION3"}));			
	}
	
	
	@Test
	public void test_FlagArg(){
		TriggerMeta tm = new TriggerMeta("TRIG1", new TriggerParam[]{new FlagParam("",new String[]{"FLAG1","FLAG2","FLAG3"})});
		assertFalse(tm.validate(new String[]{}));		
		assertFalse(tm.validate(new String[]{"ASD"}));
		assertFalse(tm.validate(new String[]{""}));
		assertFalse(tm.validate(new String[]{"3.1399"}));
		assertFalse(tm.validate(new String[]{"5.151"}));
		assertFalse(tm.validate(new String[]{"FLAG"}));
		assertFalse(tm.validate(new String[]{"FLAG4"}));
		assertTrue(tm.validate(new String[]{"FLAG1"}));
		assertTrue(tm.validate(new String[]{"FLAG2"}));
		assertTrue(tm.validate(new String[]{"FLAG3"}));
		assertTrue(tm.validate(new String[]{"FLAG3,FLAG1"}));
		assertTrue(tm.validate(new String[]{"FLAG2,FLAG1"}));
		assertTrue(tm.validate(new String[]{"FLAG2,FLAG3"}));
		assertTrue(tm.validate(new String[]{"FLAG3,FLAG1,FLAG2"}));
	}
}
