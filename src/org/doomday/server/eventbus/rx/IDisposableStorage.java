package org.doomday.server.eventbus.rx;

import io.reactivex.disposables.Disposable;

public interface IDisposableStorage {

	boolean add(Disposable d);

	void disposeAll();

}