package org.doomday.server.model;

import java.util.Collection;

import org.doomday.server.beans.User;
import org.springframework.stereotype.Component;

@Component
public class UserMemRepository implements IUserRepository{

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<User> listUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
