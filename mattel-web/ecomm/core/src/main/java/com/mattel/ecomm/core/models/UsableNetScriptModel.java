
package com.mattel.ecomm.core.models;

import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UsableNetScriptModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsableNetScriptModel.class);
	
	private String usableNetScriptPath;
	private boolean accessibilitySwitch;

	@PostConstruct
	protected void init() {
		UsableNetScriptModel.LOGGER.info("UsableNetScriptModel Init Start");
		usableNetScriptPath = EcommConfigurationUtils.getUsableNetScriptPath();
		accessibilitySwitch = EcommConfigurationUtils.getAccessibilitySwitch();
		UsableNetScriptModel.LOGGER.info("UsableNetScriptModel Init End");
	}

	public String getUsableNetScriptPath() {
		return usableNetScriptPath;
	}

	public void setUsableNetScriptPath(String usableNetScriptPath) {
		this.usableNetScriptPath = usableNetScriptPath;
	}

	public boolean isAccessibilitySwitch() { return accessibilitySwitch; }

	public void setAccessibilitySwitch(boolean accessibilitySwitch) {
		this.accessibilitySwitch = accessibilitySwitch;
	}
}
