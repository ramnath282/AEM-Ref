package com.mattel.fisherprice.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = FisherPriceConfigurationUtils.class, immediate = true)
@Designate(ocd = FisherPriceConfigurationUtils.Config.class)
public class FisherPriceConfigurationUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(FisherPriceConfigurationUtils.class);
	private static String expFragmentRootPath;
	private static String rootErrorPageName;
	private static String clientlibRootCategoryName;
	private static String headerExpFragmentName;
	private static String footerExpFragmentName;
	private static String retailerInterstitialPath;
	private static String leavingInterstitialPath;
	private static String interstitialApp;
	private static String globalfooterExpFragmentName;
	private static String[] excludedBrandsFooter;
	private static String atPropertyTargetFP;
	/**
	 * added this variable to append environment specific short-site domain
	 */
	private static String shortUrlSiteDomain;


	@Activate
	public void activate(final Config config) {
		FisherPriceConfigurationUtils.buildConfigs(config);
	}

	private static void buildConfigs(final Config config) {
		expFragmentRootPath = config.expFragmentRootPath();
		rootErrorPageName = config.rootErrorPageName();
		clientlibRootCategoryName = config.clientlibRootCategoryName();
		headerExpFragmentName = config.headerExpFragmentName();
		footerExpFragmentName = config.footerExpFragmentName();
		retailerInterstitialPath = config.retailerInterstitialPath();
		leavingInterstitialPath = config.leavingInterstitialPath();
		interstitialApp = config.interstitialApp();
		atPropertyTargetFP = config.atPropertyTargetFP();
		shortUrlSiteDomain = config.shortUrlSiteDomain();
		LOGGER.info("Experince Fragment Root Path is {}", expFragmentRootPath);
	}

	@ObjectClassDefinition(name = "Fisherprice Site Common Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Mattel - Play Experience Fragment Root Path ", description = "Experience Fragment Root Path for Mattel - Play")
		String expFragmentRootPath() default "/content/experience-fragments/fisher-price/";

		@AttributeDefinition(name = "Root Error Page Name")
		String rootErrorPageName() default "/error/";

		@AttributeDefinition(name = "Root Clientlibrary Name")
		String clientlibRootCategoryName() default "clientlib.";

		@AttributeDefinition(name = "Header Experience Fragment Name")
		String headerExpFragmentName() default "header";

		@AttributeDefinition(name = "Footer Experience Fragment Name")
		String footerExpFragmentName() default "footer";

		@AttributeDefinition(name = "Retailer Interstitial Experience Fragment Name")
		String retailerInterstitialPath() default "interstitial-retailer";

		@AttributeDefinition(name = "Interstitial Leaving the Site Experience Fragment Name")
		String leavingInterstitialPath() default "interstitial-leavingthesite";

		@AttributeDefinition(name = "Interstitial App Experience Fragment Name")
		String interstitialApp() default "interstitial-app";

		@AttributeDefinition(name = "Global US Footer Experince Fragment Name")
		String globalfooterExpFragmentName() default "footer-global_en-us";

		@AttributeDefinition(name = "Excluded brands for footer", description = "Brands to exclude from Global US Footer Logic")
		String[] excludedBrandsFooter();

		@AttributeDefinition(name = "FP at-property for Recommended Products ", description = "Please enter the value for at_property used in Recommended Products")
		String atPropertyTargetFP() default "1ac37e38-4f08-5480-d275-1c63faf46400";

		@AttributeDefinition(name = "ShortUrl Site Root Path", description = "ShortUrl Root Path of the brand")
		String shortUrlSiteDomain();
;
	}

	/**
	 * @return expFragmentRootPath
	 */
	public static String getExpFragmentRootPath() {
		return expFragmentRootPath;
	}

	/**
	 * @return rootErrorPageName
	 */
	public static String getRootErrorPageName() {
		return rootErrorPageName;
	}

	/**
	 * @return clientlibRootCategoryName
	 */
	public static String getClientlibRootCategoryName() {
		return clientlibRootCategoryName;
	}

	/**
	 * @return headerExpFragmentName
	 */
	public static String getHeaderExpFragmentName() {
		return headerExpFragmentName;
	}

	/**
	 * @return headerExpFragmentName
	 */
	public static String getFooterExpFragmentName() {
		return footerExpFragmentName;
	}

	public static String getRetailerInterstitialPath() {
		return retailerInterstitialPath;
	}

	public static String getLeavingInterstitialPath() {
		return leavingInterstitialPath;
	}

	public static String getInterstitialApp() {
		return interstitialApp;
	}

	public static String getGlobalfooterExpFragmentName() {
		return globalfooterExpFragmentName;
	}

	public static String[] getExcludedBrandsFooter() {
		return excludedBrandsFooter;
	}

	public static String getAtPropertyTargetFP() {
		return atPropertyTargetFP;
	}
	
	public static String getShortUrlSiteDomain() {
		return shortUrlSiteDomain;
	}

}