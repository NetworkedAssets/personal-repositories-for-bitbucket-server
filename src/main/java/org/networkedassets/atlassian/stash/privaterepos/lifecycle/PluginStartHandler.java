package org.networkedassets.atlassian.stash.privaterepos.lifecycle;

import javax.annotation.PostConstruct;

import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesPreScanner;
import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.plugin.event.events.PluginEnabledEvent;

@Component
public class PluginStartHandler {

	@Autowired
	private PersonalRepositoriesPreScanner personalRepositoriesPreScanner;

	private final Logger log = LoggerFactory
			.getLogger(PluginStartHandler.class);

	@PostConstruct
	public void handlePluginEnabledEvent(PluginEnabledEvent event) {
		personalRepositoriesPreScanner.scanPersonalRepositories();
	}

}
