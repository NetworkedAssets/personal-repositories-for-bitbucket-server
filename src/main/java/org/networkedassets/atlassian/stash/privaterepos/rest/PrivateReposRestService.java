package org.networkedassets.atlassian.stash.privaterepos.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.repository.RepositorySearchRequest;
import com.atlassian.stash.repository.RepositoryService;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequestImpl;

@Path("/repos/")
@Produces({ MediaType.APPLICATION_JSON })
public class PrivateReposRestService {

	private final AuthorizationVerifier authorizationVerifier;
	private final RepositoryService repositoryService;

	public PrivateReposRestService(RepositoryService repositoryService,
			AuthorizationVerifier authorizationVerifier) {
		this.repositoryService = repositoryService;
		this.authorizationVerifier = authorizationVerifier;
	}

	@Path("list")
	@GET
	public List<UserInfo> getUsers() {
		this.authorizationVerifier.verify();

		RepositoryService service = null;
		Page<Repository> search = service.search(
				new RepositorySearchRequest.Builder().projectName("~").build(),
				new PageRequestImpl(0, 20));

		for (Repository repo : search.getValues()) {

		}
		
		return null;

	}

}
