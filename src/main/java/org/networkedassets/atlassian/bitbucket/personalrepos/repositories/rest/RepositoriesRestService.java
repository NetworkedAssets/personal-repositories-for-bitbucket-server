package org.networkedassets.atlassian.bitbucket.personalrepos.repositories.rest;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.networkedassets.atlassian.bitbucket.personalrepos.auth.RestAccessFilter;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.Owner;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.PersonalRepository;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.SortCriteria;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.SortField;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.SortOrder;
import org.networkedassets.atlassian.bitbucket.personalrepos.util.rest.RestPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;
import com.atlassian.bitbucket.util.PageRequestImpl;

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
