package org.doomday.server.beans.device;

import java.util.HashMap;
import java.util.Map;

public class DeviceMeta {
	private String devSerial;
	private String devClass;
	private Map<String, SensorMeta> sensors = new HashMap<>();
	private Map<String, TriggerMeta> triggets = new HashMap<>();
}
