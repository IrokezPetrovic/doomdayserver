package org.doomday.server.eventbus.rx;

import java.util.HashSet;

import io.reactivex.disposables.Disposable;

public class Disposables extends HashSet<Disposable> implements IDisposableStorage{
	
	/* (non-Javadoc)
	 * @see org.doomday.server.eventbus.rx.IDisposableStorage#add(io.reactivex.disposables.Disposable)
	 */
	@Override
	public boolean add(Disposable d){		
		return super.add(d);
	}
	
	/* (non-Javadoc)
	 * @see org.doomday.server.eventbus.rx.IDisposableStorage#disposeAll()
	 */
	@Override
	public void disposeAll(){
		stream().forEach(Disposable::dispose);
	}
	
}
