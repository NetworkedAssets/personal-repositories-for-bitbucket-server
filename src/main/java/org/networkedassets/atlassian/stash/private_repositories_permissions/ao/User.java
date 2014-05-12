package org.networkedassets.atlassian.stash.private_repositories_permissions.ao;

import net.java.ao.Entity;

public interface User extends Entity {
	
	public String getName();
    public void setName(String name);
    
}
