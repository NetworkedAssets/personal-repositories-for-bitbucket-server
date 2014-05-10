package org.networkedassets.atlassian.stash.private_repositories_permissions.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.User;
import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedUsersService;

import com.atlassian.stash.nav.NavBuilder;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;

public class UserTemplateParametersBuilder {

	private final AllowedUsersService allowedUsersService;
	private final UserService userService;
	private final NavBuilder navBuilder;

	public UserTemplateParametersBuilder(AllowedUsersService allowedUsersService,
			UserService userService, NavBuilder navBuilder) {
		this.allowedUsersService = allowedUsersService;
		this.userService = userService;
		this.navBuilder = navBuilder;
	}

	public List<Object> build() {
		List<Object> userParams = new ArrayList<Object>();
		Set<? extends StashUser> stashUsers = getStashUsers();

		for (StashUser stashUser : stashUsers) {
			userParams.add(createUserParams(stashUser));
		}
		return userParams;
	}

	private Set<? extends StashUser> getStashUsers() {
		List<User> users = allowedUsersService.all();
		Set<String> names = new HashSet<String>();

		for (User user : users) {
			names.add(user.getName());
		}

		return userService.getUsersByName(names);
	}

	private Map<String, Object> createUserParams(StashUser user) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("stashUser", user);
		params.put("avatar", navBuilder.profile().avatar(64).buildAbsolute());
		params.put("profileUrl", navBuilder.user(user).buildRelative());

		return params;
	}

}
