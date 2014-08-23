package org.networkedassets.atlassian.stash.privaterepos.repositories;

import org.networkedassets.atlassian.stash.privaterepos.state.PluginState;
import org.networkedassets.atlassian.stash.privaterepos.state.PluginStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoriesPreScanningScheduler {

	@Autowired
	private PersonalRepositoriesPreScanner personalRepositoriesPreScanner;
	@Autowired
	private PluginStateManager pluginStateManager;

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			personalRepositoriesPreScanner.scanPersonalRepositories();
			pluginStateManager.setState(PluginState.READY);
		}
	};

	public void scheduleScan() {
		pluginStateManager.setState(PluginState.SCANNING);
		runnable.run();
	}

}
