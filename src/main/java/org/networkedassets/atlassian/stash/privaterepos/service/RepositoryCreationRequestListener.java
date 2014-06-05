package org.networkedassets.atlassian.stash.privaterepos.service;

import com.atlassian.event.api.EventListener;
import com.atlassian.stash.event.RepositoryCreationRequestedEvent;
import com.atlassian.stash.project.ProjectType;

public class RepositoryCreationRequestListener {

	private final UserPermissionsExaminer userPermissionsExaminer;

	public RepositoryCreationRequestListener(UserPermissionsExaminer userPermissionsExaminer) {
		this.userPermissionsExaminer = userPermissionsExaminer;
	}
	
	@EventListener
	public void handleCreationEvent(RepositoryCreationRequestedEvent event) {
		if (isPrivateRepoEvent(event)) {
			if (!userPermissionsExaminer.canUsePrivateRepositories()) {
				event.cancel(null);	
			}
		}
	}
	
	private boolean isPrivateRepoEvent(RepositoryCreationRequestedEvent event) {
		return event.getRepository().getProject().getType() == ProjectType.PERSONAL;
	}

}
