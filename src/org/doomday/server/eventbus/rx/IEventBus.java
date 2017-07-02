package org.doomday.server.eventbus.rx;

import io.reactivex.Observable;

public interface IEventBus {

	void pub(String key, Object val);

	Observable<Object> get(String key);

}