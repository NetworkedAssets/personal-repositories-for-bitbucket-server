package org.networkedassets.atlassian.stash.personalstash.permissions;

import org.networkedassets.atlassian.stash.personalstash.group.StoredGroupsService;
import org.networkedassets.atlassian.stash.personalstash.user.StoredUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private final Logger log = LoggerFactory
			.getLogger(DefaultPermissionsModeService.class);

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
		log.debug("Setting permissions mode to {}", mode);
		permissionsModeRepository.setPermissionsMode(mode);
		log.debug("Removing all users and groups");
		userService.removeAll();
		groupsService.removeAll();
	}
}
