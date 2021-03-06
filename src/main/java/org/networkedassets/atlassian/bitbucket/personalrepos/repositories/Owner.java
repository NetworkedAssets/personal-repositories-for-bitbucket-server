package org.networkedassets.atlassian.bitbucket.personalrepos.repositories;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

public interface Owner extends Entity {

	public Integer getUserId();

	public void setUserId(Integer integer);

	@OneToMany(reverse = "getOwner")
	public PersonalRepository[] getRepositories();

	public Long getRepositoriesSize();

	public void setRepositoriesSize(Long size);

}
