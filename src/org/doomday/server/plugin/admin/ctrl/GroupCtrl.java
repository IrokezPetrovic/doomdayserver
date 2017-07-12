package org.doomday.server.plugin.admin.ctrl;

import java.util.Collection;

import org.doomday.server.beans.Group;
import org.doomday.server.model.IGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/group")
@CrossOrigin(origins="*")
public class GroupCtrl {
	
	@Autowired
	IGroupRepository groupRepo;
	
	@RequestMapping(method=RequestMethod.POST,path="/save")
	@ResponseBody
	public Group saveGroup(@RequestBody Group group){
		return groupRepo.saveGroup(group);
	}
	
	@RequestMapping(method=RequestMethod.GET,path="/list")
	@ResponseBody
	public Collection<Group> listGroups(){
		return groupRepo.listGroups();
	}
	
	@RequestMapping(method=RequestMethod.POST,path="/remove")
	@ResponseBody
	public boolean removeGroup(@RequestBody Group group){
		return groupRepo.remove(group);
	}
	
}
