package com.mattel.play.core.utils;

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
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyReaderUtils.class);
	private static String scriptUrl;
	private static String whyplayScriptUrl;
	private static String playPath;
	private static String playDamPath;
	private static String videoDamPath;
	private static String productPath;
	private static String videoPath;
	private static String characterLandingPath;
	private static String characterPath;
	private static String characterResourceType;
	private static String gameResourceType;
	private static String productResourceType;
	private static String gamePath;
	private static String downloadInterstitialApp;
	private static String gamesLandingPath;
	private static String damGlobalPath;
	private static String homePath;
	private static String productLandingPath;
	private static String commerceProductsPath;
	private static String articleResourceType;
	private static String articleSortOrderProperty;
	private static String rescuePath;
	private static String rescueHeaderFooterURL;
	private static String imageResourceType;
	private static String fpSiteHeaderResourceType;
	private static String categoryNavResourceType;
	private static String otherNavResourceType;
	private static String[] mattelScriptUrl;

	@Activate
	public void activate(final Config config) {
		PropertyReaderUtils.buildConfigs(config);
	}
	
	private static void buildConfigs(Config config) {
		scriptUrl = config.scriptUrl();
		whyplayScriptUrl = config.whyplayScriptUrl();
		playPath = config.playPath();
		playDamPath = config.playDamPath();
		videoDamPath = config.videoDamPath();
		productPath = config.productPath();
		videoPath = config.videoPath();
		characterLandingPath = config.characterLandingPath();
		characterPath = config.characterPath();
		characterResourceType = config.characterResourceType();
		gameResourceType = config.gameResourceType();
		productResourceType = config.productResourceType();
		gamePath = config.gamePath();
		downloadInterstitialApp = config.downloadInterstitialApp();
		gamesLandingPath = config.gamesLandingPath();
		damGlobalPath = config.damGlobalPath();
		homePath = config.homePath();
		productLandingPath = config.productLandingPath();
		commerceProductsPath = config.commerceProductsPath();
		articleResourceType = config.articleResourceType();
		articleSortOrderProperty = config.articleSortOrderProperty();
		rescuePath = config.rescuePath();
		rescueHeaderFooterURL = config.rescueHeaderFooterURL();
		imageResourceType = config.imageResourceType();
		fpSiteHeaderResourceType = config.fpSiteHeaderResourceType();
		categoryNavResourceType = config.categoryNavResourceType();
		otherNavResourceType = config.otherNavResourceType();
		mattelScriptUrl = config.mattelScriptUrl();
		LOGGER.debug("Path of script {}", scriptUrl);
	}

	@ObjectClassDefinition(name = "Play Properties Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Analytics Script Url", description = "Please enter script URL "
				+ "for analytics tracking ")
		String scriptUrl();

		@AttributeDefinition(name = "Analytics Script Url for whyplay", description = "Please enter script URL for whyplay "
				+ "for analytics tracking ")
		String whyplayScriptUrl();

		@AttributeDefinition(name = "Play Path")
		String playPath();

		@AttributeDefinition(name = "Play Dam Assets Path")
		String playDamPath();

		@AttributeDefinition(name = "Play Videos Path")
		String videoDamPath();

		@AttributeDefinition(name = "Play Products Landing Path")
		String productPath();

		@AttributeDefinition(name = "Play Video Landing Path")
		String videoPath();

		@AttributeDefinition(name = "Play Character Path")
		String characterLandingPath();

		@AttributeDefinition(name = "Play Game Path")
		String gamePath();

		@AttributeDefinition(name = "Play Character Landing Path")
		String characterPath();

		@AttributeDefinition(name = "Play Character Resource Type")
		String characterResourceType();

		@AttributeDefinition(name = "Play Game Resource Type")
		String gameResourceType();

		@AttributeDefinition(name = "Play Product Resource Type")
		String productResourceType();

		@AttributeDefinition(name = "Download Image Path")
		String downloadInterstitialApp();

		@AttributeDefinition(name = "Games Landing Grid Experience Fragment Path")
		String gamesLandingPath();

		@AttributeDefinition(name = "Dam Assets Global Path")
		String damGlobalPath();

		@AttributeDefinition(name = "home Path")
		String homePath();

		@AttributeDefinition(name = " Product Landing Path")
		String productLandingPath();

		@AttributeDefinition(name = "Path for Products Creation")
		String commerceProductsPath();

		@AttributeDefinition(name = "Why Play Article Resource Type")
		String articleResourceType();

		@AttributeDefinition(name = "Why Play Article OrderType")
		String articleSortOrderProperty();

		@AttributeDefinition(name = "Play Path")
		String rescuePath();

		@AttributeDefinition(name = "Rescue Header and Footer URL From Tridion #pageLocale will be fetched dynamically and #requestFor will be either header or footer")
		String rescueHeaderFooterURL();

		@AttributeDefinition(name = "Image Resource Type")
		String imageResourceType();

		@AttributeDefinition(name = "FP SiteHeader Resource Type")
		String fpSiteHeaderResourceType();

		@AttributeDefinition(name = "Shop By Category Component Resource Type")
		String categoryNavResourceType();

		@AttributeDefinition(name = "Navigation Component Resource Type")
		String otherNavResourceType();

		@AttributeDefinition(name = "Mattel Script Url", description = "Please Configure mattel script url in format brandname:url e.g. mattel-com:assets.adobedtm.com")
		String[] mattelScriptUrl();
	}

	/**
	 * @return ScriptUrl
	 */
	public String getScriptUrl() {
		return scriptUrl;
	}

	public static String getPlayPath() {
		return playPath;
	}

	public static String getPlayDamPath() {
		return playDamPath;
	}

	public static String getVideoDamPath() {
		return videoDamPath;
	}

	public static String getProductPath() {
		return productPath;
	}

	public static String getVideoPath() {
		return videoPath;
	}

	public static String getCharacterLandingPath() {
		return characterLandingPath;
	}

	public static String getCharacterPath() {
		return characterPath;
	}

	public static String getCharacterResourceType() {
		return characterResourceType;
	}

	public static String getGameResourceType() {
		return gameResourceType;
	}

	public static String getProductResourceType() {
		return productResourceType;
	}

	public static String getGamePath() {
		return gamePath;
	}

	public static String getDownloadInterstitialApp() {
		return downloadInterstitialApp;
	}

	public static String getGamesLandingPath() {
		return gamesLandingPath;
	}

	public static String getDamGlobalPath() {
		return damGlobalPath;
	}

	public static String getHomePath() {
		return homePath;
	}

	public static String getProductLandingPath() {
		return productLandingPath;
	}

	public static String getCommerceProductsPath() {
		return commerceProductsPath;
	}

	public static String getArticleResourceType() {
		return articleResourceType;
	}

	public static String getArticleSortOrderProperty() {
		return articleSortOrderProperty;
	}

	public String getWhyplayScriptUrl() {
		return whyplayScriptUrl;
	}

	public static String[] getMattelScriptUrl() {
		return mattelScriptUrl;
	}

	public static void setWhyplayScriptUrl(String whyplayScriptUrl) {
		PropertyReaderUtils.whyplayScriptUrl = whyplayScriptUrl;
	}

	public static String getRescuePath() {
		return rescuePath;
	}

	public static String getRescueHeaderFooterURL() {
		return rescueHeaderFooterURL;
	}

	public static String getImageResourceType() {
		return imageResourceType;
	}

	public static String getFpSiteHeaderResourceType() {
		return fpSiteHeaderResourceType;
	}

	public static String getCategoryNavResourceType() {
		return categoryNavResourceType;
	}

	public static String getOtherNavResourceType() {
		return otherNavResourceType;
	}

}
