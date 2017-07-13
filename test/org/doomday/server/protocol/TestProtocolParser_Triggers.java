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
import org.doomday.server.beans.device.trigger.StrParam;
import org.doomday.server.beans.device.trigger.TriggerMeta;
import org.doomday.server.beans.device.trigger.TriggerParam;
import org.doomday.server.beans.device.trigger.ValParam;

public class TestProtocolParser_Triggers {
//	
//	Device device = new Device("","");
//	ProtocolProcessor pp = new ProtocolProcessor(device,new ITransport() {
//
//		@Override
//		public void disconnect(IProtocolProcessor protocolProcessor) {
//			// TODO Auto-generated method stub
//			
//		}
//	});
//	
//	private DeviceProfile checkMeta(Device d){
//		DeviceProfile meta = d.getProfile();
//		assertNotNull(meta);
//		return meta;
//	}
//	@Test
//	public void test_noArgs(){	
//		pp.read("ACCEPT");
//		pp.read("TRIGGER TRIG1");
//		DeviceProfile meta = checkMeta(device);
//		TriggerMeta tm = meta.getTrigger("TRIG1");
//		assertNotNull(tm);
//		TriggerParam[] params = tm.getParams();
//		assertEquals(0, params.length);		
//	} 
//	
//	@Test
//	public void test_IntArg(){
//		pp.read("ACCEPT");
//		pp.read("TRIGGER TRIG1 INT PARAM1 (10,20)");
//		DeviceProfile meta = checkMeta(device);
//		TriggerMeta tm = meta.getTrigger("TRIG1");
//		assertNotNull(tm);
//		TriggerParam[] params = tm.getParams();
//		assertEquals(1, params.length);
//		TriggerParam tp1 = params[0];
//		assertEquals(IntParam.class, tp1.getClass());
//		IntParam ip1 = (IntParam) tp1;
//		assertEquals(new Integer(10),ip1.getMin());
//		assertEquals(new Integer(20),ip1.getMax());
//	}
//	
//	@Test
//	public void test_FloatArg(){
//		pp.read("ACCEPT");
//		pp.read("TRIGGER TRIG1 FLOAT PARAM1 (3.14,5.16)");
//		DeviceProfile meta = checkMeta(device);
//		TriggerMeta tm = meta.getTrigger("TRIG1");
//		assertNotNull(tm);
//		TriggerParam[] params = tm.getParams();
//		assertEquals(1, params.length);
//		TriggerParam tp1 = params[0];
//		assertEquals(FloatParam.class, tp1.getClass());
//		FloatParam fp1 = (FloatParam) tp1;
//		assertEquals(new Float(3.14),fp1.getMin());
//		assertEquals(new Float(5.16),fp1.getMax());
//	}
//	
//	@Test
//	public void test_StrArg(){
//		pp.read("ACCEPT");
//		pp.read("TRIGGER TRIG1 STR PARAM1");
//		DeviceProfile meta = checkMeta(device);
//		TriggerMeta tm = meta.getTrigger("TRIG1");
//		assertNotNull(tm);
//		TriggerParam[] params = tm.getParams();
//		assertEquals(1, params.length);
//		TriggerParam tp1 = params[0];
//		assertEquals(StrParam.class, tp1.getClass());		
//	}
//	
//	@Test
//	public void test_BoolArg(){
//		pp.read("ACCEPT");
//		pp.read("TRIGGER TRIG1 BOOL PARAM1");
//		DeviceProfile meta = checkMeta(device);
//		TriggerMeta tm = meta.getTrigger("TRIG1");
//		assertNotNull(tm);
//		TriggerParam[] params = tm.getParams();
//		assertEquals(1, params.length);
//		TriggerParam tp1 = params[0];
//		assertEquals(BoolParam.class, tp1.getClass());		
//	}
//	
//	
//	@Test
//	public void test_ValArg(){
//		pp.read("ACCEPT");
//		pp.read("TRIGGER TRIG1 VAL PARAM1 (OPTION1)");
//		DeviceProfile meta = checkMeta(device);
//		TriggerMeta tm = meta.getTrigger("TRIG1");
//		assertNotNull(tm);
//		TriggerParam[] params = tm.getParams();
//		assertEquals(1, params.length);
//		TriggerParam tp1 = params[0];
//		assertEquals(ValParam.class, tp1.getClass());
//		ValParam vp = (ValParam) tp1;
//		assertEquals(1, vp.getOptions().size());
//		assertTrue(vp.getOptions().contains("OPTION1"));
//		assertFalse(vp.getOptions().contains("OPTION5"));
//	}
//	
//	@Test
//	public void test_FlagArg(){
//		pp.read("ACCEPT");
//		pp.read("TRIGGER TRIG1 FLAG PARAM1 (OPTION1)");
//		DeviceProfile meta = checkMeta(device);
//		TriggerMeta tm = meta.getTrigger("TRIG1");
//		assertNotNull(tm);
//		TriggerParam[] params = tm.getParams();
//		assertEquals(1, params.length);
//		TriggerParam tp1 = params[0];
//		assertEquals(FlagParam.class, tp1.getClass());
//		FlagParam vp = (FlagParam) tp1;
//		assertEquals(1, vp.getFlags().size());
//		assertTrue(vp.getFlags().contains("OPTION1"));
//		assertFalse(vp.getFlags().contains("OPTION5"));
//	}
//	
//	@Test
//	public void test_AllAvailTypes(){
//		pp.read("ACCEPT");
//		pp.read("TRIGGER TRIG1 INT INTP (10,20) FLOAT FLOATP (3.14,5.16) STR STRP BOOL BOOLP VAL VALP (OPTION1,OPTION2,OPTION3) FLAG FLAGP (FLAG1,FLAG2,FLAG3)");
//		DeviceProfile meta = checkMeta(device);
//		TriggerMeta tm = meta.getTrigger("TRIG1");
//		assertNotNull(tm);
//		TriggerParam[] params = tm.getParams();
//		assertEquals(6, params.length);
//		assertEquals(IntParam.class, params[0].getClass());
//		assertEquals(FloatParam.class, params[1].getClass());
//		assertEquals(StrParam.class, params[2].getClass());
//		assertEquals(BoolParam.class, params[3].getClass());
//		assertEquals(ValParam.class, params[4].getClass());
//		assertEquals(FlagParam.class, params[5].getClass());
//	}
//	
//	
	
}
