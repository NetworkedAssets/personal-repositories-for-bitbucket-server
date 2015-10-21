package org.networkedassets.atlassian.bitbucket.personalrepos.repositories;

import java.util.List;

import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;

public interface PersonalRepositoriesService {

	PersonalRepository addPersonalRepository(ApplicationUser user, Repository repo);

	List<PersonalRepository> addUserPersonalRepositories(ApplicationUser user,
			Iterable<? extends Repository> repositories);

	Page<Owner> getPersonalRepositoriesOwners(PageRequest pageRequest, SortCriteria sortBy);

	List<PersonalRepository> getUserPersonalRepositories(int userId);

	void deletePersonalRepository(Repository repo);

	int getOwnersCount();
	
	void purge();
	
	void updateRepositorySize(Repository repo);

	void deletePersonalRepositoryOwner(ApplicationUser deletedUser);

}
