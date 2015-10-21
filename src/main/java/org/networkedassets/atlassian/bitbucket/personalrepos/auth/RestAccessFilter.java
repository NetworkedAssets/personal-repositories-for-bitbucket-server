package org.networkedassets.atlassian.bitbucket.personalrepos.auth;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.networkedassets.atlassian.bitbucket.personalrepos.license.LicenseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.AuthorisationException;
import com.atlassian.bitbucket.permission.Permission;
import com.atlassian.bitbucket.permission.PermissionValidationService;

@Component
public class RestAccessFilter {

	@Autowired
	private PermissionValidationService permissionValidationService;

	@Autowired
	private LicenseManager licenseManager;

	public void run() {
		filterNonAdmins();
		filterInvalidLicense();
	}

	private void filterInvalidLicense() {
		if (!licenseManager.isLicenseValid()) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}

	private void filterNonAdmins() {
		try {
			permissionValidationService.validateForGlobal(Permission.ADMIN);
		} catch (AuthorisationException e) {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
	}
}
