package org.networkedassets.atlassian.stash.privaterepos.auth;

import java.util.List;

import org.networkedassets.atlassian.stash.privaterepos.group.AllowedGroupsService;
import org.networkedassets.atlassian.stash.privaterepos.group.Group;
import org.networkedassets.atlassian.stash.privaterepos.user.AllowedUsersService;
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
	private AllowedGroupsService allowedGroupsService;
	@Autowired
	private AllowedUsersService allowedUsersService;

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
