package org.networkedassets.atlassian.stash.privaterepos.service;

import org.networkedassets.atlassian.stash.privaterepos.ao.repositories.PersonalRepository;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;

public interface PersonalRepositoriesService {

	Page<? extends StashUser> findUsersHavingPersonalRepositories(
			PageRequest pageRequest);

	Page<? extends Repository> findUserRepositories(StashUser user,
			PageRequest pageRequest);
	
	PersonalRepository rememberPersonalRepository(Repository repository);

}
