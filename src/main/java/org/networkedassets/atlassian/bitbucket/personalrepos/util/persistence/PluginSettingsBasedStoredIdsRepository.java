package org.networkedassets.atlassian.bitbucket.personalrepos.util.persistence;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

/**
 * IdsRepository implementation based on PluginSettings provided by atlassian.
 * Saves a unique list of ids in plugin settings.
 * 
 * This is an abstract class - concrete classes are obliged to provide two
 * methods implementation:
 * 
 * getSettingsKey - returning a key under which the ids will be kept in
 * PluginSettings.
 * 
 * convertFromString - method enabling to convert the type used for this
 * instance to String - all values are kept as string
 * 
 * Additionally if you want to use something different then a simple toString
 * for T -> String conversion you can also override convertToString method.
 * 
 * @author holek
 *
 */
public abstract class PluginSettingsBasedStoredIdsRepository<T> implements
		IdsRepository<T> {

	@Autowired
	private PluginSettingsFactory pluginSettingsFactory;

	private PluginSettings pluginSettings;

	private final Logger log = LoggerFactory
			.getLogger(PluginSettingsBasedStoredIdsRepository.class);

	protected abstract String getSettingsKey();

	@PostConstruct
	public void initializePluginSettings() {
		pluginSettings = pluginSettingsFactory.createGlobalSettings();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<T> getAll() {
		List<String> idsAsString = (List<String>) pluginSettings
				.get(getSettingsKey());

		if (idsAsString == null) {
			return new LinkedHashSet<T>();
		}
		List<T> ids = new ArrayList<T>();
		for (String string : idsAsString) {
			ids.add(convertFromString(string));
		}
		return new LinkedHashSet<T>(ids);
	}

	protected abstract T convertFromString(String value);

	protected String convertToString(T t) {
		return t.toString();
	}

	@Override
	public void add(T id) {
		Set<T> all = getAll();
		all.add(id);
		saveSet(all);
	}

	@Override
	public void add(Set<T> ids) {
		Set<T> all = getAll();
		all.addAll(ids);
		saveSet(all);
	}

	@Override
	public void remove(T id) {
		Set<T> all = getAll();
		all.remove(id);
		saveSet(all);
	}

	@Override
	public void remove(Set<T> ids) {
		Set<T> all = getAll();
		all.removeAll(ids);
		saveSet(all);
	}

	@Override
	public void removeAll() {
		Set<T> all = getAll();
		all.clear();
		saveSet(all);
	}

	@Override
	public boolean contains(T id) {
		Set<T> all = getAll();
		return all.contains(id);
	}

	private void saveSet(Set<T> ids) {
		ArrayList<String> stringsList = new ArrayList<String>();
		for (T t : ids) {
			stringsList.add(convertToString(t));
		}
		pluginSettings.put(getSettingsKey(), stringsList);
	}

}
