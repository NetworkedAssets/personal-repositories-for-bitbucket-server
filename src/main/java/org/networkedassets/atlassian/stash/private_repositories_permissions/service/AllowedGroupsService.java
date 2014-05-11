package org.networkedassets.atlassian.stash.private_repositories_permissions.service;

import java.util.List;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.Group;

import com.atlassian.activeobjects.tx.Transactional;

@Transactional
public interface AllowedGroupsService {

	List<Group> all();
	void disallow(String groupName);
	Group allow(String groupName);
	
}
