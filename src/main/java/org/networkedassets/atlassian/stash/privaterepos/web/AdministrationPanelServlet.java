package org.networkedassets.atlassian.stash.privaterepos.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;
import com.atlassian.stash.exception.AuthorisationException;
import com.atlassian.stash.nav.NavBuilder;
import com.atlassian.stash.user.Permission;
import com.atlassian.stash.user.PermissionValidationService;

public class AdministrationPanelServlet extends HttpServlet {

	private static final long serialVersionUID = 5750547122008437666L;

	@Autowired
	private SoyTemplateRenderer soyTemplateRenderer;
	@Autowired
	private PermissionValidationService permissionValidationService;
	@Autowired
	private NavBuilder navBuilder;

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

			HashMap<String, Object> params = new HashMap<String, Object>();
			String baseApiPath = navBuilder.buildAbsolute() + "/rest/privaterepos/1.0";
			params.put("baseApiPath", baseApiPath);
			

			soyTemplateRenderer
					.render(resp.getWriter(),
							"org.networkedassets.atlassian.stash.privaterepos:templates-soy",
							"org.networkedassets.personalRepos.adminPage", params);

		} catch (SoyException e) {
			Throwable cause = e.getCause();
			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			throw new ServletException(e);
		}
	}

}