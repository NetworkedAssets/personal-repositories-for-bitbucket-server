package org.networkedassets.atlassian.stash.privaterepos.util.persistence;

import java.util.Set;

/**
 * Interface to be implemented by the classes which allow to store and get a
 * list of T ids.
 * 
 * @author holek
 */
public interface IdsRepository<T> {

	Set<T> getAll();

	void add(T userId);

	void add(Set<T> ids);

	void remove(T id);

	void remove(Set<T> ids);

	void removeAll();

	boolean contains(T id);

}
