package org.networkedassets.atlassian.stash.privaterepos.util.persistence;

import java.util.Set;

/**
 * Interface to be implemented by the classes which allow to store and get a
 * list of Integer ids.
 * 
 * @author holek
 */
public interface IdsRepository {

	Set<Integer> getAll();

	void add(Integer userId);
	
	void add(Set<Integer> ids);

	void remove(Integer id);
	
	void remove(Set<Integer> ids);
	
	void removeAll();
	
	boolean contains(Integer id);

}
