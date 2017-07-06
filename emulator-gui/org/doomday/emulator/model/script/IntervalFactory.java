package org.doomday.emulator.model.script;

import java.util.Timer;
import java.util.TimerTask;

import javax.script.Bindings;

import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
@SuppressWarnings("restriction")
public class IntervalFactory extends AbstractJSObject{
	Timer t = new Timer();
	
	
	private class IntervalTask extends TimerTask{
		private ScriptObjectMirror call;		
		public IntervalTask(ScriptObjectMirror call) {
			super();
			this.call = call;
		}

		@Override
		public void run() {			
			if (call!=null)				
				call.call(this, null);			
		}				
		
	}
	public IntervalFactory(Bindings bind) {
		
	}

	@Override
	public boolean isFunction() {
		return true;
	}
	
	@Override
	public Object call(Object thiz, Object... args) {
		if (args.length==3){
			try{
				ScriptObjectMirror c = (ScriptObjectMirror) args[0];
				Integer delay = Integer.valueOf(args[1].toString());
				Integer period = Integer.valueOf(args[2].toString());
				t.schedule(new IntervalTask(c), delay, period);
			} catch (ClassCastException e){
				e.printStackTrace();
			}
		}
		return null;
	}
}
