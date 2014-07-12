package org.networkedassets.atlassian.stash.privaterepos.repositories;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;

public interface PersonalRepositoriesService {

	PersonalRepository rememberPersonalRepository(Repository repository);
	
	Iterable<PersonalRepository> rememberUserPersonalRepositories(
			StashUser user, Iterable<? extends Repository> repositories);

	Page<? extends StashUser> getUsersHavingPersonalRepositories(
			PageRequest pageRequest);

	Iterable<? extends Repository> findUserRepositories(StashUser user);

}
