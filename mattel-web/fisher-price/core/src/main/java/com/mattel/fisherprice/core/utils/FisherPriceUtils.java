package com.mattel.fisherprice.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.pojos.TagsPojo;
import com.mattel.fisherprice.core.services.RelatedArticleService;

/**
 * @author CTS
 *
 */
@Component(service = FisherPriceUtils.class, immediate = true)
public class FisherPriceUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(FisherPriceUtils.class);
  public static final String DATE_FORMAT = "E MMM dd HH:mm:ss Z yyyy";

  @Reference
  RelatedArticleService relatedArticleService;

  /**
   * get tag details for the page using provided propertyName and details
   *
   * @param page
   *          current page object
   * @param tagPropertyName
   *          tag property name
   * @param tagPropery
   *          tag property
   * @return
   */
  public String getTagData(Page page, String tagPropertyName, String tagPropery, String separater,
          Locale locale) {
    LOGGER.info("getTagData -> Start");
    List<String> dataList = new ArrayList<>();
    if (Objects.nonNull(page)) {
      String[] tags = page.getProperties().get(tagPropertyName, String[].class);
      List<TagsPojo> tagPojoList = relatedArticleService.getTagRelatedData(tags, locale);
      tagPojoList.forEach(tagPojo -> {
        if (Constants.TAG_TITLE.equals(tagPropery)) {
          dataList.add(tagPojo.getLocaleBasedTitle());
        } else if (Constants.TAG_ID.equals(tagPropery)) {
          dataList.add(tagPojo.getTagID());
        }
      });
    }
    String detailString = String.join(separater, dataList);
    LOGGER.info("{} : {}", tagPropery, detailString);
    return detailString;
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
    LOGGER.info("getPageLocale method of FisherPriceUtil starts");
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
    LOGGER.info("getPageLocale method of FisherPriceUtil end");
    return locale;
  }

  /**
   * This method provides AT Property authored at site level configuration
   *
   * @param containingPage
   * @return inheritedAtProperty
   */
  public static String getInheritedProperty(Page containingPage, String property) {
    LOGGER.info("Start of getInheritedAtProperty method of FisherPriceUtil");
    final InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(
            containingPage.getContentResource());
    LOGGER.debug("Inherited property value is  {}",
            inheritanceValueMap.getInherited(property, String.class));
    LOGGER.info("End of getInheritedAtProperty method of FisherPriceUtil");
    return inheritanceValueMap.getInherited(property, String.class);
  }

  /**
   * This method provides AT Property authored at site level configuration
   *
   * @param resource
   * @return inheritedAtProperty
   */
  public static String getInheritedProperty(Resource resource, String property) {
    LOGGER.info("Start of getInheritedAtProperty method of FisherPriceUtil");
    final InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(resource);
    LOGGER.debug("Inherited property value is  {}",
            inheritanceValueMap.getInherited(property, String.class));
    LOGGER.info("End of getInheritedAtProperty method of FisherPriceUtil");
    return inheritanceValueMap.getInherited(property, String.class);
  }

}
