package org.networkedassets.atlassian.stash.private_repositories_permissions.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.networkedassets.atlassian.stash.private_repositories_permissions.rest.UsersInfoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;

public class AdministrationPanelServlet extends HttpServlet {

	private static final long serialVersionUID = 5750547122008437666L;

	private static final Logger log = LoggerFactory
			.getLogger(AdministrationPanelServlet.class);

	private final SoyTemplateRenderer soyTemplateRenderer;

	private final GroupTemplateParametersBuilder groupTemplateParametersBuilder;

	public AdministrationPanelServlet(SoyTemplateRenderer soyTemplateRenderer,
			UsersInfoBuilder userTemplateParametersBuilder,
			GroupTemplateParametersBuilder groupTemplateParametersBuilder) {
		this.soyTemplateRenderer = soyTemplateRenderer;
		this.groupTemplateParametersBuilder = groupTemplateParametersBuilder;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		render(resp);
	}

	private void render(HttpServletResponse resp) throws IOException,
			ServletException {
		try {
			resp.setContentType("text/html;charset=UTF-8");

			Map<String, Object> params = new HashMap<String, Object>();

			soyTemplateRenderer
					.render(resp.getWriter(),
							"org.networkedassets.atlassian.stash.private-repositories-permissions-plugin:templates-soy",
							"PrivateRepos.adminPage", params);

		} catch (SoyException e) {
			Throwable cause = e.getCause();
			if (cause instanceof IOException) {
				throw (IOException) cause;
			}
			throw new ServletException(e);
		}
	}

}
