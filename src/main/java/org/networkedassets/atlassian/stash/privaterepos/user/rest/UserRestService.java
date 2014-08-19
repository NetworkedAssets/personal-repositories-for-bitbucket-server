package org.networkedassets.atlassian.stash.privaterepos.user.rest;

import java.util.LinkedHashSet;
import java.util.Set;

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
import org.networkedassets.atlassian.stash.privaterepos.user.StoredUsersSearch;
import org.networkedassets.atlassian.stash.privaterepos.user.StoredUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component
@Path("/users/")
@Produces({ MediaType.APPLICATION_JSON })
public class UserRestService {

	@Autowired
	private StoredUsersService storedUserService;
	@Autowired
	private UsersStateCreator usersStateCreator;
	@Autowired
	private AdminAuthorizationVerifier authorizationVerifier;
	@Autowired
	private StoredUsersSearch storedUsersSearch;

	@Path("find/{key}")
	@GET
	public Set<UserState> findUsers(@PathParam("key") String key) {
		this.authorizationVerifier.verify();
		return usersStateCreator.createFrom(storedUsersSearch
				.findNonStoredUsers(key));
	}

	@Path("list")
	@GET
	public Set<UserState> getUsers() {
		authorizationVerifier.verify();
		return usersStateCreator.createFrom(storedUserService.getAll());
	}

	@Path("list")
	@POST
	public Response addUsers(Set<Integer> ids) {
		authorizationVerifier.verify();
		storedUserService.add(ids);
		return Response.ok().build();
	}

	@Path("user/{user}")
	@POST
	public Response addUser(@Context UriInfo uriInfo,
			@PathParam("id") Integer userId) {
		this.authorizationVerifier.verify();
		storedUserService.add(Sets.newHashSet(userId));
		return Response.created(uriInfo.getAbsolutePath()).build();
	}

	@Path("user/{user}")
	@DELETE
	public Response deleteUser(@PathParam("id") Integer userId) {
		authorizationVerifier.verify();
		storedUserService.remove(Sets.newHashSet(userId));
		return Response.ok().build();
	}

}
