package org.doomday.emulator.model.trigger;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class Trigger {
	private final String name;
	private final TriggerArg[] params;
	private final String def;
	private Consumer<Object[]> callback;
	public Trigger(String name, TriggerArg[] params) {
		super();
		this.name = name;
		this.params = params;
		StringBuilder sb = new StringBuilder(name);
		Stream.of(params)
		.forEach(p->{
			sb.append(" ");
			sb.append(p.toString());
		});
		
		def = sb.toString();
		
	}
	
	public String getDef() {
		return def;
	}
	
	@Override
	public String toString() {
		return "TRIGGER "+def;
	}
	
	public void setCallback(Consumer<Object[]> callback) {
		this.callback = callback;
	}
	
	
	public void invoke(String[] args){
		if (args==null) return;
		if (args.length!=params.length) return;
		for (int i=0;i<params.length;i++){
			if (!params[i].validate(args[i]))
				return;
		}
		if (callback!=null){
			callback.accept(args);
		}
	}

	public String getName() {
		return name;
	}

	public TriggerArg[] getArgs() {
		return params;
		
	}
	
}
