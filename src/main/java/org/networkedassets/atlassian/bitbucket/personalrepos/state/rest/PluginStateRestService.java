package org.networkedassets.atlassian.bitbucket.personalrepos.state.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.networkedassets.atlassian.bitbucket.personalrepos.auth.RestAccessFilter;
import org.networkedassets.atlassian.bitbucket.personalrepos.state.PluginStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/state")
@Produces({ MediaType.APPLICATION_JSON })
public class PluginStateRestService {

	@Autowired
	private RestAccessFilter restAccessFilter;
	@Autowired
	private PluginStateManager pluginStateManager;

	@GET
	public PluginState getState() {
		restAccessFilter.run();
		PluginState state = new PluginState();
		state.setState(pluginStateManager.getState().name());
		return state;
	}

}
