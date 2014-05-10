package org.networkedassets.atlassian.stash.private_repositories_permissions.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.Group;
import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedGroupsService;

import com.atlassian.stash.nav.NavBuilder;
import com.atlassian.stash.nav.NavBuilder.Groups;
import com.atlassian.stash.user.UserService;

public class GroupTemplateParametersBuilder {

	private final AllowedGroupsService allowedGroupsService;
	private final NavBuilder navBuilder;

	public GroupTemplateParametersBuilder(AllowedGroupsService allowedGroupsService,
			UserService userService, NavBuilder navBuilder) {
		this.allowedGroupsService = allowedGroupsService;
		this.navBuilder = navBuilder;
	}

	public List<Object> build() {
		List<Object> groupParams = new ArrayList<Object>();
		
		List<Group> groups = allowedGroupsService.all();
		for (Group group : groups) {
			groupParams.add(createGroupParams(group));
		}
		
		return groupParams;
	}

	private Object createGroupParams(Group group) {
		Map<String, Object> params = new HashMap<String, Object>();

		String name = group.getName();
		
		params.put("name", name);
		
		Groups groupsNavBuilder = navBuilder.admin().groups();
		params.put("viewUrl", groupsNavBuilder.view(name).buildRelative());
		params.put("deleteUrl", groupsNavBuilder.delete(name).buildRelative());

		return params;
	}
}
