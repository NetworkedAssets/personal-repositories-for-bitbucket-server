package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.networkedassets.atlassian.stash.privaterepos.auth.RestAccessFilter;
import org.networkedassets.atlassian.stash.privaterepos.repositories.Owner;
import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepository;
import org.networkedassets.atlassian.stash.privaterepos.repositories.SortCriteria;
import org.networkedassets.atlassian.stash.privaterepos.repositories.SortField;
import org.networkedassets.atlassian.stash.privaterepos.repositories.SortOrder;
import org.networkedassets.atlassian.stash.privaterepos.util.rest.RestPage;
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

	@Autowired
	private RestAccessFilter restAccessFilter;

	@Path("owners")
	@GET
	public RestPage<RepositoryOwnerState> getUsers(
			@DefaultValue("1") @QueryParam("page") int page,
			@DefaultValue("20") @QueryParam("per_page") int perPage,
			@DefaultValue("size") @QueryParam("sort_by") String sort,
			@DefaultValue("desc") @QueryParam("order") String order) {
		restAccessFilter.run();

		PageRequest pageRequest = new PageRequestImpl((page - 1) * perPage,
				perPage);
		Page<Owner> ownersPage = personalRepositoriesService
				.getPersonalRepositoriesOwners(pageRequest,
						getSortCriteriaFromRequestParams(sort, order));

		RestPage<RepositoryOwnerState> ownersStatePage = repositoryOwnerStateCreator
				.createFrom(ownersPage);

		int ownersCount = personalRepositoriesService.getOwnersCount();
		ownersStatePage.setTotalItems(ownersCount);

		return ownersStatePage;
	}

	@Path("user/{id}")
	@GET
	public List<PersonalRepositoryState> getUserRepositories(
			@PathParam("id") int userId,
			@DefaultValue("size") @QueryParam("sort_by") String sort,
			@DefaultValue("desc") @QueryParam("order") String order) {
		restAccessFilter.run();
		
		log.debug("Requested repositories for user {}", userId);
		List<PersonalRepository> userPersonalRepositories = personalRepositoriesService
				.getUserPersonalRepositories(userId);
		return personalRepositoryStateCreator.createFrom(
				userPersonalRepositories,
				getSortCriteriaFromRequestParams(sort, order));
	}

	private SortCriteria getSortCriteriaFromRequestParams(String sortParam,
			String order) {
		SortOrder sortOrder;
		if (order.equalsIgnoreCase(SortOrder.ASC.toString())) {
			sortOrder = SortOrder.ASC;
		} else {
			sortOrder = SortOrder.DESC;
		}
		if (sortParam.equals("name")) {
			return new SortCriteria(SortField.NAME, sortOrder);
		}
		return new SortCriteria(SortField.SIZE, sortOrder);
	}

}
