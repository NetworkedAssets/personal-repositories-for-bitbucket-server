package org.networkedassets.atlassian.bitbucket.personalrepos.group;

import java.util.LinkedHashSet;
import java.util.Set;

import org.networkedassets.atlassian.bitbucket.personalrepos.util.AllPagesIterator;
import org.networkedassets.atlassian.bitbucket.personalrepos.util.PageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.user.UserService;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;
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
	
	private final Logger log = LoggerFactory
			.getLogger(DefaultUserGroupsService.class);

	@Override
	public Set<String> getUserGroups(final ApplicationUser user) {
		final Set<String> groups = new LinkedHashSet<String>();
		AllPagesIterator<String> iterator = new AllPagesIterator.Builder<String>(
				createPageProcessor(user, groups)).resultsPerPage(
				GROUPS_PER_PAGE_COUNT).build();
		iterator.processAllPages();
		return groups;
	}

	private PageProcessor<String> createPageProcessor(final ApplicationUser user,
			final Set<String> groups) {
		return new PageProcessor<String>() {

			@Override
			public void process(Page<? extends String> page) {
				log.debug("Processing fetched groups {}", page.getValues());
				groups.addAll(Sets.newHashSet(page.getValues()));
			}

			@Override
			public Page<? extends String> fetchPage(PageRequest pageRequest) {
				log.debug("Fetching user {} groups from page {}", user.getName(), pageRequest);
				return userService
						.findGroupsByUser(user.getName(), pageRequest);
			}

		};
	}
}
