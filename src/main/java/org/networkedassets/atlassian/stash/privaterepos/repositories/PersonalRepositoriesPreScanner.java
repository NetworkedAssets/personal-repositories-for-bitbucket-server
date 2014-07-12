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
		AllPagesIterator<StashUser> iterator = new AllPagesIterator<StashUser>(
				createUserPageProcessor());
		iterator.processAllPages();
	}

	private PageProcessor<StashUser> createUserPageProcessor() {
		return new PageProcessor<StashUser>() {

			public Page<? extends StashUser> fetchPage(PageRequest pageRequest) {
				log.warn("Fetching page {}", pageRequest.getStart());
				return userService.findUsers(pageRequest);
			}

			public void process(Page<? extends StashUser> page) {
				for (StashUser user : page.getValues()) {
					log.warn("Processing user {}", user.getName());
					scanUserRepositories(user);
				}
			}
		};
	}

	private void scanUserRepositories(StashUser user) {
		PageProcessor<Repository> repositoryPageProcessor = createRepositoryPageProcessor(user);
		AllPagesIterator<Repository> repoIterator = new AllPagesIterator<Repository>(
				repositoryPageProcessor);
		repoIterator.processAllPages();
	}

	private PageProcessor<Repository> createRepositoryPageProcessor(
			final StashUser user) {
		return new PageProcessor<Repository>() {

			@Override
			public void process(Page<? extends Repository> page) {
				log.warn("Processing repositories {}", page.getValues());
				personalRepositoriesService.rememberUserPersonalRepositories(
						user, page.getValues());
			}

			@Override
			public Page<? extends Repository> fetchPage(PageRequest pageRequest) {
				log.warn("Fetching repositories for {}", ("~" + user.getSlug()));
				return repositoryService.findByProjectKey("~" + user.getSlug(),
						pageRequest);
			}
		};
	}

}
