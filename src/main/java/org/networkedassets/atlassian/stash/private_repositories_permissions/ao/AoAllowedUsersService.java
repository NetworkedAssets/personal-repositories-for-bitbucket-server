package org.networkedassets.atlassian.stash.private_repositories_permissions.ao;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import net.java.ao.Query;

import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedUsersService;

import com.atlassian.activeobjects.external.ActiveObjects;

public class AoAllowedUsersService implements AllowedUsersService {
	
	private final ActiveObjects ao;

    public AoAllowedUsersService(ActiveObjects ao)
    {
        this.ao = checkNotNull(ao);
    }

	@Override
	public List<User> all() {
		return newArrayList(ao.find(User.class, Query.select()));
	}

	@Override
	public User allow(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disallow(User user) {
		// TODO Auto-generated method stub
		
	}

}
