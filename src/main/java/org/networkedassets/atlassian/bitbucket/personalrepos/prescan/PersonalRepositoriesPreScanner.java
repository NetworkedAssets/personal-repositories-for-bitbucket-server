package org.networkedassets.atlassian.bitbucket.personalrepos.prescan;

import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.PersonalRepositoriesService;
import org.networkedassets.atlassian.bitbucket.personalrepos.util.AllPagesIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.repository.Repository;

@Component
public class PersonalRepositoriesPreScanner {

	// 1000 repos at once shouldn't kill us, should it ?
	private static final int REPOSITORIES_PER_PAGE = 1000;

	Logger log = LoggerFactory.getLogger(PersonalRepositoriesPreScanner.class);

	@Autowired
	PersonalRepositoriesService personalRepositoriesService;

	@Autowired
	PreScanRepositoriesPageProcessor pageProcessor;

	public void scanPersonalRepositories() {
		log.debug("Personal repositories pre-scanning started");

		personalRepositoriesService.purge();

		AllPagesIterator<Repository> repositoriesIterator = new AllPagesIterator.Builder<Repository>(
				pageProcessor).resultsPerPage(REPOSITORIES_PER_PAGE).build();

		repositoriesIterator.processAllPages();

		log.debug("Personal repositories pre scanning finished");
	}

}
