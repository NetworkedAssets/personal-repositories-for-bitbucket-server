package org.networkedassets.atlassian.stash.personalstash.repositories;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface Owner extends Entity {

	public Integer getUserId();

	public void setUserId(Integer integer);

	@OneToMany
	public PersonalRepository[] getRepositories();

	public Long getRepositoriesSize();

	public void setRepositoriesSize(Long size);

}
