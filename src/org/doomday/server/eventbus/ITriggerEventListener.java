package org.doomday.server.eventbus;

public interface ITriggerEventListener extends IDeviceEventListener{
	public void on(DeviceTriggerEvent e);
}
