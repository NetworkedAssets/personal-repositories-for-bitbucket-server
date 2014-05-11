package org.networkedassets.atlassian.stash.private_repositories_permissions.service;

import java.util.List;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.User;

import com.atlassian.activeobjects.tx.Transactional;

@Transactional
public interface AllowedUsersService {

	List<User> all();
	boolean isAllowed(String userName);
	void disallow(String userName);
	User allow(String userName);
	
}
