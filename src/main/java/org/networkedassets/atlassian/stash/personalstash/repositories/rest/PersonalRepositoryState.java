package org.networkedassets.atlassian.stash.personalstash.repositories.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersonalRepositoryState {

	@XmlElement
	private String name;
	@XmlElement
	private long size;
	@XmlElement
	private String url;
	@XmlElement
	private boolean fork; 
	

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isFork() {
		return fork;
	}

	public void setFork(boolean fork) {
		this.fork = fork;
	}

}
