package org.doomday.server.eventbus;

public class EventBus implements IDeviceEventBus{

	EventListenerList<ITriggerEventListener> triggerEventListeners = new EventListenerList<>();
	EventListenerList<ISensorEventListener> sensorEventListeners = new EventListenerList<>();
	EventListenerList<IEventEventListener> eventEventListeners = new EventListenerList<>();
	
	
	@Override
	public void emit(String devSerial, DeviceEvent event) {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public void onTrigger(String devSerial, ITriggerEventListener listener) {
		triggerEventListeners.add(devSerial, listener);
	}
	
	@Override
	public void onSensor(String devSerial, ISensorEventListener listener) {
		sensorEventListeners.add(devSerial, listener);
		
	}
	
	@Override
	public void onEvent(String devSerial, IEventEventListener listener) {
		eventEventListeners.add(devSerial, listener);
		
	}

	
}
