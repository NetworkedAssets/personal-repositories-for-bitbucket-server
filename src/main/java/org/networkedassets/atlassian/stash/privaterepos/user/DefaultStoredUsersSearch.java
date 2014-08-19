package org.networkedassets.atlassian.stash.privaterepos.user;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.PageRequestImpl;

@Component
public class DefaultStoredUsersSearch implements StoredUsersSearch {

	private static final int MAX_FOUND_USERS = 20;

	@Autowired
	private UserIdsRepository userIdsRepository;

	@Autowired
	private UserService userService;

	@Override
	public Set<StashUser> findNonStoredUsers(String searchKeyword) {
		int currentPage = 1;
		Map<Integer, StashUser> usersMap = findUsersByName(searchKeyword,
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

	private Map<Integer, StashUser> findUsersByName(String searchKeyword,
			int page) {
		Iterable<? extends StashUser> stashUsers = userService.findUsersByName(
				searchKeyword, new PageRequestImpl(0, MAX_FOUND_USERS))
				.getValues();
		Map<Integer, StashUser> usersMap = createUserByIdMap(stashUsers);
		return usersMap;
	}

	private Map<Integer, StashUser> createUserByIdMap(
			Iterable<? extends StashUser> stashUsers) {
		Map<Integer, StashUser> usersMap = new HashMap<Integer, StashUser>();

		for (StashUser stashUser : stashUsers) {
			usersMap.put(stashUser.getId(), stashUser);
		}
		return usersMap;
	}

	private void removeStoredUsersFromMap(Map<Integer, StashUser> usersMap) {
		Set<Integer> allStoredUsers = userIdsRepository.getAll();

		for (Integer userId : allStoredUsers) {
			usersMap.remove(userId);
		}
	}

	private LinkedHashSet<StashUser> getValuesSet(
			Map<Integer, StashUser> usersMap) {
		return new LinkedHashSet<StashUser>(usersMap.values());
	}

}
