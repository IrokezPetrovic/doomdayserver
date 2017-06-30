package org.doomday.server.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryWorker implements Runnable{
	@Autowired(required=false)
	List<IDiscoverService> discoverers;

	
	private Thread t;
	
	@PostConstruct
	public void init(){
		System.out.println("WORKER CONSTRUCT");
		t = new Thread(this);
		t.start();
	}
		
	@PreDestroy
	public void destroy(){
		System.out.println("WORKER DESTROY");
		t.interrupt();
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()){			
			if (discoverers!=null)
				discoverers.forEach((d)->d.discover());
			
		}
		
	}
	
	
	
}
