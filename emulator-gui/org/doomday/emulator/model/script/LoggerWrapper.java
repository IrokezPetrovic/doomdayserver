package org.doomday.emulator.model.script;

import java.util.function.Consumer;

public class LoggerWrapper {

	private Consumer<String> logger;

	public LoggerWrapper(Consumer<String> logger) {
		this.logger = logger;
	}
	
	public void log(Object val){
		logger.accept(val.toString());
	}

}
