package org.doomday.server.eventbus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;



@Component
public class EventBus implements IEventBus {
	SubscriberList subs = new SubscriberList();
	ExecutorService executor = Executors.newFixedThreadPool(4);
	
	/* (non-Javadoc)
	 * @see org.doomday.server.eventbus.IEventBus#subscribe(java.lang.Object)
	 */
	@Override
	public Subscription subscribe(Object subscriber){		
		List<Class<?>> targetClasses = Stream.of(subscriber.getClass().getMethods())		
		.filter(m->m.isAnnotationPresent(Reciever.class))		
		.map(m->{			
			Class<?> t = m.getParameters()[0].getType();
			Reciever annotation = m.getAnnotation(Reciever.class);						
			subs.put(new Subscriber(t, m, subscriber,annotation.value()));
			return t;
		}).collect(Collectors.toList());
		return new Subscription(this, subscriber, targetClasses);
		
	}
			
	/* (non-Javadoc)
	 * @see org.doomday.server.eventbus.IEventBus#emit(java.lang.Object)
	 */
	@Override
	public void emit(Object event){
		this.emit("",event,false);
	}
	/* (non-Javadoc)
	 * @see org.doomday.server.eventbus.IEventBus#emit(java.lang.String, java.lang.Object)
	 */
	@Override
	public void emit(String route,Object event){
		this.emit(route, event, false);
	}
	/* (non-Javadoc)
	 * @see org.doomday.server.eventbus.IEventBus#emit(java.lang.String, java.lang.Object, boolean)
	 */
	@Override
	public void emit(String route,Object event,boolean propogation){
		Class<?> eClass = event.getClass();
		do{
			executor.submit(new SubscriberCaller(event, subs.get(eClass)));
			eClass = eClass.getSuperclass();
			
		}while(eClass!=Object.class&&propogation);
	}
}
