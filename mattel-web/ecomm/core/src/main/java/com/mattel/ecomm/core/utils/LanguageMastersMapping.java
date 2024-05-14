package com.mattel.ecomm.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = LanguageMastersMapping.class, immediate = true)
@Designate(ocd = LanguageMastersMapping.Config.class)
public class LanguageMastersMapping {
	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageMastersMapping.class);
	private static String[] languageMapping;

	@Activate
	public void activate(final Config config) {
		LOGGER.debug("LanguageMastersMapping Start");
		extractMapping(config);
		LOGGER.debug("LanguageMastersMapping End");
	}

  /**
   * To set properties.
   *
   * @param config
   *          The osgi config.
   */
  private static void extractMapping(final Config config) {
    languageMapping = config.languageMapping();
  }

	@ObjectClassDefinition(name = "Video DAM Language Mapping Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Language Mapping", description = "Language Mapping")
		String[] languageMapping();

	}

	/**
	 * @return expFragmentRootPath
	 */
	public static String[] getLanguageMapping() {
		return languageMapping;
	}

}
