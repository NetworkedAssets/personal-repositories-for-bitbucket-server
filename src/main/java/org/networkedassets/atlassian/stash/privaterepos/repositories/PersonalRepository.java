package org.networkedassets.atlassian.stash.privaterepos.repositories;

import net.java.ao.Entity;
import net.java.ao.schema.Unique;

public interface PersonalRepository extends Entity {

	public Integer getRepositoryId();

	public void setRepositoryId(Integer id);

	public Long getRepositorySize();

	public void setRepositorySize(Long size);

	public Owner getOwner();

	public void setOwner(Owner owner);

}
