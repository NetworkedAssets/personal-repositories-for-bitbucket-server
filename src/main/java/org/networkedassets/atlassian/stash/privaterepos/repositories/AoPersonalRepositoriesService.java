package org.networkedassets.atlassian.stash.privaterepos.repositories;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import net.java.ao.Query;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.stash.project.Project;
import com.atlassian.stash.project.ProjectService;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.repository.RepositoryService;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageImpl;
import com.atlassian.stash.util.PageRequest;

@Component
public class AoPersonalRepositoriesService implements
		PersonalRepositoriesService {

	private final ActiveObjects ao;
	private final UserService userService;
	private final RepositoryService repositoryService;
	private final ProjectService projectService;

	public AoPersonalRepositoriesService(ActiveObjects ao,
			UserService userService, RepositoryService repositoryService, ProjectService projectService) {
		this.ao = ao;
		this.userService = userService;
		this.repositoryService = repositoryService;
		this.projectService = projectService;
	}

	@Override
	public Page<? extends StashUser> findUsersHavingPersonalRepositories(
			PageRequest pageRequest) {
		
		Integer usersCount = getUsersCount();
		PersonalRepository[] repos = getDistinctPersonalRepositoriesUsers(pageRequest);
		Iterable<StashUser> users = getUsersFromPersonalRepositories(repos);
		boolean isLastPage = isLastPage(pageRequest, usersCount);

		return new PageImpl<StashUser>(pageRequest, users, isLastPage);
	}

	private Integer getUsersCount() {
		return ao.count(PersonalRepository.class, "DISTINCT USER_ID");
	}

	private PersonalRepository[] getDistinctPersonalRepositoriesUsers(
			PageRequest pageRequest) {
		return ao.find(
				PersonalRepository.class,
				Query.select("USER_ID").distinct()
						.limit(pageRequest.getLimit())
						.offset(pageRequest.getStart()));
	}

	private boolean isLastPage(PageRequest pageRequest, Integer totalCount) {
		return pageRequest.getStart() + pageRequest.getLimit() <= totalCount;
	}

	@SuppressWarnings("unchecked")
	private Iterable<StashUser> getUsersFromPersonalRepositories(
			PersonalRepository[] personalRepositories) {
		Set<Integer> userIds = new HashSet<Integer>();

		for (PersonalRepository repo : personalRepositories) {
			userIds.add(repo.getUserId());
		}

		return (Iterable<StashUser>) userService.getUsersById(userIds);
	}

	@Override
	public Page<? extends Repository> findUserRepositories(StashUser user,
			PageRequest pageRequest) {
		return repositoryService.findByProjectKey(gePersonalProjectKey(user),
				pageRequest);
	}

	private String gePersonalProjectKey(StashUser user) {
		return "~" + user.getSlug().toUpperCase();
	}

	@Override
	public PersonalRepository rememberPersonalRepository(Repository repository) {
		
		StashUser user = userService.getUserBySlug(getUserSlug(repository.getProject()));
		
		PersonalRepository personalRepository = ao.create(PersonalRepository.class);
		personalRepository.setRepositoryId(repository.getId());
		personalRepository.setUserId(user.getId());
		personalRepository.save();
		
		return personalRepository;
	}
	
	private String getUserSlug(Project project) {
		return project.getKey().substring(1);
	}

}
