package org.networkedassets.atlassian.stash.privaterepos.repositories.listeners;

import org.networkedassets.atlassian.stash.privaterepos.auth.UserPermissionsExaminer;
import org.networkedassets.atlassian.stash.privaterepos.repositories.RepositoryTypeVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.stash.event.RepositoryCreationRequestedEvent;

@Component
public class RepositoryCreationRequestListener {

	@Autowired
	private UserPermissionsExaminer userPermissionsExaminer;
	@Autowired
	private RepositoryTypeVerifier repositoryTypeVerifier;

	@EventListener
	public void handleCreationEvent(RepositoryCreationRequestedEvent event) {
		if (repositoryTypeVerifier.isPersonal(event.getRepository())) {
			if (!userPermissionsExaminer.canUsePrivateRepositories()) {
				event.cancel(null);
			}
		}
	}

}
