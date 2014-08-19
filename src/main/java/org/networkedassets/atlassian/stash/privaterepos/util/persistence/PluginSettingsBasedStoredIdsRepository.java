package org.networkedassets.atlassian.stash.privaterepos.util.persistence;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

/**
 * IdsRepository implementation based on PluginSettings provided by atlassian.
 * Saves a unique list of ids in plugin settings.
 * 
 * This is an abstract class - concrete classes are obliged to provide only one
 * method implementation and that is getSettingsKey() - returning a key under
 * which the ids will be kept in PluginSettings.
 * 
 * @author holek
 *
 */
public abstract class PluginSettingsBasedStoredIdsRepository implements
		IdsRepository {

	@Autowired
	private PluginSettingsFactory pluginSettingsFactory;

	private PluginSettings pluginSettings;

	protected abstract String getSettingsKey();

	@PostConstruct
	public void initializePluginSettings() {
		pluginSettings = pluginSettingsFactory.createGlobalSettings();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Integer> getAll() {
		List<Integer> listOfIds = (List<Integer>) pluginSettings
				.get(getSettingsKey());
		return new LinkedHashSet<Integer>(listOfIds);
	}

	@Override
	public void add(Integer id) {
		Set<Integer> all = getAll();
		all.add(id);
		saveSet(all);
	}

	@Override
	public void add(Set<Integer> ids) {
		Set<Integer> all = getAll();
		all.addAll(ids);
		saveSet(ids);
	}

	@Override
	public void remove(Integer id) {
		Set<Integer> all = getAll();
		all.remove(id);
		saveSet(all);
	}

	@Override
	public void remove(Set<Integer> ids) {
		Set<Integer> all = getAll();
		all.removeAll(ids);
		saveSet(all);
	}

	@Override
	public void removeAll() {
		Set<Integer> all = getAll();
		all.clear();
		saveSet(all);
	}

	@Override
	public boolean contains(Integer id) {
		Set<Integer> all = getAll();
		return all.contains(id);
	}

	private void saveSet(Set<Integer> ids) {
		pluginSettings.put(getSettingsKey(), new ArrayList<Integer>(ids));
	}

}
