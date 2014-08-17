package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonalRepositoryState {

	@XmlElement
	private String name;

	@XmlElement
	private long size;

	public String getRepositoryName() {
		return name;
	}

	public void setRepositoryName(String repositoryName) {
		this.name = repositoryName;
	}

	public long getRepositorySize() {
		return size;
	}

	public void setRepositorySize(long repositorySize) {
		this.size = repositorySize;
	}

}
