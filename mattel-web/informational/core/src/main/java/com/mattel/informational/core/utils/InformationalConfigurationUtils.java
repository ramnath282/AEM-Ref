package com.mattel.informational.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = InformationalConfigurationUtils.class, immediate = true)
@Designate(ocd = InformationalConfigurationUtils.Config.class)
public class InformationalConfigurationUtils {

	private static String rootErrorPageName;
	private static String clientlibRootCategoryName;
	private static String headerExpFragmentName;
	private static String footerExpFragmentName;
	private static String retailerInterstitialPath;
	private static String leavingInterstitialPath;
	private static String interstitialApp;
	private static String scriptUrl;
	private static String[] expFragmentRootPathArray;
	private static String[] siteErrorPages;


	@Activate
	public void activate(final Config config) {
		InformationalConfigurationUtils.buildConfigs(config);
	}

	private static void buildConfigs(final Config config) {
		rootErrorPageName = config.rootErrorPageName();
		clientlibRootCategoryName = config.clientlibRootCategoryName();
		headerExpFragmentName = config.headerExpFragmentName();
		footerExpFragmentName = config.footerExpFragmentName();
		retailerInterstitialPath = config.retailerInterstitialPath();
		leavingInterstitialPath = config.leavingInterstitialPath();
		interstitialApp = config.interstitialApp();
		scriptUrl = config.scriptUrl();
		expFragmentRootPathArray = config.expFragmentRootPathArray();
		siteErrorPages = config.siteErrorPages();
	}

	@ObjectClassDefinition(name = "Informational Site Common Configuration")
	public @interface Config {
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

		@AttributeDefinition(name = "Analytics Script Url", description = "Please enter script URL "
				+ "for analytics tracking ")
		String scriptUrl();

		@AttributeDefinition(name = "Informational Experience Fragment Root Path Array ", description = "Experience Fragment Root Path for All Brands")
		String[] expFragmentRootPathArray();

		@AttributeDefinition(name = "Site Error Page", description = "Please enter site error page URL")
		String[] siteErrorPages();
	}

	/**
	 * @return siteErrorPage
	 */
    public String getSiteErrorPages(String clientkey){
        return InformationalUtils.getValueFromKeyMappings(siteErrorPages,clientkey);
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

	/**
	 * @return ScriptUrl
	 */
	public static String getScriptUrl() {
		return scriptUrl;
	}

	public static String[] getExpFragmentRootPathArray() {
		return expFragmentRootPathArray;
	}

}