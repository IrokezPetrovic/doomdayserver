package org.doomday.server.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.doomday.server.beans.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupMemRepository implements IGroupRepository{

	private Map<String, Group> groups = new HashMap<>();
	@Override
	public Group saveGroup(Group group) {
		if (group.get_id()==null){
			group.set_id(Integer.toHexString(group.hashCode()));
		}
		
		if (groups.containsKey(group.get_id())){
			return groups.get(group.get_id()).merge(group);
		} else {			
			groups.put(group.get_id(), group);
			return group;
		}
	}

	@Override
	public Collection<Group> listGroups() {
		return groups.values();
	}

	@Override
	public boolean remove(Group group) {
		return groups.remove(group.get_id())==null;
	}
	
}
