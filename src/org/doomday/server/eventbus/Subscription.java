package org.doomday.server.eventbus;

import java.util.List;

public class Subscription {
	private final IEventBus bus;
	private final Object subscriber;
	private final List<Class<?>> targetClasses;
	
	
	public Subscription(IEventBus bus, Object subscriber, List<Class<?>> targetClasses) {
		super();
		this.bus = bus;
		this.subscriber = subscriber;
		this.targetClasses = targetClasses;
	}
	public void stopSubscribe(){
		
	};
	public void pauseSubscribe(){
		
	};
	
}
