package org.networkedassets.atlassian.stash.privaterepos.repositories.listeners;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.networkedassets.atlassian.stash.privaterepos.license.LicenseManager;
import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.stash.privaterepos.repositories.RepositoryTypeVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.stash.event.RepositoryModifiedEvent;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.user.StashAuthenticationContext;

@Component
public class RepositoryModifiedListener {

	@Autowired
	private PersonalRepositoriesService personalRepositoriesService;
	@Autowired
	private RepositoryTypeVerifier repositoryTypeVerifier;
	@Autowired
	private EventPublisher eventPublisher;
	@Autowired
	private StashAuthenticationContext authenticationContext;
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
	public void handleCreationEvent(RepositoryModifiedEvent event) {
		if (licenseManager.isLicenseInvalid()) {
			return;
		}
		Repository repo = event.getRepository();
		if (repositoryTypeVerifier.isPersonal(repo)) {
			personalRepositoriesService.updateRepositorySize(repo);
		}
	}

}
