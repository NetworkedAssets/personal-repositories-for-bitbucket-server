package org.networkedassets.atlassian.stash.private_repositories_permissions.servlet.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.stash.exception.AuthorisationException;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.PermissionValidationService;

public class PrivateRepositoryCreationFilter implements Filter {

	private final PermissionValidationService permissionValidationService;

	private static final Logger log = LoggerFactory
			.getLogger(PrivateRepositoryCreationFilter.class);

	private static final String FILTER_URI_REGEX = "/users/user/repos.*|/stash/mvc/projects/.*|.*/projects/~.*/repos";
	
	public PrivateRepositoryCreationFilter(PermissionValidationService permissionValidationService) {
		this.permissionValidationService = permissionValidationService;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String uri = httpRequest.getRequestURI();


		if (uriMatchesFilter(uri)) {
			log.warn("MATCH ! " + uri);
			try {
				permissionValidationService.validateForGlobal(Permission.ADMIN);
				chain.doFilter(request, response);
			} catch (AuthorisationException e) {
				rejectRequest(httpRequest, httpResponse);
			}
		} else {
			log.warn("NOT MATCHING " + uri);
			chain.doFilter(request, response);
		}
	}

	private void rejectRequest(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws IOException {

		PrintWriter writer = httpResponse.getWriter();

		httpResponse.setStatus(401);
		httpResponse.resetBuffer();

		writer.print("");
		writer.close();
	}

	private boolean uriMatchesFilter(String uri) {
		return uri.matches(FILTER_URI_REGEX);
	}

}