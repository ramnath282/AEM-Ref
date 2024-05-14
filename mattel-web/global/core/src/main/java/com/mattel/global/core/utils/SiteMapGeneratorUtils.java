package com.mattel.global.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = SiteMapGeneratorUtils.class, immediate = true)
@Designate(ocd = SiteMapGeneratorUtils.Config.class)
public class SiteMapGeneratorUtils {	
	
	private static String[] countryMapping;
	
	private static String [] brandSiteMapDetails;

	@Activate
	public void activate(final Config config) {
		SiteMapGeneratorUtils.buildConfigs(config);
	}

	/**
	 * created this method to avoid sonar issues
	 * @param config
	 */
	private static void buildConfigs(final Config config) {		
		countryMapping = config.countryMapping();
		brandSiteMapDetails = config.brandSiteMapDetails();
	}

	@ObjectClassDefinition(name = "Global Sitemap Generator Configuration")
	public @interface Config {	
		
		@AttributeDefinition(name = "Language Mapping", description = "Language Mapping")
		String[] countryMapping();
		
		@AttributeDefinition(name = "Brand SiteMap Details", description = "brand=rootSitemapPath,siteRootPath,siteDomain,shortUrlSiteDomain")
		String [] brandSiteMapDetails();
	}	

	public String[] getCountryMapping() {
		return countryMapping;
	}

	public String[] getBrandSiteMapDetails() {
		return brandSiteMapDetails;
	}

}
