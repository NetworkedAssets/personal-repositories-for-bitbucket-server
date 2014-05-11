package org.networkedassets.atlassian.stash.private_repositories_permissions.rest;

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

import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedGroupsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/groups/")
@Produces({ MediaType.APPLICATION_JSON })
public class GroupRestService {

	private final static Logger log = LoggerFactory
			.getLogger(GroupRestService.class);

	private final AllowedGroupsService allowedGroupsService;

	private final GroupsInfoBuilder groupsInfoBuilder;

	public GroupRestService(AllowedGroupsService allowedGroupsService,
			GroupsInfoBuilder groupInfoBuilder) {
		this.allowedGroupsService = allowedGroupsService;
		this.groupsInfoBuilder = groupInfoBuilder;
	}

	@Path("list")
	@GET
	public List<GroupInfo> getGroups() {
		return groupsInfoBuilder.build();
	}

	@Path("group/{group}")
	@POST
	public Response addGroup(@Context UriInfo uriInfo,
			@PathParam("group") String groupName) {
		this.allowedGroupsService.allow(groupName);
		return Response.created(uriInfo.getAbsolutePath()).build();
	}

	@Path("group/{group}")
	@DELETE
	public Response deleteGroup(@PathParam("group") String groupName) {
		log.warn("Delete entered");
		this.allowedGroupsService.disallow(groupName);
		return Response.ok().build();
	}

}
