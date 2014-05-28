package org.networkedassets.atlassian.stash.privaterepos.servlet.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.networkedassets.atlassian.stash.privaterepos.service.UserPermissionsExaminer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.stash.user.StashAuthenticationContext;
import com.atlassian.stash.user.StashUser;

public class PrivateRepositoryCreationFilter implements Filter {

	private final UserPermissionsExaminer userPermissionsExaminer;

	private final StashAuthenticationContext stashAuthenticationContext;

	private static final String FORBIDDEN_URI_REGEX = ".*/projects/~(.*)/repos.*";

	private static final Logger log = LoggerFactory
			.getLogger(PrivateRepositoryCreationFilter.class);

	public PrivateRepositoryCreationFilter(
			UserPermissionsExaminer userPermissionsExaminer,
			StashAuthenticationContext stashAuthenticationContext) {
		this.userPermissionsExaminer = userPermissionsExaminer;
		this.stashAuthenticationContext = stashAuthenticationContext;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		

		if (isUriAllowed(httpRequest.getRequestURI())
				|| userPermissionsExaminer.canUsePrivateRepositories()) {
			chain.doFilter(request, response);
			return;
		}

		log.info(
				"Private Repositories Permissions Plugin: Tried accessing forbidden URI {}",
				httpRequest.getRequestURI());
		rejectRequest(httpRequest, httpResponse);
	}

	private boolean isUriAllowed(String uri) {
		StashUser currentUser = stashAuthenticationContext.getCurrentUser();
		if (!stashAuthenticationContext.isAuthenticated()) {
			// leave it to the standard handler
			return true;
		}

		String currentUserSlug = currentUser.getSlug();
		Matcher uriMatcher = getUriMatcher(uri);

		if (uriMatcher.matches()) {
			return !uriUserMatchesCurrentUser(uriMatcher.group(1),
					currentUserSlug);
		}
		return true;
	}

	private Matcher getUriMatcher(String uri) {
		Pattern pattern = Pattern.compile(FORBIDDEN_URI_REGEX);
		Matcher matcher = pattern.matcher(uri);
		return matcher;
	}

	private boolean uriUserMatchesCurrentUser(String uriUser,
			String currentUserSlug) {
		if (uriUser == null) {
			return false;
		}
		return (uriUser.equals(currentUserSlug));
	}

	private void rejectRequest(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException {
		httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"You are not allowed to see this.");
	}

}