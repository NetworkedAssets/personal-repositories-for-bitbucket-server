package org.networkedassets.atlassian.stash.privaterepos.state;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

@Component
public class PluginStateRepository {

	private final String PLUGIN_PRESCANNED_KEY = "plugin-prescanned-status";
	private final boolean DEFAULT_PRESCAN_STATUS = false;

	@Autowired
	private PluginSettingsFactory pluginSettingsFactory;

	private PluginSettings pluginSettings;

	@PostConstruct
	public void initializePluginSettings() {
		pluginSettings = pluginSettingsFactory.createGlobalSettings();
	}

	public void setPrescanned(boolean status) {
		pluginSettings.put(PLUGIN_PRESCANNED_KEY, String.valueOf(status));
	}

	public boolean isPrescanned() {
		String stringStatus = (String) pluginSettings
				.get(PLUGIN_PRESCANNED_KEY);
		if (stringStatus == null) {
			return DEFAULT_PRESCAN_STATUS;
		}
		return Boolean.getBoolean(stringStatus);
	}

}
