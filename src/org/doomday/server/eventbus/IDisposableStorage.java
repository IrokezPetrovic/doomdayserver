package org.doomday.server.eventbus;

import io.reactivex.disposables.Disposable;

public interface IDisposableStorage {

	boolean add(Disposable d);

	void disposeAll();

}