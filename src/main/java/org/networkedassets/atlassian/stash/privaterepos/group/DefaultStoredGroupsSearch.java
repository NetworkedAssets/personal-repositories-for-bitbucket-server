package org.networkedassets.atlassian.stash.privaterepos.group;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.PageRequestImpl;
import com.google.common.collect.Sets;

@Component
public class DefaultStoredGroupsSearch implements StoredGroupsSearch {

	private static final int MAX_FOUND_GROUPS = 20;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupsRepository groupsRepository;

	private Logger log = LoggerFactory
			.getLogger(DefaultStoredGroupsSearch.class);

	@Override
	public Set<String> findNonStoredGroups(String searchKeyword) {
		Set<String> storedGroups = groupsRepository.getAll();
		int currentPage = 1;
		Set<String> groups = findGroupsByName(searchKeyword, currentPage);
		int foundGroupsCount = groups.size();
		groups.removeAll(storedGroups);

		while (groups.isEmpty() && foundGroupsCount == MAX_FOUND_GROUPS) {
			groups = findGroupsByName(searchKeyword, ++currentPage);
			foundGroupsCount = groups.size();
			groups.removeAll(storedGroups);
		}
		return groups;
	}

	private Set<String> findGroupsByName(String searchKeyword, int page) {
		int offset = MAX_FOUND_GROUPS * (page - 1);
		int limit = offset + MAX_FOUND_GROUPS;
		log.debug("Searching for groups by {} with limit {} and offset {}",
				searchKeyword, limit, offset);
		Iterable<String> foundGroups = userService.findGroupsByName(
				searchKeyword, new PageRequestImpl(offset, limit)).getValues();
		return Sets.newLinkedHashSet(foundGroups);
	}
}
