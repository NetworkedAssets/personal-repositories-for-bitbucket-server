package org.networkedassets.atlassian.stash.privaterepos.user.rest;

import java.util.ArrayList;
import java.util.List;

import org.networkedassets.atlassian.stash.privaterepos.user.AllowedUsersService;
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

	public List<UserInfo> create(List<StashUser> stashUsers) {
		List<UserInfo> usersInfo = new ArrayList<UserInfo>();

		for (StashUser stashUser : stashUsers) {
			usersInfo.add(create(stashUser));
		}
		return usersInfo;
	}

	public UserInfo create(StashUser stashUser) {
		UserInfo info = new UserInfo();
		fillUserInfoWithStashUserData(info, stashUser);
		return info;
	}

	public void fillUserInfoWithStashUserData(UserInfo userInfo,
			StashUser stashUser) {
		userInfo.setAvatarUrl(navBuilder.user(stashUser).avatar(64)
				.buildAbsolute());
		userInfo.setName(stashUser.getName());
		userInfo.setDisplayName(stashUser.getDisplayName());
		userInfo.setProfileUrl(navBuilder.user(stashUser).buildRelative());
		userInfo.setEmail(stashUser.getEmailAddress());
	}

}
