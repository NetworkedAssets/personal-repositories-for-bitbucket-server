package org.networkedassets.atlassian.stash.privaterepos.group.rest;

import java.util.ArrayList;
import java.util.List;

import org.networkedassets.atlassian.stash.privaterepos.group.AllowedGroupsService;
import org.networkedassets.atlassian.stash.privaterepos.group.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.nav.NavBuilder;

@Component
public class GroupsInfoBuilder {

	@Autowired
	private AllowedGroupsService allowedGroupsService;
	@Autowired
	private NavBuilder navBuilder;

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
