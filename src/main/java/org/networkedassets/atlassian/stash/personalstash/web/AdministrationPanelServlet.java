package org.networkedassets.atlassian.stash.personalstash.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.networkedassets.atlassian.stash.personalstash.license.LicenseManager;
import org.networkedassets.atlassian.stash.personalstash.repositories.RepositoriesPreScanningScheduler;
import org.networkedassets.atlassian.stash.personalstash.state.PluginState;
import org.networkedassets.atlassian.stash.personalstash.state.PluginStateManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.stash.exception.AuthorisationException;
import com.atlassian.stash.nav.NavBuilder;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.PermissionValidationService;

public class AdministrationPanelServlet extends SoyTemplateServlet {

	private static final long serialVersionUID = 7919713417018246905L;

	private String templatesResource = "org.networkedassets.atlassian.stash.personalstash:templates-soy";
	private String templateName = "org.networkedassets.personalstash.adminPanel";

	private String licenseErrorTemplatesResource = "org.networkedassets.atlassian.stash.personalstash:license-servlet-resources";
	private String licenseErrorTemplateName = "org.networkedassets.personalstash.license.errorPage";

	@Autowired
	private LicenseManager licenseManager;
	@Autowired
	private NavBuilder navBuilder;
	@Autowired
	private PluginStateManager pluginStateManager;
	@Autowired
	private RepositoriesPreScanningScheduler repositoriesPreScanningScheduler;
	@Autowired
	private PermissionValidationService permissionValidationService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			permissionValidationService.validateForGlobal(Permission.ADMIN);
			render(resp);
		} catch (AuthorisationException e) {
			resp.sendRedirect(navBuilder.login().next().buildAbsolute());
		} catch (Exception e) {

		}
	}

	protected void render(HttpServletResponse resp) throws IOException,
			ServletException {
		if (licenseManager.isLicenseValid()) {
			scanRepositoriesIfNeeded();
			renderAdminPanel(resp);
		} else {
			renderLicenseError(resp);
		}
	}

	private void scanRepositoriesIfNeeded() {
		if (pluginStateManager.getState() == PluginState.SCAN_NEEDED) {
			repositoriesPreScanningScheduler.scheduleScan();
		}
	}

	private void renderAdminPanel(HttpServletResponse resp) throws IOException,
			ServletException {
		Map<String, Object> params = new HashMap<String, Object>();
		String baseApiPath = navBuilder.buildAbsolute()
				+ "/rest/personalstash/1.0";
		params.put("baseApiPath", baseApiPath);

		super.render(resp, templatesResource, templateName, params);
	}

	private void renderLicenseError(HttpServletResponse resp)
			throws IOException, ServletException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", licenseManager.getLicenseStatus());

		super.render(resp, licenseErrorTemplatesResource,
				licenseErrorTemplateName, params);
	}
}