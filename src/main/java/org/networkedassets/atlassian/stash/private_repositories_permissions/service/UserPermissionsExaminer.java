package org.networkedassets.atlassian.stash.private_repositories_permissions.service;

import java.util.List;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.Group;

import com.atlassian.stash.exception.AuthorisationException;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.PermissionValidationService;
import com.atlassian.stash.user.StashAuthenticationContext;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;

public class UserPermissionsExaminer {

	private final PermissionValidationService permissionValidationService;
	private final UserService userService;
	private final StashAuthenticationContext authenthicationContext;
	private final AllowedGroupsService allowedGroupsService;
	
	private StashUser currentUser;
	
	public UserPermissionsExaminer(
			PermissionValidationService permissionValidationService,
			UserService userService,
			StashAuthenticationContext authenticationContext,
			AllowedGroupsService allowedGroupsService) {
		this.permissionValidationService = permissionValidationService;
		this.userService = userService;
		this.authenthicationContext = authenticationContext;
		this.allowedGroupsService = allowedGroupsService;
	}
	
	public boolean canUsePrivateRepositories() {
		currentUser = authenthicationContext.getCurrentUser();
		if (isUserAnonymous()) {
			return true;
		}

		if (isCurrentUserAdmin()) {
			return true;
		}
		
		return userAllowedToUsePrivateRepositories();
	}

	private boolean isUserAnonymous() {
		return (currentUser == null);
	}

	private boolean isCurrentUserAdmin() {
		try {
			permissionValidationService.validateForGlobal(Permission.ADMIN);
		} catch (AuthorisationException e) {
			return false;
		}
		return true;
	}

	private boolean userAllowedToUsePrivateRepositories() {
		return isUserInAllowedGroup(currentUser);
	}

	private boolean isUserInAllowedGroup(StashUser currentUser) {

		List<Group> groups = getGroupsAllowedToAccessPrivateRepositories();

		for (Group group : groups) {
			if (userService.isUserInGroup(currentUser, group.getName())) {
				return true;
			}
		}
		return false;
	}

	private List<Group> getGroupsAllowedToAccessPrivateRepositories() {
		return allowedGroupsService.all();
	}
}
