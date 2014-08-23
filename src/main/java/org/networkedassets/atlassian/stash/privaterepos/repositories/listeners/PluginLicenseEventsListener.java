package org.networkedassets.atlassian.stash.privaterepos.repositories.listeners;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.networkedassets.atlassian.stash.privaterepos.license.LicenseManager;
import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesPreScanner;
import org.networkedassets.atlassian.stash.privaterepos.state.PluginState;
import org.networkedassets.atlassian.stash.privaterepos.state.PluginStateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.stash.user.SecurityService;
import com.atlassian.stash.user.StashAuthenticationContext;
import com.atlassian.upm.api.license.event.PluginLicenseChangeEvent;
import com.atlassian.upm.api.license.event.PluginLicenseExpiredEvent;
import com.atlassian.upm.api.license.event.PluginLicenseRemovedEvent;

@Component
public class PluginLicenseEventsListener {

	private static final String PLUGIN_KEY = "org.networkedassets.atlassian.stash.privaterepos";

	@Autowired
	private EventPublisher eventPublisher;
	@Autowired
	private PersonalRepositoriesPreScanner personalRepositoriesPreScanner;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private StashAuthenticationContext authenticationContext;
	@Autowired
	private LicenseManager licenseManager;
	@Autowired
	private PluginStateManager pluginStateRepository;

	private final Logger log = LoggerFactory
			.getLogger(PluginLicenseEventsListener.class);

	@PostConstruct
	public void registerEvents() {
		eventPublisher.register(this);
	}

	@PreDestroy
	public void unregisterEvents() {
		eventPublisher.unregister(this);
	}

	@EventListener
	public void onPluginLicenseExpiredEvent(PluginLicenseExpiredEvent event) {
		pluginStateRepository.setState(PluginState.SCAN_NEEDED);
	}

	@EventListener
	public void onPluginLicenseRemovedEvent(PluginLicenseRemovedEvent event) {
		pluginStateRepository.setState(PluginState.SCAN_NEEDED);
	}

	@EventListener
	public void onPluginLicenseChangeEvent(PluginLicenseChangeEvent event) {
		if (licenseManager.isLicenseInvalid()) {
			pluginStateRepository.setState(PluginState.SCAN_NEEDED);
		}
	}

}
