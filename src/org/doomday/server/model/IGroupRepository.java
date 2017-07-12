package org.doomday.server.model;

import java.util.Collection;

import org.doomday.server.beans.Group;

public interface IGroupRepository {

	Group saveGroup(Group group);

	Collection<Group> listGroups();

	boolean remove(Group group);

}
