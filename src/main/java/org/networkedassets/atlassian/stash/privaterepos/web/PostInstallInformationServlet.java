package org.networkedassets.atlassian.stash.privaterepos.web;

public class PostInstallInformationServlet extends SoyTemplateServlet {

	private static final long serialVersionUID = 4973352820822186785L;

	@Override
	protected String getTemplateResources() {
		return "org.networkedassets.atlassian.stash.privaterepos:post-install-soy-templates";
	}

	@Override
	protected String getTemplateKey() {
		return "org.networkedassets.personalRepos.postInstall.page";
	}

}
