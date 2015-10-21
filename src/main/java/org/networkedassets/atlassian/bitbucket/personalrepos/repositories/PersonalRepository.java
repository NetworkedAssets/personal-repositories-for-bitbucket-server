package org.networkedassets.atlassian.bitbucket.personalrepos.repositories;

import net.java.ao.Entity;

public interface PersonalRepository extends Entity {

	public Integer getRepositoryId();

	public void setRepositoryId(Integer id);

	public Long getRepositorySize();

	public void setRepositorySize(Long size);

	public Owner getOwner();

	public void setOwner(Owner owner);

}
