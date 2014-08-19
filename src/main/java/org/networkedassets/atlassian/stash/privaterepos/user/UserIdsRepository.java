package org.networkedassets.atlassian.stash.privaterepos.user;

import org.networkedassets.atlassian.stash.privaterepos.util.persistence.PluginSettingsBasedStoredIdsRepository;
import org.springframework.stereotype.Component;

@Component
public class UserIdsRepository extends
		PluginSettingsBasedStoredIdsRepository<Integer> {

	@Override
	protected String getSettingsKey() {
		return "stored-users";
	}

	@Override
	protected Integer convertFromString(String value) {
		return Integer.valueOf(value);
	}

}
