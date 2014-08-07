package org.networkedassets.atlassian.stash.privaterepos.lifecycle;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.core.SecurityContext;

import org.joda.time.Seconds;
import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesPreScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.plugin.event.events.PluginEnabledEvent;
import com.atlassian.stash.user.EscalatedSecurityContext;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.SecurityService;
import com.atlassian.stash.user.StashAuthenticationContext;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.Operation;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequestImpl;
import com.atlassian.stash.util.UncheckedOperation;

@Component
public class PluginStartHandler {

	private static final String PLUGIN_KEY = "org.networkedassets.atlassian.stash.privaterepos";

	@Autowired
	private EventPublisher eventPublisher;

	@Autowired
	private PersonalRepositoriesPreScanner personalRepositoriesPreScanner;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private StashAuthenticationContext authenticationContext;

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
			log.debug("Scheduling prescan");
			schedulePreScan();
		}
	}

	private void schedulePreScan() {
		final StashUser currentUser = authenticationContext.getCurrentUser();

		Timer timer = new Timer();

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				EscalatedSecurityContext secContext = securityService
						.impersonating(currentUser,
								"Scanning list of personal repositories")
						.withPermission(Permission.ADMIN);
				secContext.call(preScan());
			}
		}, 1000 * 1);
	}

	private Operation<Void, RuntimeException> preScan() {
		return new UncheckedOperation<Void>() {
			@Override
			public Void perform() {
				personalRepositoriesPreScanner.scanPersonalRepositories();
				return null;
			}
		};
	}

}
