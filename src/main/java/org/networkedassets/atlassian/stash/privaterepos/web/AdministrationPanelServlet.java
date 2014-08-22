package org.networkedassets.atlassian.stash.privaterepos.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.networkedassets.atlassian.stash.privaterepos.license.LicenseManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.stash.nav.NavBuilder;

public class AdministrationPanelServlet extends SoyTemplateServlet {

	private static final long serialVersionUID = 790189345704146559L;

	private String templateResources = "org.networkedassets.atlassian.stash.privaterepos:templates-soy";
	private String templateKey = "org.networkedassets.personalRepos.adminPage";

	private String licenseErrorTemplateResources = "org.networkedassets.atlassian.stash.privaterepos:license-servlet-resources";
	private String licenseErrorTemplateKey = "org.networkedassets.personalRepos.license.errorPage";

	@Autowired
	private LicenseManager licenseManager;

	@Autowired
	private NavBuilder navBuilder;

	@Override
	protected String getTemplateResources() {
		return templateResources;
	}

	@Override
	protected String getTemplateKey() {
		return templateKey;
	}

	@Override
	protected HashMap<String, Object> getTemplateParams() {
		HashMap<String, Object> params = super.getTemplateParams();
		String baseApiPath = navBuilder.buildAbsolute()
				+ "/rest/privaterepos/1.0";
		params.put("baseApiPath", baseApiPath);
		return params;
	}

	@Override
	protected void render(HttpServletResponse resp, String templateResources,
			String templateKey, HashMap<String, Object> templateParams)
			throws IOException, ServletException {
		if (licenseManager.isLicenseValid()) {
			super.render(resp, getTemplateResources(), getTemplateKey(),
					getTemplateParams());
			return;
		}

		renderLicenseError(resp);
	}

	private void renderLicenseError(HttpServletResponse resp)
			throws IOException, ServletException {
		HashMap<String, Object> params = super.getTemplateParams();
		params.put("status", licenseManager.getLicenseStatus());
		super.render(resp, licenseErrorTemplateResources,
				licenseErrorTemplateKey, params);
	}
}
