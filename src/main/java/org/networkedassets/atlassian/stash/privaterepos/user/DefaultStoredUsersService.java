package org.networkedassets.atlassian.stash.privaterepos.user;

import java.util.HashSet;
import java.util.Set;

import org.networkedassets.atlassian.stash.privaterepos.permissions.PermissionsModeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;

@Component
public class DefaultStoredUsersService implements StoredUsersService {

	@Autowired
	private UserIdsRepository userIdsRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PermissionsModeService permissionsModeService;

	private Logger log = LoggerFactory
			.getLogger(DefaultStoredUsersService.class);

	@SuppressWarnings("unchecked")
	@Override
	public Set<StashUser> getAll() {
		Set<Integer> userIds = userIdsRepository.getAll();
		log.debug("Stored ids {}", userIds);
		if (userIds.isEmpty()) {
			return new HashSet<StashUser>();
		}
		return (Set<StashUser>) userService.getUsersById(userIds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<StashUser> add(Set<Integer> userIds) {
		userIdsRepository.add(userIds);
		return (Set<StashUser>) userService.getUsersById(userIds);
	}

	@Override
	public void remove(Set<Integer> userIds) {
		userIdsRepository.remove(userIds);
	}

	@Override
	public void removeAll() {
		userIdsRepository.removeAll();
	}

	@Override
	public boolean isAllowed(Integer userId) {
		boolean userStored = userIdsRepository.contains(userId);
		if (permissionsModeService.isAllowMode()) {
			return !userStored;
		} else {
			return userStored;
		}
	}

	@Override
	public boolean isDenied(Integer userId) {
		return !isAllowed(userId);
	}

}
