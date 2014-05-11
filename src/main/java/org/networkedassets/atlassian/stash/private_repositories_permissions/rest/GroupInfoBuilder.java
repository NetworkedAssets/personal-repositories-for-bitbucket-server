package org.networkedassets.atlassian.stash.private_repositories_permissions.rest;

import java.util.ArrayList;
import java.util.List;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.Group;
import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedGroupsService;

import com.atlassian.stash.nav.NavBuilder;

public class GroupInfoBuilder {

	private final AllowedGroupsService allowedGroupsService;
	private final NavBuilder navBuilder;

	public GroupInfoBuilder(AllowedGroupsService allowedGroupsService,
			NavBuilder navBuilder) {
		this.allowedGroupsService = allowedGroupsService;
		this.navBuilder = navBuilder;
	}

	public List<GroupInfo> build() {
		List<GroupInfo> groupsInfo = new ArrayList<GroupInfo>();

		List<Group> groups = allowedGroupsService.all();
		for (Group group : groups) {
			groupsInfo.add(createGroupInfo(group));
		}

		return groupsInfo;
	}

	private GroupInfo createGroupInfo(Group group) {
		GroupInfo info = new GroupInfo();

		String name = group.getName();

		info.setName(name);
		info.setViewUrl(navBuilder.admin().groups().view(name).buildRelative());

		return info;
	}

}
