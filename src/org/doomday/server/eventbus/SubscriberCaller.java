package org.doomday.server.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class SubscriberCaller implements Runnable{
	private final Object e;
	private final Set<Subscriber> subs;
	
	public SubscriberCaller(Object e, Set<Subscriber> subs) {
		super();
		this.e = e;
		this.subs = subs;
	}

	@Override
	public void run() {
		subs.forEach(s->{
			try {
				boolean a = s.getMethod().isAccessible();
				s.getMethod().setAccessible(true);				
				s.getMethod().invoke(s.getSubs(), e);
				s.getMethod().setAccessible(a);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				System.out.println(s.getMethod().getParameters()[0].getType());
				System.out.println(e.getClass());
				e.printStackTrace();
			}
		});
		
	}

}
