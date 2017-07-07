package org.doomday.server.event.dashboard;

import org.doomday.server.beans.Dashboard;

public class DashboardSavedEvent {
	private final Dashboard dashboard;

	public DashboardSavedEvent(Dashboard dashboard) {
		super();
		this.dashboard = dashboard;
	}

	public Dashboard getDashboard() {
		return dashboard;
	}
	
	
}
