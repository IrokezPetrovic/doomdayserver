package org.doomday.emulator.model;

import java.util.function.Consumer;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.doomday.emulator.model.script.DeviceWrapper;
import org.doomday.emulator.model.script.IntSensorWrapper;
import org.doomday.emulator.model.script.LoggerWrapper;
public class ScriptedDeviceModelFactory {

	public DeviceModel build(String scriptSource, Consumer<String> logger) throws ScriptException {
		DeviceModel model = new DeviceModel();
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine engine = sem.getEngineByName("nashorn");		
		Bindings bind = new SimpleBindings();
		bind.put("device", new DeviceWrapper(model));
		bind.put("logger", new LoggerWrapper(logger));			
		bind.put("IntSensor", new IntSensorWrapper());
		engine.eval(scriptSource, bind);
		return model;
	}

	

}
