package com.mattel.informational.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.informational.core.constants.Constants;
import com.mattel.informational.core.helper.InformationalHelper;
import com.mattel.informational.core.pojos.HrefLangPojo;

/**
 * @author CTS
 *
 */
@Component(service = InformationalUtils.class, immediate = true)
public class InformationalUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(InformationalUtils.class);
  public static final String DATE_FORMAT = "E MMM dd HH:mm:ss Z yyyy";
  /* Added Private Constructor for Sonar */
  private InformationalUtils() {
  }

  /**
   * get UNIX date format
   * 
   * @param timeStamp
   *          timestamp
   * @param dateFormat
   *          dateformat of timestamp
   * @return
   */
  public static String getUnixDate(String timeStamp, String dateFormat) {
    LOGGER.info("getUnixDate -> START");
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
      Date dt = sdf.parse(timeStamp);
      long epoch = dt.getTime();
      long epochDate = epoch / 1000;
      LOGGER.debug("Unix Time : {} ", epochDate);
      return Long.toString(epochDate);
    } catch (ParseException e) {
      LOGGER.error("Date parsing issue : {}", e);
    }
    return StringUtils.EMPTY;
  }

  /**
   * method takes following inputs and return date in expected format
   * 
   * @param timeStamp
   *          original timestamp
   * @param originalDateFormat
   *          original timestamp date format
   * @param expectedDateFormat
   *          expected date format
   * @return
   */
  public static String formatDate(String timeStamp, String originalDateFormat,
      String expectedDateFormat) {
    LOGGER.info("formatDate -> START");
    try {
      SimpleDateFormat originalDF = new SimpleDateFormat(originalDateFormat);
      SimpleDateFormat expectedDF = new SimpleDateFormat(expectedDateFormat);
      Date originalDate = originalDF.parse(timeStamp);
      String expectedDate = expectedDF.format(originalDate);
      LOGGER.debug("Date has been converted from {} to {} format", timeStamp, expectedDate);
      return expectedDate;
    } catch (ParseException e) {
      LOGGER.error("formatDate Date parsing issue : {}", e);
    }
    return StringUtils.EMPTY;
  }

  /**
   * @param pagePath
   * @return This method returns page locale
   */
  public static Locale getPageLocale(Page page) {
    LOGGER.info("getPageLocale method of InformationalUtils starts");
    Locale locale = null;
    if (Objects.nonNull(page)) {
      Page parentPage = page.getAbsoluteParent(3);
      String pagePath = parentPage.getPath();
      LOGGER.debug("pagePath : {}", pagePath);
      String localeString = pagePath.substring(pagePath.lastIndexOf(Constants.SLASH) + 1,
          pagePath.length());
      String[] localeDetails = pagePath.contains("language-masters")
          ? localeString.split(Constants.UNDERSCORE) : localeString.split(Constants.HYPHEN);

      if (localeDetails.length == 2) {
        LOGGER.debug("Language Code : {} , Country Code : {}", localeDetails[0], localeDetails[1]);
        locale = new Locale(localeDetails[0], localeDetails[1]);
      }
    }
    LOGGER.info("getPageLocale method of InformationalUtils end");
    return locale;
  }
  
  public static String getCurrentBrandExpFragmentRootPath(String currentBrandName, String expOrSiteRootPath, String resourcePath) {
		LOGGER.info("getCurrentBrandExpFragmentRootPath() starts here ");
		if(StringUtils.isNotBlank(expOrSiteRootPath)) {
			String[] brandRootPath = InformationalConfigurationUtils.getExpFragmentRootPathArray();
			return getExpFragOrBrandSiteRootPath(brandRootPath, currentBrandName, resourcePath);

		}
		LOGGER.info("getCurrentBrandExpFragmentRootPath() ends here");
		return "";
	}

	private static String getExpFragOrBrandSiteRootPath(String[] brandRootPath,String currentBrandName, String resourcePath) {
		LOGGER.info("getExpFragOrBrandSiteRootPath() starts here ");
		if (Objects.nonNull(brandRootPath) && brandRootPath.length > 0) {
			for (String rootPath : brandRootPath) {
				if (rootPath.contains(":") && rootPath.split(":").length > 1) {
					String brandName = rootPath.split(":")[0];
					String[] brandExpFragPathArray = rootPath.split(":")[1].split("/");
					String readUniqueBrandExpFragPath = brandExpFragPathArray[brandExpFragPathArray.length-1];
					LOGGER.debug("getExpFragOrBrandSiteRootPath() readUniqueBrandExpFragPath {}", readUniqueBrandExpFragPath);
					if (currentBrandName.equals(brandName) && resourcePath.contains(readUniqueBrandExpFragPath)) {
						String rootPathAfterSplit = rootPath.split(":")[1];
						LOGGER.debug("display either experienceRootPath or SiteRootPath {}",rootPathAfterSplit);
						return rootPathAfterSplit;
					}
				}
			}
		}
		LOGGER.info("getExpFragOrBrandSiteRootPath() starts here ");
		return "";
	}
	
	/**
	 * @return storeKeyValueMap
	 */
	public static String getValueFromKeyMappings(String[] idMappings, String key) {
		LOGGER.info("InformationalUtils:Start of getValueFromKeyMappings method");
		long startTime = System.currentTimeMillis();
		String value = StringUtils.EMPTY;
		Map<String, String> configKeyValueMap = new HashMap<>();
		for (String mapping : idMappings) {
			String[] mappingIdSplit = mapping.split(":", 2);
			LOGGER.debug("mappingIdSplit :: {}", mappingIdSplit);
			if (mappingIdSplit.length > 1) {
				configKeyValueMap.put(mappingIdSplit[0], mappingIdSplit[1]);
				LOGGER.debug("configKeyValueMap{}", configKeyValueMap);
			}
		}
		if (configKeyValueMap.containsKey(key)) {
			value = configKeyValueMap.get(key);
			LOGGER.debug("value :: {}", value);

		}
		LOGGER.debug("configKeyValueMap :: {}", configKeyValueMap);
		long endTime = System.currentTimeMillis();
		LOGGER.debug("time :: {}", endTime - startTime);
		LOGGER.info("InformationalUtils :End of getValueFromKeyMappings method");
		return value;
	}
	
	/**
	 * This method gets the hreflang URLs for the current page based on 
	 * the site hierarchy present for the brand.
	 * @param brandName brand name of the site
	 * @param resource page resource
	 * @return hrefLangList list if objects containing hreflang properties
	 */
    public static List<HrefLangPojo> getHrefLangPropertyList(String brandName,Resource resource) {
        LOGGER.info("start of getHrefLangPropertyList method");
        List<HrefLangPojo> hrefLangList = new ArrayList<>();
        String currentResPath = resource.getPath();
        String currentpagePath = currentResPath.replace("jcr:content", "");
        String countryLocale = InformationalHelper.getRelativePath(currentpagePath, resource);
        if (StringUtils.isNotBlank(countryLocale)) {
            String tempPath = currentpagePath.substring(currentpagePath.indexOf(countryLocale),
                    currentpagePath.length() - 1);
            String relativePath = tempPath.replace(countryLocale, "");
            int indxBrandName = currentpagePath.indexOf(brandName);
            String sitesRootPath = currentpagePath.substring(0, indxBrandName + brandName.length());
            LOGGER.debug("sitesRootPath of getHrefLangPropertyList method {}",sitesRootPath);
            Session session = resource.getResourceResolver().adaptTo(Session.class);
            QueryBuilder queryBuilder = resource.getResourceResolver().adaptTo(QueryBuilder.class);
            SearchResult result = InformationalHelper.getCountryNodesByLanguage(sitesRootPath, session, queryBuilder);
            if (Objects.nonNull(result)) {
            	fetchHrefLangList(result, relativePath,resource,hrefLangList);
            }
        }
        LOGGER.info("End of getHrefLangPropertyList method");
        return hrefLangList;
    }
    
    /**
     * This method iterates over the query results and updates 
     * the hreflang list with page details
     * @param result query results
     * @param relativePath relative path like "us/en-us"
     * @param resource page resource
     * @param hrefLangList list if objects containing hreflang properties
     */
    private static void fetchHrefLangList(SearchResult result, String relativePath,Resource resource,List<HrefLangPojo> hrefLangList) {
        try {
            for (Hit hit : result.getHits()) {
                if (Objects.nonNull(hit.getPath())) {
                    PageManager pgmgr = resource.getResourceResolver().adaptTo(PageManager.class);
                    if (Objects.nonNull(pgmgr)) {
                        String pagepath = hit.getPath().replace(Constants.SLASH_JCR_CONTENT, "");
                        boolean enableHrefLang = checkHrefLangEligibility(pagepath, pgmgr, relativePath);
                        if (enableHrefLang) {
                            HrefLangPojo hrefLangPojo = new HrefLangPojo();
                            hrefLangPojo.setUrl(pagepath + relativePath);
                            hrefLangPojo.setLocale(InformationalHelper.getPageLocale(pagepath));
                            hrefLangList.add(hrefLangPojo);
                            LOGGER.debug("Size of hrefLangList is {}", hrefLangList.size());
                        }
                    }
                }
            }
            Iterator<Resource> resources = result.getResources();
            if (resources.hasNext()) {
                ResourceResolver leakingResResolver = resources.next().getResourceResolver();
                if (leakingResResolver.isLive()) {
                    leakingResResolver.close();
                }
            }
        } catch (RepositoryException e) {
            LOGGER.error("RepositoryException occured while retriving hreflang page list {} ", e);
        }
    }
    
    /**
     * This method checks the eligibility of the current page. It checks 
     * if page with similar name is present in other site hierarchies
     * @param pagepath page path
     * @param pgmgr Page Manager Object
     * @param relativePath relative path for country and locale like "us/en-us"
     * @return
     */
    private static boolean checkHrefLangEligibility(String pagepath, PageManager pgmgr, String relativePath) {
        boolean isHreflangUrl = true;
        if (Objects.nonNull(pgmgr) && Objects.nonNull(pgmgr.getPage(pagepath))) {
            Page currentPage = pgmgr.getPage(pagepath);
            Page localepage = pgmgr.getPage(pagepath + relativePath);
            if (Objects.nonNull(currentPage)) {
                String homePPath = pagepath + "/home";
                Page homepage = pgmgr.getPage(homePPath);
                if (Objects.nonNull(homepage) && Objects.nonNull(homepage.getProperties())) {
                    ValueMap map = homepage.getProperties();
                    if (map.containsKey(Constants.CQ_REDIRECT_TARGET) && Objects.nonNull(map.get(Constants.CQ_REDIRECT_TARGET))) {
                        LOGGER.debug("cq:redirectTarget property is set and its value is-> {}",
                                map.get(Constants.CQ_REDIRECT_TARGET));
                        isHreflangUrl = false;
                    }
                }
            }
            if (Objects.isNull(localepage)) {
                isHreflangUrl = false;
            }
        }
        return isHreflangUrl;
    }
}
