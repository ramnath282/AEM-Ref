package com.mattel.ag.explore.core.model;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ag.explore.core.constants.Constants;
import com.mattel.ag.explore.core.pojos.TagsPojo;
import com.mattel.ag.explore.core.services.GetRelatedArticles;
import javax.annotation.PostConstruct;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * @author CTS.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleBannerModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(ArticleBannerModel.class);

  @Self
  private Resource resource;

  @OSGiService
  private GetRelatedArticles getRelatedArticles;

  private String[] primaryTagTitle;
  private List<String> secondaryTagTitle;
  private String pageTitle;
  private String date;
  private boolean hideDate;

  @PostConstruct
  protected void init() {
    LOGGER.info("Start of in it method in Article Banner Model");

    if (null != resource) {
      PageManager pageManager = null;
      Page page = null;
      pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
      if (null != pageManager) {
        page = pageManager.getContainingPage(resource);
        String[] primaryTags = page.getProperties().get("primaryTags", String[].class);
        String[] secondaryTags = page.getProperties().get("secondaryTags", String[].class);
        String datePublished = page.getProperties().get("datepickerarticle", String.class);
        String dateHide = page.getProperties().get("hideDate", String.class);
        pageTitle = page.getTitle();
        List<TagsPojo> primaryTag = getRelatedArticles.getTagRelatedData(primaryTags);
        LOGGER.debug("Primary Tags Pojo is {}", primaryTag);
        List<TagsPojo> secondaryTag = getRelatedArticles.getTagRelatedData(secondaryTags);
        LOGGER.debug("Secondary Tags Pojo is {}", secondaryTag);
        List<String> primaryTagNamesList = new ArrayList<>();
        List<String> secondaryTagNamesList = new ArrayList<>();
        for (TagsPojo primaryTagName : primaryTag) {
          primaryTagNamesList.add(primaryTagName.getTagTitle());
          LOGGER.debug("Primary tag Title is {}", primaryTagName.getTagTitle());
        }
        for (TagsPojo secondaryTagName : secondaryTag) {
          secondaryTagNamesList.add(secondaryTagName.getTagTitle());
          LOGGER.debug("Secondary tag Title is {}", secondaryTagName.getTagTitle());
        }
        primaryTagTitle = new String[primaryTagNamesList.size()];
        primaryTagTitle = primaryTagNamesList.toArray(primaryTagTitle);
        secondaryTagTitle = secondaryTagNamesList;
        if (null != datePublished) {
          date = returnDate(datePublished);
        }
        hideDate = false;
        if ("true".equals(dateHide)) {
          hideDate = true;
        }
        LOGGER.debug("Date with year is {}", date);
        LOGGER.debug("Hide Date {}", hideDate);
        LOGGER.debug("Final Primary tag is {}", primaryTagTitle);
        LOGGER.debug("Final Secondary Tag Titles are {}", secondaryTagTitle);
        LOGGER.info("End of in it method in Article Banner Model");
      }
    }

  }

  /**
   * Method for returning date with year.
   *
   * @param date
   * @return String.
   */
  private String returnDate(String date) {
    LOGGER.info("Start of return Date method");
    LOGGER.debug("Date from the parameter is {}", date);
    String dateReturned = null;
    try (Formatter monthFormatter = new Formatter()) {
      OffsetDateTime odt = OffsetDateTime.parse(date);
      SimpleDateFormat format = new SimpleDateFormat(Constants.NODE_DATE_FORMAT);
      Date recipieDate = format.parse(date);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(recipieDate);
      calendar.setTimeZone(TimeZone.getTimeZone(odt.getOffset()));
      String month = monthFormatter.format("%tB", calendar).toString();
      dateReturned = month + " " + calendar.get(Calendar.DATE) + ",  " + calendar.get(Calendar.YEAR);
      LOGGER.debug("Date returned is {}", dateReturned);
    } catch (ParseException pe) {
      LOGGER.error("Parse Exception {}", pe);
    }
    LOGGER.info("End of return Date method");
    return dateReturned;
  }

  /**
   * @return
   */
  public String[] getPrimaryTagTitle() {
    return primaryTagTitle;
  }

  public List<String> getSecondaryTagTitle() { return secondaryTagTitle; }

  public String getPageTitle() {
    return pageTitle;
  }

  public String getDate() {
    return date;
  }

  public boolean isHideDate() {
    return hideDate;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  public void setGetRelatedArticles(GetRelatedArticles getRelatedArticles) {
    this.getRelatedArticles = getRelatedArticles;
  }

}
