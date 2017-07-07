package org.doomday.server.model;

import java.util.Collection;

import org.doomday.server.beans.Dashboard;

public interface IDashboardRepository {

	Collection<Dashboard> listDashboards();

	Dashboard save(Dashboard dashboard);

	boolean remove(Dashboard dashboard);

}
