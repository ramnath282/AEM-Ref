package com.mattel.ag.explore.core.constants;

/**
 * 
 * @author CTS. Common Constant Class for storing site specific constants.
 *
 */
public class Constants {
	/**
	 * Slash CONSTANT
	 */
	public static final String SLASH = "/";
	/**
	 * Constant for Primary Tags
	 */
	public static final String PRIMARY_TAGS = "primaryTags";

	/**
	 * Constant for Tags (Combination of primaryTags , secondaryTags ,cqTags)
	 */
	public static final String ALL_TAGS = "allTags";

	/**
	 * Constant for Keywords
	 */
	public static final String KEYWORDS = "keywords";

	/**
	 * Constant for Secondary Tags
	 */
	public static final String SECONDARY_TAGS = "secondaryTags";

	/**
	 * Constant for CQ Tags
	 */
	public static final String CQ_TAGS = "cqTags";

	/**
	 * Constant for templateTypesPaths
	 */

	public static final String TEMPLATE_TYPES = "templateTypesPaths";

	/**
	 * Constant for Formatted ArticleDate
	 */

	public static final String ROBOTSFILEPATH = "";

	public static final String ARTICLE_DATE = "articleDate";

	/**
	 * Constant for path
	 */

	public static final String PATH = "path";

	/**
	 * Constant for Article Creation Date
	 */

	public static final String ARTICLE_CREATED_DATE = "jcr:created";

	/**
	 * Constant for Article Updation Date
	 */

	public static final String ARTICLE_UPDATED_DATE = "cq:lastModified";

	/**
	 * Constant for Article Display Date Authored at Page properties Level
	 */

	public static final String ARTICLE_DISPLAY_DATE = "datepickerarticle";

	/**
	 * Constant for Article Description
	 */

	public static final String ARTICLE_DESCRIPTION = "articleDescription";

	/**
	 * Constant for Social Media Sharing
	 */

	public static final String ARTICLE_SOCIAL_SHARING = "socialMediaEnabled";

	/**
	 * Constant for Title
	 */

	public static final String TITLE = "jcr:title";

	/**
	 * Constant for Date Format
	 */

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	/**
	 * AEM Node Date Format
	 */
	public static final String NODE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	/**
	 * Constant for BACKSLASH
	 */
	public static final String BACKSLASH = "/";

	/**
	 * Constant for the path of ARTICLE BANNER COMPONENT
	 */

	public static final String ARTICLE_BANNER_COMPONENT = "/jcr:content/root/articlebannercompone";

	/**
	 * Constant for the path of DEFAULT EXPLORE CONTENT
	 */

	public static final String DEFAULT_EXPLORE_CONTENT_PATH = "/content/ag"+SLASH+"en/explore";

	/**
	 * Constant to limit the number of query results
	 */
	public static final String DEFAULT_LIMIT = "limit";

	/**
	 * Constant to provide unique ID to the Article
	 */
	public static final String ARTICLE_ID = "articleID";

	/**
	 * Constant to display Article on explore landing page
	 */
	public static final String DISPLAY_HOME_PAGE = "displayhomepage";

	/**
	 * Constant to display Article on americangirl landing page
	 */
	public static final String DISPLAY_SITE_HOME = "displaysitehome";

	/**
	 * Constant for Article Landing Page Template.
	 */
	public static final String ARTICLE_LANDING_TEMPLATE_PATH = "/conf/ag/settings/wcm/templates"
			+ "/article-template-landing-page";

	/**
	 * 
	 * Private Constructor for avoiding Sonar issue.
	 */
	private Constants() {
	}

	/**
	 * Constant for hideDate
	 */

	public static final String HIDE_ARTICLE_DATE = "hideDate";

	public static final String SITEMAP_STRING = "sitemap";

	public static final String PROPBASEPATH = "sitemap.basepath";
	
	public static final Object SLING_VANITY_PATH = "sling:vanityPath";
	
	public static final Object READWRITESERVICE = "readwriteservice";

	public static final String XML_SCHEMAS = "<urlset" + " " + "xmlns="
			+ "\"https://www.sitemaps.org/schemas/sitemap/0.9\"" + " " + "xmlns:xsi="
			+ "\"https://www.w3.org/2001/XMLSchema-instance\"" + " " + "xsi:schemaLocation="
			+ "\"https://www.sitemaps.org/schemas/sitemap/0.9" + " "
			+ "https://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\"" + ">";

}
