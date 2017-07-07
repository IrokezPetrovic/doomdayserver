package org.doomday.emulator.model;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.StringTokenizer;

import org.doomday.emulator.model.trigger.Trigger;
import org.doomday.emulator.model.trigger.TriggerArg;

public class DeviceModel {
	public static enum State{
		DISCONNECTED,CONNECTED,AUTHED,ARMED
	}
	private State state = State.DISCONNECTED;
	private String devClass;
	private String devSerial;
	private String pincode = "";
	private Map<String, Sensor> sensors = new HashMap<String, Sensor>();
	private Map<String, Trigger> triggers = new HashMap<>();

	private Queue<String> msgQueue = new ArrayDeque<>();

	public String getDevSerial() {
		return this.devSerial;
	}

	public void process(String s) {
		try{
		StringTokenizer st = new StringTokenizer(s, " ");
		String command = st.nextToken();		
			switch(command){
				case "CONNECT":processConnect(st);break;
				case "PROFILE":processProfile(st);break;
				case "READY":processReady(st);break;
				case "CALL":processCall(st);break;
				case "DISCONNECT":processDisconnect(st);break;
			}
		} catch(NoSuchElementException e){
			
		}
	}
	private void processDisconnect(StringTokenizer st){
		if (state==State.DISCONNECTED) return;		
	}
	
	private void processCall(StringTokenizer st){
		if (state!=State.ARMED) return;
		String triggerName = st.nextToken();
		Trigger t = triggers.get(triggerName);
		if (t==null) return;
		
		TriggerArg[] args = t.getArgs();
		String[] strArgs = new String[args.length];
		for (int i=0;i<args.length;i++){
			String arg = st.nextToken();
			if (!args[i].validate(arg)) return;
			strArgs[i] = arg;
		}
		t.invoke(strArgs);
	}
	private void processReady(StringTokenizer st){
		if (state!=State.AUTHED) return;
		this.msgQueue.add("READY");
		this.state = State.ARMED;
	}
	
	private void processProfile(StringTokenizer st){
		if (state!=State.AUTHED) return;
		this.sensors.values().forEach(s->msgQueue.add(s.toString()));
		this.triggers.values().forEach(t->msgQueue.add(t.toString()));
		this.msgQueue.add("READY");
		this.state = State.ARMED;
	}
	private void processConnect(StringTokenizer st){
		if (state!=State.CONNECTED) return;
		
		String pin = st.nextToken();
		if (pin.equals(pincode)){
			this.state = State.AUTHED;
			msgQueue.add("ACCEPT");
		} else {
			msgQueue.add("DENY");
		}
	}
	
	

	public String getWritrable() {
		return msgQueue.poll();
	}

	public boolean hasWritable() {
		return msgQueue.size()>0;
	}
	public void setDevClass(String s) {
		this.devClass = s;		
	}
	public String getDevClass() {
		return devClass;
	}
	public void setDevSerial(String serial) {
		this.devSerial = serial;		
	}
	public void addSensor(Sensor sensor) {
		this.sensors.put(sensor.getName(),sensor);		
	}
	public void addTrigger(Trigger trigger) {
		this.triggers .put(trigger.getName(),trigger);	
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public String getPincode() {
		return this.pincode;
	}

	public void setPincode(String pin) {
		this.pincode = pin;
		
	}

	public Trigger getTrigger(String name) {
		return triggers.get(name);
		
	}

	public void send(String str) {
		if (state==State.ARMED){
			msgQueue.add(str);
		}
		
	}

}
