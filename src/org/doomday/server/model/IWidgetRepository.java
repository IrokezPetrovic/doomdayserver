package org.doomday.server.model;

import org.doomday.server.beans.Widget;

public interface IWidgetRepository {

	void addWidget(String[] dashboards, Widget widget);

}
