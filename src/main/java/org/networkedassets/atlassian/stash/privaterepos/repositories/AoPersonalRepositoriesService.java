package org.networkedassets.atlassian.stash.privaterepos.repositories;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.java.ao.Query;

import org.apache.commons.collections.IteratorUtils;
import org.networkedassets.atlassian.stash.privaterepos.util.AllPagesIterator;
import org.networkedassets.atlassian.stash.privaterepos.util.PageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.stash.project.Project;
import com.atlassian.stash.project.ProjectService;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.repository.RepositoryService;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageImpl;
import com.atlassian.stash.util.PageRequest;

@Transactional
@Component
public class AoPersonalRepositoriesService implements
		PersonalRepositoriesService {

	@Autowired
	private ActiveObjects ao;
	@Autowired
	private UserService userService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProjectService projectService;

	@Override
	public Page<? extends StashUser> getUsersHavingPersonalRepositories(
			PageRequest pageRequest) {

		Set<Integer> personalRepositoriesUserIds = getPersonalRepositoriesUserIds(pageRequest);
		Iterable<StashUser> users = getStashUsersFromUserIds(personalRepositoriesUserIds);

		boolean isLastPage = isLastPage(pageRequest,
				personalRepositoriesUserIds.size());

		return new PageImpl<StashUser>(pageRequest, users, isLastPage);
	}

	private Set<Integer> getPersonalRepositoriesUserIds(PageRequest pageRequest) {
		PersonalRepository[] repos = ao.find(PersonalRepository.class, Query
				.select("USER_ID").distinct().limit(pageRequest.getLimit())
				.offset(pageRequest.getStart()));

		Set<Integer> userIds = new HashSet<Integer>();

		for (PersonalRepository repo : repos) {
			userIds.add(repo.getUserId());
		}

		return userIds;
	}

	private boolean isLastPage(PageRequest pageRequest, Integer totalCount) {
		return pageRequest.getStart() + pageRequest.getLimit() <= totalCount;
	}

	@SuppressWarnings("unchecked")
	private Iterable<StashUser> getStashUsersFromUserIds(Set<Integer> userIds) {
		return (Iterable<StashUser>) userService.getUsersById(userIds);
	}

	@Override
	public Iterable<? extends Repository> findUserRepositories(StashUser user) {
		List<? extends Repository> userRepositories = new ArrayList<Repository>();
		PageProcessor<Repository> pageProcessor = createUserReposPageProcessor(
				user, userRepositories);
		AllPagesIterator<Repository> pagesIterator = new AllPagesIterator<Repository>(
				pageProcessor);
		pagesIterator.processAllPages();

		return userRepositories;
	}

	private PageProcessor<Repository> createUserReposPageProcessor(
			final StashUser user,
			final List<? extends Repository> userRepositories) {
		return new PageProcessor<Repository>() {

			@Override
			@SuppressWarnings("unchecked")
			public void process(Page<? extends Repository> page) {
				userRepositories.addAll(IteratorUtils.toList(page.getValues()
						.iterator()));
			}

			@Override
			public Page<? extends Repository> fetchPage(PageRequest pageRequest) {
				return repositoryService.findByProjectKey(
						getPersonalProjectKey(user), pageRequest);
			}
		};
	}

	private String getPersonalProjectKey(StashUser user) {
		return "~" + user.getSlug().toUpperCase();
	}

	@Override
	public Iterable<PersonalRepository> rememberUserPersonalRepositories(
			StashUser user, Iterable<? extends Repository> repositories) {

		List<PersonalRepository> personalRepos = new ArrayList<PersonalRepository>();

		for (Repository repo : repositories) {
			personalRepos.add(addPersonalRepository(repo, user));
		}

		return personalRepos;
	}

	private PersonalRepository addPersonalRepository(Repository repo,
			StashUser user) {

		PersonalRepository personalRepository = ao
				.create(PersonalRepository.class);
		personalRepository.setRepositoryId(repo.getId());
		personalRepository.setUserId(user.getId());
		personalRepository.save();

		return personalRepository;
	}

	@Override
	public PersonalRepository rememberPersonalRepository(Repository repository) {
		StashUser owner = getRepositoryOwner(repository);
		return addPersonalRepository(repository, owner);
	}

	private StashUser getRepositoryOwner(Repository repository) {
		Project project = repository.getProject();
		return findUserFromProject(project);
	}

	private StashUser findUserFromProject(Project project) {
		String userSlug = findUserSlugFromProjectKey(project.getKey());
		return userService.getUserBySlug(userSlug);
	}

	/**
	 * Cut the ~ from the beginning
	 */
	private String findUserSlugFromProjectKey(String key) {
		return key.substring(1);
	}

}
