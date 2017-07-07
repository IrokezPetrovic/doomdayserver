package org.doomday.server.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.doomday.server.beans.Dashboard;
import org.doomday.server.beans.Widget;
import org.springframework.stereotype.Component;

@Component
public class WidgetMemRepository implements IWidgetRepository{

	private Map<String, Set<Widget>> widgets = new HashMap<>();
	
	
	
	@Override
	public void addWidget(String[] dashboards, Widget widget) {
		Stream.of(dashboards)
		.map(d->widgets.containsKey(d)?widgets.get(d):widgets.put(d, new HashSet<>()))
		.forEach(w->w.add(widget));
	}
	public Collection<Widget> getWidgets(Dashboard dashboard){
		return null;
	}

}
