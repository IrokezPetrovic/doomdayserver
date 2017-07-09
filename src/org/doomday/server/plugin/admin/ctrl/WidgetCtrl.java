package org.doomday.server.plugin.admin.ctrl;

import org.doomday.server.beans.Widget;
import org.doomday.server.model.IWidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/widgets")
@CrossOrigin(origins="*")
public class WidgetCtrl {

	@Autowired
	IWidgetRepository widgetRepo;
	
	
	@RequestMapping(path="/add",method=RequestMethod.POST)
	@ResponseBody
	public boolean addWidget(@RequestParam("dashboards") String dashboards, @RequestParam("widget") String widget){
		System.out.println("Add widgets "+widget+" to "+dashboards);
		//System.out.println(widget.getLabel()+" "+widget.getDescription());
		//widgetRepo.addWidget(dashboards,widget);
		return true;
	}
	
	
}
