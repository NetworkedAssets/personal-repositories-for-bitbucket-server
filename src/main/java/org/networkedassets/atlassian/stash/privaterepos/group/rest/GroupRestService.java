package org.networkedassets.atlassian.stash.privaterepos.group.rest;

import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.networkedassets.atlassian.stash.privaterepos.auth.RestAccessFilter;
import org.networkedassets.atlassian.stash.privaterepos.group.StoredGroupsSearch;
import org.networkedassets.atlassian.stash.privaterepos.group.StoredGroupsService;
import org.networkedassets.atlassian.stash.privaterepos.util.rest.IdsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

@Component
@Path("/groups/")
@Produces({ MediaType.APPLICATION_JSON })
public class GroupRestService {

	@Autowired
	private StoredGroupsService storedGroupsService;
	@Autowired
	private StoredGroupsSearch storedGroupsSearch;
	@Autowired
	private GroupsInfoBuilder groupsInfoBuilder;
	@Autowired
	private RestAccessFilter restAccessFilter;

	@Path("list")
	@GET
	public Set<GroupInfo> getGroups() {
		restAccessFilter.run();
		return groupsInfoBuilder.createFrom(storedGroupsService.getAll());
	}

	@Path("group/{group}")
	@POST
	public Response addGroup(@Context UriInfo uriInfo,
			@PathParam("group") String groupName) {
		restAccessFilter.run();
		storedGroupsService.add(Sets.newHashSet(groupName));
		return Response.created(uriInfo.getAbsolutePath()).build();
	}

	@Path("list")
	@POST
	public Response addGroups(IdsSet<String> names) {
		restAccessFilter.run();
		storedGroupsService.add(names.getIds());
		return Response.ok().build();
	}

	@Path("group/{group}")
	@DELETE
	public Response deleteGroup(@PathParam("group") String groupName) {
		restAccessFilter.run();
		storedGroupsService.remove(Sets.newHashSet(groupName));
		return Response.ok().build();
	}

	@Path("find/{key}")
	@GET
	public Set<GroupInfo> findUsers(@PathParam("key") String key) {
		restAccessFilter.run();
		return groupsInfoBuilder.createFrom(storedGroupsSearch
				.findNonStoredGroups(key));
	}

}
