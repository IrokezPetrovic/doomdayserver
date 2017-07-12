package org.doomday.server.beans;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {
	private String _id;
	private String username;
	
	private Boolean enabled;	
	private String login;
	
	private String passwd;
	
	private Set<String> groups = new HashSet<>();

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Set<String> getGroups() {
		return groups;
	}

	public void setGroups(Set<String> groups) {
		this.groups = groups;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public User merge(User user) {
		this.enabled = user.enabled!=null?user.enabled:this.enabled;
		this.username = user.username!=null?user.username:this.username;
		this.login = user.login!=null?user.login:this.login;
		this.groups = user.groups!=null?user.groups:this.groups;
		return this;
	}

	
	
	
	
}
