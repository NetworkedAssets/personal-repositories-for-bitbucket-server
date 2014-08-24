package org.networkedassets.atlassian.stash.personalstash.auth;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.networkedassets.atlassian.stash.personalstash.license.LicenseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.exception.AuthorisationException;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.PermissionValidationService;

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
