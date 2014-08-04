package org.networkedassets.atlassian.stash.privaterepos.repositories;

import org.networkedassets.atlassian.stash.privaterepos.util.AllPagesIterator;
import org.networkedassets.atlassian.stash.privaterepos.util.PageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.repository.RepositoryService;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;

@Component
public class PersonalRepositoriesPreScanner {

	// 1000 users at once shouldn't kill us, should it ?
	private static final int USERS_PER_PAGE = 1000;

	// we should fit in 10000 repositories per user - the idea is to get
	// them in one hit
	private static final int USER_REPOSITORIES_PER_PAGE = 10000;

	Logger log = LoggerFactory.getLogger(PersonalRepositoriesPreScanner.class);

	@Autowired
	PersonalRepositoriesService personalRepositoriesService;

	@Autowired
	private UserService userService;

	@Autowired
	private RepositoryService repositoryService;

	public void scanPersonalRepositories() {
		log.debug("Personal repositories pre-scanning started");

		new AllPagesIterator.Builder<StashUser>(createUserPageProcessor())
				.resultsPerPage(USERS_PER_PAGE).build();

		log.debug("Personal repositories pre scanning finished");
	}

	private PageProcessor<StashUser> createUserPageProcessor() {
		return new PageProcessor<StashUser>() {

			public Page<? extends StashUser> fetchPage(PageRequest pageRequest) {
				return userService.findUsers(pageRequest);
			}

			public void process(Page<? extends StashUser> page) {
				for (StashUser user : page.getValues()) {
					log.debug("Scanning user '{}' repositories", user.getName());
					scanUserRepositories(user);
				}
			}
		};
	}

	private void scanUserRepositories(StashUser user) {
		AllPagesIterator<Repository> repoIterator = new AllPagesIterator.Builder<Repository>(
				createRepositoryPageProcessor(user)).resultsPerPage(USER_REPOSITORIES_PER_PAGE)
				.build();
		repoIterator.processAllPages();
	}

	private PageProcessor<Repository> createRepositoryPageProcessor(
			final StashUser user) {
		return new PageProcessor<Repository>() {

			@Override
			public void process(Page<? extends Repository> page) {
				log.debug("Found repositories: {}", page.getValues());
				if (isIterableEmpty(page.getValues())) {
					return;
				}
				personalRepositoriesService.addUserPersonalRepositories(user,
						page.getValues());
			}

			@Override
			public Page<? extends Repository> fetchPage(PageRequest pageRequest) {
				return repositoryService.findByProjectKey("~" + user.getSlug(),
						pageRequest);
			}
		};
	}

	private boolean isIterableEmpty(
			@SuppressWarnings("rawtypes") Iterable iterable) {
		return !iterable.iterator().hasNext();
	}

}
