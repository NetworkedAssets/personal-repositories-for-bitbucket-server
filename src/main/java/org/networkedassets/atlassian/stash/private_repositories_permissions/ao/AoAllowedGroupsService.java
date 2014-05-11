package org.networkedassets.atlassian.stash.private_repositories_permissions.ao;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.java.ao.Query;

import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedGroupsService;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.stash.user.StashUser;
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
		Group group = ao.create(Group.class);
		group.setName(groupName);
		group.save();
		return group;
	}

	private Group findGroup(String groupName) {
		Group[] groups = ao.find(Group.class,
				Query.select().where("name = ?", groupName));
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
			for (Group group : allowedGroups) {
				if (groupName != group.getName()) {
					filteredGroupNames.add(groupName);
				}
			}
		}

		return filteredGroupNames;
	}
}
