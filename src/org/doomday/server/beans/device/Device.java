package org.doomday.server.beans.device;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Device implements Mergeable<Device>{
	public static enum ConnectionStatus{
		ONLINE,
		OFFLINE,
		DISCOVERED
	}
	private String id;
	private String pincode;
	private String name;
	
	private String devSerial;
	private String devClass;
	
	@JsonProperty(value="profile")
	private DeviceProfile meta;
	
	private Map<String,String> sensorData = new HashMap<>();
	private ConnectionStatus connectionStatus = ConnectionStatus.DISCOVERED;

	public void merge(Device d) {
		this.pincode = d.pincode!=null?d.pincode:this.pincode;
		this.name = d.name!=null?d.name:this.name;
		this.meta = d.getProfile()!=null?d.getProfile():this.meta;		
	}
		
	public Device(){
		
	}
	public Device(String devClass, String devSerial) {
		this.devClass = devClass;
		this.devSerial = devSerial;
		this.id = devClass+":"+devSerial;
	}

	public String getPincode() {
		return pincode;
	}
	


	public void setConnectionStatus(ConnectionStatus status) {
		this.connectionStatus = status;
		
	}

	public DeviceProfile getProfile() {		
		return meta;
	}

	public void setData(String sensorName, String sensorValue) {
		sensorData.put(sensorName, sensorValue);
		
	}

	public String getDevSerial() {
		return devSerial;
	}

	public void setProfile(DeviceProfile meta) {
		this.meta = meta;		
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
		
	}
	
	public String getDevClass() {
		return devClass;
	}
	
	public ConnectionStatus getConnectionStatus() {
		return connectionStatus;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getId(){
		return this.id;
	}
		
	public void setId(String id) {
		this.id = id;
	}
	


	
}
