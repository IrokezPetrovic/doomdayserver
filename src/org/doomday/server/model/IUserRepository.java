package org.doomday.server.model;

import java.util.Collection;

import org.doomday.server.beans.User;

public interface IUserRepository {

	User addUser(User user);

	Collection<User> listUsers();

	boolean remove(User user);

}
