package org.networkedassets.atlassian.stash.privaterepos.repositories;

import org.springframework.stereotype.Component;

import com.atlassian.stash.project.ProjectType;
import com.atlassian.stash.repository.Repository;

@Component
public class RepositoryTypeVerifier {

	public boolean isPersonal(Repository repository) {
		return repository.getProject().getType() == ProjectType.PERSONAL;
	}
}
