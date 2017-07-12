package org.doomday.server.plugin.admin.ctrl;

import java.util.Collection;

import org.doomday.server.beans.Widget;
import org.doomday.server.model.IWidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Widget addWidget(@RequestParam("widget") Widget widget, @RequestParam("widget") String widgetSrc){
		System.out.println("Add widget "+widgetSrc);
		return widgetRepo.save(widget);				
	}
	
	
	@RequestMapping(path="/",method=RequestMethod.GET)
	@ResponseBody
	public Collection<Widget> listWidgets(){
		return widgetRepo.getWidgets();
	}
	
	@RequestMapping(path="/",method=RequestMethod.POST)
	@ResponseBody
	public Widget save(@RequestBody Widget widget){
		return widgetRepo.save(widget);
	}
	
	@RequestMapping(path="/",method=RequestMethod.DELETE)
	@ResponseBody
	public boolean remove(@RequestParam String id){
		return widgetRepo.remove(id);
	}
	
	@RequestMapping(path="/save",method=RequestMethod.POST)
	@ResponseBody
	public Widget saveWidget(@RequestBody Widget widget){
		return widgetRepo.save(widget);
	}
	
}
