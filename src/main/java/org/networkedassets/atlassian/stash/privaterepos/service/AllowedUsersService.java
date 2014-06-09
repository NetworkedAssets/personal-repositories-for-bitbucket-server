package org.networkedassets.atlassian.stash.privaterepos.service;

import java.util.List;

import org.networkedassets.atlassian.stash.privaterepos.ao.user.User;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.stash.user.StashUser;

@Transactional
public interface AllowedUsersService {

	/**
	 * List of all the users allowed to use private repositories.
	 * 
	 * @return
	 */
	List<User> all();

	/**
	 * Is user allowed to use private repositories
	 * 
	 * @param userName
	 * @return
	 */
	boolean isAllowed(String userName);

	/**
	 * Disallows user to use private repositories
	 * 
	 * @param userName
	 */
	void disallow(String userName);

	/**
	 * Allows user to use private repositories
	 * 
	 * @param userName
	 * @return
	 */
	User allow(String userName);

	/**
	 * Finds List of StashUsers who are not yet individually allowed.
	 * 
	 * @param key
	 *            part of name to match
	 * @return
	 */
	List<StashUser> findNotAllowed(String key);

	/**
	 * Allows given users to access private repositories
	 * @param names
	 */
	List<User> allow(List<String> names);

}
