package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.networkedassets.atlassian.stash.privaterepos.repositories.Owner;
import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepository;
import org.networkedassets.atlassian.stash.privaterepos.util.RestPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	@Autowired
	private PersonalRepositoryStateCreator personalRepositoryStateCreator;

	@Path("owners")
	@GET
	public RestPage<RepositoryOwnerState> getUsers(
			@DefaultValue("1") @QueryParam("page") int page,
			@DefaultValue("20") @QueryParam("perPage") int perPage) {

		PageRequest pageRequest = new PageRequestImpl((page - 1) * perPage,
				perPage);

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
	public List<PersonalRepositoryState> getUserRepositories(
			@PathParam("id") int userId) {
		// this.authorizationVerifier.verify();
		log.debug("Requested repositories for user {}", userId);
		List<PersonalRepository> userPersonalRepositories = personalRepositoriesService
				.getUserPersonalRepositories(userId);
		return personalRepositoryStateCreator
				.createFrom(userPersonalRepositories);
	}

}
