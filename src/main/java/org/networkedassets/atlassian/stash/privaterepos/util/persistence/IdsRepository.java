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

	void add(int userId);

	void remove(int userId);

}
