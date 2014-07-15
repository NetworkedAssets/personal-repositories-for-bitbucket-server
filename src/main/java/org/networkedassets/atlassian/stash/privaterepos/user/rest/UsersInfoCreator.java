package org.networkedassets.atlassian.stash.privaterepos.user.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.networkedassets.atlassian.stash.privaterepos.user.AllowedUsersService;
import org.networkedassets.atlassian.stash.privaterepos.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.nav.NavBuilder;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;

@Component
public class UsersInfoCreator {

	@Autowired
	private AllowedUsersService allowedUsersService;
	@Autowired
	private UserService userService;
	@Autowired
	private NavBuilder navBuilder;

	public List<UserInfo> create(List<User> users) {
		return createFromStashUsers(getStashUsers(users));
	}

	public List<UserInfo> createFromStashUsers(List<StashUser> stashUsers) {
		List<UserInfo> usersInfo = new ArrayList<UserInfo>();

		for (StashUser stashUser : stashUsers) {
			usersInfo.add(createUserInfo(stashUser));
		}
		return usersInfo;
	}

	private List<StashUser> getStashUsers(List<User> users) {
		Set<String> names = new HashSet<String>();

		for (User user : users) {
			names.add(user.getName());
		}

		List<StashUser> stashUsers = new ArrayList<StashUser>();
		stashUsers.addAll(userService.getUsersByName(names));
		return stashUsers;
	}

	private UserInfo createUserInfo(StashUser user) {

		UserInfo info = new UserInfo();

		info.setAvatarUrl(navBuilder.user(user).avatar(64).buildAbsolute());
		info.setName(user.getName());
		info.setDisplayName(user.getDisplayName());
		info.setProfileUrl(navBuilder.user(user).buildRelative());
		info.setEmail(user.getEmailAddress());

		return info;
	}

}
