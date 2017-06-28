package org.doomday.server.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryWorker implements Runnable{
	@Autowired
	List<IDiscoverService> discoverers;

	
	@PostConstruct
	public void init(){
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		while(true){
			discoverers.forEach((d)->d.discover());
		}
		
	}
	
	
	
}
