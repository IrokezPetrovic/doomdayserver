package org.doomday.server.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.doomday.server.beans.Dashboard;
import org.doomday.server.event.dashboard.DashboardRemoveEvent;
import org.doomday.server.event.dashboard.DashboardSavedEvent;
import org.doomday.server.eventbus.IEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DashboardMemRepository implements IDashboardRepository{
	//Set<Dashboard> dashboards = new HashSet<>();
	Map<String, Dashboard> dashes = new HashMap<>();
	
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	IEventBus eventBus;
	
	@PostConstruct
	public void init() {
		try{
			FileReader fr = new FileReader("/tmp/dashboards.conf");
			BufferedReader br = new BufferedReader(fr);		
			String line = "";
			while((line=br.readLine())!=null){
				Dashboard d = mapper.readValue(line, Dashboard.class);
				//dashboards.add(d);
				dashes.put(d.get_id(), d);
			}
			br.close();
		} catch (IOException e){
			
		}
	}
	
	@PreDestroy
	public void destroy() {
		try{
			FileWriter fw = new FileWriter("/tmp/dashboards.conf");
			dashes.values()
			.forEach(d->{
				try {
					fw.write(mapper.writeValueAsString(d)+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});		
			fw.flush();
			fw.close();
		} catch(IOException e){
			
		}
	}
	
	
	@Override
	public Collection<Dashboard> listDashboards() {
		return dashes.values();
	}

	@Override
	public Dashboard save(Dashboard dashboard) {
		if (dashboard.get_id()==null){
			dashboard.set_id(Integer.toHexString(dashboard.hashCode()));	
		}
		dashes.put(dashboard.get_id(), dashboard);
		eventBus.pub("/dashboard", new DashboardSavedEvent(dashboard));
		return dashboard;
	}

	@Override
	public boolean remove(Dashboard dashboard) {		
		if (dashes.remove(dashboard.get_id()).get_id()==dashboard.get_id()){
			eventBus.pub("/dashboard", new DashboardRemoveEvent(dashboard));
			return true;
		} else {
			return false;
		}
		
	}

}
