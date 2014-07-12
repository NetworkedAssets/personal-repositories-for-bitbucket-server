package org.networkedassets.atlassian.stash.privaterepos.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesPreScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.plugin.event.events.PluginEnabledEvent;

@Component
public class PluginStartHandler {

	private static final String PLUGIN_KEY = "org.networkedassets.atlassian.stash.privaterepos";

	@Autowired
	private EventPublisher eventPublisher;

	@Autowired
	private PersonalRepositoriesPreScanner personalRepositoriesPreScanner;

	private final Logger log = LoggerFactory
			.getLogger(PluginStartHandler.class);

	@PostConstruct
	public void registerEvents() {
		eventPublisher.register(this);
	}

	@PreDestroy
	public void unregisterEvents() {
		eventPublisher.unregister(this);
	}

	@EventListener
	public void onPluginEnabledEvent(PluginEnabledEvent event) {
		if (event.getPlugin().getKey().equals(PLUGIN_KEY)) {
			personalRepositoriesPreScanner.scanPersonalRepositories();
		}
	}

}
