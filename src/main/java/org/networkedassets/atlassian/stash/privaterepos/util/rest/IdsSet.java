package org.networkedassets.atlassian.stash.privaterepos.util.rest;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdsSet {

	@XmlElement
	private Set<Integer> ids;

	public Set<Integer> getIds() {
		return ids;
	}

	public void setIds(Set<Integer> ids) {
		this.ids = ids;
	}


}
