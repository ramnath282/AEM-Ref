package com.mattel.ag.retail.core.utils;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS. Service for properties configuration of AG site.
 */
@Component(service = PropertyReaderUtils.class, immediate = true)
@Designate(ocd = PropertyReaderUtils.Config.class)
public class PropertyReaderUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyReaderUtils.class);
	private String retailQuestionairreBaseURL;
	private String personalShopperBaseURL;
	private String bazaarVoiceBaseURL;
	private String contentDamPath;
	private String tealiumUrl;
	private String eventsPath;
	private String agStoreTagsPath;
	private String typeAheadAccountURLs;
	private String snpAccountURLs;
	private String snpParams;
	private String targetSnpUrl;
	private String targetUrl;

	public String getTargetSnpUrl() {
		return targetSnpUrl;
	}

	public String getSnpParams() {
		return snpParams;
	}

	public void setSnpParams(String snpParams) {
		this.snpParams = snpParams;
	}

	public void setBazaarVoiceBaseURL(String bazaarVoiceBaseURL) {
		this.bazaarVoiceBaseURL = bazaarVoiceBaseURL;
	}

	public void setRetailQuestionairreBaseURL(String retailQuestionairreBaseURL) {
		this.retailQuestionairreBaseURL = retailQuestionairreBaseURL;
	}

	public void setPersonalShopperBaseURL(String personalShopperBaseURL) {
		this.personalShopperBaseURL = personalShopperBaseURL;
	}

	public void setContentDamPath(String contentDamPath) {
		this.contentDamPath = contentDamPath;
	}

	public void setEventsPath(String eventsPath) {
		this.eventsPath = eventsPath;
	}

	public void setAgStoreTagsPath(String agStoreTagsPath) {
		this.agStoreTagsPath = agStoreTagsPath;
	}

	@Activate
	public void activate(final Config config) {
		retailQuestionairreBaseURL = config.retailQuestionairreBaseURL();
		personalShopperBaseURL = config.personalShopperBaseURL();
		bazaarVoiceBaseURL = config.bazaarVoiceBaseURL();
		contentDamPath = config.contentDamPath();
		tealiumUrl = config.tealiumUrl();
		eventsPath = config.eventsPath();
		agStoreTagsPath = config.agStoreTagPath();
		typeAheadAccountURLs = config.typeAheadAccountURLs();
		snpAccountURLs = config.snpAccountURLs();
		snpParams = config.snpParams();
		targetSnpUrl = config.targetSnpUrl();
		targetUrl = config.targetUrl();
	}

	@ObjectClassDefinition(name = "AG Properties Configuration")
	public @interface Config {
		@AttributeDefinition(name = "Retail Questionairre Base URL", description = "Please enter Retail Questionairre Base URLRetail Questionairre Base URL")
		String retailQuestionairreBaseURL() default "https://www.americangirl.com/retail/contact_email.php";

		@AttributeDefinition(name = "Personal Shopper Base URL", description = "Please provide the base url for Personal Shopper form")
		String personalShopperBaseURL() default "https://www.americangirl.com/retail/ps_email.php";

		@AttributeDefinition(name = "Bazzar Voice Base URL", description = "Please provide the base url for Bazaar Voice")
		String bazaarVoiceBaseURL() default "https://display.ugc.bazaarvoice.com/static/Americangirl/retail_stores/en_US/bvapi.js";

		@AttributeDefinition(name = "Content Dam Path for Events", description = "Please provide the content dam path for events")
		String contentDamPath() default "/content/dam/ag-dam";

		@AttributeDefinition(name = "Tealium URL", description = "Please enter tealium URL for Data Tracking")
		String tealiumUrl() default "//tags.tiqcdn.com/utag/mattel/americangirl.aem/dev/utag.js";

		@AttributeDefinition(name = "Events Path", description = "All events get created under the this path")
		String eventsPath() default "/content/ag/en/retail/events";

		@AttributeDefinition(name = "AG Store Tag's Path", description = "Ag Store Tag's Path")
		String agStoreTagPath() default "/content/cq:tags/ag/retail/storesList";

		@AttributeDefinition(name = "Search & Promote Account URL", description = "Search & Promote Account URL")
		String snpAccountURLs() default "https://stage-sp1004f984.guided.ss-omtrdc.net/?index=prod";

		@AttributeDefinition(name = "Type Ahead Account URL", description = "Type Ahead Account URL")
		String typeAheadAccountURLs() default "https://content.atomz.com";

		@AttributeDefinition(name = "Parameters for Search and Promote endpoint", description = "Parameters for Search and Promote endpoint")
		String snpParams() default "&do=json_sayt&";

		@AttributeDefinition(name = "Target Search & Promote Account URL", description = "Target Search & Promote Account URL")
		String targetSnpUrl() default "https://stage-sp1004f984.guided.ss-omtrdc.net/?index=prod";

		@AttributeDefinition(name = "Target Url", description = "Please enter Adobe Target script URL ")
		String targetUrl();

		}

	/**
	 * @return retailQuestionairreBaseURL
	 */
	public String getRetailQuestionairreBaseURL() {
		LOGGER.debug("Retail Questionairre BaseUrl configured is{}", personalShopperBaseURL);
		return retailQuestionairreBaseURL;
	}

	/**
	 * @return personalShopperBaseURL
	 */
	public String getPersonalShopperBaseURL() {
		LOGGER.debug("Personal shopper BaseUrl configured is{}", personalShopperBaseURL);
		return personalShopperBaseURL;
	}

	/**
	 * @return bazaarVoiceBaseURL
	 */
	public String getBazaarVoiceBaseURL() {
		LOGGER.debug("Bazaar Voice BaseUrl configured is{}", bazaarVoiceBaseURL);
		return bazaarVoiceBaseURL;
	}

	/**
	 * @return contentDamPath
	 */
	public String getContentDamPath() {
		LOGGER.debug("contentDamPath configured is{}", contentDamPath);
		return contentDamPath;
	}

	/**
	 * @return eventsPath
	 */
	public String getEventsPath() {
		LOGGER.debug("eventsPath configured is{}", eventsPath);
		return eventsPath;
	}

	/**
	 * @return agStoreTagsPath
	 */
	public String getAgStoreTagsPath() {
		LOGGER.debug("agStoreTagsPath configured is{}", agStoreTagsPath);
		return agStoreTagsPath;
	}

	/**
	 *
	 * @return Tealium URL.
	 */

	public String getTealiumUrl() {
		LOGGER.debug("Tealium URL Configured is {}", tealiumUrl);
		return tealiumUrl;
	}

	public void setTealiumUrl(String tealiumUrl) {
		this.tealiumUrl = tealiumUrl;
	}

	public void setTargetSnpUrl(String targetSnpUrl) {
		this.targetSnpUrl = targetSnpUrl;
	}

	public String getTypeAheadAccountURLs() {
		return typeAheadAccountURLs;
	}

	public void setTypeAheadAccountURLs(String typeAheadAccountURLs) {
		this.typeAheadAccountURLs = typeAheadAccountURLs;
	}

	public String getSnpAccountURLs() {
		return snpAccountURLs;
	}

	public void setSnpAccountURLs(String snpAccountURLs) {
		this.snpAccountURLs = snpAccountURLs;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getTargetUrl() { return targetUrl; }

}
