package org.doomday.server.model;

import org.doomday.server.beans.device.Device;
import org.junit.Test;
import org.junit.Assert;

public class DeviceMemModelTest {
	@Test
	public void mergeTest(){
		DeviceMemRepository repo = new DeviceMemRepository();
		Device d = repo.getDevice("CLASS", "SERIAL");
		Device m = new Device();
		m.setId("CLASS:SERIAL");
		m.setPincode("1234");
		repo.updateDevice(m);
		Device updated = repo.getDevice("CLASS", "SERIAL");
		Assert.assertEquals("1234", updated.getPincode());
	}
}
