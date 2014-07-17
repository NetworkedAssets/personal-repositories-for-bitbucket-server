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

	Logger log = LoggerFactory.getLogger(PersonalRepositoriesPreScanner.class);

	@Autowired
	PersonalRepositoriesService personalRepositoriesService;

	@Autowired
	private UserService userService;

	@Autowired
	private RepositoryService repositoryService;

	public void scanPersonalRepositories() {
		log.debug("Peronal repositories pre-scanning started");

		// 1000 users at once shouldn't kill us, should it ?
		new AllPagesIterator.Builder<StashUser>(createUserPageProcessor())
				.resultsPerPage(1000).build();

		log.debug("Peronal repositories pre scanning finished");
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
		// we should fit in 10000 repositories per user - the idea is to get
		// them in one hit
		AllPagesIterator<Repository> repoIterator = new AllPagesIterator.Builder<Repository>(
				createRepositoryPageProcessor(user)).resultsPerPage(10000)
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
