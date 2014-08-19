package org.networkedassets.atlassian.stash.privaterepos.group;

import java.util.Set;

import org.networkedassets.atlassian.stash.privaterepos.permissions.PermissionsModeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;

@Component
public class DefaultStoredGroupsService implements StoredGroupsService {

	@Autowired
	private GroupsRepository groupsRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PermissionsModeService permissionsModeService;

	private Logger log = LoggerFactory
			.getLogger(DefaultStoredGroupsService.class);

	@Override
	public Set<String> getAll() {
		return groupsRepository.getAll();
	}

	@Override
	public Set<String> add(Set<String> groupNames) {
		groupsRepository.add(groupNames);
		return groupNames;
	}

	@Override
	public void remove(Set<String> groupNames) {
		groupsRepository.remove(groupNames);
	}

	@Override
	public boolean isAllowed(String group) {
		boolean groupStored = groupsRepository.contains(group);
		if (permissionsModeService.isAllowMode()) {
			return !groupStored;
		} else {
			return groupStored;
		}
	}

	@Override
	public boolean isDenied(String group) {
		return !isAllowed(group);
	}

	@Override
	public void removeAll() {
		groupsRepository.removeAll();
	}

}
