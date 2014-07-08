package org.networkedassets.atlassian.stash.privaterepos.repositories;

import net.java.ao.Entity;

public interface PersonalRepository extends Entity {
	
	public Integer getRepositoryId();
    public void setRepositoryId(Integer id);
    
    public Integer getUserId();
    public void setUserId(Integer integer);
    
}
