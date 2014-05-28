package org.networkedassets.atlassian.stash.privaterepos.rest;

import java.util.ArrayList;
import java.util.List;

import org.networkedassets.atlassian.stash.privaterepos.ao.Group;
import org.networkedassets.atlassian.stash.privaterepos.service.AllowedGroupsService;

import com.atlassian.stash.nav.NavBuilder;

public class GroupsInfoBuilder {

	private final AllowedGroupsService allowedGroupsService;
	private final NavBuilder navBuilder;

	public GroupsInfoBuilder(AllowedGroupsService allowedGroupsService,
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
	
	public List<GroupInfo> build(List<String> groupNames) {
		List<GroupInfo> groupsInfo = new ArrayList<GroupInfo>();

		for (String groupName : groupNames) {
			groupsInfo.add(createGroupInfo(groupName));
		}

		return groupsInfo;
	}
	
	private GroupInfo createGroupInfo(Group group) {
		String name = group.getName();
		return createGroupInfo(name);
	}
	
	private GroupInfo createGroupInfo(String groupName) {
		GroupInfo info = new GroupInfo();

		info.setName(groupName);
		info.setViewUrl(navBuilder.admin().groups().view(groupName).buildRelative());

		return info;
	}

}
