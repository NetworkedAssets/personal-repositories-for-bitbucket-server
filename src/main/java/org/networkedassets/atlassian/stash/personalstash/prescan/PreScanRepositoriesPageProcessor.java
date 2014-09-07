package org.networkedassets.atlassian.stash.personalstash.prescan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.networkedassets.atlassian.stash.personalstash.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.stash.personalstash.repositories.RepositoryTypeVerifier;
import org.networkedassets.atlassian.stash.personalstash.util.PageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.project.PersonalProject;
import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.repository.RepositoryService;
import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;

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
		Map<StashUser, List<Repository>> ownersReposMap = new HashMap<StashUser, List<Repository>>();

		for (Repository repo : page.getValues()) {
			addIfPersonalRepository(ownersReposMap, repo);
		}

		for (StashUser user : ownersReposMap.keySet()) {
			personalRepositoriesService.addUserPersonalRepositories(user,
					ownersReposMap.get(user));
		}
	}

	private void addIfPersonalRepository(
			Map<StashUser, List<Repository>> ownersReposMap, Repository repo) {

		log.debug("Analyzing repository {}", repo);
		if (repositoryTypeVerifier.isPersonal(repo)) {
			log.debug("Repo {} is personal", repo);
			addPersonalRepository(ownersReposMap, repo);
		}
	}

	private void addPersonalRepository(
			Map<StashUser, List<Repository>> ownersReposMap, Repository repo) {
		PersonalProject personalProject = (PersonalProject) repo.getProject();
		StashUser owner = personalProject.getOwner();
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