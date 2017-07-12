package org.doomday.server.beans;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Dashboard implements Cloneable,Serializable{	
	private static final long serialVersionUID = -6432347898775302497L;
	private String _id;
	private String title;
	private String icon;
	@JsonIgnore
	private String[] accessTo;
	@JsonIgnore
	private String[] allowTo;
	
	private Collection<Widget> widgets;
	public Dashboard() {
		super();
	}

	public Dashboard(String _id, String title, String icon) {
		super();
		this._id = _id;
		this.title = title;
		this.icon = icon;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String[] getAccessTo() {
		return accessTo;
	}

	public void setAccessTo(String[] accessTo) {
		this.accessTo = accessTo;
	}

	public String[] getAllowTo() {
		return allowTo;
	}

	public void setAllowTo(String[] allowTo) {
		this.allowTo = allowTo;
	}
	
	
	public Collection<Widget> getWidgets() {
		return widgets;
	}
	
	public void setWidgets(Collection<Widget> widgets) {
		this.widgets = widgets;
	}
	
	public Dashboard clone(){
		try {
			return (Dashboard) super.clone();
		} catch (CloneNotSupportedException e) {		
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
