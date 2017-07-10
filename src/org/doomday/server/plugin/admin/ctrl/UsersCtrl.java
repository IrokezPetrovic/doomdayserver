package org.doomday.server.plugin.admin.ctrl;

import java.util.Collection;

import org.doomday.server.beans.User;
import org.doomday.server.model.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

@Controller
@RequestMapping("/admin/users")
@CrossOrigin(origins="*")
public class UsersCtrl {
	
	@Autowired
	IUserRepository userRepository;
	
	@RequestMapping("/save")
	public User save(@RequestPart User user){
		return userRepository.addUser(user);
	}
	
	@RequestMapping("/list")
	public Collection<User> listUsers(){
		return userRepository.listUsers();
	}
	
	@RequestMapping("/remove")
	public boolean remove (User user){
		return userRepository.remove(user);
	}
	
}
