package org.doomday.server.beans;

import java.util.HashSet;
import java.util.Set;

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
	private Boolean enabled;
	
	@JsonRawValue
	@JsonDeserialize(using=KeepAsJsonDeserializer.class)
	private String config;
	private Set<String> dashboards = new HashSet<>();
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
	public Set<String> getDashboards() {
		return dashboards;
	}
	public void setDashboards(Set<String> dashboards) {
		this.dashboards = dashboards;
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
		
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Widget clone(){
		try {
			return (Widget) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Widget merge(Widget w) {
		this.config = w.config!=null?w.config:this.config;
		this.dashboards = w.dashboards!=null?w.dashboards:this.dashboards;
		this.description = w.description!=null?w.description:this.description;
		this.icon = w.icon!=null?w.icon:this.icon;
		this.label = w.label!=null?w.label:this.label;
		this.enabled = w.enabled!=null?w.enabled:this.enabled;
		
		return this;
	}
	
	
	
	
	
}
