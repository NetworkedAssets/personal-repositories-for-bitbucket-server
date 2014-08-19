package org.networkedassets.atlassian.stash.privaterepos.permissions;

public interface PermissionsModeService {
	PermissionsMode getPermissionsMode();
	void setPermissionsMode(PermissionsMode mode);
	boolean isAllowMode();
	boolean isDenyMode();
}
