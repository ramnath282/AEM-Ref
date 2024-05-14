package com.mattel.fisherprice.core.models;

import java.util.Locale;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.utils.FisherPriceUtils;

/**
 * @author CTS
 *
 */
@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(ArticleModel.class);
  private static final String TAG_ID = "tagid";
  private static final String TAG_TITLE = "tagtitle";

  @OSGiService
  private FisherPriceUtils fisherPriceUtils;

  @Inject
  private Resource resource;

  @Inject
  private Page currentPage;


  private String primaryTagIds;
  private String secondaryTagIds;
  private String primaryTagTitle;
  private String secondaryTagTitle;
  private String pageTitle;

  @PostConstruct
  protected void init() {
    LOGGER.info("ProductTargetModel init() -> Start");
    if (Objects.nonNull(resource)) {
      setProperties(resource);
    }
    LOGGER.info("ProductTargetModel init() -> End");
  }

  /**
   * get all property values from page
   * 
   * @param resource
   */
  private void setProperties(Resource resource) {
    LOGGER.info("setProperties -> Start");
    Locale locale = FisherPriceUtils.getPageLocale(currentPage);
    PageManager pageManager;
    Page page;
    pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
    if (null != pageManager) {
      page = pageManager.getContainingPage(resource);
      primaryTagTitle = fisherPriceUtils.getTagData(page, Constants.PRIMARY_TAG,
          ArticleModel.TAG_TITLE, Constants.COMMA, locale);
      secondaryTagTitle = fisherPriceUtils.getTagData(page, Constants.SECONDARY_TAG,
          ArticleModel.TAG_TITLE, Constants.COMMA_WITH_SPACE, locale);
      primaryTagIds = fisherPriceUtils.getTagData(page, Constants.PRIMARY_TAG, ArticleModel.TAG_ID,
          Constants.COMMA, locale);
      secondaryTagIds = fisherPriceUtils.getTagData(page, Constants.SECONDARY_TAG,
          ArticleModel.TAG_ID, Constants.COMMA, locale);
      pageTitle = page.getTitle();
    }

    LOGGER.info("setProperties -> End");
  }

  public String getPrimaryTagIds() {
    return primaryTagIds;
  }

  public String getSecondaryTagIds() {
    return secondaryTagIds;
  }

  public String getPrimaryTagTitle() {
    return primaryTagTitle;
  }

  public String getSecondaryTagTitle() {
    return secondaryTagTitle;
  }

  public String getPageTitle() {
    return pageTitle;
  }
}
