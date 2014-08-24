package org.networkedassets.atlassian.stash.personalstash.state;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

@Component
public class PluginStateManager {

	private final String PLUGIN_STATUS_KEY = "plugin-prescanned-status";
	private final PluginState DEFAULT_STATUS = PluginState.SCAN_NEEDED;

	@Autowired
	private PluginSettingsFactory pluginSettingsFactory;

	private PluginSettings pluginSettings;

	@PostConstruct
	public void initializePluginSettings() {
		pluginSettings = pluginSettingsFactory.createGlobalSettings();
	}

	public void setState(PluginState state) {
		pluginSettings.put(PLUGIN_STATUS_KEY, state.name());
	}

	public PluginState getState() {
		String stringStatus = (String) pluginSettings.get(PLUGIN_STATUS_KEY);
		if (stringStatus == null) {
			return DEFAULT_STATUS;
		}
		return PluginState.valueOf(stringStatus);
	}

}
