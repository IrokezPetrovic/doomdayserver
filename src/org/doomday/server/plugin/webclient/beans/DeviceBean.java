package org.doomday.server.plugin.webclient.beans;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.doomday.server.beans.device.sensor.SensorMeta;
import org.doomday.server.beans.device.trigger.TriggerMeta;

public class DeviceBean {
	private String name;
	private String caption;
	private Collection<SensorMeta> sensors;
	private Collection<TriggerMeta> triggers;
	private Map<String,String> values = new HashMap<>();
	public DeviceBean(String name, String caption, Collection<SensorMeta> sensors, Collection<TriggerMeta> triggers) {
		super();
		this.name = name;
		this.caption = caption;
		this.sensors = sensors;
		this.triggers = triggers;
	}
	public String getName() {
		return name;
	}
	public String getCaption() {
		return caption;
	}
	public Collection<SensorMeta> getSensors() {
		return sensors;
	}
	public Collection<TriggerMeta> getTriggers() {
		return triggers;
	}
	
	public Map<String, String> getValues() {
		return values;
	}
	public void setValues(Map<String, String> values) {
		this.values = values;
	}
}
