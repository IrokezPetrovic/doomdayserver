package org.doomday.server.model;

public interface ISensorValueRepository {

	void put(String id, String sensorName, String sensorValue);

	String getValue(String deviceId, String sensorName);

}
