package org.networkedassets.atlassian.stash.privaterepos.repositories;

import org.networkedassets.atlassian.stash.privaterepos.repositories.ao.Owner;
import org.networkedassets.atlassian.stash.privaterepos.repositories.ao.PersonalRepository;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;

public interface PersonalRepositoriesService {

	PersonalRepository addPersonalRepository(Repository repository);

	Iterable<? extends PersonalRepository> addUserPersonalRepositories(
			StashUser user, Iterable<? extends Repository> repositories);

	Page<? extends Owner> getPersonalRepositoriesOwners(PageRequest pageRequest);

	Iterable<? extends PersonalRepository> getUserPersonalRepositories(StashUser user);

}
