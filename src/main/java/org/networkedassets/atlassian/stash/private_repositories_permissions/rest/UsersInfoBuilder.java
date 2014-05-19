package org.networkedassets.atlassian.stash.private_repositories_permissions.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.User;
import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedUsersService;

import com.atlassian.stash.nav.NavBuilder;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;

public class UsersInfoBuilder {

	private final AllowedUsersService allowedUsersService;
	private final UserService userService;
	private final NavBuilder navBuilder;

	public UsersInfoBuilder(AllowedUsersService allowedUsersService,
			UserService userService, NavBuilder navBuilder) {
		this.allowedUsersService = allowedUsersService;
		this.userService = userService;
		this.navBuilder = navBuilder;
	}

	public List<UserInfo> build() {
		return build(allowedUsersService.all());
	}

	public List<UserInfo> build(List<User> users) {
		return buildFromStashUsers(getStashUsers(users));
	}

	public List<UserInfo> buildFromStashUsers(List<StashUser> stashUsers) {
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
