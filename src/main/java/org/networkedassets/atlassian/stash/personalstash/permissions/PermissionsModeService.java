package org.networkedassets.atlassian.stash.personalstash.permissions;

public interface PermissionsModeService {
	boolean isAllowMode();

	boolean isDenyMode();

	PermissionsMode getPermissionsMode();

	void setPermissionsMode(PermissionsMode mode);
}
