package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RepositoryOwnerInfo {
	@XmlElement
	private String name;
	@XmlElement
	private String displayName;
	@XmlElement
	private String avatarUrl;
	@XmlElement
	private String profileUrl;
	@XmlElement
	private String email;
	@XmlElement
	private long repositoriesSize;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getRepositoriesSize() {
		return repositoriesSize;
	}

	public void setRepositoriesSize(long repositoriesSize) {
		this.repositoriesSize = repositoriesSize;
	}

}
