package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import java.util.ArrayList;
import java.util.List;

import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.repository.RepositoryService;

@Component
public class PersonalRepositoryStateCreator {

	private final Logger log = LoggerFactory
			.getLogger(PersonalRepositoryStateCreator.class);

	@Autowired
	private RepositoryService repositoryService;

	public List<PersonalRepositoryState> createFrom(
			List<PersonalRepository> userPersonalRepositories) {
		
		log.debug("Creating state objects from {}", userPersonalRepositories);

		List<PersonalRepositoryState> personalRepositoryStates = new ArrayList<PersonalRepositoryState>();

		for (PersonalRepository personalRepository : userPersonalRepositories) {
			personalRepositoryStates.add(createFrom(personalRepository));
		}
		return personalRepositoryStates;
	}

	public PersonalRepositoryState createFrom(
			PersonalRepository personalRepository) {
		Repository stashRepository = repositoryService
				.getById(personalRepository.getRepositoryId());
		return createFrom(personalRepository, stashRepository);
	}

	private PersonalRepositoryState createFrom(
			PersonalRepository personalRepository, Repository stashRepository) {
		PersonalRepositoryState state = new PersonalRepositoryState();
		state.setRepositorySize(personalRepository.getRepositorySize());
		state.setRepositoryName(stashRepository.getName());
		return state;
	}

}
