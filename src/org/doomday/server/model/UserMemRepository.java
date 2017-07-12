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

import org.doomday.server.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserMemRepository implements IUserRepository{
	
	private Map<String,User> users = new HashMap<>();
	
	
	@Autowired
	ObjectMapper mapper;
	
	@PostConstruct
	public void init(){
		try{
			FileReader fr = new FileReader("/tmp/users.json");
			BufferedReader br = new BufferedReader(fr);		
			String line = "";
			while((line=br.readLine())!=null){
//				Dashboard d = mapper.readValue(line, Dashboard.class);
				//dashboards.add(d);
//				dashes.put(d.get_id(), d);
				User user = mapper.readValue(line, User.class);
				users.put(user.get_id(), user);
			}
			br.close();
		} catch (IOException e){
			
		}
	}
	
	@PreDestroy
	public void preDestroy(){
		try{
			FileWriter fw = new FileWriter("/tmp/groups.json");			
			users.values()
			.forEach(u->{
				try {
					fw.write(mapper.writeValueAsString(u)+"\n");
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
	public User saveUser(User user) {
		if (user.get_id()==null){
			user.set_id(Integer.toHexString(user.hashCode()));
		}
		if (users.containsKey(user.get_id())){
			return users.get(user.get_id()).merge(user);
		
		} else {			
			users.put(user.get_id(), user);
			return user;
			
		}
		
	}

	@Override
	public Collection<User> listUsers() {
		return users.values();
	}

	@Override
	public boolean remove(User user) {
		return users.remove(user.get_id())==null;
	}

}
