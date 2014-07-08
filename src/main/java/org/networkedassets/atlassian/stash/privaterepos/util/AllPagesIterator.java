package org.networkedassets.atlassian.stash.privaterepos.util;

import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;
import com.atlassian.stash.util.PageRequestImpl;

public class AllPagesIterator<T> {

	private final PageProcessor<T> processor;

	public AllPagesIterator(PageProcessor<T> processor) {
		this.processor = processor;
	}

	public void processAllPages() {
		Page<? extends T> page = fetchFirstPage();
		processPage(page);
		while (notLastPage(page)) {
			page = fetchNextPage(page);
			processPage(page);
		}
	}

	private void processPage(Page<? extends T> page) {
		processor.process(page);
	}

	private Page<? extends T> fetchFirstPage() {
		PageRequest pageRequest = new PageRequestImpl(0, 100);
		return processor.fetchPage(pageRequest);
	}

	private boolean notLastPage(Page<? extends T> page) {
		return !page.getIsLastPage();
	}

	private Page<? extends T> fetchNextPage(Page<? extends T> page) {
		return processor.fetchPage(page.getNextPageRequest());
	}

}
