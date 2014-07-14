package org.networkedassets.atlassian.stash.privaterepos.repositories.listeners;

import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.stash.privaterepos.repositories.RepositoryTypeVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.stash.event.RepositoryCreatedEvent;
import com.atlassian.stash.repository.Repository;

@Component
public class RepositoryCreatedListener {

	@Autowired
	private PersonalRepositoriesService personalRepositoriesService;
	@Autowired
	private RepositoryTypeVerifier repositoryTypeVerifier;

	@EventListener
	public void handleCreationEvent(RepositoryCreatedEvent event) {
		Repository repo = event.getRepository();
		if (repositoryTypeVerifier.isPersonal(repo)) {
			personalRepositoriesService.addPersonalRepository(repo);
		}
	}

}
