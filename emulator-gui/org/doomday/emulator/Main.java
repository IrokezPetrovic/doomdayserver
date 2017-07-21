package org.doomday.emulator;

import java.util.function.Consumer;

import javax.script.ScriptException;

import org.doomday.emulator.model.DeviceModel;
import org.doomday.emulator.model.ScriptedDeviceModelFactory;
import org.doomday.emulator.ui.IMainForm;
import org.doomday.emulator.ui.MainForm;



public class Main {
	public static DeviceWorker worker;
	public static void main(String[] args){
		IMainForm form = new MainForm();
		ScriptedDeviceModelFactory modelFactory = new ScriptedDeviceModelFactory();
		Consumer<String> logger = new Consumer<String>() {

			@Override
			public void accept(String t) {
				form.log(t);
				
			}
		};
		form.onStart(p->{			
			try {
				DeviceModel model = modelFactory.build(form.getScriptSource(),logger);
				worker = new DeviceWorker(model,logger);
				
				worker.onConnect(b->{										
					form.setConnected(b);
				});
				worker.start(p.getMcastIp(),p.getMcastPort(),p.getIpPort());
				form.setStarted(true);
			} catch (ScriptException e) {
				form.log(e.toString());
				form.setStarted(false);				
			}
			
		});
		form.onStop(p->{
			if (worker!=null){				
				worker.stop();
			}
			form.setConnected(false);
			form.setStarted(false);
		});
		
		form.onDisconnect(disconnect->{			
			worker.disconnect();			
		});
		
		
		form.setVisible(true);
	}
}
