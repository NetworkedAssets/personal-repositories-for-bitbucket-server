package org.networkedassets.atlassian.stash.privaterepos.group;

import java.util.LinkedHashSet;
import java.util.Set;

import org.networkedassets.atlassian.stash.privaterepos.util.AllPagesIterator;
import org.networkedassets.atlassian.stash.privaterepos.util.PageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;
import com.google.common.collect.Sets;

@Component
public class DefaultUserGroupsService implements UserGroupsService {

	/**
	 * Get a lot - that probably will be enough for most cases to get it in one
	 * hit
	 */
	private static final int GROUPS_PER_PAGE_COUNT = 1000;

	@Autowired
	private UserService userService;

	@Override
	public Set<String> getUserGroups(final StashUser user) {
		final Set<String> groups = new LinkedHashSet<String>();
		AllPagesIterator<String> iterator = new AllPagesIterator.Builder<String>(
				createPageProcessor(user, groups)).resultsPerPage(
				GROUPS_PER_PAGE_COUNT).build();
		iterator.processAllPages();
		return groups;
	}

	private PageProcessor<String> createPageProcessor(final StashUser user,
			final Set<String> groups) {
		return new PageProcessor<String>() {

			@Override
			public void process(Page<? extends String> page) {
				groups.addAll(Sets.newHashSet(page.getValues()));
			}

			@Override
			public Page<? extends String> fetchPage(PageRequest pageRequest) {
				return userService
						.findGroupsByName(user.getName(), pageRequest);
			}

		};
	}
}
