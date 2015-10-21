package org.networkedassets.atlassian.bitbucket.personalrepos.permissions;

public interface PermissionsModeService {
	boolean isAllowMode();

	boolean isDenyMode();

	PermissionsMode getPermissionsMode();

	void setPermissionsMode(PermissionsMode mode);
}
