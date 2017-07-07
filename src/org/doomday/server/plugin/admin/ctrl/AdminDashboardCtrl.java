package org.doomday.server.plugin.admin.ctrl;

import java.util.Collection;

import org.doomday.server.beans.Dashboard;
import org.doomday.server.model.IDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/dashboard")
@CrossOrigin(origins="*")
public class AdminDashboardCtrl {

	@Autowired
	IDashboardRepository dashboardRepo;
	
	@RequestMapping(path="/list",method=RequestMethod.GET)
	@ResponseBody
	public Collection<Dashboard> list(){
		return dashboardRepo.listDashboards(); 
	}
		
	@RequestMapping(path="/save",method=RequestMethod.POST)
	@ResponseBody
	public Dashboard save(@RequestBody Dashboard dashboard){		
		return dashboardRepo.save(dashboard);
	}
	
	@RequestMapping(path="/remove",method=RequestMethod.POST)
	@ResponseBody
	public boolean remove(@RequestBody Dashboard dashboard){
		return dashboardRepo.remove(dashboard); 
	}
}
