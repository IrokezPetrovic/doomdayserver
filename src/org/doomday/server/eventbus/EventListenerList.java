package org.doomday.server.eventbus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventListenerList<T extends IDeviceEventListener> {
	private Map<String,List<T>> l = new HashMap<>();
	
	public void add(String key, T listener){
		List<T> list = l.get(key);
		if (list==null){
			list = new ArrayList<>();
			l.put(key, list);
		}
		list.add(listener);
	}
	
	public Collection<T> get(String key){
		if (l.containsKey(key)){
			return l.get(key);
		} else {
			return new ArrayList<>();
		}
	}
	
	
	
}
