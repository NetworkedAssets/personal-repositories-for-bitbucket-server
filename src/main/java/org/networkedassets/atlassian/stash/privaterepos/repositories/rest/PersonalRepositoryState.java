package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonalRepositoryState {

	@XmlElement
	private String repositoryName;

	@XmlElement
	private long repositorySize;

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public long getRepositorySize() {
		return repositorySize;
	}

	public void setRepositorySize(long repositorySize) {
		this.repositorySize = repositorySize;
	}

}
