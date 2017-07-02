package org.doomday.server.eventbus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SubscriberList extends HashMap<Class<?>, Set<Subscriber>>{
	private static final long serialVersionUID = -2926758520174290132L;

	public void put(Subscriber value) {
		
		Class<?> key = value.getMsgClass();		
		Set<Subscriber> s = get(key);
		if (s==null){
			s = new HashSet<>();
			put(key,s);
		}
		s.add(value);
	}
	
	public void remove(Subscriber value){
		Set<Subscriber> s = get(value.getMsgClass());
		if (s!=null){
			s.remove(value);
		}
	}
	
}
