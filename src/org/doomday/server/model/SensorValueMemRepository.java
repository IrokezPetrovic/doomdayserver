package org.doomday.server.model;

import java.util.HashMap;
import java.util.Map;

import org.doomday.server.event.DeviceSensorEvent;
import org.doomday.server.eventbus.IEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SensorValueMemRepository implements ISensorValueRepository{
	private Map<String,String> values = new HashMap<>();
	
	@Autowired
	IEventBus eventBus;
	@Override
	public void put(String deviceId, String sensorName, String sensorValue) {		
		values.put(deviceId+"-"+sensorName, sensorValue);
		eventBus.pub("/device", new DeviceSensorEvent(deviceId, sensorName, sensorValue));
	}
	@Override
	public String getValue(String deviceId, String sensorName) {
		return values.get(deviceId+"-"+sensorName);
	}

}
