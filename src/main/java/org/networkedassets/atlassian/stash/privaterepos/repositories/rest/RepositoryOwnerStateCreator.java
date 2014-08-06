package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.networkedassets.atlassian.stash.privaterepos.repositories.Owner;
import org.networkedassets.atlassian.stash.privaterepos.user.rest.UsersStateCreator;
import org.networkedassets.atlassian.stash.privaterepos.util.RestPage;
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
		List<RepositoryOwnerState> ownersState = new ArrayList<RepositoryOwnerState>();

		Set<? extends StashUser> stashUsers = getStashUsersFromOwners(owners);
		Iterator<? extends StashUser> stashUsersIterator = stashUsers
				.iterator();

		log.debug("{} StashUsers found", stashUsers.size());

		for (Owner owner : owners) {
			log.debug("Creating State object from Owner {}", owner);
			ownersState.add(create(owner, stashUsersIterator.next()));
		}
		return ownersState;
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
		return userService.getUserById(owner.getUserId());
	}

	private Set<? extends StashUser> getStashUsersFromOwners(
			Iterable<Owner> owners) {
		Set<Integer> ids = new HashSet<Integer>();
		for (Owner owner : owners) {
			ids.add(owner.getUserId());
		}
		if (ids.size() > 0) {
			return userService.getUsersById(ids);
		}
		
		return new HashSet<StashUser>();
	}

}
