package org.networkedassets.atlassian.stash.private_repositories_permissions.ao;

import net.java.ao.Entity;
import net.java.ao.schema.Unique;

public interface User extends Entity {
	
	@Unique
	public String getName();
    public void setName(String name);
    
}
