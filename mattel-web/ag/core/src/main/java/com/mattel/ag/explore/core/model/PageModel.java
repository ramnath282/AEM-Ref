package com.mattel.ag.explore.core.model;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.mattel.ag.retail.core.utils.PropertyReaderUtils;
import com.mattel.ag.retail.core.services.MultifieldReader;

@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PageModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(PageModel.class);
  Map<String, String> metaKeywordsDescription = new LinkedHashMap<>();
  Map<String, String> ogTags = new LinkedHashMap<>();
  List<String> robotTags = new LinkedList<>();
  @Inject
  private MultifieldReader multifieldReader;
  private boolean isHasOgImage;
  private boolean isHasOgDescription;
  private boolean isHasOgTitle;
  private String imagePath;
  private String categoryName;
  private boolean keywordsFlag;
  private boolean descriptionFlag;
  private boolean titleFlag;
  private boolean templateFlag;
  private boolean viewportFlag;
  private String cssPath;
  private String targetUrl;
  private String canonicalTag;

  @Self
  @Via("resource")
  private Resource resource;

  @OSGiService
  PropertyReaderUtils propertyReaderUtils;

  @SlingObject
  private SlingHttpServletRequest request;

  @PostConstruct
  protected void init() {
    LOGGER.info("Start of PageModel init");

    if (null != resource) {
      checkCanonicalUrl();
      collectMetaKeywordDescription();
      collectOgTags();
      collectRobotTags();
      setBooleanProperties();
      setDefaultImagePathForOg();
      setThemesproperties();
      targetUrl = propertyReaderUtils.getTargetUrl();

    }
  }

  private void checkCanonicalUrl() {
    PageModel.LOGGER.info("PageModel checkCanonicalUrl Start");
    final Page currentPage = getCurrentPage();
    PageModel.LOGGER.debug("PageModel current page object {}", currentPage);
    if (Objects.nonNull(currentPage)) {
      final ValueMap valuemap = currentPage.getProperties();
      PageModel.LOGGER.debug("PageModel value map object {}", valuemap);

      final String vanityUrl = currentPage.getVanityUrl();

      if (StringUtils.isNotBlank(vanityUrl)) {
        final StringBuilder canonicalTagBuilder = new StringBuilder();
        canonicalTagBuilder.append(request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getRequestURI())));
        canonicalTagBuilder.append(vanityUrl);
        canonicalTag = canonicalTagBuilder.toString();

      } else {
        canonicalTag = valuemap.get("canonicalUrl", String.class);
      }
    }
    PageModel.LOGGER.info("PageModel checkCanonicalUrl End");
  }

  private Page getCurrentPage() {
    PageModel.LOGGER.info("PageModel getCurrentPage Start");
    Page currentPage = null;
    final PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
    if (Objects.nonNull(pageManager)) {
      currentPage = pageManager.getContainingPage(resource);
    }
    PageModel.LOGGER.info("PageModel getCurrentPage End");
    return currentPage;
  }

  private void collectMetaKeywordDescription() {
    Map<String, ValueMap> stringValueMapLinkedHashMap = getPropertyMap("metaKeywordsDescription");
    stringValueMapLinkedHashMap.forEach((String key, ValueMap valuemap) -> metaKeywordsDescription
        .put(valuemap.get("metaName", String.class), valuemap.get("metaContent", String.class)));
    setFlags(metaKeywordsDescription);
  }

  private void setFlags(Map<String, String> metaKeywordsDescriptions) {
    keywordsFlag = metaKeywordsDescriptions.containsKey("keywords");
    descriptionFlag = metaKeywordsDescriptions.containsKey("description");
    titleFlag = metaKeywordsDescriptions.containsKey("title");
    viewportFlag = metaKeywordsDescriptions.containsKey("viewport");
    templateFlag = metaKeywordsDescriptions.containsKey("template");
  }

  private void collectOgTags() {
    Map<String, ValueMap> stringValueMapLinkedHashMap = getPropertyMap("ogTags");
    stringValueMapLinkedHashMap.forEach((String key, ValueMap valuemap) -> ogTags
        .put(valuemap.get("ogProperty", String.class), valuemap.get("ogContent", String.class))

    );
  }

  private void setBooleanProperties() {

    for (Map.Entry<String, String> map : ogTags.entrySet()) {
      if (map.getKey().equals("og:description")) {
        isHasOgDescription = true;
      }
      if (map.getKey().equals("og:title")) {
        isHasOgTitle = true;
      }
      if (map.getKey().equals("og:image")) {
        isHasOgImage = true;
      }

    }
    LOGGER.debug("Post Map Validation description is {}", isHasOgDescription);
    LOGGER.debug("Post Map Validation Title is {}", isHasOgTitle);
    LOGGER.debug("Post Map Validation Image path is {}", isHasOgImage);
  }

  private void collectRobotTags() {
    Map<String, ValueMap> stringValueMapLinkedHashMap = getPropertyMap("robotTags");
    stringValueMapLinkedHashMap.forEach((String key, ValueMap valuemap) -> robotTags
        .add(valuemap.get("robotContent", String.class)));
  }

  private void setThemesproperties() {
    LOGGER.info("Start of setThemesproperties");
    InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(resource);
    categoryName = inheritanceValueMap.getInherited("themeCategory", String.class);
    if (null == categoryName) {
      categoryName = "";
    }
    LOGGER.debug("Category name  is {}", categoryName);
    cssPath = inheritanceValueMap.getInherited("dynamicCss", String.class);
    LOGGER.debug("Css path is {}", cssPath);
    LOGGER.info("End of setThemesproperties");
  }

  private void setDefaultImagePathForOg() {
    LOGGER.info("Start of setDefaultImagePathForOg");
    LOGGER.debug("Current resource is {}", resource.getPath());
    Resource articlePageComponent = resource.getResourceResolver()
        .getResource(resource.getPath() + "/root/articlebannercompone");
    if (null != articlePageComponent) {
      LOGGER.debug("Article Page Component is not null");
      imagePath = articlePageComponent.getValueMap().get("articleGridImagePath", String.class);
      LOGGER.debug("Image Path to be shared is {}", imagePath);

    }
    LOGGER.info("End of setDefaultImagePathForOg Method");
  }

  public void setPropertyReaderUtils(PropertyReaderUtils propertyReaderUtils) {
    this.propertyReaderUtils = propertyReaderUtils;
  }

  private Map<String, ValueMap> getPropertyMap(String tagPath) {
    Map<String, ValueMap> stringValueMapLinkedHashMap = new HashMap<>();
    try {
      ResourceResolver resolver = resource.getResourceResolver();
      Resource metaKeywordsNodeResource = resolver.getResource(resource.getPath() + "/" + tagPath);
      Node metaKeywordsNode = null;
      if (metaKeywordsNodeResource != null) {
        metaKeywordsNode = metaKeywordsNodeResource.adaptTo(Node.class);
        if (null != metaKeywordsNode) {
          LOGGER.debug("metaKeywords path node is {}", metaKeywordsNode.getPath());
          stringValueMapLinkedHashMap = multifieldReader.propertyReader(metaKeywordsNode);
        }

        LOGGER.info("Start of PageModel end");
      }
    } catch (RepositoryException e) {
      LOGGER.error("Exception from PlayPageModel page Model", e);
    }
    return stringValueMapLinkedHashMap;
  }

  public String getCanonicalTag() { return this.canonicalTag; }



  public Map<String, String> getMetaKeywordsDescription() {
    return metaKeywordsDescription;
  }

  public Map<String, String> getOgTags() {
    return ogTags;
  }

  public List<String> getRobotTags() {
    return robotTags;
  }

  public boolean isHasOgImage() {
    return isHasOgImage;
  }

  public boolean isHasOgDescription() {
    return isHasOgDescription;
  }

  public boolean isHasOgTitle() {
    return isHasOgTitle;
  }

  public String getImagePath() {
    return imagePath;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getCssPath() {
    return cssPath;
  }

  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  public void setOgTags(Map<String, String> ogTags) {
    this.ogTags = ogTags;
  }

  public boolean isKeywordsFlag() {
    return keywordsFlag;
  }

  public void setKeywordsFlag(boolean keywordsFlag) {
    this.keywordsFlag = keywordsFlag;
  }

  public boolean isDescriptionFlag() {
    return descriptionFlag;
  }

  public void setDescriptionFlag(boolean descriptionFlag) {
    this.descriptionFlag = descriptionFlag;
  }

  public boolean isTitleFlag() {
    return titleFlag;
  }

  public void setTitleFlag(boolean titleFlag) {
    this.titleFlag = titleFlag;
  }

  public boolean isTemplateFlag() {
    return templateFlag;
  }

  public void setTemplateFlag(boolean templateFlag) {
    this.templateFlag = templateFlag;
  }

  public boolean isViewportFlag() {
    return viewportFlag;
  }

  public void setViewportFlag(boolean viewportFlag) {
    this.viewportFlag = viewportFlag;
  }

  public String getTargetUrl() {
    return this.targetUrl;
  }

}
