package org.doomday.server.eventbus;

import java.lang.reflect.Method;

public class Subscriber {
	private final Class<?> msgClass;
	private final Method method;
	private final Object subs;
	private final String key;
	public Subscriber(Class<?> msgClass, Method method, Object subs,String key) {
		super();
		this.msgClass = msgClass;
		this.method = method;
		this.subs = subs;
		this.key = key;
	}
	public Class<?> getMsgClass() {
		return msgClass;
	}
	public Method getMethod() {
		return method;
	}
	public Object getSubs() {
		return subs;
	}
	
	
	
	
	
}
