package org.networkedassets.atlassian.stash.privaterepos.util;

import com.atlassian.stash.util.Page;
import com.atlassian.stash.util.PageRequest;
import com.atlassian.stash.util.PageRequestImpl;

public class AllPagesIterator<T> {

	private final PageProcessor<T> processor;
	private int resultsPerPage;

	private AllPagesIterator(Builder<T> builder) {
		this.processor = builder.processor;
		this.resultsPerPage = builder.resultsPerPage;
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
		PageRequest pageRequest = new PageRequestImpl(0, resultsPerPage);
		return processor.fetchPage(pageRequest);
	}

	private boolean notLastPage(Page<? extends T> page) {
		return !page.getIsLastPage();
	}

	private Page<? extends T> fetchNextPage(Page<? extends T> page) {
		return processor.fetchPage(page.getNextPageRequest());
	}

	public static class Builder<T> {

		private final PageProcessor<T> processor;

		private int resultsPerPage = 100;

		public Builder(PageProcessor<T> processor) {
			this.processor = processor;
		}

		public Builder<T> resultsPerPage(int resultsPerPage) {
			this.resultsPerPage = resultsPerPage;
			return this;
		}

		public AllPagesIterator<T> build() {
			return new AllPagesIterator<T>(this);
		}
	}

}
