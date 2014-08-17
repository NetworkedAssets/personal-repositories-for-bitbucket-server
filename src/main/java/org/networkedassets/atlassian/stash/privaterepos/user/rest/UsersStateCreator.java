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
public class UsersStateCreator {

	@Autowired
	private AllowedUsersService allowedUsersService;
	@Autowired
	private UserService userService;
	@Autowired
	private NavBuilder navBuilder;

	public List<UserState> createFrom(List<StashUser> stashUsers) {
		List<UserState> usersState = new ArrayList<UserState>();

		for (StashUser stashUser : stashUsers) {
			usersState.add(createFrom(stashUser));
		}
		return usersState;
	}

	public UserState createFrom(StashUser stashUser) {
		UserState state = new UserState();
		fillFrom(state, stashUser);
		return state;
	}

	public void fillFrom(UserState userState,
			StashUser stashUser) {
		userState.setAvatarUrl(navBuilder.user(stashUser).avatar(64)
				.buildAbsolute());
		userState.setName(stashUser.getName());
		userState.setDisplayName(stashUser.getDisplayName());
		userState.setProfileUrl(navBuilder.user(stashUser).buildRelative());
		userState.setEmail(stashUser.getEmailAddress());
	}

}
