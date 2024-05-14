package com.mattel.ag.retail.core.constants;

/**
 * 
 * @author CTS. Common Constant Class for storing site specific constants.
 *
 */
public class Constants {

	/**
	 * Slash Constant
	 */
	public static final String SLASH = "/";
	
	/**
	 * AEM Node Date Format
	 */
	public static final String NODE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	public static final Object READWRITESERVICE = "readwriteservice";

	/**
	 * Template path for store locator pages.
	 */
	public static final String STOREPAGETEMPLATEPATH = "/conf/ag/settings/wcm/templates" + SLASH
			+ "retail-template-store-page";
	/**
	 * Property Operation value for Query Builder
	 */
	public static final String LIKE = "like";

	/**
	 * Template property value for Query Builder
	 */
	public static final String CQ_TEMPLATE = "cq:template";

	/**
	 * Constant for A.M.
	 */
	public static final String AM = "am";

	/**
	 * Constant for P.M.
	 */
	public static final String PM = "pm";

	/**
	 * Constant for jcr:content.
	 */
	public static final String JCR_CONTENT = "jcr:content";

	
	/**
	 * Constant for cq:PageContent.
	 */
	public static final String PAGE_CONTENT = "cq:PageContent";
	
	
	/**
	 * Constant for cq:Page.
	 */
	public static final String PAGE_TYPE = "cq:Page";
	/**
	 * Constant for template path
	 */
	public static final String TEMPLATE_PATH = "/conf/ag/settings/dam/cfm/models" + SLASH + "events-cf-model";

	/**
	 * 
	 * Private Constructor for avoiding Sonar issue.
	 */
	private Constants() {
	}
	
	/* Events Constant - Start*/
	
	public static final String EVENT_NAME_PREFIX = "event_";
	public static final String EVENT_DATE = "eventDate";
	public static final String EVENT_STARTTIME = "eventStartTime";
	public static final String EVENT_ENDTIME = "eventEndTime";
	public static final String EVENT_SCHEDULEDESC = "scheduleDescription";
	public static final String EVENT_ADDITIONALDATEINFO = "additionalDateInfo";
	public static final String EVENT_STORENAME = "storeName";
	public static final String EVENT_ZOMATOURL = "zomatoURL";
	public static final String EVENT_STORETAG = "storeTag";
	public static final String EVENT_ID = "eventId";
	public static final String EVENT_TITLE = "eventTitle";
	public static final String EVENT_DESC = "eventDescription";
	public static final String EVENT_MINAGE = "minAge";
	public static final String EVENT_RESERVATION_REQUIRED = "reservationRequired";
	public static final String EVENT_KEYWORDS = "keywords";
	public static final String EVENT_EVENTID_ATSTORE = "eventIdAtStore";
	public static final String EVENT_GRATUITYREQUIRED = "gratuityRequired";
	public static final String EVENT_PRICINGAMOUNT = "pricingAmount";
	public static final String EVENT_PRICINGOPTION = "pricingOption";
	public static final String EVENT_COSTINFO = "costInfo";
	
	public static final String EVENT_CREATE = "create";
	public static final String EVENT_UPDATE = "update";
	
	/* Events Constant - End*/
	
	public static final String CONTENT_TYPE_JSON = "application/json";
}
