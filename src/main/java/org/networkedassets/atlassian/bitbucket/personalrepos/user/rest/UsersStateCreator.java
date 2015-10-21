package org.networkedassets.atlassian.bitbucket.personalrepos.user.rest;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.nav.NavBuilder;
import com.atlassian.bitbucket.user.ApplicationUser;

@Component
public class UsersStateCreator {

	@Autowired
	private NavBuilder navBuilder;

	public Set<UserState> createFrom(Set<ApplicationUser> bitbucketUsers) {
		Set<UserState> usersState = new LinkedHashSet<UserState>();

		for (ApplicationUser bitbucketUser : bitbucketUsers) {
			usersState.add(createFrom(bitbucketUser));
		}
		return usersState;
	}

	public UserState createFrom(ApplicationUser bitbucketUser) {
		UserState state = new UserState();
		fillFrom(state, bitbucketUser);
		return state;
	}

	public void fillFrom(UserState userState, ApplicationUser bitbucketUser) {
		userState.setAvatarUrl(navBuilder.user(bitbucketUser).avatar(64)
				.buildAbsolute());
		userState.setId(bitbucketUser.getId());
		userState.setName(bitbucketUser.getName());
		userState.setDisplayName(bitbucketUser.getDisplayName());
		userState.setProfileUrl(navBuilder.user(bitbucketUser).buildRelative());
		userState.setEmail(bitbucketUser.getEmailAddress());
		userState.setActive(bitbucketUser.isActive());
	}

}
