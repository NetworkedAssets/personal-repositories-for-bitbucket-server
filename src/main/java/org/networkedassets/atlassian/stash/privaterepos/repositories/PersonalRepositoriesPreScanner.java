package org.networkedassets.atlassian.stash.privaterepos.repositories;

import org.networkedassets.atlassian.stash.privaterepos.util.AllPagesIterator;
import org.networkedassets.atlassian.stash.privaterepos.util.PageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.stash.user.StashUser;
import com.atlassian.stash.user.UserService;
import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;

@Component
public class PersonalRepositoriesPreScanner implements PageProcessor<StashUser> {

	@Autowired
	private UserService userService;

	public void scanPersonalRepositories() {
		AllPagesIterator<StashUser> iterator = new AllPagesIterator<StashUser>(
				this);
		iterator.processAllPages();
	}

	@Override
	public Page<? extends StashUser> fetchPage(PageRequest pageRequest) {
		return userService.findUsers(pageRequest);
	}

	@Override
	public void process(Page<? extends StashUser> page) {
		// TODO Auto-generated method stub
	}

}
