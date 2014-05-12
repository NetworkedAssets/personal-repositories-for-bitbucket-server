package org.networkedassets.atlassian.stash.private_repositories_permissions.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;
import com.atlassian.stash.exception.AuthorisationException;
import com.atlassian.stash.nav.NavBuilder;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.PermissionValidationService;

public class AdministrationPanelServlet extends HttpServlet {

	private static final long serialVersionUID = 5750547122008437666L;

	private final SoyTemplateRenderer soyTemplateRenderer;
	private final PermissionValidationService permissionValidationService;
	private final NavBuilder navBuilder;

	public AdministrationPanelServlet(SoyTemplateRenderer soyTemplateRenderer,
			PermissionValidationService permissionValidationService,
			NavBuilder navBuilder) {
		this.soyTemplateRenderer = soyTemplateRenderer;
		this.permissionValidationService = permissionValidationService;
		this.navBuilder = navBuilder;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			permissionValidationService.validateForGlobal(Permission.SYS_ADMIN);
			render(resp);
		} catch (AuthorisationException e) {
			resp.sendRedirect(navBuilder.login().buildAbsolute());
		}
	}

	private void render(HttpServletResponse resp) throws IOException,
			ServletException {
		try {
			resp.setContentType("text/html;charset=UTF-8");

			soyTemplateRenderer
					.render(resp.getWriter(),
							"org.networkedassets.atlassian.stash.private-repositories-permissions-plugin:templates-soy",
							"PrivateRepos.adminPage",
							new HashMap<String, Object>());

		} catch (SoyException e) {
			Throwable cause = e.getCause();
			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			throw new ServletException(e);
		}
	}

}
