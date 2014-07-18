package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.networkedassets.atlassian.stash.privaterepos.repositories.Owner;
import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageImpl;
import com.atlassian.stash.util.PageRequest;
import com.atlassian.stash.util.PageRequestImpl;
import com.google.common.collect.Lists;

@Component
@Path("/repositories/")
@Produces({ MediaType.APPLICATION_JSON })
public class RepositoriesRestService {

	private final Logger logger = LoggerFactory
			.getLogger(RepositoriesRestService.class);

	@Autowired
	private PersonalRepositoriesService personalRepositoriesService;

	@Autowired
	private RepositoryOwnerInfoCreator repositoryOwnerInfoCreator;

	@Path("owners")
	@GET
	public Page<RepositoryOwnerInfo> getUsers(
			@QueryParam("limit") Integer limit,
			@QueryParam("from") Integer offset) {

		PageRequest pageRequest = new PageRequestImpl(offset, limit);
		Page<Owner> ownersPage = personalRepositoriesService
				.getPersonalRepositoriesOwners(pageRequest);

		ArrayList<Owner> ownersList = Lists
				.newArrayList(ownersPage.getValues());

		List<RepositoryOwnerInfo> ownersInfoList = repositoryOwnerInfoCreator
				.create(ownersList);

		return new PageImpl<RepositoryOwnerInfo>(pageRequest,
				ownersInfoList.size(), ownersInfoList, false);
	}

	@Path("user/{id}")
	@GET
	public Page<Repository> getUserRepositories(
			@PathParam("userId") Integer userId) {
		// this.authorizationVerifier.verify();
		return null;
	}

}
