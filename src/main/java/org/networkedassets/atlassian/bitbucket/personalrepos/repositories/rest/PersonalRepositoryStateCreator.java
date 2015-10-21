package org.networkedassets.atlassian.bitbucket.personalrepos.repositories.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.PersonalRepository;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.SortCriteria;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.SortField;
import org.networkedassets.atlassian.bitbucket.personalrepos.repositories.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.nav.NavBuilder;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.repository.RepositoryService;

@Component
public class PersonalRepositoryStateCreator {

	private final Logger log = LoggerFactory
			.getLogger(PersonalRepositoryStateCreator.class);

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private NavBuilder navBuilder;

	public List<PersonalRepositoryState> createFrom(
			List<PersonalRepository> userPersonalRepositories,
			SortCriteria sortCriteria) {

		log.debug("Creating state objects from {}", userPersonalRepositories);

		List<PersonalRepositoryState> personalRepositoryStates = new ArrayList<PersonalRepositoryState>();

		for (PersonalRepository personalRepository : userPersonalRepositories) {
			personalRepositoryStates.add(createFrom(personalRepository));
		}

		Collections.sort(personalRepositoryStates, getComparator(sortCriteria));
		return personalRepositoryStates;
	}

	private Comparator<PersonalRepositoryState> getComparator(
			SortCriteria sortCriteria) {
		if (sortCriteria.getField() == SortField.NAME) {
			return getNameComparator(sortCriteria.getDirection());
		} else {
			return getSizeComparator(sortCriteria.getDirection());
		}
	}

	private Comparator<PersonalRepositoryState> getSizeComparator(
			SortOrder direction) {
		Comparator<PersonalRepositoryState> sizeComparator = new Comparator<PersonalRepositoryState>() {
			@Override
			public int compare(PersonalRepositoryState o1,
					PersonalRepositoryState o2) {
				if (o1.getRepositorySize() == o2.getRepositorySize()) {
					return 0;
				}
				return o1.getRepositorySize() < o2.getRepositorySize() ? -1 : 1;
			}
		};
		return orderComparator(sizeComparator, direction);
	}

	private Comparator<PersonalRepositoryState> orderComparator(
			Comparator<PersonalRepositoryState> comparator, SortOrder direction) {
		if (direction == SortOrder.DESC) {
			comparator = Collections.reverseOrder(comparator);
		}
		return comparator;
	}

	private Comparator<PersonalRepositoryState> getNameComparator(
			SortOrder direction) {
		Comparator<PersonalRepositoryState> sizeComparator = new Comparator<PersonalRepositoryState>() {
			@Override
			public int compare(PersonalRepositoryState o1,
					PersonalRepositoryState o2) {
				return o1.getRepositoryName().compareToIgnoreCase(o2.getRepositoryName());
			}
		};
		return orderComparator(sizeComparator, direction);
	}

	public PersonalRepositoryState createFrom(
			PersonalRepository personalRepository) {
		Repository bitbucketRepository = repositoryService
				.getById(personalRepository.getRepositoryId());
		return createFrom(personalRepository, bitbucketRepository);
	}

	private PersonalRepositoryState createFrom(
			PersonalRepository personalRepository, Repository bitbucketRepository) {
		PersonalRepositoryState state = new PersonalRepositoryState();
		state.setRepositorySize(personalRepository.getRepositorySize());
		state.setRepositoryName(bitbucketRepository.getName());

		String url = navBuilder.repo(bitbucketRepository).browse().buildAbsolute();
		state.setUrl(url);
		state.setFork(bitbucketRepository.isFork());
		return state;
	}

}
