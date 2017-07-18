package org.doomday.server.plugin.webclient.ctrl;

import java.util.Collection;

import org.doomday.server.beans.Widget;
import org.doomday.server.model.IWidgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("webclient-widgetctrl")
@RequestMapping("/webclient/widget")
@CrossOrigin(origins="*")
public class WidgetCtrl {
	
	@Autowired
	IWidgetRepository widgetRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Collection<Widget> listWidgets(){
		return widgetRepository.getWidgets();
	}
	
}
