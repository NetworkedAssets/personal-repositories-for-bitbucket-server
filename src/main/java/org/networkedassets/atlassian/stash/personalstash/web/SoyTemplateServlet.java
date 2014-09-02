package org.networkedassets.atlassian.stash.personalstash.web;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.soy.renderer.SoyException;
import com.atlassian.soy.renderer.SoyTemplateRenderer;
import com.atlassian.stash.nav.NavBuilder;

public abstract class SoyTemplateServlet extends HttpServlet {

	private static final long serialVersionUID = -7773632602555052731L;

	@Autowired
	private SoyTemplateRenderer soyTemplateRenderer;
	@Autowired
	private NavBuilder navBuilder;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		render(resp, getTemplateResources(), getTemplateKey(),
				getTemplateParams());
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
