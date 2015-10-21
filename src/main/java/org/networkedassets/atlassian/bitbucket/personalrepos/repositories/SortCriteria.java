package org.networkedassets.atlassian.bitbucket.personalrepos.repositories;

public class SortCriteria {
	
	private final SortField field;
	private final SortOrder direction;
	
	public SortCriteria(SortField sortField, SortOrder direction) {
		this.field = sortField;
		this.direction = direction;
	}

	public SortField getField() {
		return field;
	}

	public SortOrder getDirection() {
		return direction;
	}
}
