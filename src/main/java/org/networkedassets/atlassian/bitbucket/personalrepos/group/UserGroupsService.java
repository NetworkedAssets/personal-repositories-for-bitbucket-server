package org.networkedassets.atlassian.bitbucket.personalrepos.group;

import java.util.Set;

import com.atlassian.bitbucket.user.ApplicationUser;

public interface UserGroupsService {
	Set<String> getUserGroups(ApplicationUser user);
}
