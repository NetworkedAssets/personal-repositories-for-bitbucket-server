package org.networkedassets.atlassian.stash.privaterepos.permissions;

import org.networkedassets.atlassian.stash.privaterepos.group.StoredGroupsService;
import org.networkedassets.atlassian.stash.privaterepos.user.StoredUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultPermissionsModeService implements PermissionsModeService {

	@Autowired
	private PermissionsModeRepository permissionsModeRepository;
	@Autowired
	private StoredUsersService userService;
	@Autowired
	private StoredGroupsService groupsService;

	@Override
	public boolean isAllowMode() {
		return permissionsModeRepository.getPermissionsMode() == PermissionsMode.ALLOW;
	}

	@Override
	public boolean isDenyMode() {
		return permissionsModeRepository.getPermissionsMode() == PermissionsMode.DENY;
	}

	@Override
	public PermissionsMode getPermissionsMode() {
		return permissionsModeRepository.getPermissionsMode();
	}

	@Override
	public void setPermissionsMode(PermissionsMode mode) {
		permissionsModeRepository.setPermissionsMode(mode);
		userService.removeAll();
		groupsService.removeAll();
	}
}
