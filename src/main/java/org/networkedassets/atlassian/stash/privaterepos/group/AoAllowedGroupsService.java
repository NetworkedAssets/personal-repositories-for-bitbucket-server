package org.networkedassets.atlassian.stash.privaterepos.group;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import net.java.ao.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.PageRequestImpl;

@Component
@Transactional
public class AoAllowedGroupsService implements AllowedGroupsService {

	private static final int MAX_FOUND_USERS = 20;

	@Autowired
	private ActiveObjects ao;
	@Autowired
	private UserService userService;

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
