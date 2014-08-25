package org.networkedassets.atlassian.stash.personalstash.group;

import java.util.Set;

import com.atlassian.stash.user.StashUser;

public interface UserGroupsService {
	Set<String> getUserGroups(StashUser user);
}
