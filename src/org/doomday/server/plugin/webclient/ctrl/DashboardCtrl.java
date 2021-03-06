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

@Controller("webclient-dashboard-controller")
@RequestMapping("/webclient/dashboards")
@CrossOrigin(origins="*")
public class DashboardCtrl {
	
	@Autowired
	IDashboardRepository dashboardRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Collection<Dashboard> listDashboards(){
		return dashboardRepository.listDashboards();
	}
}
