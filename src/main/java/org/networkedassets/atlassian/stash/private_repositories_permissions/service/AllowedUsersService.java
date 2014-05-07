package org.networkedassets.atlassian.stash.private_repositories_permissions.service;

import java.util.List;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.User;

public interface AllowedUsersService {

	List<User> all();
	User allow(User user);
	void disallow(User user);
	
}
