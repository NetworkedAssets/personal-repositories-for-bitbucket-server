package org.networkedassets.atlassian.stash.privaterepos.permissions;

public interface PermissionsModeRepository {

	PermissionsMode getPermissionsMode();

	void setPermissionsMode(PermissionsMode mode);
}
