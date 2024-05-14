package com.mattel.fisherprice.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS. Service for properties configuration of play site.
 */
@Component(service = PropertyReaderUtils.class, immediate = true)
@Designate(ocd = PropertyReaderUtils.Config.class)
public class PropertyReaderUtils {

	private static final Logger LOG = LoggerFactory.getLogger(PropertyReaderUtils.class);
	private static String scriptUrl;
	private static String fisherPricePath;

	@Activate
	public void activate(final Config config) {
		PropertyReaderUtils.buildConfigs(config);
	}

	private static void buildConfigs(final Config config) {
		scriptUrl = config.scriptUrl();
		fisherPricePath = config.fisherPricePath();
		LOG.debug("Analytics Script Url - {}", scriptUrl);
	}

	@ObjectClassDefinition(name = "Fisher Price Properties Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Analytics Script Url", description = "Please enter script URL "
				+ "for analytics tracking ")
		String scriptUrl();

		@AttributeDefinition(name = "Fisher Price Path")
		String fisherPricePath() default "/content/fisher-price/";
	}

	/**
	 * @return ScriptUrl
	 */
	public String getScriptUrl() {
		return scriptUrl;
	}

	public static String getFisherPricePath() {
		return fisherPricePath;
	}
}
