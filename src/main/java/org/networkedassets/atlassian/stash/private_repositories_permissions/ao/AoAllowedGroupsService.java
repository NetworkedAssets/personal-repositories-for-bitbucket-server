package org.networkedassets.atlassian.stash.private_repositories_permissions.ao;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import net.java.ao.Query;

import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedGroupsService;

import com.atlassian.activeobjects.external.ActiveObjects;

public class AoAllowedGroupsService implements AllowedGroupsService {
	
	private final ActiveObjects ao;

    public AoAllowedGroupsService(ActiveObjects ao)
    {
        this.ao = checkNotNull(ao);
    }

	@Override
	public List<Group> all() {
		return newArrayList(ao.find(Group.class, Query.select()));
	}

	@Override
	public void disallow(Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Group allow(Group group) {
		// TODO Auto-generated method stub
		return null;
	}

}
