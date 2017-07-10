package org.doomday.server.beans;

import org.doomday.server.misc.KeepAsJsonDeserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Widget implements Cloneable{
	private String _id;
	private String label;
	private String description;
	@JsonProperty("class")	
	private String widgetClass;
	private String icon;
	
	@JsonRawValue
	@JsonDeserialize(using=KeepAsJsonDeserializer.class)
	private String config;
	private String dashboardId;
	public Widget() {
		
	}
	
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
	public String getDashboardId() {
		return dashboardId;
	}
	public void setDashboardId(String dashboardId) {
		this.dashboardId = dashboardId;
	}	
	public String getWidgetClass() {
		return widgetClass;
	}	
	public void setWidgetClass(String widgetClass) {
		this.widgetClass = widgetClass;
	}	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Widget clone(){
		try {
			return (Widget) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
}
