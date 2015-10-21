package org.networkedassets.atlassian.bitbucket.personalrepos.repositories.rest;

import java.util.ArrayList;
import java.util.List;

import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.Owner;
import org.networkedassets.atlassian.bitbucket.personalrepos.user.rest.UsersStateCreator;
import org.networkedassets.atlassian.bitbucket.personalrepos.util.rest.RestPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.user.UserService;
import com.atlassian.bitbucket.util.Page;

@Component
class RepositoryOwnerStateCreator {

	private final Logger log = LoggerFactory
			.getLogger(RepositoryOwnerStateCreator.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UsersStateCreator usersStateCreator;

	public RestPage<RepositoryOwnerState> createFrom(Page<Owner> owners) {
		RestPage<RepositoryOwnerState> resultsPage = new RestPage<RepositoryOwnerState>();
		resultsPage.setItems(createFrom(owners.getValues()));
		return resultsPage;
	}

	public List<RepositoryOwnerState> createFrom(Iterable<Owner> owners) {

		List<RepositoryOwnerState> ownerStates = new ArrayList<RepositoryOwnerState>();

		for (Owner owner : owners) {
			ownerStates.add(create(owner,
					userService.getUserById(owner.getUserId(), true)));
		}

		return ownerStates;
	}

	public RepositoryOwnerState createFrom(Owner owner) {
		return create(owner, getApplicationUserFromOwner(owner));
	}

	private RepositoryOwnerState create(Owner owner, ApplicationUser user) {
		RepositoryOwnerState ownerState = new RepositoryOwnerState();
		usersStateCreator.fillFrom(ownerState, user);
		fillFromOwner(ownerState, owner);
		return ownerState;
	}

	private void fillFromOwner(RepositoryOwnerState ownerState, Owner owner) {
		ownerState.setRepositoriesSize(owner.getRepositoriesSize());
	}

	private ApplicationUser getApplicationUserFromOwner(Owner owner) {
		return userService.getUserById(owner.getUserId(), true);
	}

}
