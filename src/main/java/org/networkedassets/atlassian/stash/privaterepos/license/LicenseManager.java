package org.networkedassets.atlassian.stash.privaterepos.license;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger log = LoggerFactory.getLogger(LicenseManager.class);

	public boolean isLicenseValid() {
		Option<PluginLicense> licenseOption = pluginLicenseManager.getLicense();
		if (!licenseOption.isDefined()) {
			log.debug("Checking license validity, license is invalid");
			return false;
		}
		log.debug("{}", licenseOption);
		log.debug("{}", licenseOption.get());
		boolean licenseValid = licenseOption.get().isValid();
		log.debug("Checking license validity, license is {}",
				licenseValid ? "valid" : "invalid");
		return licenseValid;
	}

	public boolean isLicenseInvalid() {
		return !isLicenseValid();
	}

	public LicenseStatus getLicenseStatus() {
		Option<PluginLicense> licenseOption = pluginLicenseManager.getLicense();
		if (licenseOption.isDefined()) {
			PluginLicense pluginLicense = licenseOption.get();
			if (pluginLicense.isValid()) {
				return LicenseStatus.OK;
			}
			Option<LicenseError> error = pluginLicense.getError();
			return LicenseStatus.valueOf(error.get().name());
		} else {
			return LicenseStatus.NO_LICENSE;
		}
	}

}
