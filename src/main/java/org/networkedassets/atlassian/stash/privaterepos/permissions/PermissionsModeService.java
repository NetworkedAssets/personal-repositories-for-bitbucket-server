package org.networkedassets.atlassian.stash.privaterepos.permissions;

public interface PermissionsModeService {
	boolean isAllowMode();

	boolean isDenyMode();

	PermissionsMode getPermissionsMode();

	void setPermissionsMode(PermissionsMode mode);
}
