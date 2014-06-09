package org.networkedassets.atlassian.stash.privaterepos.user;

import net.java.ao.Entity;

public interface User extends Entity {
	
	public String getName();
    public void setName(String name);
    
}
