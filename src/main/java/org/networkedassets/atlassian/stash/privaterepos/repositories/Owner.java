package org.networkedassets.atlassian.stash.privaterepos.repositories;

import net.java.ao.Entity;
import net.java.ao.OneToMany;
import net.java.ao.schema.Unique;

public interface Owner extends Entity {

	@Unique
	public Integer getUserId();

	public void setUserId(Integer integer);

	@OneToMany
	public PersonalRepository[] getRepositories();

	public Long getRepositoriesSize();

	public void setRepositoriesSize(Long size);

}
