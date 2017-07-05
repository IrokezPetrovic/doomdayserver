package org.doomday.server.beans.device;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.doomday.server.beans.device.sensor.SensorMeta;
import org.doomday.server.beans.device.trigger.TriggerMeta;

public class DeviceProfile {	
	private Map<String, SensorMeta> sensors = new HashMap<>();
	private Map<String, TriggerMeta> triggers = new HashMap<>();
	
	public void addSensor(SensorMeta s) {
		sensors.put(s.getName(), s);		
	}

	public void addTrigger(TriggerMeta tm) {
		triggers.put(tm.getName(), tm);		
	}

	public SensorMeta getSensor(String name) {
		return sensors.get(name);
	}

	public TriggerMeta getTrigger(String name) {
		return triggers.get(name);
	}
	
	public Collection<SensorMeta> getSensors() {
		return sensors.values();
	}
	
	public Collection<TriggerMeta> getTriggers() {
		return triggers.values();
	}
	
	
}
