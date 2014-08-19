package org.networkedassets.atlassian.stash.privaterepos.user;

import java.util.Set;

import com.atlassian.stash.user.StashUser;

public interface StoredUsersService {

	Set<StashUser> getAll();

	Set<StashUser> add(Set<Integer> userIds);

	void remove(Set<Integer> userIds);

	void removeAll();
	
	boolean isAllowed(Integer userId);
	
	boolean isDenied(Integer userId);

}
