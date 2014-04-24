package org.networkedassets.atlassian.stash.private_repositories_permissions.servlet.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.AllowedGroupsService;
import org.networkedassets.atlassian.stash.private_repositories_permissions.ao.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.stash.exception.AuthorisationException;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.PermissionValidationService;
import com.atlassian.stash.user.StashAuthenticationContext;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;

public class PrivateRepositoryCreationFilter implements Filter {

	private final PermissionValidationService permissionValidationService;
	private final UserService userService;
	private final StashAuthenticationContext authenthicationContext;
	private final AllowedGroupsService allowedGroupsService;
	
	private StashUser currentUser;

	private static final Logger log = LoggerFactory
			.getLogger(PrivateRepositoryCreationFilter.class);

	private static final String FORBIDDEN_URI_REGEX = "/users/user/repos.*|/stash/mvc/projects/.*|.*/projects/~.*/repos|.*/repos/.*/permissions/groups.*";

	public PrivateRepositoryCreationFilter(
			PermissionValidationService permissionValidationService,
			UserService userService,
			StashAuthenticationContext authenticationContext,
			AllowedGroupsService allowedGroupsService) {
		this.permissionValidationService = permissionValidationService;
		this.userService = userService;
		this.authenthicationContext = authenticationContext;
		this.allowedGroupsService = allowedGroupsService;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (isRequestAllowed(httpRequest)) {
			chain.doFilter(request, response);
			return;
		}

		log.info(
				"Private Repositories Permissions Plugin: Tried accessing forbidden URI {}",
				httpRequest.getRequestURI());
		rejectRequest(httpRequest, httpResponse);
	}

	private boolean isRequestAllowed(HttpServletRequest httpRequest) {
		currentUser = authenthicationContext.getCurrentUser();
		
		if (isUserAnonymous()) {
			return true;
		}
		
		if (isCurrentUserAdmin()) {
			return true;
		}

		String uri = httpRequest.getRequestURI();

		/*
		 * Check URI first - faster then fetching allowed groups
		 */
		if (isUriAllowed(uri)) {
			return true;
		}
		
		return (userAllowedToUsePrivateRepositories());
	}

	private boolean isUserAnonymous() {
		return (currentUser == null);
	}

	private boolean isCurrentUserAdmin() {
		try {
			permissionValidationService.validateForGlobal(Permission.ADMIN);
		} catch (AuthorisationException e) {
			return false;
		}
		return true;
	}

	private boolean isUriAllowed(String uri) {
		return !uri.matches(FORBIDDEN_URI_REGEX);
	}

	private boolean userAllowedToUsePrivateRepositories() {
		return isUserInAllowedGroup(currentUser);
	}

	private boolean isUserInAllowedGroup(StashUser currentUser) {

		List<Group> groups = getGroupsAllowedToAccessPrivateRepositories();

		for (Group group : groups) {
			if (userService.isUserInGroup(currentUser, group.getName())) {
				return true;
			}
		}
		return false;
	}

	private List<Group> getGroupsAllowedToAccessPrivateRepositories() {
		List<Group> groups = allowedGroupsService.all();
		log.warn("Groups fetched {}", groups.size());
		return groups;
	}

	private void rejectRequest(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException {

		PrintWriter writer = httpResponse.getWriter();

		httpResponse.setStatus(401);
		httpResponse.resetBuffer();

		writer.print("");
		writer.close();
	}

}