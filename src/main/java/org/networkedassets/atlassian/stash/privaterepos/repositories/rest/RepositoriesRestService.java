package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequestImpl;

@Path("/repositories/")
@Produces({ MediaType.APPLICATION_JSON })
public class RepositoriesRestService {

	private final Logger logger = LoggerFactory
			.getLogger(RepositoriesRestService.class);

	private final PersonalRepositoriesService personalRepositoriesService;

	public RepositoriesRestService(
			PersonalRepositoriesService personalRepositoriesService) {
		this.personalRepositoriesService = personalRepositoriesService;
	}

	@Path("users")
	@GET
	@SuppressWarnings("unchecked")
	public Page<StashUser> getUsers(@QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset) {
		// this.authorizationVerifier.verify();

		return (Page<StashUser>) personalRepositoriesService
				.findUsersHavingPersonalRepositories(new PageRequestImpl(
						offset, limit));
	}

	@Path("user/{id}")
	@DELETE
	public Response deleteUserRepositories(@PathParam("userId") Integer userId) {
		// this.authorizationVerifier.verify();
		return Response.ok().build();

	}

	@Path("user/{id}")
	@GET
	public Page<Repository> getUserRepositories(
			@PathParam("userId") Integer userId) {
		// this.authorizationVerifier.verify();
		return null;
	}

	@Path("repository/{id}")
	@DELETE
	public Response deleteRepository(
			@PathParam("repositoryId") Integer repositoryId) {
		// this.authorizationVerifier.verify();
		return Response.ok().build();

	}
}
