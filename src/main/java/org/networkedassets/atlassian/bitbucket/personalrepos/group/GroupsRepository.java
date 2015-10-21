package org.networkedassets.atlassian.bitbucket.personalrepos.group;

import org.networkedassets.atlassian.bitbucket.personalrepos.util.persistence.PluginSettingsBasedStoredIdsRepository;
import org.springframework.stereotype.Component;

@Component
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
