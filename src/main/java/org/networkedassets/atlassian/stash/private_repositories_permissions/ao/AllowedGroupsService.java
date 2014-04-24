package org.networkedassets.atlassian.stash.private_repositories_permissions.ao;

import java.util.List;

public interface AllowedGroupsService {

	List<Group> all();
	Group allow(Group group);
	void disallow(Group group);
	
}
