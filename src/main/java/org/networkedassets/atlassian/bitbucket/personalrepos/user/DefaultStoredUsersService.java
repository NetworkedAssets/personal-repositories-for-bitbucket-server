package org.networkedassets.atlassian.bitbucket.personalrepos.user;

import java.util.HashSet;
import java.util.Set;

import org.networkedassets.atlassian.bitbucket.personalrepos.permissions.PermissionsModeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.user.UserService;

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
	public Set<ApplicationUser> getAll() {
		Set<Integer> userIds = userIdsRepository.getAll();
		log.debug("Stored ids {}", userIds);
		if (userIds.isEmpty()) {
			return new HashSet<ApplicationUser>();
		}
		return (Set<ApplicationUser>) userService.getUsersById(userIds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<ApplicationUser> add(Set<Integer> userIds) {
		userIdsRepository.add(userIds);
		return (Set<ApplicationUser>) userService.getUsersById(userIds);
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
		log.debug("checking if user {} is allowed", userId);
		boolean userStored = userIdsRepository.contains(userId);
		if (permissionsModeService.isAllowMode()) {
			log.debug("Currently allow mode, user stored {}", userStored);
			return !userStored;
		} else {
			log.debug("Currently deny mode, user stored {}", userStored);
			return userStored;
		}
	}

	@Override
	public boolean isDenied(Integer userId) {
		return !isAllowed(userId);
	}

}
