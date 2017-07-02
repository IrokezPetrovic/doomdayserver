package org.doomday.server.eventbus.rx;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class EventBus implements IEventBus {
	private Map<String, Subject<Object>> buses = new HashMap<String, Subject<Object>>();
	
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	/* (non-Javadoc)
	 * @see org.doomday.server.eventbus.rx.IEventBus#pub(java.lang.String, java.lang.Object)
	 */
	@Override
	public void pub(String key,Object val){
		Subject<Object> subj = buses.get(key);
		if (subj==null){
			subj = PublishSubject.create();
			buses.put(key, subj);
		}
		subj.onNext(val);
	}
		
	/* (non-Javadoc)
	 * @see org.doomday.server.eventbus.rx.IEventBus#get(java.lang.String)
	 */
	@Override
	public Observable<Object> get(String key){				
		Subject<Object> subj = buses.get(key);
		if (subj==null){
			subj = PublishSubject.create();
			buses.put(key, subj);
		}
		return subj.observeOn(Schedulers.from(executor));
	}
}
