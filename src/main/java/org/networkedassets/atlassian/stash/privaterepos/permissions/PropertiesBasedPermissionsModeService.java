package org.networkedassets.atlassian.stash.privaterepos.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.sal.api.pluginsettings.PluginSettings;

@Component
public class PropertiesBasedPermissionsModeService implements
		PermissionsModeService {

	private final String PERMISSIONS_MODE_KEY = "permissions-mode";
	private final PermissionsMode DEFAULT_PERMISSIONS_MODE = PermissionsMode.ALLOW;

	@Autowired
	private PluginSettings pluginSettings;

	@Override
	public PermissionsMode getPermissionsMode() {
		String mode = pluginSettings.get(PERMISSIONS_MODE_KEY).toString();
		if (mode == null) {
			setPermissionsMode(DEFAULT_PERMISSIONS_MODE);
			return DEFAULT_PERMISSIONS_MODE;
		}
		return PermissionsMode.valueOf(mode);
	}

	@Override
	public void setPermissionsMode(PermissionsMode mode) {
		pluginSettings.put(PERMISSIONS_MODE_KEY, mode);
	}

}
