package org.networkedassets.atlassian.stash.privaterepos.user;

import org.networkedassets.atlassian.stash.privaterepos.util.persistence.PluginSettingsBasedStoredIdsRepository;
import org.springframework.stereotype.Component;

@Component
public class UserIdsRepository extends PluginSettingsBasedStoredIdsRepository {

	@Override
	protected String getSettingsKey() {
		return "stored-users";
	}

}
