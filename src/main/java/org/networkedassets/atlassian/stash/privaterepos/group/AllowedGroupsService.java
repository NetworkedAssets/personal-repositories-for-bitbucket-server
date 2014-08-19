package org.networkedassets.atlassian.stash.privaterepos.group;

import java.util.List;

public interface AllowedGroupsService {

	List<Group> all();

	void disallow(String groupName);

	Group allow(String groupName);

	List<Group> allow(List<String> groupNames);

	/**
	 * Gets list of group names matching given key which are not allowed to use
	 * private repos.
	 * 
	 * @param key
	 * @return
	 */
	List<String> findNotAllowed(String key);

}
