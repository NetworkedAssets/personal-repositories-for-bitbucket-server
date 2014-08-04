package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.networkedassets.atlassian.stash.privaterepos.repositories.Owner;
import org.networkedassets.atlassian.stash.privaterepos.user.rest.UsersStateCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageImpl;
import com.atlassian.stash.util.PageRequestImpl;

@Component
class RepositoryOwnerStateCreator {

	@Autowired
	private UserService userService;

	@Autowired
	private UsersStateCreator usersStateCreator;

	public Page<RepositoryOwnerState> createFrom(Page<Owner> owners) {
		PageRequestImpl pageRequest = new PageRequestImpl(owners.getStart(),
				owners.getLimit());

		return new PageImpl<RepositoryOwnerState>(pageRequest,
				createFrom(owners.getValues()), owners.getIsLastPage());
	}

	public List<RepositoryOwnerState> createFrom(Iterable<Owner> owners) {
		List<RepositoryOwnerState> ownersState = new ArrayList<RepositoryOwnerState>();

		Set<? extends StashUser> stashUsers = getStashUsersFromOwners(owners);
		Iterator<? extends StashUser> stashUsersIterator = stashUsers
				.iterator();

		for (Owner owner : owners) {
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
		return userService.getUsersById(ids);
	}

}
