package org.doomday.server.plugin.webclient.ctrl;

import java.util.Collection;

import org.doomday.server.beans.Dashboard;
import org.doomday.server.model.IDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/webclient/dashboard")
@CrossOrigin(origins="*")
public class WebclientDashboardCtrl {
	
	@Autowired
	IDashboardRepository dashboardRepo;
	
	@RequestMapping(method=RequestMethod.GET,path="/list")
	@ResponseBody
	public Collection<Dashboard> listDashboards(){
		return dashboardRepo.listDashboards();
	}

}
