package org.networkedassets.atlassian.stash.privaterepos.listeners;

import org.networkedassets.atlassian.stash.privaterepos.service.RepositoryTypeVerifier;
import org.networkedassets.atlassian.stash.privaterepos.service.UserPermissionsExaminer;

import com.atlassian.event.api.EventListener;
import com.atlassian.stash.event.RepositoryCreationRequestedEvent;

public class RepositoryCreationRequestListener {

	private final UserPermissionsExaminer userPermissionsExaminer;
	private final RepositoryTypeVerifier repositoryTypeVerifier;

	public RepositoryCreationRequestListener(
			UserPermissionsExaminer userPermissionsExaminer,
			RepositoryTypeVerifier repositoryTypeVerifier) {
		this.userPermissionsExaminer = userPermissionsExaminer;
		this.repositoryTypeVerifier = repositoryTypeVerifier;
	}

	@EventListener
	public void handleCreationEvent(RepositoryCreationRequestedEvent event) {
		if (repositoryTypeVerifier.isPersonal(event.getRepository())) {
			if (!userPermissionsExaminer.canUsePrivateRepositories()) {
				event.cancel(null);
			}
		}
	}

}
