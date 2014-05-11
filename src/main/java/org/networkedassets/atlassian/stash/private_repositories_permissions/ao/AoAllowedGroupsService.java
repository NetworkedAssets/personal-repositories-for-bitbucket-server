package org.networkedassets.atlassian.stash.private_repositories_permissions.ao;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import net.java.ao.Query;

import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedGroupsService;

import com.atlassian.activeobjects.external.ActiveObjects;

public class AoAllowedGroupsService implements AllowedGroupsService {
	
	private final ActiveObjects ao;

    public AoAllowedGroupsService(ActiveObjects ao)
    {
        this.ao = checkNotNull(ao);
    }

	@Override
	public List<Group> all() {
		return newArrayList(ao.find(Group.class, Query.select()));
	}

	@Override
	public void disallow(String groupName) {
		Group group = findGroup(groupName);
		if (group != null) {
			ao.delete(group);
		}
	}

	@Override
	public Group allow(String groupName) {
		Group group = ao.create(Group.class);
		group.setName(groupName);
		group.save();
		return group;
	}

	private Group findGroup(String groupName) {
		Group[] groups = ao.find(Group.class, Query.select().where("name = ?", groupName));
		if (groups.length == 0) {
			return null;
		} else {
			return groups[0];
		}
	}
}
