package org.networkedassets.atlassian.stash.privaterepos.group.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.networkedassets.atlassian.stash.privaterepos.auth.AdminAuthorizationVerifier;
import org.networkedassets.atlassian.stash.privaterepos.group.AllowedGroupsService;
import org.networkedassets.atlassian.stash.privaterepos.util.NamesList;
import org.springframework.stereotype.Component;

@Component
@Path("/groups/")
@Produces({ MediaType.APPLICATION_JSON })
public class GroupRestService {

	private final AllowedGroupsService allowedGroupsService;
	private final GroupsInfoBuilder groupsInfoBuilder;
	private final AdminAuthorizationVerifier authorizationVerifier;

	public GroupRestService(AllowedGroupsService allowedGroupsService,
			GroupsInfoBuilder groupInfoBuilder,
			AdminAuthorizationVerifier authorizationVerifier) {
		this.allowedGroupsService = allowedGroupsService;
		this.groupsInfoBuilder = groupInfoBuilder;
		this.authorizationVerifier = authorizationVerifier;
	}

	@Path("list")
	@GET
	public List<GroupInfo> getGroups() {
		this.authorizationVerifier.verify();
		return groupsInfoBuilder.build();
	}

	@Path("group/{group}")
	@POST
	public Response addGroup(@Context UriInfo uriInfo,
			@PathParam("group") String groupName) {
		this.authorizationVerifier.verify();
		this.allowedGroupsService.allow(groupName);
		return Response.created(uriInfo.getAbsolutePath()).build();
	}

	@Path("list")
	@POST
	public Response addGroups(NamesList names) {
		this.authorizationVerifier.verify();
		this.allowedGroupsService.allow(names.getNames());
		return Response.ok().build();
	}

	@Path("group/{group}")
	@DELETE
	public Response deleteGroup(@PathParam("group") String groupName) {
		this.authorizationVerifier.verify();
		this.allowedGroupsService.disallow(groupName);
		return Response.ok().build();
	}

	@Path("find/{key}")
	@GET
	public List<GroupInfo> findUsers(@PathParam("key") String key) {
		this.authorizationVerifier.verify();
		return groupsInfoBuilder
				.build(allowedGroupsService.findNotAllowed(key));
	}

}
