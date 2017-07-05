package emulator;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.StringTokenizer;

import org.doomday.server.beans.device.sensor.SensorMeta;
import org.doomday.server.beans.device.trigger.TriggerMeta;

public class DeviceModel {
	public static enum Status{
		CONNECTED,AUTHED,ARMED,DISCONNECTED
	}
	private Status status;
	private String pincode;
	
	public DeviceModel(String pincode) {	
		this.pincode = pincode;
	}
	
	Queue<String> msgQueue = new ArrayDeque<String>();
	Map<String,Sensor> sensors = new HashMap<>();
	Map<String,Trigger> triggers = new HashMap<>();
	
	private void processConnect(StringTokenizer st){
		if (status!=Status.CONNECTED) return;
		String pin = st.nextToken();
		if (pin.equals(this.pincode)){
			status = Status.AUTHED;
			msgQueue.add("ACCEPT");
		} else {
			msgQueue.add("DENY");
		}
	}
	private void processInfo(StringTokenizer st){		
		if (status!=Status.AUTHED) return;
		sensors.values()
		.stream()
		.map(Sensor::toString)
		.forEach(msgQueue::add);
		
		triggers.values().stream()
		.map(Trigger::toString)
		.forEach(msgQueue::add);
		msgQueue.add("READY");
		status = Status.ARMED;
			
	}
	private void processReady(StringTokenizer st){
		if (status!=Status.AUTHED) return;
		status = Status.ARMED;
		msgQueue.add("READY");
	}
	
	private void processTrigger(String triggerName, StringTokenizer st){
		if (status!=Status.ARMED) return;
		
	}
	public void parse(String line){
		StringTokenizer st = new StringTokenizer(line," ");		
		try{
			String cmd = st.nextToken();
			switch(cmd){
			case "CONNECT":processConnect(st);break;
			case "PROFILE": processInfo(st);break;
			case "READY": processReady(st);break;
			default: processTrigger(cmd,st);break;
			}
		} catch (NoSuchElementException e){
			
		}
		
	}

	public void addSensor(SensorMeta sensorMeta) {
		sensors.put(sensorMeta.getName(), new Sensor(sensorMeta));		
	}

	public void addTrigger(TriggerMeta triggerMeta) {
		triggers.put(triggerMeta.getName(), new Trigger(triggerMeta));
	}

	public Queue<String> getQueue() {
		return msgQueue;
	}
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {	
		this.status = status;
	}
}
