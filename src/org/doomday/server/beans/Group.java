package org.doomday.server.beans;

public class Group {
	private String _id;
	private String name;
	private Boolean enabled;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Group merge(Group group) {
		this.name = group.name!=null?group.name:this.name;
		this.enabled = group.enabled!=null?group.enabled:this.enabled;
		return this;
	}
	
	
}
