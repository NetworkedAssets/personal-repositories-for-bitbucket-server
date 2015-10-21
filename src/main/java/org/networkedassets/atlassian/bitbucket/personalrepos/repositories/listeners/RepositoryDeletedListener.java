package org.networkedassets.atlassian.bitbucket.personalrepos.repositories.listeners;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.networkedassets.atlassian.bitbucket.personalrepos.license.LicenseManager;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.RepositoryTypeVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.bitbucket.event.repository.RepositoryDeletedEvent;
import com.atlassian.bitbucket.repository.Repository;

@Component
public class RepositoryDeletedListener {

	@Autowired
	private PersonalRepositoriesService personalRepositoriesService;
	@Autowired
	private RepositoryTypeVerifier repositoryTypeVerifier;
	@Autowired
	private EventPublisher eventPublisher;
	@Autowired
	private LicenseManager licenseManager;

	@PostConstruct
	public void registerEvents() {
		eventPublisher.register(this);
	}

	@PreDestroy
	public void unregisterEvents() {
		eventPublisher.unregister(this);
	}

	@EventListener
	public void handleDeletionEvent(RepositoryDeletedEvent event) {
		if (licenseManager.isLicenseInvalid()) {
			return;
		}
		Repository repo = event.getRepository();
		if (repositoryTypeVerifier.isPersonal(repo)) {
			personalRepositoriesService.deletePersonalRepository(repo);
		}
	}

}
