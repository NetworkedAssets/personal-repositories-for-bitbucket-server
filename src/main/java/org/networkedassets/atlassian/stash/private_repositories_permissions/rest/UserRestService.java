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

import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedUsersService;

@Path("/")
@Produces({ MediaType.APPLICATION_JSON })
public class UserRestService {

	private final AllowedUsersService allowedUsersService;

	private final UsersInfoBuilder usersInfoBuilder;

	public UserRestService(AllowedUsersService allowedUsersService,
			UsersInfoBuilder usersInfoBuilder) {
		this.allowedUsersService = allowedUsersService;
		this.usersInfoBuilder = usersInfoBuilder;
	}

	@Path("users")
	@GET
	public List<UserInfo> getUsers() {
		return usersInfoBuilder.build();
	}

	@Path("user/${user}")
	@POST
	public Response addUser(@Context UriInfo uriInfo,
			@PathParam("user") String userName) {
		this.allowedUsersService.allow(userName);
		return Response.created(uriInfo.getAbsolutePath()).build();
	}

	@Path("user/${user}")
	@DELETE
	public Response deleteUser(@PathParam("user") String userName) {
		this.allowedUsersService.disallow(userName);
		return Response.ok().build();
	}

}
