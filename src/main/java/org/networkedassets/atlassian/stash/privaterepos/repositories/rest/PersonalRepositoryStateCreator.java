package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import java.util.ArrayList;
import java.util.List;

import org.networkedassets.atlassian.stash.privaterepos.repositories.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.stash.repository.Repository;
import com.atlassian.stash.repository.RepositoryService;

public class PersonalRepositoryStateCreator {

	@Autowired
	private RepositoryService repositoryService;

	public List<PersonalRepositoryState> createFrom(
			List<PersonalRepository> userPersonalRepositories) {

		List<PersonalRepositoryState> personalRepositoryStates = new ArrayList<PersonalRepositoryState>();

		for (PersonalRepository personalRepository : userPersonalRepositories) {
			personalRepositoryStates.add(createFrom(personalRepository));
		}
		return personalRepositoryStates;
	}

	public PersonalRepositoryState createFrom(
			PersonalRepository personalRepository) {
		Repository stashRepository = repositoryService
				.getById(personalRepository.getID());
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
