package org.doomday.server.plugin.admin.ctrl;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.doomday.server.beans.Widget;
import org.doomday.server.model.IWidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/widgets")
@CrossOrigin(origins="*")
public class WidgetCtrl {

	@Autowired
	IWidgetRepository widgetRepo;
	
	
	@RequestMapping(path="/add",method=RequestMethod.POST)
	@ResponseBody
	public Collection<Widget>addWidget(@RequestParam("dashboards") String[] dashboards, @RequestParam("widget") Widget widget){
		return Stream.of(dashboards)
		.map(dashboard -> {
			Widget w = (Widget) widget.clone();
			w.setDashboardId(dashboard);
			return widgetRepo.save(w);
		}).collect(Collectors.toList());
		
	}
	
	
}
