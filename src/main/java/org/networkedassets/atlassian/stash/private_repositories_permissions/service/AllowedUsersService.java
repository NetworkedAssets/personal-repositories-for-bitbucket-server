package org.networkedassets.atlassian.stash.private_repositories_permissions.service;

import java.util.List;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.User;

public interface AllowedUsersService {

	List<User> all();
	boolean isAllowed(String userName);
	void disallow(String userName);
	User allow(String userName);
	
}
