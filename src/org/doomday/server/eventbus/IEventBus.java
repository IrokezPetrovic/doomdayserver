package org.doomday.server.eventbus;

import io.reactivex.Observable;

public interface IEventBus {

	void pub(String key, Object val);

	Observable<Object> get(String key);

}