package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import java.util.ArrayList;
import java.util.List;

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
		for (Owner owner : owners) {
			ownersInfo.add(createOwnersInfo(owner));
		}
		return ownersInfo;
	}

	private RepositoryOwnerInfo createOwnersInfo(Owner owner) {
		RepositoryOwnerInfo ownerInfo = new RepositoryOwnerInfo();
		usersInfoCreator.fillUserInfoWithStashUserData(ownerInfo,
				getStashUserFromOwner(owner));
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

}
