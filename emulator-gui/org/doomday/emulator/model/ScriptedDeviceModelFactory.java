package org.doomday.emulator.model;

import java.util.function.Consumer;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.doomday.emulator.model.script.BoolArgFactory;
import org.doomday.emulator.model.script.BoolSensorFactory;
import org.doomday.emulator.model.script.DeviceWrapper;
import org.doomday.emulator.model.script.FlagArgFactory;
import org.doomday.emulator.model.script.FlagSensorFactory;
import org.doomday.emulator.model.script.FloatArgFactory;
import org.doomday.emulator.model.script.FloatSensorFactory;
import org.doomday.emulator.model.script.IntArgFactory;
import org.doomday.emulator.model.script.IntSensorFactory;
import org.doomday.emulator.model.script.IntervalFactory;
import org.doomday.emulator.model.script.LoggerWrapper;
import org.doomday.emulator.model.script.StrArgFactory;
import org.doomday.emulator.model.script.StrSensorFactory;
import org.doomday.emulator.model.script.TriggerFactory;
import org.doomday.emulator.model.script.ValArgFactory;
import org.doomday.emulator.model.script.ValSensorFactory;
public class ScriptedDeviceModelFactory {

	public DeviceModel build(String scriptSource, Consumer<String> logger) throws ScriptException {
		DeviceModel model = new DeviceModel();
		ScriptEngineManager sem = new ScriptEngineManager();
		ScriptEngine engine = sem.getEngineByName("nashorn");		
		Bindings bind = engine.createBindings();
		bind.put("device", new DeviceWrapper(model));
		bind.put("logger", new LoggerWrapper(logger));			
		bind.put("IntSensor", new IntSensorFactory(model));
		bind.put("FloatSensor", new FloatSensorFactory(model));
		bind.put("StrSensor", new StrSensorFactory(model));
		bind.put("BoolSensor", new BoolSensorFactory(model));
		bind.put("ValSensor", new ValSensorFactory(model));
		bind.put("FlagSensor", new FlagSensorFactory(model));
				
		bind.put("IntArg", new IntArgFactory());
		bind.put("FloatArg", new FloatArgFactory());
		bind.put("StrArg", new StrArgFactory());
		bind.put("BoolArg", new BoolArgFactory());
		bind.put("ValArg", new ValArgFactory());
		bind.put("FlagArg", new FlagArgFactory());
		bind.put("Trigger", new TriggerFactory());
		
		bind.put("setInterval",new IntervalFactory(bind));
		
		engine.eval(scriptSource, bind);
		return model;
	}

	

}
