package org.networkedassets.atlassian.stash.privaterepos.group;

import java.util.Set;

public interface StoredGroupsSearch {
	Set<String> findNonStoredGroups(String searchKeyword);
}
