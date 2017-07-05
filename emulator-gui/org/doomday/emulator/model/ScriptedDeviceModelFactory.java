package org.doomday.emulator.model;

import java.util.function.Consumer;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.doomday.emulator.model.script.BoolSensorFactory;
import org.doomday.emulator.model.script.DeviceWrapper;
import org.doomday.emulator.model.script.FloatSensorFactory;
import org.doomday.emulator.model.script.IntSensorFactory;
import org.doomday.emulator.model.script.LoggerWrapper;
import org.doomday.emulator.model.script.StrSensorFactory;
public class ScriptedDeviceModelFactory {

	public DeviceModel build(String scriptSource, Consumer<String> logger) throws ScriptException {
		DeviceModel model = new DeviceModel();
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine engine = sem.getEngineByName("nashorn");		
		Bindings bind = new SimpleBindings();
		bind.put("device", new DeviceWrapper(model));
		bind.put("logger", new LoggerWrapper(logger));			
		bind.put("IntSensor", new IntSensorFactory(model));
		bind.put("FloatSensor", new FloatSensorFactory(model));
		bind.put("StrSensor", new StrSensorFactory(model));
		bind.put("BoolSensor", new BoolSensorFactory(model));
		
		engine.eval(scriptSource, bind);
		return model;
	}

	

}
