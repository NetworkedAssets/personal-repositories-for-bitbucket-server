package org.networkedassets.atlassian.bitbucket.personalrepos.group;

import java.util.Set;

public interface StoredGroupsSearch {
	Set<String> findNonStoredGroups(String searchKeyword);
}
