package org.networkedassets.atlassian.stash.privaterepos.repositories;

import com.atlassian.stash.project.ProjectType;
import com.atlassian.stash.repository.Repository;

public class RepositoryTypeVerifier {

	public boolean isPersonal(Repository repository) {
		return repository.getProject().getType() == ProjectType.PERSONAL;
	}
}
