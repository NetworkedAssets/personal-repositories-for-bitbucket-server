package org.networkedassets.atlassian.stash.personalstash.license;

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
		PluginLicense pluginLicense = licenseOption.get();
		boolean licenseValid = pluginLicense.isValid();
		log.debug("Checking license validity, license is {}",
				licenseValid ? "valid" : "invalid");
		return licenseValid;
	}

	public boolean isLicenseInvalid() {
		return !isLicenseValid();
	}

	public LicenseStatus getLicenseStatus() {
		Option<PluginLicense> licenseOption = pluginLicenseManager.getLicense();
		LicenseStatus licenseStatus;
		if (licenseOption.isDefined()) {
			PluginLicense pluginLicense = licenseOption.get();
			if (pluginLicense.isValid()) {
				licenseStatus = LicenseStatus.OK;
			}
			Option<LicenseError> error = pluginLicense.getError();
			licenseStatus = LicenseStatus.valueOf(error.get().name());
		} else {
			licenseStatus = LicenseStatus.NO_LICENSE;
		}
		log.debug("License status {} ", licenseStatus.name());
		return licenseStatus;
	}

}