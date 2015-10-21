package org.networkedassets.atlassian.bitbucket.personalrepos.group.rest;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.nav.NavBuilder;

@Component
public class GroupsInfoBuilder {

	@Autowired
	private NavBuilder navBuilder;

	public Set<GroupInfo> createFrom(Set<String> groupNames) {
		Set<GroupInfo> groupsInfo = new LinkedHashSet<GroupInfo>();
		for (String group : groupNames) {
			groupsInfo.add(createGroupInfo(group));
		}
		return groupsInfo;
	}

	private GroupInfo createGroupInfo(String groupName) {
		GroupInfo info = new GroupInfo();

		info.setName(groupName);
		info.setViewUrl(navBuilder.admin().groups().view(groupName)
				.buildRelative());

		return info;
	}

}
