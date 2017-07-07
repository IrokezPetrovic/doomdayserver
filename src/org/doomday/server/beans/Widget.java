package org.doomday.server.beans;

public class Widget {
	private String _id;
	private String label;
	private String description;
	private String config;
	public Widget(String _id, String label, String description, String config) {
		super();
		this._id = _id;
		this.label = label;
		this.description = description;
		this.config = config;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	
	
	
	
	
	
}
