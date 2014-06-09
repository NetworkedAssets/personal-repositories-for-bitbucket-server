package org.networkedassets.atlassian.stash.privaterepos.listeners;

import org.networkedassets.atlassian.stash.privaterepos.service.PersonalRepositoriesService;
import org.networkedassets.atlassian.stash.privaterepos.service.RepositoryTypeVerifier;

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
