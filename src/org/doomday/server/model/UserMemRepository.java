package org.doomday.server.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.doomday.server.beans.User;
import org.springframework.stereotype.Component;

@Component
public class UserMemRepository implements IUserRepository{
	
	private Map<String,User> users = new HashMap<>();
	
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
