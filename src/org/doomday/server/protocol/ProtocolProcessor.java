package org.doomday.server.protocol;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.doomday.server.beans.device.Device;
import org.doomday.server.beans.device.DeviceMeta;
import org.doomday.server.beans.device.sensor.BoolSensorMeta;
import org.doomday.server.beans.device.sensor.FlagSensorMeta;
import org.doomday.server.beans.device.sensor.FloatSensorMeta;
import org.doomday.server.beans.device.sensor.IntSensorMeta;
import org.doomday.server.beans.device.sensor.SensorMeta;
import org.doomday.server.beans.device.sensor.StrSensorMeta;
import org.doomday.server.beans.device.sensor.ValSensorMeta;
import org.doomday.server.beans.device.trigger.BoolParam;
import org.doomday.server.beans.device.trigger.FlagParam;
import org.doomday.server.beans.device.trigger.FloatParam;
import org.doomday.server.beans.device.trigger.IntParam;
import org.doomday.server.beans.device.trigger.StrParam;
import org.doomday.server.beans.device.trigger.TriggerMeta;
import org.doomday.server.beans.device.trigger.TriggerParam;
import org.doomday.server.beans.device.trigger.ValParam;
import org.doomday.server.eventbus.DeviceSensorEvent;
import org.doomday.server.eventbus.IDeviceEventBus;
import org.springframework.beans.factory.annotation.Autowired;



public class ProtocolProcessor implements IProtocolProcessor{
	
	public static enum ProtocolState{
		DISAUTHED,AUTHED,READY
	}
	
	
	private final Device device;
	private ProtocolState state = ProtocolState.DISAUTHED;
	private final Queue<String> msgQueue = new ArrayDeque<>();
	
	@Autowired
	private IDeviceEventBus eventBus;
	
	public ProtocolProcessor(Device device) {
		this.device = device;
		System.out.println("PP CONSTRUCTOR");
	}
	
	@PostConstruct
	public void init(){
		msgQueue.add("CONNECT "+device.getPincode());
		
		eventBus.onTrigger(device.getDevSerial(), (e)->{
			DeviceMeta meta = device.getMeta();
			if (meta==null) return;
			
			TriggerMeta tm = meta.getTrigger(e.getTrigger());
			if (tm==null) return;
			
			if (tm.validate(e.getParams())){				
				msgQueue.add(e.getTrigger()+" "+Stream.of(e.getParams()).reduce((s,v)->s+" "+v));
			} else {
				
			}
		});
		
		
		System.out.println("PP PostConstruct");
		
	}
	
	
	public void read(String content){
		StringTokenizer st = new StringTokenizer(content, " ");
		String cmd = st.nextToken();
		switch (cmd){
		
			case "ACCEPT": parseAccept(st);break;
			case "DENY": parseDeny(st);break;
			
			case "SENSOR": parseSensor(st);break;
			case "TRIGGER": parseTrigger(st);break;
			//case "EVENT": parseEvent(st);break;
			//case "LANG": parseLang(st);break;		
			case "READY": parseReady(st);break;
			
			case "SET": parseSet(st);break;
			//case "EMIT": parseEmit(st);break;
		}
		
	} 

	
	private void parseDeny(StringTokenizer st) throws IllegalStateException{
		if (state!=ProtocolState.DISAUTHED) throw new IllegalStateException("Illegal state to parse DENY");
		msgQueue.add("CONNECT "+device.getPincode());		
	}


	private void parseAccept(StringTokenizer st) throws IllegalStateException{
		if (state!=ProtocolState.DISAUTHED) throw new IllegalStateException("Illegal state to parse ACCEPT");
		this.state = ProtocolState.AUTHED;
		
	}
	
	private void parseReady(StringTokenizer st) {
		if (state!=ProtocolState.AUTHED) throw new IllegalStateException("Illegal state to parse READY");
		this.state = ProtocolState.READY;
		
	}
	
	/**
	 * SET <NAME> <VALUE>
	 * @param st
	 */
	private void parseSet(StringTokenizer st) {	
		if (state!=ProtocolState.READY) throw new IllegalStateException("Illegal state to parse SET");
		String sensorName = st.nextToken();
		String sensorValue = st.nextToken();
		SensorMeta s = device.getMeta().getSensor(sensorName);
		if (s.validate(sensorValue)){
			device.setData(sensorName,sensorValue);
			eventBus.emit(device.getDevSerial(), new DeviceSensorEvent(sensorName,sensorValue));
		}
	}

		
	
	/**
	 * TRIGGER <NAME> INT <NAME> (MIN,MAX) FLOAT <NAME> (MIN,MAX) STR <NAME> BOOL <NAME> VAL <NAME> (VALUE1..VALUE_N) FLAG <NAME> (VALUE_1..VALUE_N)
	 */
	private void parseTrigger(StringTokenizer st) {
		if (state!=ProtocolState.AUTHED) throw new IllegalStateException("Illegal state to parse TRIGGER");
		String name = st.nextToken();
		List<TriggerParam> params = new ArrayList<>();
		while(st.hasMoreTokens()){
			String paramType = st.nextToken();
			String paramName = st.nextToken();
			switch (paramType){
			case "INT":params.add(parseIntParam(paramName,st));break;
			case "FLOAT":params.add(parseFloatParam(paramName,st));break;
			case "BOOL":params.add(parseBoolParam(paramName,st));break;
			case "STR":params.add(parseStrParam(paramName,st));break;
			case "VAL":params.add(parseValParam(paramName,st));break;
			case "FLAG":params.add(parseFlagParam(paramName,st));break;
			}			
		}
		
		TriggerMeta tm = new TriggerMeta(name,params.toArray(new TriggerParam[0]));		
		DeviceMeta meta = device.getMeta();
		if (meta==null){
			meta = new DeviceMeta();
			device.setMeta(meta);					
		}
		meta.addTrigger(tm);
		}

	private TriggerParam parseFlagParam(String name, StringTokenizer st) {
		String s = st.nextToken();
		String[] ss = s.substring(1,s.length()-1).split(",");
		return new FlagParam(name, ss);
	}

	private TriggerParam parseValParam(String name, StringTokenizer st) {
		String s = st.nextToken();
		String[] ss = s.substring(1,s.length()-1).split(",");
		return new ValParam(name, ss);
	}

	private TriggerParam parseStrParam(String name, StringTokenizer st) {
		return new StrParam(name);
	}

	private TriggerParam parseBoolParam(String name, StringTokenizer st) {
		return new BoolParam(name);
	}

	private TriggerParam parseFloatParam(String name, StringTokenizer st) {
		String s = st.nextToken();
		String[] ss = s.substring(1,s.length()-1).split(",");
		return new FloatParam(name, Float.parseFloat(ss[0]), Float.parseFloat(ss[1]));
	}

	private TriggerParam parseIntParam(String name, StringTokenizer st) {
		String s = st.nextToken();
		String[] ss = s.substring(1,s.length()-1).split(",");
		return new IntParam(name, Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));
	}

	/**
	 * SENSOR INT <NAME> <MIN> <MAX>
	 * SENSOR FLOAT <NAME> <MIN> <MAX>
	 * SENSOR BOOL <NAME>
	 * SENSOR STR <NAME>
	 * SENSOR VAL <NAME> (<OPTION_1>..<OPTION_N>)
	 * SENSOR FLAG <NAME> (<FLAG_1>..<FLAG_2>) 
	 */
	private void parseSensor(StringTokenizer st) {
		if (state!=ProtocolState.AUTHED) throw new IllegalStateException("Illegal state to parse SENSOR");
		String type = st.nextToken();
		String name = st.nextToken();
		SensorMeta s = null;
		try{
			switch(type){
			case "INT":s = parseIntSensor(name,st);break;
			case "FLOAT":s = parseFloatSensor(name,st);break;
			case "BOOL":s = parseBoolSensor(name,st);break;
			case "STR":s = parseStrSensor(name,st);break;
			case "VAL":s = parseValSensor(name,st);break;
			case "FLAG":s = parseFlagSensor(name,st);break;
			}
		} catch (NoSuchElementException e){
			
		}
		if (s!=null){
			DeviceMeta meta = device.getMeta();
			if (meta==null){
				meta = new DeviceMeta();
				device.setMeta(meta);
			}
			meta.addSensor(s);
		}
		
	}
	/**
	 * SENSOR FLAG <NAME> [<FLAG_1>..<FLAG_2>] 
	 * 
	 * @param name
	 * @param st
	 * @return
	 */
	
	private SensorMeta parseFlagSensor(String name, StringTokenizer st) {
		String valuesStringWithBrackets = st.nextToken();
		String commaSeparatedValues = valuesStringWithBrackets.substring(1, valuesStringWithBrackets.length()-1);		
		return new FlagSensorMeta(name, commaSeparatedValues.split(","));
	}

	/**
	 * SENSOR VAL <NAME> [<OPTION_1>..<OPTION_N>]
	 * 
	 * @param name
	 * @param st
	 * @return
	 */
	private SensorMeta parseValSensor(String name, StringTokenizer st) {	
		String valuesStringWithBrackets = st.nextToken();
		String commaSeparatedValues = valuesStringWithBrackets.substring(1, valuesStringWithBrackets.length()-1);		
		return new ValSensorMeta(name, commaSeparatedValues.split(","));
	}

	/**
	 * SENSOR STR <NAME>
	 * 
	 * @param name
	 * @param st
	 * @return
	 */
	private SensorMeta parseStrSensor(String name, StringTokenizer st) {
		return new StrSensorMeta(name);
	}

	/**
	  * SENSOR BOOL <NAME>
	  * 
	  * @param name
	  * @param st
	  * @return
	  */
	private SensorMeta parseBoolSensor(String name, StringTokenizer st) {		
		return new BoolSensorMeta(name);
	}

	/**
	 * SENSOR FLOAT <NAME> (<MIN>,<MAX>)
	 * @param name
	 * @param st
	 * @return
	 */
	private SensorMeta parseFloatSensor(String name, StringTokenizer st) {
		String s = st.nextToken();
		String[] ss = s.substring(1,s.length()-1).split(",");
		return new FloatSensorMeta(name, Float.parseFloat(ss[0]), Float.parseFloat(ss[1]));
	}

	/**
	 * SENSOR INT <NAME> (<MIN>,<MAX>)
	 * 
	 * @param name
	 * @param st
	 * @return 
	 */
	private IntSensorMeta parseIntSensor(String name, StringTokenizer st) {
		String s = st.nextToken();
		String[] ss = s.substring(1,s.length()-1).split(",");	
		return new IntSensorMeta(name, Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));
	}

	public Queue<String> getWriteQueue() {
		return msgQueue;
	}

	public Device getDevice() {
		return device;
		
	}

	
}
