package org.doomday.server.model;

import java.util.Collection;

import org.doomday.server.beans.Widget;

public interface IWidgetRepository {

	
	

	Widget save(Widget w);

	Collection<Widget> getWidgets(String get_id);

	Collection<Widget> getWidgets();

	boolean remove(String id);

}
