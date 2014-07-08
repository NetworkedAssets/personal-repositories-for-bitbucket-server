package org.networkedassets.atlassian.stash.privaterepos.auth;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.exception.AuthorisationException;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.PermissionValidationService;

@Component
public class AdminAuthorizationVerifier {

	private final PermissionValidationService permissionValidationService;

	@Autowired
	public AdminAuthorizationVerifier(
			PermissionValidationService permissionValidationService) {
		this.permissionValidationService = permissionValidationService;
	}

	public void verify() {
		try {
			permissionValidationService.validateForGlobal(Permission.SYS_ADMIN);
		} catch (AuthorisationException e) {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
	}
}
