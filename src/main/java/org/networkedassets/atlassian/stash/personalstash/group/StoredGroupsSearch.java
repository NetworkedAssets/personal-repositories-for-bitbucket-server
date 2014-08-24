package org.networkedassets.atlassian.stash.personalstash.group;

import java.util.Set;

public interface StoredGroupsSearch {
	Set<String> findNonStoredGroups(String searchKeyword);
}
