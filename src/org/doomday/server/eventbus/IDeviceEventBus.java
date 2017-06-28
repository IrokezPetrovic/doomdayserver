package org.doomday.server.eventbus;

public interface IDeviceEventBus {
	public void emit(String devSerial,DeviceEvent event);
	public void onTrigger(String devSerial,ITriggerEventListener listener);
	public void onSensor(String devSerial,ISensorEventListener listener);
	public void onEvent(String devSerial,IEventEventListener listener);
}
