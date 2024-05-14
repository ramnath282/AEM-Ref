package com.mattel.fisherprice.core.models;

import java.util.Locale;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.mattel.fisherprice.core.utils.FisherPriceUtils;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticleGridModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(ArticleGridModel.class);

  private static final String CATEGORY_LANDING_PAGE = "/conf/fisher-price/settings/wcm/templates/category-landing-page";

  private static final String PRIMARY_TAG = "primaryTags";

  @Inject
  @Via("resource")
  private String seeMoreLabel;

  @Inject
  @Via("resource")
  private String defaultImage;

  @Inject
  @Via("resource")
  private String searchType;

  @Inject
  @Via("resource")
  private String categoryId;

  @Inject
  @Via("resource")
  private String heading;

  @Inject
  @Via("resource")
  private Integer initialLoadCount;

  @Inject
  @Via("resource")
  private String viewAllLabel;

  @Inject
  @Via("resource")
  private Integer productLimit;

  @Inject
  private Page currentPage;

  @SlingObject
  SlingHttpServletRequest request;

  private String categoryTagTitle;

  /**
   * The init method to process listing module details
   */
  @PostConstruct
  protected void init() {
    LOGGER.info("ArticleGrid init method  ----> Start");
    if (Objects.nonNull(currentPage)) {
      LOGGER.debug("currentPage Template path ----> {}", currentPage.getTemplate().getPath());
      updateCategoryTagTitle();
    }

    LOGGER.info("ArticleGrid init method  ----> End");
  }

  private void updateCategoryTagTitle() {
    LOGGER.info("ArticleGrid getCategoryName method  ----> Start");
    Locale locale = FisherPriceUtils.getPageLocale(currentPage);
    ResourceResolver resourceResolver = request.getResourceResolver();
    TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
    LOGGER.debug("tagManager object ----> {}", tagManager);
    if (StringUtils.isEmpty(categoryId)
        && StringUtils.contains(currentPage.getTemplate().getPath(), CATEGORY_LANDING_PAGE)) {
      LOGGER.debug("categoryId  via dialog----> {}", categoryId);
      String[] tags = currentPage.getProperties().get(PRIMARY_TAG, String[].class);
      if (Objects.nonNull(tags)) {
        categoryId = tags[0];
      }
    }
    if (Objects.nonNull(tagManager) && StringUtils.isNotEmpty(categoryId)) {
      Tag tag = tagManager.resolve(categoryId);
      if (Objects.nonNull(tag)) {
        categoryTagTitle = tag.getTitle(locale);
      }
    }
    LOGGER.debug("category Tag Name----> {}", categoryTagTitle);
    LOGGER.info("ArticleGrid getCategoryName method  ----> End");
  }

  public String getSeeMoreLabel() {
    return seeMoreLabel;
  }

  public Integer getInitialLoadCount() {
    return initialLoadCount;
  }

  public String getSearchType() {
    return searchType;
  }

  public String getViewAllLabel() {
    return viewAllLabel;
  }

  public Integer getProductLimit() {
    return productLimit;
  }

  public String getDefaultImage() {
    return defaultImage;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public String getHeading() {
    return heading;
  }

  public String getCategoryTagTitle() {
    return categoryTagTitle;
  }

}
