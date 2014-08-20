package org.networkedassets.atlassian.stash.privaterepos.web;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.stash.nav.NavBuilder;

public class AdministrationPanelServlet extends SoyTemplateServlet {

	private static final long serialVersionUID = 790189345704146559L;

	@Autowired
	private NavBuilder navBuilder;

	@Override
	protected String getTemplateResources() {
		return "org.networkedassets.atlassian.stash.privaterepos:templates-soy";
	}

	@Override
	protected String getTemplateKey() {
		return "org.networkedassets.personalRepos.adminPage";
	}

	@Override
	protected HashMap<String, Object> getTemplateParams() {
		HashMap<String, Object> params = super.getTemplateParams();
		String baseApiPath = navBuilder.buildAbsolute()
				+ "/rest/privaterepos/1.0";
		params.put("baseApiPath", baseApiPath);
		return params;
	}

}
