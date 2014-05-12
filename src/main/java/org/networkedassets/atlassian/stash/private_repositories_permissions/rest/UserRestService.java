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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/users/")
@Produces({ MediaType.APPLICATION_JSON })
public class UserRestService {

	private final static Logger log = LoggerFactory
			.getLogger(UserRestService.class);

	private final AllowedUsersService allowedUsersService;
	private final UsersInfoBuilder usersInfoBuilder;

	public UserRestService(AllowedUsersService allowedUsersService,
			UsersInfoBuilder usersInfoBuilder) {
		this.allowedUsersService = allowedUsersService;
		this.usersInfoBuilder = usersInfoBuilder;
	}
	
	@Path("find/{key}")
	@GET
	public List<UserInfo> findUsers(@PathParam("key") String key) {
		return usersInfoBuilder.buildFromStashUsers(allowedUsersService.findNotAllowed(key));
	}

	@Path("list")
	@GET
	public List<UserInfo> getUsers() {
		return usersInfoBuilder.build();
	}
	
	@Path("list")
	@POST
	public Response addGroups(NamesList names) {
		this.allowedUsersService.allow(names.getNames());
		return Response.ok().build();
	}
	
	@Path("user/{user}")
	@POST
	public Response addUser(@Context UriInfo uriInfo,
			@PathParam("user") String userName) {
		allowedUsersService.allow(userName);
		return Response.created(uriInfo.getAbsolutePath()).build();
	}

	@Path("user/{user}")
	@DELETE
	public Response deleteUser(@PathParam("user") String userName) {
		log.warn("Delete entered");
		allowedUsersService.disallow(userName);
		return Response.ok().build();
	}

}
