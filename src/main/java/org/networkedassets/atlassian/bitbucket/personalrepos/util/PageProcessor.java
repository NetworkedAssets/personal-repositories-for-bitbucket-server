package org.networkedassets.atlassian.bitbucket.personalrepos.util;

import com.atlassian.bitbucket.util.Page;
import com.atlassian.bitbucket.util.PageRequest;

public interface PageProcessor<T> {

	public void process(Page<? extends T> page);

	public Page<? extends T> fetchPage(PageRequest pageRequest);
}
