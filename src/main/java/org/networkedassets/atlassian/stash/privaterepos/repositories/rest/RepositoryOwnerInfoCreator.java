package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.networkedassets.atlassian.stash.privaterepos.repositories.Owner;
import org.networkedassets.atlassian.stash.privaterepos.user.rest.UsersInfoCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;

@Component
class RepositoryOwnerInfoCreator {

	@Autowired
	private UserService userService;

	@Autowired
	private UsersInfoCreator usersInfoCreator;

	public List<RepositoryOwnerInfo> create(List<Owner> owners) {
		List<RepositoryOwnerInfo> ownersInfo = new ArrayList<RepositoryOwnerInfo>();

		Set<? extends StashUser> stashUsers = getStashUsersFromOwners(owners);
		Iterator<? extends StashUser> stashUsersIterator = stashUsers
				.iterator();

		for (Owner owner : owners) {
			ownersInfo.add(create(owner, stashUsersIterator.next()));
		}
		return ownersInfo;
	}

	public RepositoryOwnerInfo create(Owner owner) {
		return create(owner, getStashUserFromOwner(owner));
	}

	private RepositoryOwnerInfo create(Owner owner, StashUser user) {
		RepositoryOwnerInfo ownerInfo = new RepositoryOwnerInfo();
		usersInfoCreator.fillUserInfoWithStashUserData(ownerInfo, user);
		fillOwnerInfoWithOwnerData(ownerInfo, owner);
		return ownerInfo;
	}

	private void fillOwnerInfoWithOwnerData(RepositoryOwnerInfo ownerInfo,
			Owner owner) {
		ownerInfo.setRepositoriesSize(owner.getRepositoriesSize());
	}

	private StashUser getStashUserFromOwner(Owner owner) {
		return userService.getUserById(owner.getUserId());
	}

	private Set<? extends StashUser> getStashUsersFromOwners(List<Owner> owners) {
		Set<Integer> ids = new HashSet<Integer>();
		for (Owner owner : owners) {
			ids.add(owner.getUserId());
		}
		return userService.getUsersById(ids);
	}

}
