package org.networkedassets.atlassian.stash.privaterepos.auth;

import java.util.List;

import org.networkedassets.atlassian.stash.privaterepos.group.AllowedGroupsService;
import org.networkedassets.atlassian.stash.privaterepos.group.Group;
import org.networkedassets.atlassian.stash.privaterepos.user.AllowedUsersService;

import com.atlassian.stash.user.StashAuthenticationContext;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;

public class UserPermissionsExaminer {

	private final UserService userService;
	private final StashAuthenticationContext authenthicationContext;
	private final AllowedGroupsService allowedGroupsService;
	private final AllowedUsersService allowedUsersService;
	
	private StashUser currentUser;
	
	public UserPermissionsExaminer(
			UserService userService,
			StashAuthenticationContext authenticationContext,
			AllowedGroupsService allowedGroupsService,
			AllowedUsersService allowedUsersService) {
		this.userService = userService;
		this.authenthicationContext = authenticationContext;
		this.allowedGroupsService = allowedGroupsService;
		this.allowedUsersService = allowedUsersService;
	}
	
	public boolean canUsePrivateRepositories() {
		currentUser = authenthicationContext.getCurrentUser();
		
		if (isUserAnonymous()) {
			return false;
		}
		
		return userAllowedToUsePrivateRepositories();
	}

	private boolean isUserAnonymous() {
		return (currentUser == null);
	}

	private boolean userAllowedToUsePrivateRepositories() {
		return isUserAllowed(currentUser) || isUserInAllowedGroup(currentUser);
	}
	
	private boolean isUserAllowed(StashUser stashUser) {
		return allowedUsersService.isAllowed(stashUser.getName());
	}

	private boolean isUserInAllowedGroup(StashUser user) {

		List<Group> groups = getGroupsAllowedToAccessPrivateRepositories();

		for (Group group : groups) {
			if (userService.isUserInGroup(user, group.getName())) {
				return true;
			}
		}
		return false;
	}

	private List<Group> getGroupsAllowedToAccessPrivateRepositories() {
		return allowedGroupsService.all();
	}
}
