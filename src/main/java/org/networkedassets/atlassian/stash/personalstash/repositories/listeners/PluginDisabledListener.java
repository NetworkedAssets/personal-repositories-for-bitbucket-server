package org.networkedassets.atlassian.stash.personalstash.repositories.listeners;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.networkedassets.atlassian.stash.personalstash.license.LicenseManager;
import org.networkedassets.atlassian.stash.personalstash.plugin.PluginConfig;
import org.networkedassets.atlassian.stash.personalstash.repositories.PersonalRepositoriesPreScanner;
import org.networkedassets.atlassian.stash.personalstash.state.PluginState;
import org.networkedassets.atlassian.stash.personalstash.state.PluginStateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.plugin.event.events.BeforePluginDisabledEvent;
import com.atlassian.stash.user.SecurityService;
import com.atlassian.stash.user.StashAuthenticationContext;

@Component
public class PluginDisabledListener {

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
			.getLogger(PluginDisabledListener.class);

	@PostConstruct
	public void registerEvents() {
		eventPublisher.register(this);
	}

	@PreDestroy
	public void unregisterEvents() {
		eventPublisher.unregister(this);
	}

	@EventListener
	public void onPluginDisabledEvent(BeforePluginDisabledEvent event) {
		if (licenseManager.isLicenseInvalid()) {
			return;
		}
		if (event.getPlugin().getKey().equals(PluginConfig.PLUGIN_KEY)) {
			log.info("Plugin disabled event received");
			pluginStateRepository.setState(PluginState.SCAN_NEEDED);
		}
	}

}
