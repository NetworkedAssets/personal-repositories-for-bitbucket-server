package org.networkedassets.atlassian.bitbucket.personalrepos.repositories.listeners;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.networkedassets.atlassian.bitbucket.personalrepos.license.LicenseManager;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.RepositoryTypeVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.bitbucket.event.repository.RepositoryRefsChangedEvent;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.auth.AuthenticationContext;

@Component
public class RepositoryModifiedListener {

	@Autowired
	private PersonalRepositoriesService personalRepositoriesService;
	@Autowired
	private RepositoryTypeVerifier repositoryTypeVerifier;
	@Autowired
	private EventPublisher eventPublisher;
	@Autowired
	private AuthenticationContext authenticationContext;
	@Autowired
	private LicenseManager licenseManager;
	
	private final Logger log = LoggerFactory
			.getLogger(RepositoryModifiedListener.class);

	@PostConstruct
	public void registerEvents() {
		eventPublisher.register(this);
	}

	@PreDestroy
	public void unregisterEvents() {
		eventPublisher.unregister(this);
	}

	@EventListener
	public void handleModifiedEvent(RepositoryRefsChangedEvent event) {
		if (licenseManager.isLicenseInvalid()) {
			return;
		}
		Repository repo = event.getRepository();
		if (repositoryTypeVerifier.isPersonal(repo)) {
			log.debug("Personal repository {} modified", repo);
			personalRepositoriesService.updateRepositorySize(repo);
		}
	}

}
