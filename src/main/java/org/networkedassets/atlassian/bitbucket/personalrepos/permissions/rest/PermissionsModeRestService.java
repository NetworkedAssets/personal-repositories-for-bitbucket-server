package org.networkedassets.atlassian.bitbucket.personalrepos.permissions.rest;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.networkedassets.atlassian.bitbucket.personalrepos.auth.RestAccessFilter;
import org.networkedassets.atlassian.bitbucket.personalrepos.permissions.PermissionsModeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/permissions")
@Produces({ MediaType.APPLICATION_JSON })
public class PermissionsModeRestService {

	private final Logger log = LoggerFactory
			.getLogger(PermissionsModeRestService.class);

	@Autowired
	private PermissionsModeService permissionsModeService;
	
	@Autowired
	private RestAccessFilter restAccessFilter;

	@GET
	@Path("/mode")
	public PermissionsMode getMode() {
		restAccessFilter.run();
		return createPermissionModeReponse(permissionsModeService
				.getPermissionsMode());
	}

	@PUT
	@Path("/mode")
	public Response putMode(PermissionsMode mode) {
		restAccessFilter.run();
		permissionsModeService.setPermissionsMode(parsePermissionModeParam(mode
				.getMode()));
		return Response.ok().build();
	}

	private org.networkedassets.atlassian.bitbucket.personalrepos.permissions.PermissionsMode parsePermissionModeParam(
			String mode) {
		if (mode.equalsIgnoreCase("deny")) {
			return org.networkedassets.atlassian.bitbucket.personalrepos.permissions.PermissionsMode.DENY;
		} else {
			return org.networkedassets.atlassian.bitbucket.personalrepos.permissions.PermissionsMode.ALLOW;
		}
	}

	private PermissionsMode createPermissionModeReponse(
			org.networkedassets.atlassian.bitbucket.personalrepos.permissions.PermissionsMode mode) {
		PermissionsMode permissionsMode = new PermissionsMode();
		permissionsMode.setMode(mode.toString().toLowerCase());
		return permissionsMode;
	}

}
