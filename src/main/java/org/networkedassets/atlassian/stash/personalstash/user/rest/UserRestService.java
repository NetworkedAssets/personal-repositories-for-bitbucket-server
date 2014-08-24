package org.networkedassets.atlassian.stash.personalstash.user.rest;

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

import org.networkedassets.atlassian.stash.personalstash.auth.RestAccessFilter;
import org.networkedassets.atlassian.stash.personalstash.user.StoredUsersSearch;
import org.networkedassets.atlassian.stash.personalstash.user.StoredUsersService;
import org.networkedassets.atlassian.stash.personalstash.util.rest.IdsSet;
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
	private RestAccessFilter restAccessFilter;
	@Autowired
	private StoredUsersSearch storedUsersSearch;

	@Path("find/{key}")
	@GET
	public Set<UserState> findUsers(@PathParam("key") String key) {
		restAccessFilter.run();
		return usersStateCreator.createFrom(storedUsersSearch
				.findNonStoredUsers(key));
	}

	@Path("list")
	@GET
	public Set<UserState> getUsers() {
		restAccessFilter.run();
		return usersStateCreator.createFrom(storedUserService.getAll());
	}

	@Path("list")
	@POST
	public Response addUsers(IdsSet<Integer> ids) {
		restAccessFilter.run();
		storedUserService.add(ids.getIds());
		return Response.ok().build();
	}

	@Path("user/{id}")
	@POST
	public Response addUser(@Context UriInfo uriInfo,
			@PathParam("id") Integer userId) {
		restAccessFilter.run();
		storedUserService.add(Sets.newHashSet(userId));
		return Response.created(uriInfo.getAbsolutePath()).build();
	}

	@Path("user/{id}")
	@DELETE
	public Response deleteUser(@PathParam("id") Integer userId) {
		restAccessFilter.run();
		storedUserService.remove(Sets.newHashSet(userId));
		return Response.ok().build();
	}

}
