package org.networkedassets.atlassian.stash.private_repositories_permissions.ao;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import net.java.ao.Query;

import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedGroupsService;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.PageRequestImpl;

public class AoAllowedGroupsService implements AllowedGroupsService {

	private static final int MAX_FOUND_USERS = 20;

	private final ActiveObjects ao;

	private final UserService userService;

	public AoAllowedGroupsService(ActiveObjects ao, UserService userService) {
		this.userService = userService;
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
		return addGroup(groupName);
	}

	@Override
	public List<Group> allow(List<String> groupNames) {
		List<Group> groups = new ArrayList<Group>();
		for (String name : groupNames) {
			groups.add(addGroup(name));
		}
		return groups;
	}

	private Group addGroup(String name) {
		Group group = ao.create(Group.class);
		group.setName(name);
		group.save();
		return group;
	}

	private Group findGroup(String groupName) {
		Group[] groups = ao.find(Group.class,
				Query.select().where("NAME = ?", groupName));
		if (groups.length == 0) {
			return null;
		} else {
			return groups[0];
		}
	}

	@Override
	public List<String> findNotAllowed(String key) {
		List<Group> allowedGroups = all();
		Iterable<String> foundGroupNames = userService.findGroupsByName(key,
				new PageRequestImpl(0, MAX_FOUND_USERS)).getValues();

		List<String> filteredGroupNames = new ArrayList<String>();

		for (String groupName : foundGroupNames) {
			filteredGroupNames.add(groupName);
		}

		for (Group group : allowedGroups) {
			filteredGroupNames.remove(group.getName());
		}

		return filteredGroupNames;
	}

}
