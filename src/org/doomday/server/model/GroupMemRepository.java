package org.doomday.server.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.doomday.server.beans.Dashboard;
import org.doomday.server.beans.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class GroupMemRepository implements IGroupRepository{

	private Map<String, Group> groups = new HashMap<>();
	
	@Autowired
	ObjectMapper mapper;
	
	@PostConstruct
	public void init() {
		try{
			FileReader fr = new FileReader("/tmp/groups.json");
			BufferedReader br = new BufferedReader(fr);		
			String line = "";
			while((line=br.readLine())!=null){
				Group g = mapper.readValue(line, Group.class);
				//dashboards.add(d);
//				dashes.put(d.get_id(), d);
				groups.put(g.get_id(), g);
			}
			br.close();
		} catch (IOException e){
			
		}
	}
	

	@PreDestroy
	public void destroy() {
		try{
			FileWriter fw = new FileWriter("/tmp/groups.json");
			groups.values()
			.forEach(g->{
				try {
					fw.write(mapper.writeValueAsString(g)+"\n");
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
	public Group saveGroup(Group group) {
		if (group.get_id()==null){
			group.set_id(Integer.toHexString(group.hashCode()));
		}
		
		if (groups.containsKey(group.get_id())){
			return groups.get(group.get_id()).merge(group);
		} else {			
			groups.put(group.get_id(), group);
			return group;
		}
	}

	@Override
	public Collection<Group> listGroups() {
		return groups.values();
	}

	@Override
	public boolean remove(Group group) {
		return groups.remove(group.get_id())==null;
	}
	
}
