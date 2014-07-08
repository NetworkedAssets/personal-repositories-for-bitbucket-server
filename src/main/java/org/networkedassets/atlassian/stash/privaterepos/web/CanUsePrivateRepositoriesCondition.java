package org.networkedassets.atlassian.stash.privaterepos.web;

import java.util.Map;

import org.networkedassets.atlassian.stash.privaterepos.auth.UserPermissionsExaminer;
import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;

public class CanUsePrivateRepositoriesCondition implements Condition {
	
	@Autowired
	private UserPermissionsExaminer userPermissionsExaminer;
	
	@Override
	public void init(Map<String, String> arg0) throws PluginParseException {
		
	}

	@Override
	public boolean shouldDisplay(Map<String, Object> arg0) {
		return userPermissionsExaminer.canUsePrivateRepositories();
	}

}
