package org.networkedassets.atlassian.stash.privaterepos.web;

import java.util.Map;

import org.networkedassets.atlassian.stash.privaterepos.service.UserPermissionsExaminer;

import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;

public class CanUsePrivateRepositoriesCondition implements Condition {
	
	private final UserPermissionsExaminer userPermissionsExaminer;
	
	public CanUsePrivateRepositoriesCondition(UserPermissionsExaminer userPermissionsExaminer) {
		this.userPermissionsExaminer = userPermissionsExaminer;
	}

	@Override
	public void init(Map<String, String> arg0) throws PluginParseException {
		
	}

	@Override
	public boolean shouldDisplay(Map<String, Object> arg0) {
		return userPermissionsExaminer.canUsePrivateRepositories();
	}

}
