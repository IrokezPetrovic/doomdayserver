package org.doomday.server.misc;

import java.io.IOException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.doomday.server.beans.device.DeviceProfile;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("prototype")
public class DeviceProfileJsonDeserializer {
	@Autowired
	ObjectMapper mapper;
	
	public DeviceProfile parse(String str){
		DeviceProfile profile = new DeviceProfile();
		
		try {
			JsonNode node = mapper.readTree(str);
			JsonNode sensorsArray = node.get("sensors");
			StreamSupport.stream(Spliterators.spliteratorUnknownSize(sensorsArray.fields(), Spliterator.ORDERED), false)
			.map(e->{
				JsonNode n = e.getValue();
				String nodeType = n.get("type").asText().toLowerCase();
				switch (nodeType){
					case "int":return intSensorProfile(n);
					case "float":return floatSensorProfile(n);
					case "str":return strSensorProfile(n);
					case "bool":return boolSensorMeta(n);
					case "val":return valSensorMeta(n);
					case "flag":return flagSensorMeta(n);
				}
				
				return null;
			}).forEach(profile::addSensor);;
			JsonNode triggersArray = node.get("triggers");
			StreamSupport.stream(Spliterators.spliteratorUnknownSize(triggersArray.fields(), Spliterator.ORDERED), false)
			.map(e->{
				JsonNode tn = e.getValue();
				String triggerName = tn.get("name").asText();
				JsonNode pn = tn.get("params");
				TriggerParam[] params = new TriggerParam[pn.size()];
				for(int i=0;i<pn.size();i++){
					params[i] = parseTriggerParam(pn.get(i));
				}
				
				TriggerMeta tm = new TriggerMeta(triggerName, params);
				return tm;
			}).forEach(profile::addTrigger);
			
			return profile;
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
				
	}
	
	private TriggerParam parseTriggerParam(JsonNode jsonNode) {
		String ptype = jsonNode.get("type").asText();
		switch(ptype){
		case "int":return parseIntParam(jsonNode);
		case "float":return parseFloatParam(jsonNode);
		case "str":return parseStrParam(jsonNode);
		case "bool":return parseBoolParam(jsonNode);
		case "val":return parseValParam(jsonNode);
		case "flag":return parseFlagParam(jsonNode);
		}
		return null;
	}

	private TriggerParam parseValParam(JsonNode jsonNode) {
		String name = jsonNode.get("name").asText();
		JsonNode on = jsonNode.get("options");
		String[] options = new String[on.size()];
		for (int i=0;i<on.size();i++){
			options[i] = on.get(i).asText();
		}
		return new ValParam(name, options);
	}

	private TriggerParam parseIntParam(JsonNode jsonNode) {
		String name = jsonNode.get("name").asText();
		Integer min = jsonNode.get("min").asInt();
		Integer max = jsonNode.get("max").asInt();
		return new IntParam(name, min, max);
	}

	private TriggerParam parseFloatParam(JsonNode jsonNode) {
		String name = jsonNode.get("name").asText();
		Float min = jsonNode.get("min").floatValue();
		Float max = jsonNode.get("max").floatValue();
		return new FloatParam(name, min, max);
	}

	private TriggerParam parseStrParam(JsonNode jsonNode) {
		String name = jsonNode.get("name").asText();
		return new StrParam(name);
	}

	private TriggerParam parseBoolParam(JsonNode jsonNode) {
		String name = jsonNode.get("name").asText();
		return new BoolParam(name);		
	}

	private TriggerParam parseFlagParam(JsonNode jsonNode) {
		String name = jsonNode.get("name").asText();		
		JsonNode fn = jsonNode.get("flags");
		String[] flags = new String[fn.size()];
		
		for(int i=0;i<fn.size();i++){
			flags[i] = fn.get(i).asText();
		}		
		return new FlagParam(name, flags);
	}

	private SensorMeta intSensorProfile(JsonNode node){
		String name = node.get("name").asText();
		Integer min = node.get("min").asInt();
		Integer max = node.get("max").asInt();
		return new IntSensorMeta(name, min, max);
	}
	
	private SensorMeta floatSensorProfile(JsonNode node){
		String name = node.get("name").asText();
		Double min = node.get("min").asDouble();
		Double max = node.get("max").asDouble();
		
		return new FloatSensorMeta(name, min.floatValue(), max.floatValue());
	}
	
	private SensorMeta strSensorProfile(JsonNode node){
		String name = node.get("name").asText();
		return new StrSensorMeta(name);
	}
	
	private SensorMeta boolSensorMeta(JsonNode node){
		String name = node.get("name").asText();
		return new BoolSensorMeta(name);
	}
	
	private SensorMeta valSensorMeta(JsonNode node){
		String name = node.get("name").asText();
		JsonNode optionsNode = node.get("options");
		int size = optionsNode.size();
		String[] options = new String[size];
		for (int i=0;i<size;i++){
			options[i] = optionsNode.get(i).asText();					
		}
		return new ValSensorMeta(name, options);
	}
	private SensorMeta flagSensorMeta(JsonNode node){
		String name = node.get("name").asText();
		JsonNode optionsNode = node.get("flags");
		int size = optionsNode.size();
		String[] flags = new String[size];
		for (int i=0;i<size;i++){
			flags[i] = optionsNode.get(i).asText();					
		}
		return new FlagSensorMeta(name, flags);
	}
	
}
