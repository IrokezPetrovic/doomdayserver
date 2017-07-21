package org.doomday.server.beans.device;

import java.util.HashMap;

import org.doomday.server.beans.device.sensor.SensorMeta;
import org.doomday.server.beans.device.trigger.TriggerMeta;

public class DeviceProfile {
	private String profileId;
	
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
				
	public HashMap<String, TriggerMeta> getTriggers() {
		return triggersMap;
	}
	public HashMap<String, SensorMeta> getSensors() {
		return sensorsMap;
	}
	
	public String getProfileId() {
		return profileId;
	}
	
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	
}
