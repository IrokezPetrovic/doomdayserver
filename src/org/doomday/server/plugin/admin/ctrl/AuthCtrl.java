package org.doomday.server.plugin.admin.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/")
public class AuthCtrl {
	@RequestMapping(path="auth",method=RequestMethod.GET)
	@ResponseBody
	public String auth(){
		return "AUTH";
	}
	
}
