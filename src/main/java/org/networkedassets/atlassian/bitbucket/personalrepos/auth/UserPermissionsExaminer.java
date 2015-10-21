package org.networkedassets.atlassian.bitbucket.personalrepos.auth;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.networkedassets.atlassian.bitbucket.personalrepos.group.StoredGroupsService;
import org.networkedassets.atlassian.bitbucket.personalrepos.group.UserGroupsService;
import org.networkedassets.atlassian.bitbucket.personalrepos.permissions.PermissionsMode;
import org.networkedassets.atlassian.bitbucket.personalrepos.permissions.PermissionsModeService;
import org.networkedassets.atlassian.bitbucket.personalrepos.user.StoredUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.auth.AuthenticationContext;
import com.atlassian.bitbucket.user.ApplicationUser;

@Component
public class UserPermissionsExaminer {

	@Autowired
	private AuthenticationContext authenthicationContext;
	@Autowired
	private StoredGroupsService storedGroupsService;
	@Autowired
	private StoredUsersService storedUsersService;
	@Autowired
	private PermissionsModeService permissionsModeService;
	@Autowired
	private UserGroupsService userGroupsService;
	
	private final Logger log = LoggerFactory.getLogger(UserPermissionsExaminer.class);

	public boolean canUsePersonalRepositories() {
		ApplicationUser currentUser = authenthicationContext.getCurrentUser();

		if (isUserAnonymous(currentUser)) {
			return false;
		}

		return userAllowedToUsePersonalRepositories(currentUser);
	}

	private boolean isUserAnonymous(ApplicationUser currentUser) {
		return (currentUser == null);
	}

	private boolean userAllowedToUsePersonalRepositories(ApplicationUser currentUser) {
		if (permissionsModeService.getPermissionsMode() == PermissionsMode.ALLOW) {
			if (isUserAllowed(currentUser)) {
				return isUserInAllowedGroup(currentUser);
			} else {
				return false;
			}
		} else {
			if (isUserAllowed(currentUser)) {
				return true;
			} else {
				return isUserInAllowedGroup(currentUser);
			}
		}
	}

	private boolean isUserAllowed(ApplicationUser bitbucketUser) {
		return storedUsersService.isAllowed(bitbucketUser.getId());
	}

	private boolean isUserInAllowedGroup(ApplicationUser user) {
		Set<String> userGroups = userGroupsService.getUserGroups(user);
		Set<String> storedGroups = storedGroupsService.getAll();
		@SuppressWarnings("unchecked")
		Collection<String> intersection = CollectionUtils.intersection(
				userGroups, storedGroups);

		log.debug("Groups intersection {}", intersection);;
		if (permissionsModeService.isAllowMode()) {
			return intersection.size() == 0;
		} else {
			return intersection.size() > 0;
		}

	}
}
