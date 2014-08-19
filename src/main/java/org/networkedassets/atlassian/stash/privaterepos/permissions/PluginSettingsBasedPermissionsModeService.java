package org.networkedassets.atlassian.stash.privaterepos.permissions;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

@Component
public class PluginSettingsBasedPermissionsModeService implements
		PermissionsModeRepository {

	private final String PERMISSIONS_MODE_KEY = "permissions-mode";
	private final PermissionsMode DEFAULT_PERMISSIONS_MODE = PermissionsMode.ALLOW;

	@Autowired
	private PluginSettingsFactory pluginSettingsFactory;

	private PluginSettings pluginSettings;

	@PostConstruct
	public void initializePluginSettings() {
		pluginSettings = pluginSettingsFactory.createGlobalSettings();
	}

	@Override
	public PermissionsMode getPermissionsMode() {
		String mode = (String) pluginSettings.get(PERMISSIONS_MODE_KEY);
		if (mode == null) {
			setPermissionsMode(DEFAULT_PERMISSIONS_MODE);
			return DEFAULT_PERMISSIONS_MODE;
		}
		return PermissionsMode.valueOf(mode);
	}

	@Override
	public void setPermissionsMode(PermissionsMode mode) {
		pluginSettings.put(PERMISSIONS_MODE_KEY, mode.toString());
	}

}
