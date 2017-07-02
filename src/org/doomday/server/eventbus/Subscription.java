package org.doomday.server.eventbus;

import java.util.List;

public class Subscription {
	private final EventBus bus;
	private final Object subscriber;
	private final List<Class<?>> targetClasses;
	
	
	public Subscription(EventBus bus, Object subscriber, List<Class<?>> targetClasses) {
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
