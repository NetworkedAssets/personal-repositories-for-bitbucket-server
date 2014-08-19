package org.networkedassets.atlassian.stash.privaterepos.util.rest;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdsSet<T> {

	@XmlElement
	private Set<T> ids;

	public Set<T> getIds() {
		return ids;
	}

	public void setIds(Set<T> ids) {
		this.ids = ids;
	}

}
