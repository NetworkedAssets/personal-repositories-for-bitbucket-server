package org.networkedassets.atlassian.bitbucket.personalrepos.user;

import java.util.Set;

import com.atlassian.bitbucket.user.ApplicationUser;

public interface StoredUsersSearch {
	Set<ApplicationUser> findNonStoredUsers(String searchKeyword);
}
