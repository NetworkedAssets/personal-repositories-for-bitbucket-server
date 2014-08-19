package org.networkedassets.atlassian.stash.privaterepos.group;

import org.networkedassets.atlassian.stash.privaterepos.util.persistence.PluginSettingsBasedStoredIdsRepository;

public class GroupsRepository extends
		PluginSettingsBasedStoredIdsRepository<String> {

	@Override
	protected String getSettingsKey() {
		return "stored-groups";
	}

	@Override
	protected String convertFromString(String value) {
		return value;
	}

}
