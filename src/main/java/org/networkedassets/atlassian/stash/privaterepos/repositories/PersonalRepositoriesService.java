package org.networkedassets.atlassian.stash.privaterepos.repositories;

import java.util.List;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;

public interface PersonalRepositoriesService {

	PersonalRepository addPersonalRepository(Repository repo);

	List<PersonalRepository> addUserPersonalRepositories(StashUser user,
			Iterable<? extends Repository> repositories);

	Page<Owner> getPersonalRepositoriesOwners(PageRequest pageRequest);

	List<PersonalRepository> getUserPersonalRepositories(StashUser user);
	
	List<PersonalRepository> getUserPersonalRepositories(int userId);

	void deletePersonalRepository(Repository repo);

	int getOwnersCount();

}
