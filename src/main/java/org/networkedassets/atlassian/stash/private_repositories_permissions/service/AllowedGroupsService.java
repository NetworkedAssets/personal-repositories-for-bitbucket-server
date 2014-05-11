package org.networkedassets.atlassian.stash.private_repositories_permissions.service;

import java.util.List;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.Group;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.stash.user.StashUser;

@Transactional
public interface AllowedGroupsService {

	List<Group> all();
	void disallow(String groupName);
	Group allow(String groupName);
	
	/**
	 * Gets list of group names matching given key which are not allowed to use private repos.
	 * @param key
	 * @return
	 */
	List<String> findNotAllowed(String key);
	
}
