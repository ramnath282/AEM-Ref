package com.mattel.play.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author CTS. Service for DAM Video Language Mapping configuration of play site.
 */
@Component(service = VideosDamLanguageMapping.class, immediate = true)
@Designate(ocd = VideosDamLanguageMapping.Config.class)
public class VideosDamLanguageMapping {
	private static String[] languageMapping;

	@Activate
	public void activate(final Config config) {
		VideosDamLanguageMapping.buildConfigs(config);
	}
	
	private static void buildConfigs(Config config) {
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
