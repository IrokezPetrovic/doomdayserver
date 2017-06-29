package org.doomday.server.beans.device;

import java.util.HashMap;
import java.util.Map;

public class Device {
	public static enum ConnectionStatus{
		ONLINE,
		OFFLINE,
		DISCOVERED
	}
	
	private String pincode;
	private String name;

	private String localAddr;
	private String devSerial;
	private String devClass;
	private DeviceMeta meta;
	private Map<String,String> sensorData = new HashMap<>();
	private ConnectionStatus connectionStatus;

	
		
	public Device(String devClass, String devSerial) {
		this.devClass = devClass;
		this.devSerial = devSerial;
	}

	public String getPincode() {
		return pincode;
	}
	
	public String getLocalAddr() {
		return localAddr;
	}

	public void setConnectionStatus(ConnectionStatus status) {
		this.connectionStatus = status;
		
	}

	public DeviceMeta getMeta() {		
		return meta;
	}

	public void setData(String sensorName, String sensorValue) {
		sensorData.put(sensorName, sensorValue);
		
	}

	public String getDevSerial() {
		return devSerial;
	}

	public void setMeta(DeviceMeta meta) {
		this.meta = meta;		
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
		
	}



	
}
