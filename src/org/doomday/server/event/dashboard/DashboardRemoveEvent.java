package org.doomday.server.event.dashboard;

import org.doomday.server.beans.Dashboard;

public class DashboardRemoveEvent {
	private final Dashboard dashboard;

	public DashboardRemoveEvent(Dashboard dashboard) {
		super();
		this.dashboard = dashboard;
	}

	public Dashboard getDashboard() {
		return dashboard;
	}
	
	
	
}
