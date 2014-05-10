package org.networkedassets.atlassian.stash.private_repositories_permissions.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.networkedassets.atlassian.stash.private_repositories_permissions.service.AllowedUsersService;

public class UserRestServlet extends HttpServlet {

	private static final long serialVersionUID = -8251168964064985101L;

	private final AllowedUsersService allowedUsersService;

	public UserRestServlet(AllowedUsersService allowedUsersService) {
		this.allowedUsersService = allowedUsersService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handleRequestWithCommand(req, resp, new Command() {
			@Override
			public void execute(String userName) {
				allowedUsersService.disallow(userName);
			}
		});
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		handleRequestWithCommand(req, resp, new Command() {
			@Override
			public void execute(String userName) {
				allowedUsersService.allow(userName);
			}
		});
	}
	
	private interface Command {
		void execute(String userName);
	}

	private void handleRequestWithCommand(HttpServletRequest req,
			HttpServletResponse resp, Command command) throws IOException {
		String userName = getUserNameFromRequest(req);
		if (!userNameValid(userName)) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "");
		}
	}

	private String getUserNameFromRequest(HttpServletRequest req) {
		return req.getPathInfo().substring(1);
	}

	private boolean userNameValid(String userName) {
		return userName.length() > 0;
	}

}
