package org.networkedassets.atlassian.stash.privaterepos.license;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.atlassian.upm.api.license.PluginLicenseManager;
import com.atlassian.upm.api.license.entity.LicenseError;
import com.atlassian.upm.api.license.entity.PluginLicense;
import com.atlassian.upm.api.util.Option;

@Component
public class LicenseManager {

	@Autowired
	private PluginLicenseManager pluginLicenseManager;

	public boolean isLicenseValid() {
		Option<PluginLicense> licenseOption = pluginLicenseManager.getLicense();
		if (!licenseOption.isDefined()) {
			return false;
		}
		return licenseOption.get().isValid();
	}

	public String getLicenseErrorMessage() {
		Option<PluginLicense> licenseOption = pluginLicenseManager.getLicense();
		if (licenseOption.isDefined()) {
			PluginLicense pluginLicense = licenseOption.get();
			Option<LicenseError> error = pluginLicense.getError();
			return error.get().toString();
		} else {
			return "No license found";
		}
	}

}
