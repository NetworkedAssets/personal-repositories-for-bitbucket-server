package org.networkedassets.atlassian.bitbucket.personalrepos.prescan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.RepositoryTypeVerifier;
import org.networkedassets.atlassian.bitbucket.personalrepos.util.PageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.project.PersonalProject;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;
import com.atlassian.bitbucket.user.ApplicationUser;
import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;

@Component
class PreScanRepositoriesPageProcessor implements PageProcessor<Repository> {

	private final Logger log = LoggerFactory
			.getLogger(PreScanRepositoriesPageProcessor.class);

	@Autowired
	PersonalRepositoriesService personalRepositoriesService;

	@Autowired
	RepositoryService repositoryService;

	@Autowired
	RepositoryTypeVerifier repositoryTypeVerifier;

	@Override
	public void process(Page<? extends Repository> page) {
		Map<ApplicationUser, List<Repository>> ownersReposMap = new HashMap<ApplicationUser, List<Repository>>();

		for (Repository repo : page.getValues()) {
			addIfPersonalRepository(ownersReposMap, repo);
		}

		for (ApplicationUser user : ownersReposMap.keySet()) {
			personalRepositoriesService.addUserPersonalRepositories(user,
					ownersReposMap.get(user));
		}
	}

	private void addIfPersonalRepository(
			Map<ApplicationUser, List<Repository>> ownersReposMap, Repository repo) {

		log.debug("Analyzing repository {}", repo);
		if (repositoryTypeVerifier.isPersonal(repo)) {
			log.debug("Repo {} is personal", repo);
			addPersonalRepository(ownersReposMap, repo);
		}
	}

	private void addPersonalRepository(
			Map<ApplicationUser, List<Repository>> ownersReposMap, Repository repo) {
		PersonalProject personalProject = (PersonalProject) repo.getProject();
		ApplicationUser owner = personalProject.getOwner();
		if (!ownersReposMap.containsKey(owner)) {
			ownersReposMap.put(personalProject.getOwner(),
					new ArrayList<Repository>());
		}
		ownersReposMap.get(owner).add(repo);
	}

	@Override
	public Page<? extends Repository> fetchPage(PageRequest pageRequest) {
		return repositoryService.findAll(pageRequest);
	}
}