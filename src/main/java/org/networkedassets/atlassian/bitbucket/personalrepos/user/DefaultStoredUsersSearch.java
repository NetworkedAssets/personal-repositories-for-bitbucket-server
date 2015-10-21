package org.networkedassets.atlassian.bitbucket.personalrepos.user;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.user.UserService;
import com.atlassian.bitbucket.util.PageRequestImpl;

@Component
public class DefaultStoredUsersSearch implements StoredUsersSearch {

	private static final int MAX_FOUND_USERS = 20;

	@Autowired
	private UserIdsRepository userIdsRepository;

	@Autowired
	private UserService userService;

	private final Logger log = LoggerFactory
			.getLogger(DefaultStoredUsersSearch.class);

	@Override
	public Set<ApplicationUser> findNonStoredUsers(String searchKeyword) {
		int currentPage = 1;
		Map<Integer, ApplicationUser> usersMap = findUsersByName(searchKeyword,
				currentPage);
		int foundUsersCount = usersMap.size();
		removeStoredUsersFromMap(usersMap);

		while (usersMap.isEmpty() && foundUsersCount == MAX_FOUND_USERS) {
			usersMap = findUsersByName(searchKeyword, ++currentPage);
			foundUsersCount = usersMap.size();
			removeStoredUsersFromMap(usersMap);
		}

		return getValuesSet(usersMap);
	}

	private Map<Integer, ApplicationUser> findUsersByName(String searchKeyword,
			int page) {
		int offset = MAX_FOUND_USERS * (page - 1);
		int limit = offset + MAX_FOUND_USERS;
		log.debug("Searching for users by {} with limit {} and offset {}",
				searchKeyword, limit, offset);
		Iterable<? extends ApplicationUser> bitbucketUsers = userService.findUsersByName(
				searchKeyword, new PageRequestImpl(offset, limit)).getValues();
		Map<Integer, ApplicationUser> usersMap = createUserByIdMap(bitbucketUsers);
		return usersMap;
	}

	private Map<Integer, ApplicationUser> createUserByIdMap(
			Iterable<? extends ApplicationUser> bitbucketUsers) {
		Map<Integer, ApplicationUser> usersMap = new HashMap<Integer, ApplicationUser>();

		for (ApplicationUser bitbucketUser : bitbucketUsers) {
			usersMap.put(bitbucketUser.getId(), bitbucketUser);
		}
		return usersMap;
	}

	private void removeStoredUsersFromMap(Map<Integer, ApplicationUser> usersMap) {
		Set<Integer> allStoredUsers = userIdsRepository.getAll();

		for (Integer userId : allStoredUsers) {
			usersMap.remove(userId);
		}
	}

	private LinkedHashSet<ApplicationUser> getValuesSet(
			Map<Integer, ApplicationUser> usersMap) {
		return new LinkedHashSet<ApplicationUser>(usersMap.values());
	}

}
