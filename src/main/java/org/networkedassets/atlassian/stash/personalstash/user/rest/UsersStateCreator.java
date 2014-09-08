package org.networkedassets.atlassian.stash.personalstash.user.rest;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.nav.NavBuilder;
import com.atlassian.stash.user.StashUser;

@Component
public class UsersStateCreator {

	@Autowired
	private NavBuilder navBuilder;

	public Set<UserState> createFrom(Set<StashUser> stashUsers) {
		Set<UserState> usersState = new LinkedHashSet<UserState>();

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

	public void fillFrom(UserState userState, StashUser stashUser) {
		userState.setAvatarUrl(navBuilder.user(stashUser).avatar(64)
				.buildAbsolute());
		userState.setId(stashUser.getId());
		userState.setName(stashUser.getName());
		userState.setDisplayName(stashUser.getDisplayName());
		userState.setProfileUrl(navBuilder.user(stashUser).buildRelative());
		userState.setEmail(stashUser.getEmailAddress());
		userState.setActive(stashUser.isActive());
	}

}
