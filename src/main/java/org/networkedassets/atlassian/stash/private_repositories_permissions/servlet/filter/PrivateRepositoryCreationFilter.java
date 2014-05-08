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

import org.networkedassets.atlassian.stash.private_repositories_permissions.service.UserPermissionsExaminer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrivateRepositoryCreationFilter implements Filter {

	private final UserPermissionsExaminer userPermissionsExaminer;
	
	private static final String FORBIDDEN_URI_REGEX = ".*/projects/~.*/repos.*";
	
	private static final Logger log = LoggerFactory
			.getLogger(PrivateRepositoryCreationFilter.class);


	public PrivateRepositoryCreationFilter(UserPermissionsExaminer userPermissionsExaminer
			) {
		this.userPermissionsExaminer = userPermissionsExaminer;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		if (isUriAllowed(httpRequest.getRequestURI()) || userPermissionsExaminer.canUsePrivateRepositories()) {
			chain.doFilter(request, response);
			return;
		}

		log.info(
				"Private Repositories Permissions Plugin: Tried accessing forbidden URI {}",
				httpRequest.getRequestURI());
		rejectRequest(httpRequest, httpResponse);
	}
	
	private boolean isUriAllowed(String uri) {
		return !uri.matches(FORBIDDEN_URI_REGEX);
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