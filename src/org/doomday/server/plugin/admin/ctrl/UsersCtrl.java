package org.doomday.server.plugin.admin.ctrl;

import java.util.Collection;

import org.doomday.server.beans.User;
import org.doomday.server.model.IGroupRepository;
import org.doomday.server.model.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/users")
@CrossOrigin(origins="*")
public class UsersCtrl {
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IGroupRepository groupRepository;
	
	@RequestMapping(method=RequestMethod.POST,path="/save")
	@ResponseBody
	public User save(@RequestBody User user){
		return userRepository.saveUser(user);
	}
	
	@RequestMapping(method=RequestMethod.GET,path="/list")
	@ResponseBody
	public Collection<User> listUsers(){
		return userRepository.listUsers();
	}
	
	@RequestMapping(method=RequestMethod.POST,path="/remove")
	@ResponseBody
	public boolean remove (@RequestBody User user){
		return userRepository.remove(user);
	}
	
}
