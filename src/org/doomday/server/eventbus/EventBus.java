package org.doomday.server.eventbus;

import java.util.HashMap;
import java.util.Map;



public class EventBus implements IDeviceEventBus{

	Map<String, ITriggerEventListener> triggerEventListeners = new HashMap<>();
	Map<String, ISensorEventListener> sensorEventListener = new HashMap<>();
	Map<String, IEventEventListener> eventEventListener = new HashMap<>();
	
	
	@Override
	public void emit(String devSerial, DeviceEvent event) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onTrigger(String devSerial, ITriggerEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensor(String devSerial, ISensorEventListener listener) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onEvent(String devSerial, IEventEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	
}
