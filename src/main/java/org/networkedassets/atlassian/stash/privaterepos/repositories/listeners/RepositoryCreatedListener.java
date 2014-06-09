package org.networkedassets.atlassian.stash.privaterepos.repositories.listeners;

import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.stash.privaterepos.repositories.RepositoryTypeVerifier;

import com.atlassian.event.api.EventListener;
import com.atlassian.stash.event.RepositoryCreatedEvent;
import com.atlassian.stash.repository.Repository;

public class RepositoryCreatedListener {

	private final PersonalRepositoriesService personalRepositoriesService;
	private final RepositoryTypeVerifier repositoryTypeVerifier;

	public RepositoryCreatedListener(
			PersonalRepositoriesService personalRepositoriesService,
			RepositoryTypeVerifier repositoryTypeVerifier) {
		this.personalRepositoriesService = personalRepositoriesService;
		this.repositoryTypeVerifier = repositoryTypeVerifier;
	}

	@EventListener
	public void handleCreationEvent(RepositoryCreatedEvent event) {
		Repository repo = event.getRepository();
		if (repositoryTypeVerifier.isPersonal(repo)) {
			personalRepositoriesService.rememberPersonalRepository(repo);
		}
	}

}
