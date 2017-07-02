package org.doomday.server.eventbus;

public interface IEventBus {

	Subscription subscribe(Object subscriber);

	void emit(Object event);

	void emit(String route, Object event);

	void emit(String route, Object event, boolean propogation);

}