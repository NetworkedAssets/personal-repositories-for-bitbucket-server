package org.networkedassets.atlassian.bitbucket.personalrepos.permissions;

public interface PermissionsModeRepository {

	PermissionsMode getPermissionsMode();

	void setPermissionsMode(PermissionsMode mode);
}
