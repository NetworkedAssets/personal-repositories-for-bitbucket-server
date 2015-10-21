package org.networkedassets.atlassian.bitbucket.personalrepos.repositories;

import org.springframework.stereotype.Component;

import com.atlassian.bitbucket.project.ProjectType;
import com.atlassian.bitbucket.repository.Repository;

@Component
public class RepositoryTypeVerifier {

	public boolean isPersonal(Repository repository) {
		return repository.getProject().getType() == ProjectType.PERSONAL;
	}
}
