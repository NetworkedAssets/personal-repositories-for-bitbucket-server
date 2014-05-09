package org.networkedassets.atlassian.stash.private_repositories_permissions.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;
import com.google.common.collect.ImmutableMap;

public class AdministrationPanelServlet extends HttpServlet {

	private static final long serialVersionUID = 5750547122008437666L;
	
	private static final Logger log = LoggerFactory
			.getLogger(AdministrationPanelServlet.class);

	private final SoyTemplateRenderer soyTemplateRenderer;

	public AdministrationPanelServlet(SoyTemplateRenderer soyTemplateRenderer) {
		this.soyTemplateRenderer = soyTemplateRenderer;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.warn(req.getRequestURI());
		render(resp);
	}

	private void render(HttpServletResponse resp) throws IOException, ServletException {
		try {
			
			resp.setContentType("text/html;charset=UTF-8");
			soyTemplateRenderer
					.render(resp.getWriter(), "org.networkedassets.atlassian.stash.private-repositories-permissions-plugin:templates-soy",
							"admin.panel", ImmutableMap.<String, Object> builder().build());
		} catch (SoyException e) {
			Throwable cause = e.getCause();
			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			throw new ServletException(e);
		}
	}

}
