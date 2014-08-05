package org.networkedassets.atlassian.stash.privaterepos.repositories.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RepositoryOwnerStatePage {

	@XmlElement(name = "total_items")
	private int totalItems;

	@XmlElement
	private List<RepositoryOwnerState> items;

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public List<RepositoryOwnerState> getItems() {
		return items;
	}

	public void setItems(List<RepositoryOwnerState> items) {
		this.items = items;
	}

}
