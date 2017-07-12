package org.doomday.server.beans.device;

import java.util.Collection;
import java.util.HashMap;

import org.doomday.server.beans.device.sensor.SensorMeta;
import org.doomday.server.beans.device.trigger.TriggerMeta;

public class DeviceProfile {
	
	
	private HashMap<String, SensorMeta> sensorsMap = new HashMap<>();
	private HashMap<String, TriggerMeta> triggersMap = new HashMap<>();
	
	public void addSensor(SensorMeta s) {
		sensorsMap.put(s.getName(), s);		
	}

	public void addTrigger(TriggerMeta tm) {
		triggersMap.put(tm.getName(), tm);		
	}

	public SensorMeta getSensor(String name) {
		return sensorsMap.get(name);
	}

	public TriggerMeta getTrigger(String name) {
		return triggersMap.get(name);
	}
	
	public Collection<SensorMeta> getSensors() {
		return sensorsMap.values();
	}
	
	
	
	public Collection<TriggerMeta> getTriggers() {
		return triggersMap.values();
	}
	
	
	
}
