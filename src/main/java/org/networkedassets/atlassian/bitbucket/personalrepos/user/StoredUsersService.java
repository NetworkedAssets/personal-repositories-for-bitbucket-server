package org.networkedassets.atlassian.bitbucket.personalrepos.user;

import java.util.Set;

import com.atlassian.bitbucket.user.ApplicationUser;

public interface StoredUsersService {

	Set<ApplicationUser> getAll();

	Set<ApplicationUser> add(Set<Integer> userIds);

	void remove(Set<Integer> userIds);

	void removeAll();
	
	boolean isAllowed(Integer userId);
	
	boolean isDenied(Integer userId);

}
