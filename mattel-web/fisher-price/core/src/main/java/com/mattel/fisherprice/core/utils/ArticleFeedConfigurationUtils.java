package com.mattel.fisherprice.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * OSGI configuration containing variables required for FP article feed
 */
@Component(service = ArticleFeedConfigurationUtils.class, immediate = true)
@Designate(ocd = ArticleFeedConfigurationUtils.Config.class)
public class ArticleFeedConfigurationUtils {
	private static String[] localesForArticleFeed;
	private static String damPathToUploadArticleFeed; 
	private static String articleFeedFileInitialName;
	private static String[] headerLinesForCSV;
	private static String articleCompResourceType;
	private static String brandBasePath;
	private static String articleDetailTemplatePath;
	private static String articleBasePath;

	@Activate
	public void activate(final Config config) {
		ArticleFeedConfigurationUtils.buildConfigs(config);
	}

	private static void buildConfigs(final Config config) {
		localesForArticleFeed = config.localesForArticleFeed();
		damPathToUploadArticleFeed = config.damPathToUploadArticleFeed();
		articleFeedFileInitialName = config.articleFeedFileInitialName();
		headerLinesForCSV = config.headerLinesForCSV();
		articleCompResourceType = config.articleCompResourceType();
		brandBasePath = config.brandBasePath();
		articleDetailTemplatePath = config.articleDetailTemplatePath();
		articleBasePath = config.articleBasePath();
	}

	@ObjectClassDefinition(name = "Fisherprice Article Feed Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Locales for Article Feed", description = "Please enter the list of locales for which article feed files needs to be uplaoded : in the format 'en-us' ")
		String[] localesForArticleFeed();

		@AttributeDefinition(name = "DAM Path to Upload Article Feed files", description = "Please enter the DAM path on which the files for article feed needs to uploaded")
		String damPathToUploadArticleFeed() default "/content/dam/fp-dam/articlefeed";	

		@AttributeDefinition(name = "Initial Name for the Article Feed File generated", description = "Please enter the initial name for the article feed files generated. It will be appended by '_conutryLocale'")
		String articleFeedFileInitialName() default "articleFeed";	

		@AttributeDefinition(name = "Header Lines for CSV files", description = "Please enter the lines to be shown in the header for CSV article feed files. CAUTION : PLEASE USE ',' CHARACTER ONLY INSIDE \"\" ")
		String[] headerLinesForCSV();	

		@AttributeDefinition(name = "Resource Type of Article Component", description = "Please enter the resource type of article component. Do not include \" /apps/\"")
		String articleCompResourceType() default "mattel/ecomm/fisher-price/components/content/articleComponent";			

		@AttributeDefinition(name = "Brand base path", description = "Please enter the base path for Brand")
		String brandBasePath() default "/content/fisher-price";	

		@AttributeDefinition(name = "Path of Article Detail Template", description = "Please enter the path of article detail template")
		String articleDetailTemplatePath() default "/conf/fisher-price/settings/wcm/templates/article-details-page";		

		@AttributeDefinition(name = "Path for Article Base Page", description = "Please enter the base path for Article pages")
		String articleBasePath() default "/home/articles";	
	}

	public static String[] getLocalesForArticleFeed() {
		return localesForArticleFeed;
	}

	public static String getDamPathToUploadArticleFeed() {
		return damPathToUploadArticleFeed;
	}

	public static String getArticleFeedFileInitialName() {
		return articleFeedFileInitialName;
	}

	public static String[] getHeaderLinesForCSV() {
		return headerLinesForCSV;
	}

	public static String getArticleCompResourceType() {
		return articleCompResourceType;
	}

	public static String getBrandBasePath() {
		return brandBasePath;
	}

	public static String getArticleDetailTemplatePath() {
		return articleDetailTemplatePath;
	}

	public static String getArticleBasePath() {
		return articleBasePath;
	}

}
