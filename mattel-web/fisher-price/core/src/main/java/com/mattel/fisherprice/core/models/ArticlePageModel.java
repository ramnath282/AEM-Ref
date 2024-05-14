package com.mattel.fisherprice.core.models;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.helper.FisherPriceHelper;
import com.mattel.fisherprice.core.utils.FisherPriceUtils;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ArticlePageModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(ArticlePageModel.class);
  private static final String ARTICLE_PATH = Constants.SLASH_ROOT
      + "/columncontrol/columnone/articlecomponent";
  private static final String ARTICLE_TITLE_PROP = "articleTitle";
  private static final String ARTICLE_SHORT_DESC_PROP = "articleShortDescription";
  private static final String ARTICLE_IMAGE = "image";
  private static final String ARTICLE_ID = "articleId";
  private static final String IMAGE_SERVER_URL = "imageserverurl";
  private static final String ASSET_ID_IMAGE = "assetIDimage";
  private static final String REQUIRED_DATE_FORMAT = "MMMM dd, yyyy hh:mm:ss z";

  @OSGiService
  private FisherPriceUtils fisherPriceUtils;

  @Self
  @Via("resource")
  private Resource resource;

  @Inject
  private Page currentPage;

  private String articleTitle;
  private String articleShotDescription;
  private String articleImage;
  private String articleDate;
  private String articleUpdate;
  private String articleId;
  private String primaryTagNames;
  private String secondaryTagNames;
  private String primaryTagIds;
  private String secondaryTagIds;
  private String imageserverurl;
  private String assetIDimage;
  private String categoryPage;

  private ResourceResolver resourceResolver;

  @PostConstruct
  protected void init() {
    LOGGER.info("article page model init -> START");
    if (null != resource) {
      resourceResolver = resource.getResourceResolver();
      getArticleNodePropties();
      getArticlePageDetails();

      if (Objects.nonNull(currentPage)) {
        Page parentPage = currentPage.getParent();
        if (Objects.nonNull(parentPage)) {
          categoryPage = FisherPriceHelper.checkLink(parentPage.getPath(), resource);
        }
      }
    }
    LOGGER.info("article page model init -> END");
  }

  private void getArticleNodePropties() {
    LOGGER.info("getArticleNodePropties -> START");

    String resourcePath = resource.getPath() + ARTICLE_PATH;
    Resource articleResource = resourceResolver.resolve(resourcePath);
    Node articleNode = articleResource.adaptTo(Node.class);

    if (null != articleNode) {
      articleTitle = getArticleComponentPropertyValues(articleNode,
          ArticlePageModel.ARTICLE_TITLE_PROP);
      articleShotDescription = getArticleComponentPropertyValues(articleNode,
          ArticlePageModel.ARTICLE_SHORT_DESC_PROP);
      articleImage = getArticleComponentPropertyValues(articleNode, ArticlePageModel.ARTICLE_IMAGE);
      imageserverurl = getArticleComponentPropertyValues(articleNode,
          ArticlePageModel.IMAGE_SERVER_URL);
      assetIDimage = getArticleComponentPropertyValues(articleNode,
          ArticlePageModel.ASSET_ID_IMAGE);
    }

    LOGGER.info("ArticlePageModel init -> END");
  }

  /**
   * @param articleNode
   * @param propertyName
   * @return
   */
  private String getArticleComponentPropertyValues(Node articleNode, String propertyName) {
    LOGGER.info("getArticlePropertyValues -> START");

    String propertyValue = StringUtils.EMPTY;
    try {
      PropertyIterator articleProperty = articleNode.getProperties(propertyName);
      propertyValue = (null != articleProperty)
          ? articleNode.getProperty(propertyName).getValue().getString()
          : StringUtils.EMPTY;
    } catch (RepositoryException e) {
      LOGGER.error("getArticlePropertyValues Repository Exception : {}", e);
    }

    LOGGER.info("ProperyName : {} , ProperyValue : {}", propertyName, propertyValue);
    return propertyValue;
  }

  /**
   * get article page level details
   */
  private void getArticlePageDetails() {
    LOGGER.info("getArticlePageDetails -> START");
    Locale locale = FisherPriceUtils.getPageLocale(currentPage);
    PageManager pageManager;
    Page page;
    pageManager = resourceResolver.adaptTo(PageManager.class);

    if (Objects.nonNull(pageManager)) {
      page = pageManager.getContainingPage(resource);

      if (Objects.nonNull(page)) {

        Date createdDate = page.getProperties().get(JcrConstants.JCR_CREATED, Date.class);
        if (Objects.nonNull(createdDate)) {
          articleDate = FisherPriceUtils.formatDate(createdDate.toString(),
              FisherPriceUtils.DATE_FORMAT, ArticlePageModel.REQUIRED_DATE_FORMAT);
        }

        Date publishedDate = page.getProperties().get(Constants.LAST_MODIFIED_DATE, Date.class);
        if (Objects.nonNull(publishedDate)) {
          articleUpdate = FisherPriceUtils.formatDate(publishedDate.toString(),
              FisherPriceUtils.DATE_FORMAT, ArticlePageModel.REQUIRED_DATE_FORMAT);
        }

        articleId = page.getProperties().get(ArticlePageModel.ARTICLE_ID, String.class);

        primaryTagNames = fisherPriceUtils.getTagData(page, Constants.PRIMARY_TAG,
            Constants.TAG_TITLE, Constants.PIPE, locale);
        secondaryTagNames = fisherPriceUtils.getTagData(page, Constants.SECONDARY_TAG,
            Constants.TAG_TITLE, Constants.PIPE, locale);
        primaryTagIds = fisherPriceUtils.getTagData(page, Constants.PRIMARY_TAG, Constants.TAG_ID,
            Constants.PIPE, locale);
        secondaryTagIds = fisherPriceUtils.getTagData(page, Constants.SECONDARY_TAG,
            Constants.TAG_ID, Constants.PIPE, locale);
      }
    }

    LOGGER.info("getArticlePageDetails -> END");
  }

  public String getArticleTitle() {
    return articleTitle;
  }

  public String getArticleShotDescription() {
    return articleShotDescription;
  }

  public String getArticleImage() {
    return articleImage;
  }

  public String getArticleDate() {
    return articleDate;
  }

  public String getArticleUpdate() {
    return articleUpdate;
  }

  public String getArticleId() {
    return articleId;
  }

  public String getPrimaryTagNames() {
    return primaryTagNames;
  }

  public String getSecondaryTagNames() {
    return secondaryTagNames;
  }

  public String getPrimaryTagIds() {
    return primaryTagIds;
  }

  public String getSecondaryTagIds() {
    return secondaryTagIds;
  }

  public String getImageserverurl() {
    return imageserverurl;
  }

  public String getAssetIDimage() {
    return assetIDimage;
  }

  public String getCategoryPage() {
    return categoryPage;
  }

}
