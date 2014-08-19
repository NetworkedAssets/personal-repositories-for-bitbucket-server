package org.networkedassets.atlassian.stash.privaterepos.auth;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.networkedassets.atlassian.stash.privaterepos.group.StoredGroupsService;
import org.networkedassets.atlassian.stash.privaterepos.permissions.PermissionsModeService;
import org.networkedassets.atlassian.stash.privaterepos.user.StoredUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.user.StashAuthenticationContext;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;

@Component
public class UserPermissionsExaminer {

	@Autowired
	private UserService userService;
	@Autowired
	private StashAuthenticationContext authenthicationContext;
	@Autowired
	private StoredGroupsService storedGroupsService;
	@Autowired
	private StoredUsersService storedUsersService;
	@Autowired
	private PermissionsModeService permissionsModeService;

	public boolean canUsePrivateRepositories() {
		StashUser currentUser = authenthicationContext.getCurrentUser();

		if (isUserAnonymous(currentUser)) {
			return false;
		}

		return userAllowedToUsePrivateRepositories(currentUser);
	}

	private boolean isUserAnonymous(StashUser currentUser) {
		return (currentUser == null);
	}

	private boolean userAllowedToUsePrivateRepositories(StashUser currentUser) {
		return isUserAllowed(currentUser) || isUserInAllowedGroup(currentUser);
	}

	private boolean isUserAllowed(StashUser stashUser) {
		return storedUsersService.isAllowed(stashUser.getId());
	}

	private boolean isUserInAllowedGroup(StashUser user) {

		Set<String> userGroups = storedGroupsService.getUserGroups(user);
		Set<String> storedGroups = storedGroupsService.getAll();
		@SuppressWarnings("unchecked")
		Collection<String> intersection = CollectionUtils.intersection(
				userGroups, storedGroups);

		if (permissionsModeService.isAllowMode()) {
			return intersection.size() == 0;
		} else {
			return intersection.size() > 0;
		}

	}
}
