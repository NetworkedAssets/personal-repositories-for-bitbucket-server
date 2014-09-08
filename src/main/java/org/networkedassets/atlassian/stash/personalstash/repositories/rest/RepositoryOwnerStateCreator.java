package org.networkedassets.atlassian.stash.personalstash.repositories.rest;

import java.util.ArrayList;
import java.util.List;

import org.networkedassets.atlassian.stash.personalstash.repositories.Owner;
import org.networkedassets.atlassian.stash.personalstash.user.rest.UsersStateCreator;
import org.networkedassets.atlassian.stash.personalstash.util.rest.RestPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.Page;

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
		return create(owner, getStashUserFromOwner(owner));
	}

	private RepositoryOwnerState create(Owner owner, StashUser user) {
		RepositoryOwnerState ownerState = new RepositoryOwnerState();
		usersStateCreator.fillFrom(ownerState, user);
		fillFromOwner(ownerState, owner);
		return ownerState;
	}

	private void fillFromOwner(RepositoryOwnerState ownerState, Owner owner) {
		ownerState.setRepositoriesSize(owner.getRepositoriesSize());
	}

	private StashUser getStashUserFromOwner(Owner owner) {
		return userService.getUserById(owner.getUserId(), true);
	}

}
