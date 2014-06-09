package org.networkedassets.atlassian.stash.privaterepos.rest.group;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class GroupInfo {

	@XmlElement
	private String name;
	@XmlElement
	private String viewUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

}
