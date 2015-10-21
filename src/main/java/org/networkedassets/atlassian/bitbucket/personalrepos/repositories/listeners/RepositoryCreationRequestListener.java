package org.networkedassets.atlassian.bitbucket.personalrepos.repositories.listeners;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.networkedassets.atlassian.bitbucket.personalrepos.auth.UserPermissionsExaminer;
import org.networkedassets.atlassian.bitbucket.personalrepos.license.LicenseManager;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.RepositoryTypeVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.bitbucket.event.repository.RepositoryCreationRequestedEvent;
import com.atlassian.bitbucket.i18n.I18nService;

@Component
public class RepositoryCreationRequestListener {

	@Autowired
	private UserPermissionsExaminer userPermissionsExaminer;
	@Autowired
	private RepositoryTypeVerifier repositoryTypeVerifier;
	@Autowired
	private EventPublisher eventPublisher;
	@Autowired
	private LicenseManager licenseManager;
	@Autowired
	I18nService i18nService;

	private final Logger log = LoggerFactory
			.getLogger(RepositoryCreationRequestListener.class);

	@PostConstruct
	public void registerEvents() {
		eventPublisher.register(this);
	}

	@PreDestroy
	public void unregisterEvents() {
		eventPublisher.unregister(this);
	}

	@EventListener
	public void handleCreationEvent(RepositoryCreationRequestedEvent event) {
		if (licenseManager.isLicenseInvalid()) {
			return;
		}
		log.debug("Repository Creation Request received");
		if (repositoryTypeVerifier.isPersonal(event.getRepository())) {
			log.debug("Repository is personal");
			if (!userPermissionsExaminer.canUsePersonalRepositories()) {
				log.debug("User can't use presonal repositories");
				event.cancel(i18nService
						.getKeyedText(
								"org.networkedassets.atlassian.bitbucket.personalrepos.repository.creationblocked",
								"You are not allowed to create a personal repository.\nYour administrator blocked this feature.\nIf you think you should be able to execute this action, contact your administrator."));
			}
		}
	}

}
