package org.networkedassets.atlassian.stash.privaterepos.repositories.ao;

import java.math.BigInteger;

import net.java.ao.Entity;

public interface Owner extends Entity {

	public Integer getUserId();

	public void setUserId(Integer integer);

	public PersonalRepository[] getRepositories();

	public BigInteger getRepositoriesSize();

	public void setRepositoriesSize(BigInteger size);

}
