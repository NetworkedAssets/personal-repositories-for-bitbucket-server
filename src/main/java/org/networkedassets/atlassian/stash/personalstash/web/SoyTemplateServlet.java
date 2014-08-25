package org.networkedassets.atlassian.stash.personalstash.web;

import java.io.IOException;
import java.io.PrintWriter;
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

public abstract class SoyTemplateServlet extends HttpServlet {

	private static final long serialVersionUID = -7773632602555052731L;

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
			render(resp, getTemplateResources(), getTemplateKey(),
					getTemplateParams());
		} catch (AuthorisationException e) {
			resp.sendRedirect(navBuilder.login().buildAbsolute());
		}
	}

	protected void render(HttpServletResponse resp, String templateResources,
			String templateKey, HashMap<String, Object> templateParams)
			throws IOException, ServletException {
		try {
			resp.setContentType("text/html;charset=UTF-8");
			soyTemplateRenderer.render(resp.getWriter(), templateResources,
					templateKey, templateParams);

		} catch (SoyException e) {
			Throwable cause = e.getCause();
			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			throw new ServletException(e);
		}
	}

	protected abstract String getTemplateResources();

	protected abstract String getTemplateKey();

	protected HashMap<String, Object> getTemplateParams() {
		return new HashMap<String, Object>();
	}
}
