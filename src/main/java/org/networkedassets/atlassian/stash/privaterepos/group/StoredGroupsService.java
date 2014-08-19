package org.networkedassets.atlassian.stash.privaterepos.group;

import java.util.Set;

public interface StoredGroupsService {

	Set<String> getAll();

	Set<String> add(Set<String> groupNames);

	void remove(Set<String> groupNames);

	void removeAll();

	boolean isAllowed(String group);

	boolean isDenied(String group);

}
