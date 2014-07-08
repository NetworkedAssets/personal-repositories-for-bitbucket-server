package org.networkedassets.atlassian.stash.privaterepos.repositories;

import org.springframework.stereotype.Component;

import net.java.ao.Entity;

@Component
public interface PersonalRepository extends Entity {
	
	public Integer getRepositoryId();
    public void setRepositoryId(Integer id);
    
    public Integer getUserId();
    public void setUserId(Integer integer);
    
}
