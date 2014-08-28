package org.networkedassets.atlassian.stash.personalstash.repositories.listeners;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.networkedassets.atlassian.stash.personalstash.license.LicenseManager;
import org.networkedassets.atlassian.stash.personalstash.repositories.PersonalRepositoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.stash.event.user.UserCleanupEvent;

@Component
public class UserCleanupEventListener {

	@Autowired
	private PersonalRepositoriesService personalRepositoriesService;
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
	public void handleDeletionEvent(UserCleanupEvent event) {
		if (licenseManager.isLicenseInvalid()) {
			return;
		}
		personalRepositoriesService.deletePersonalRepositoryOwner(event.getDeletedUser());
	}

}
