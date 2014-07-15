package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.networkedassets.atlassian.stash.privaterepos.user.rest.UserInfo;

@XmlRootElement
public class RepositoryOwnerInfo extends UserInfo {

	@XmlElement
	private long repositoriesSize;

	public long getRepositoriesSize() {
		return repositoriesSize;
	}

	public void setRepositoriesSize(long repositoriesSize) {
		this.repositoriesSize = repositoriesSize;
	}

}
