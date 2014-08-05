package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.networkedassets.atlassian.stash.privaterepos.repositories.Owner;
import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.stash.privaterepos.util.RestPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;
import com.atlassian.stash.util.PageRequestImpl;

@Component
@Path("/repositories/")
@Produces({ MediaType.APPLICATION_JSON })
public class RepositoriesRestService {

	private final Logger log = LoggerFactory
			.getLogger(RepositoriesRestService.class);

	@Autowired
	private PersonalRepositoriesService personalRepositoriesService;

	@Autowired
	private RepositoryOwnerStateCreator repositoryOwnerStateCreator;

	@Path("owners")
	@GET
	public RestPage<RepositoryOwnerState> getUsers(
			@DefaultValue("0") @QueryParam("offset") int offset,
			@DefaultValue("20") @QueryParam("limit") int limit) {

		PageRequest pageRequest = new PageRequestImpl(offset, limit);

		Page<Owner> ownersPage = personalRepositoriesService
				.getPersonalRepositoriesOwners(pageRequest);

		RestPage<RepositoryOwnerState> ownersStatePage = repositoryOwnerStateCreator
				.createFrom(ownersPage);

		int ownersCount = personalRepositoriesService.getOwnersCount();
		ownersStatePage.setTotalItems(ownersCount);

		return ownersStatePage;
	}

	@Path("user/{id}")
	@GET
	public Page<Repository> getUserRepositories(@PathParam("userId") int userId) {
		// this.authorizationVerifier.verify();
		return null;
	}

}
