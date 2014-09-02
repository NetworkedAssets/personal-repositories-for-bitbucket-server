package org.networkedassets.atlassian.stash.personalstash.util;

import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;

public interface PageProcessor<T> {

	public void process(Page<? extends T> page);

	public Page<? extends T> fetchPage(PageRequest pageRequest);
}
