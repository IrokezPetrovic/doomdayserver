package org.doomday.server.eventbus.rx;

import java.util.HashSet;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.reactivex.disposables.Disposable;

@Component
@Scope("prototype")
public class Disposables extends HashSet<Disposable> implements IDisposableStorage{
	private static final long serialVersionUID = 5928207873620056883L;

	
	@Override
	public boolean add(Disposable d){		
		return super.add(d);
	}
	
	
	@Override
	public void disposeAll(){
		stream().forEach(Disposable::dispose);
	}
	
}
