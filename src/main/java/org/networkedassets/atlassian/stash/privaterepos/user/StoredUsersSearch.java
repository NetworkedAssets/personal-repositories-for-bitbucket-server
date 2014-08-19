package org.networkedassets.atlassian.stash.privaterepos.user;

import java.util.Set;

import com.atlassian.stash.user.StashUser;

public interface StoredUsersSearch {
	Set<StashUser> findNonStoredUsers(String searchKeyword);
}
