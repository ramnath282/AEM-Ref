package com.mattel.global.core.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.mattel.global.core.pojo.CategoryColumnPojo;
import com.mattel.global.core.pojo.GlobalNavigationPojo;
import com.mattel.global.core.pojo.PromoImagePojo;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.master.core.constants.Constants;

@Component(service = NavigationUtil.class, immediate = true)
@Designate(ocd = NavigationUtil.Config.class)
public class NavigationUtil {
  private static final Logger LOGGER = LoggerFactory.getLogger(NavigationUtil.class);
  private static final String SINGLE_AWNING_IMAGE = "singleAwningImage";
  public static final String LAST_MODIFIED_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
  
  Boolean defaultBoolean = false;
  String desktop = "desktop";
  String splLink = "spl-link";

  @Reference
  private MultifieldReader multifieldReader;

  /**
   * 
   * @param page
   * @return
   */
  public SiteNavigationPojo fetchPageProperties(Page page) {
    LOGGER.info("Navigation.fetchPageProperties -> start");
    SiteNavigationPojo pagePojo = new SiteNavigationPojo();
    String pageTitle = page.getTitle();
    String navigationTitle = page.getNavigationTitle();
    checkTitle(pageTitle, navigationTitle, pagePojo);
    String pageUrl = page.getPath();
    String redirectUrl = GlobalUtils
        .checkPropertyObject(page.getProperties().get("cq:redirectTarget"));
    String redirectTarget = GlobalUtils
        .checkPropertyObject(page.getProperties().get("cq:redirectTargetOption"));
    Boolean isLinkable = GlobalUtils.checkBooleanProperty(page, "cq:isNotLinkable", defaultBoolean);
    pagePojo.setNotLinkable(isLinkable);
    Boolean isSpecialLink = GlobalUtils.checkBooleanProperty(page, "cq:isSpecialLink",
        defaultBoolean);
    pagePojo.setSpecialLink(isSpecialLink ? splLink : "");
    checkUrl(pageUrl, redirectUrl, pagePojo, page.getContentResource(), redirectTarget);
    if (StringUtils
        .isNotEmpty(GlobalUtils.checkPropertyObject(page.getProperties().get("navThumbImage")))) {
      pagePojo.setThumbnailImg(
          GlobalUtils.checkPropertyObject(page.getProperties().get("navThumbImage")));
      pagePojo.setThumbnailAltText(
          GlobalUtils.checkPropertyObject(page.getProperties().get("navThumbAlt")));
    }
    Object adobeTrackingNameForPage = page.getProperties().get("adobeTrackingNameForPage");
    pagePojo.setAdobeTrackingNameForPage(GlobalUtils.checkPropertyObject(adobeTrackingNameForPage));
   
    String lastModifiedDateStr = page.getProperties().get("cq:lastModified", String.class);
    Date lastModifiedDate = GlobalUtils.getParsedDate(NavigationUtil.LAST_MODIFIED_DATE, lastModifiedDateStr);
    pagePojo.setLastModifiedDate(lastModifiedDate);
    LOGGER.info("Navigation.fetchPageProperties -> end");
    return pagePojo;
  }

  /**
   * 
   * @param pageUrl
   * @param redirectUrl
   * @param pagePojo
   * @param resource
   * @param redirectTarget
   */
  private void checkUrl(String pageUrl, String redirectUrl, SiteNavigationPojo pagePojo,
      Resource resource, String redirectTarget) {
    if (!redirectUrl.isEmpty()) {
      pagePojo.setIsRedirect(true);
      pagePojo.setUrlTarget(redirectTarget);
      pagePojo.setPageUrl(GlobalUtils.checkLink(redirectUrl, resource));
    } else {
      pagePojo.setPageUrl(GlobalUtils.checkLink(pageUrl, resource));
    }
  }

  /**
   * 
   * @param pageTitle
   * @param navigationTitle
   * @param pagePojo
   */
  private void checkTitle(String pageTitle, String navigationTitle, SiteNavigationPojo pagePojo) {
    if (!StringUtils.isEmpty(navigationTitle)) {
      pagePojo.setPageName(navigationTitle);
    } else {
      pagePojo.setPageName(pageTitle);
    }
  }

  /**
   * 
   * @param viewAllPojo
   * @param viewAllText
   * @param viewAllLink
   * @param linkTargetForViewAll
   * @param aeForViewAll
   * @param resource
   * @return
   */
  public SiteNavigationPojo setViewAllDetails(SiteNavigationPojo viewAllPojo, String viewAllText,
      String viewAllLink, String linkTargetForViewAll, String aeForViewAll, Resource resource) {
    if (viewAllLink != null) {
      viewAllPojo.setPageName(viewAllText);
      viewAllPojo.setPageUrl(GlobalUtils.checkLink(viewAllLink, resource));
      viewAllPojo.setUrlTarget(linkTargetForViewAll);
      viewAllPojo.setAdobeTrackingNameForPage(aeForViewAll);
      viewAllPojo.setSpecialLink(splLink);
    }
    return viewAllPojo;

  }

  /**
   * 
   * @param childNode
   * @return
   * @throws RepositoryException
   */
  public PromoImagePojo getImageProperties(Node childNode, Resource childResource)
      throws RepositoryException {
    PromoImagePojo promoImagePojo = new PromoImagePojo();
    ValueMap nodeValues = childResource.adaptTo(ValueMap.class);
    if (childNode.hasProperty("fileReference") && null != nodeValues) {
      promoImagePojo.setPromoImagePath(GlobalUtils.checkForProperty(childNode, "fileReference"));
      LOGGER.debug(" Navigation Util Promo Image path  ::{}", promoImagePojo.getPromoImagePath());
      promoImagePojo.setPromoImageAltText(GlobalUtils.checkForProperty(childNode, "promoAltText"));
      promoImagePojo.setPromoHeader(GlobalUtils.checkForProperty(childNode, "promoTitle"));
      promoImagePojo.setAwningImageOption(GlobalUtils.checkForProperty(childNode, "awningImageOption"));
      String awningImageOption = promoImagePojo.getAwningImageOption();

      if(Objects.nonNull(awningImageOption) && StringUtils.isNotBlank(awningImageOption)){
            LOGGER.debug("AwningImageOption {}", awningImageOption);

            if(SINGLE_AWNING_IMAGE.equalsIgnoreCase(awningImageOption)
                    && childNode.hasProperty("promoHoverImagePath")) {
                promoImagePojo.setPromoHoverImagePath(GlobalUtils.checkForProperty(childNode, "promoHoverImagePath"));
                LOGGER.debug(" Navigation Util Awing Image path ::{}", promoImagePojo.getPromoHoverImagePath());
            } else if (childNode.hasProperty("promoHoverTopImagePath") &&
                   childNode.hasProperty("promoHoverMiddleImagePath") &&
                   childNode.hasProperty("promoHoverBottomImagePath")) {
                promoImagePojo.setPromoHoverTopImagePath(GlobalUtils.checkForProperty(childNode, "promoHoverTopImagePath"));
                promoImagePojo.setPromoHoverMiddleImagePath(GlobalUtils.checkForProperty(childNode, "promoHoverMiddleImagePath"));
                promoImagePojo.setPromoHoverBottomImagePath(GlobalUtils.checkForProperty(childNode, "promoHoverBottomImagePath"));
                LOGGER.debug(" Navigation Util :: Top Awing Images for explore::{}", promoImagePojo.getPromoHoverTopImagePath());
                LOGGER.debug(" Navigation Util :: Middle Awing Images for explore::{}", promoImagePojo.getPromoHoverMiddleImagePath());
                LOGGER.debug(" Navigation Util ::Bottom Awing Images for explore::{}", promoImagePojo.getPromoHoverBottomImagePath());
            }
      }
      Boolean checkBoxLink = nodeValues.containsKey("checkBoxLink")
          ? nodeValues.get("checkBoxLink", Boolean.class) : defaultBoolean;
      if (checkBoxLink) {
        promoImagePojo.setCheckBoxLink(checkBoxLink);
      } else {
        promoImagePojo.setCheckBoxLink(checkBoxLink);
        promoImagePojo
            .setTitleColourType(GlobalUtils.checkForProperty(childNode, "titleColourType"));
      }
      promoImagePojo
          .setPromoDescription(GlobalUtils.checkForProperty(childNode, "promoDescription"));
      promoImagePojo.setImageCrop(GlobalUtils.checkForProperty(childNode, "imageCrop"));
      promoImagePojo.setImageRotate(GlobalUtils.checkForProperty(childNode, "imageRotate"));
      promoImagePojo.setAlwaysEnglish(GlobalUtils.checkForProperty(childNode, "alwaysEnglish"));
      if (childNode.hasProperty("promoButtonLink")) {
        String promoButtonLink = GlobalUtils.checkForProperty(childNode, "promoButtonLink");
        promoImagePojo.setPromoUrl(GlobalUtils.checkLink(promoButtonLink, childResource));
        promoImagePojo.setPromoTarget(GlobalUtils.checkForProperty(childNode, "promoTargetURL"));
        promoImagePojo.setPromoCTAText(GlobalUtils.checkForProperty(childNode, "promoButton"));
      }
      if (childNode.hasNode("cq:responsive/default")) {
        Node defaultNode = childNode.getNode("cq:responsive/default");
        if (defaultNode.hasProperty("width")) {
          promoImagePojo.setGridNum(GlobalUtils.checkForProperty(defaultNode, "width"));
        }
      } else {
        promoImagePojo.setGridNum("12");
      }
    }

    return promoImagePojo;

  }

  /**
   * 
   * @param resource
   * @param childIterator
   * @param imageSectionList
   * @param limit
   * @throws RepositoryException
   */
  public void checkForImages(Resource resource, Iterator<Resource> childIterator,
      List<PromoImagePojo> imageSectionList, int limit) throws RepositoryException {
    boolean getImage = false;
    int count = 0;
    while (childIterator.hasNext() && count < limit) {
      Resource childResource = childIterator.next();
      Node childNode = childResource.adaptTo(Node.class);
      if (childNode != null) {
        String childPath = childNode.getPath();
        if (childPath.equals(resource.getPath())) {
          getImage = true;
          continue;
        }
        if (getImage && childResource.getResourceType().contains(Constants.IMAGE_RESOURCE_TYPE)) {
          LOGGER.info("Getting Image");
          fetchImages(childNode, childResource, imageSectionList);
          count++;
        } else {
          getImage = false;
        }
      }
    }
  }

  /**
   * 
   * @param childNode
   * @param childResource
   * @param imageSectionList
   * @throws RepositoryException
   */
  private void fetchImages(Node childNode, Resource childResource,
      List<PromoImagePojo> imageSectionList) throws RepositoryException {
    PromoImagePojo imagePojo = getImageProperties(childNode, childResource);
    if (StringUtils.isNotEmpty(imagePojo.getPromoImagePath())) {
      imageSectionList.add(imagePojo);
    }
  }

  /**
   * 
   * @param resource
   * @return
   * @throws RepositoryException
   */
  public String getShopByValue(Resource resource) throws RepositoryException {
    Resource parentResource = resource.getParent();
    if (null != parentResource) {
      Resource headerResource = parentResource.getParent();
      if (null != headerResource) {
        Node headerNode = headerResource.adaptTo(Node.class);
        if (headerNode != null
            && headerResource.getResourceType().contains(Constants.SITEHEADER_RESOURCE_TYPE)) {
          LOGGER.info("Fetching Value");
          return GlobalUtils.checkForProperty(headerNode, "shopByValue");
        }
      }
    }
    return "";
  }

  /**
   * 
   * @param columnPojo
   * @param subCatLink
   * @param subCatTitle
   * @param resolver
   * @return
   */
  public SiteNavigationPojo fetchCategoryPageTitle(SiteNavigationPojo columnPojo, String subCatLink,
      String subCatTitle, ResourceResolver resolver) {
    Resource categoryResource = resolver.getResource(subCatLink);
    if (null != categoryResource) {
      Page categoryPage = categoryResource.adaptTo(Page.class);
      if (null != categoryPage) {
        String pageTitle = categoryPage.getTitle();
        String navigationTitle = categoryPage.getNavigationTitle();
        if (StringUtils.isBlank(subCatTitle)) {
          columnPojo.setPageName(navigationTitle != null ? navigationTitle : pageTitle);
        }
        Boolean isLinkable = GlobalUtils.checkBooleanProperty(categoryPage, "cq:isNotLinkable",
            defaultBoolean);
        columnPojo.setNotLinkable(isLinkable);
        Boolean isSpecialLink = GlobalUtils.checkBooleanProperty(categoryPage, "cq:isSpecialLink",
            defaultBoolean);
        columnPojo.setSpecialLink(isSpecialLink ? "splLink" : " ");
      }
    }
    return columnPojo;

  }

  /**
   * 
   * @param pagePath
   * @param pagePojo
   * @param resolver
   * @return
   */
  public SiteNavigationPojo getPageDetails(String pagePath, SiteNavigationPojo pagePojo,
      ResourceResolver resolver) {
    Resource featurePageResource = resolver.getResource(pagePath);
    if (null != featurePageResource) {
      Page currentPage = featurePageResource.adaptTo(Page.class);
      if (null != currentPage) {
        pagePojo = fetchPageProperties(currentPage);
      }
    }
    return pagePojo;
  }

  /**
   * 
   * @param nodeValues
   * @param column
   * @return
   */
  public CategoryColumnPojo fetchColumnDetails(ValueMap nodeValues, String column) {
    CategoryColumnPojo columnPojo = new CategoryColumnPojo();
    columnPojo.setSubCatTitle(nodeValues.get("sCategory" + column, String.class));
    columnPojo.setSubCatLink(nodeValues.get("sCategoryLink" + column, String.class));
    columnPojo.setSubCatUrlTarget(nodeValues.get("sCategoryUrlTarget" + column, String.class));
    columnPojo.setSubCatAeText(nodeValues.get("aeForScategory" + column, String.class));
    columnPojo.setParentPage(nodeValues.get("parentPage" + column, String.class));
    columnPojo.setColumnListFrom(nodeValues.get("column" + column + "ListFrom", String.class));
    columnPojo.setViewAllText(nodeValues.get("viewAll" + column, String.class));
    columnPojo.setViewAllLink(nodeValues.get("viewAllLink" + column, String.class));
    columnPojo
        .setLinkTargetForViewAll(nodeValues.get("linkTargetForViewAll" + column, String.class));
    columnPojo.setAeForViewAll(nodeValues.get("aeForViewAll" + column, String.class));
    columnPojo.setParentPageList(nodeValues.get("column" + column + "Pages", String[].class));
    return columnPojo;

  }

  /**
   * 
   * @param viewAllPojo
   * @param columnDetailsPojo
   * @param resource
   * @return
   */
  public SiteNavigationPojo setViewAllDetails(SiteNavigationPojo viewAllPojo,
      CategoryColumnPojo columnDetailsPojo, Resource resource) {
    String viewAllLink = columnDetailsPojo.getViewAllLink();
    if (viewAllLink != null) {
      viewAllPojo.setPageName(columnDetailsPojo.getViewAllText());
      viewAllPojo.setPageUrl(GlobalUtils.checkLink(viewAllLink, resource));
      viewAllPojo.setUrlTarget(columnDetailsPojo.getLinkTargetForViewAll());
      viewAllPojo.setAdobeTrackingNameForPage(columnDetailsPojo.getAeForViewAll());
      viewAllPojo.setSpecialLink(splLink);
    }
    return viewAllPojo;
  }

  /**
   * 
   * @param columnDetails
   * @param parentLinksList
   * @param resource
   * @param device
   * @return
   */
  public List<SiteNavigationPojo> fetchColumnLinks(CategoryColumnPojo columnDetails,
      List<SiteNavigationPojo> parentLinksList, Resource resource, String device) {
        LOGGER.info("NavigationUtil.fetchColumnLinks() --> start");
    SiteNavigationPojo columnPojo = checkLinkOptions(columnDetails, device, resource);
    String subCatTitle = columnDetails.getSubCatTitle();
    String subCatLink = columnDetails.getSubCatLink();
    String subCatUrlTarget = columnDetails.getSubCatUrlTarget();
    String subCatAeText = columnDetails.getSubCatAeText();
    columnPojo.setPageName(StringUtils.isNotBlank(subCatTitle) ? subCatTitle : "Title Here");
    if (subCatLink != null) {
      columnPojo.setPageUrl(GlobalUtils.checkLink(subCatLink, resource));
      if (subCatTitle == null) {
        fetchCategoryPageTitle(columnPojo, subCatLink, subCatTitle, resource.getResourceResolver());
      }
      columnPojo.setUrlTarget(subCatUrlTarget);
      columnPojo.setAdobeTrackingNameForPage(subCatAeText);
    }
    if (subCatTitle != null || subCatLink != null) {
      parentLinksList.add(columnPojo);
    }
        LOGGER.info("NavigationUtil.fetchColumnLinks() --> end");
    return parentLinksList;
  }

  /**
   * 
   * @param columnDetailsPojo
   * @param device
   * @param resource
   * @return
   */
  private SiteNavigationPojo checkLinkOptions(CategoryColumnPojo columnDetailsPojo, String device,
      Resource resource) {
    SiteNavigationPojo columnPojo = new SiteNavigationPojo();
    String columnListFrom = columnDetailsPojo.getColumnListFrom();
    if (columnListFrom != null) {
      if (columnListFrom.equals("children")) {
        columnPojo = fetchChildLinks(columnDetailsPojo, device, resource);
      } else if (columnListFrom.equals("static")) {
        columnPojo = fetchFixedList(columnDetailsPojo, device, resource);
      }
    }
    return columnPojo;

  }

  /**
   * 
   * @param columnDetailsPojo
   * @param device
   * @param resource
   * @return
   */
  private SiteNavigationPojo fetchChildLinks(CategoryColumnPojo columnDetailsPojo, String device,
      Resource resource) {
    SiteNavigationPojo parentPojo = new SiteNavigationPojo();
    String path = columnDetailsPojo.getParentPage();
    ResourceResolver resolver = resource.getResourceResolver();
    if (!StringUtils.isEmpty(path)) {
      Resource parentPageResource = resolver.getResource(path);
      if (null != parentPageResource) {
        Page currentPage = parentPageResource.adaptTo(Page.class);
        if (null != currentPage) {
          fetchChildPageDetails(currentPage, columnDetailsPojo, parentPojo, device, resource);
        }
      }
    }
    return parentPojo;

  }

  /**
   * 
   * @param columnDetailsPojo
   * @param device
   * @param resource
   * @return
   */
  private SiteNavigationPojo fetchFixedList(CategoryColumnPojo columnDetailsPojo, String device,
      Resource resource) {
    SiteNavigationPojo columnPojo = new SiteNavigationPojo();
    List<SiteNavigationPojo> childPageList = new ArrayList<>();
    if (device.equals("mobile")) {
      handleViewAll(childPageList, resource, columnDetailsPojo);
    }
    String[] parentPageList = columnDetailsPojo.getParentPageList();
    ResourceResolver resolver = resource.getResourceResolver();
    if (!GlobalUtils.isNullOrEmpty(parentPageList)) {
      for (String navLinks : parentPageList) {
        Resource childResource = resolver.getResource(navLinks);
        if (null != childResource) {
          Page currentPage = childResource.adaptTo(Page.class);
          if (null != currentPage) {
            SiteNavigationPojo siteNavigationPojo = fetchPageProperties(currentPage);
            childPageList.add(siteNavigationPojo);

          }
        }
      }
    }
    if (device.equals(desktop)) {
      handleViewAll(childPageList, resource, columnDetailsPojo);
    }
    if (!childPageList.isEmpty()) {
      columnPojo.setChildPageList(childPageList);
    }
    return columnPojo;

  }

  /**
   * 
   * @param currentPage
   * @param columnDetailsPojo
   * @param parentPojo
   * @param device
   * @param resource
   */
  private void fetchChildPageDetails(Page currentPage, CategoryColumnPojo columnDetailsPojo,
      SiteNavigationPojo parentPojo, String device, Resource resource) {
    Iterator<Page> rootPageIterator = currentPage.listChildren(new PageFilter(), false);
    List<SiteNavigationPojo> childPageList = new ArrayList<>();
    if (device.equals("mobile")) {
      handleViewAll(childPageList, resource, columnDetailsPojo);
    }
    while (rootPageIterator.hasNext()) {
      Page childPage = rootPageIterator.next();
      SiteNavigationPojo childPojo = fetchPageProperties(childPage);
      childPageList.add(childPojo);
    }
    if (device.equals(desktop)) {
      handleViewAll(childPageList, resource, columnDetailsPojo);
    }

    if (!childPageList.isEmpty()) {
      parentPojo.setChildPageList(childPageList);
    }
  }

  /**
   * 
   * @param childPageList
   * @param resource
   * @param columnDetailsPojo
   */
  private void handleViewAll(List<SiteNavigationPojo> childPageList, Resource resource,
      CategoryColumnPojo columnDetailsPojo) {
    SiteNavigationPojo viewAllPojo = new SiteNavigationPojo();
    viewAllPojo = setViewAllDetails(viewAllPojo, columnDetailsPojo, resource);

    childPageList.add(viewAllPojo);

  }

  /**
   * 
   * @param categoryLinksPojo
   * @param resource
   * @param displayShopByValue
   * @param categoryTitleLink
   * @param categoryTitle
   * @param linkTargetCategory
   * @param device
   * @return
   * @throws RepositoryException
   */
  public SiteNavigationPojo setCategoryDetails(SiteNavigationPojo categoryLinksPojo,
      Resource resource, Boolean displayShopByValue, String categoryTitleLink, String categoryTitle,
      String linkTargetCategory, String device) throws RepositoryException {
    LOGGER.info("setCategoryDetails Method Start");
    String headerShopByValue = StringUtils.EMPTY;
    if (displayShopByValue) {
      headerShopByValue = getShopByValue(resource);
    }
    if (StringUtils.isNotBlank(categoryTitleLink) && categoryTitleLink.startsWith("/content")) {
      categoryLinksPojo = fetchCategoryPageDetails(categoryTitleLink, categoryLinksPojo,
          resource.getResourceResolver());
      if (StringUtils.isNotBlank(headerShopByValue) && device.equals(desktop)) {
        String temp = categoryLinksPojo.getPageName();
        categoryLinksPojo.setPageName(headerShopByValue + " " + temp);
        LOGGER.debug("Current Page Name :  {}", temp);
      }
    } else {
      if (StringUtils.isNotBlank(headerShopByValue) && device.equals(desktop)) {
        categoryLinksPojo.setPageName(headerShopByValue + " " + categoryTitle);
      } else {
        categoryLinksPojo.setPageName(categoryTitle);
      }
      if (StringUtils.isNotBlank(categoryTitleLink)) {
        categoryLinksPojo.setPageUrl(GlobalUtils.checkLink(categoryTitleLink, resource));
        categoryLinksPojo.setUrlTarget(linkTargetCategory);
      } else {
        categoryLinksPojo.setPageUrl("#");
      }
    }
    LOGGER.info("setCategoryDetails Method End");
    return categoryLinksPojo;
  }
  
  public SiteNavigationPojo setCategoryDetailsForPage(SiteNavigationPojo categoryLinksPojo,
	      Resource resource, GlobalNavigationPojo cnv) throws RepositoryException {
	    LOGGER.info("setCategoryDetails Method Start");
	    String headerShopByValue = StringUtils.EMPTY;
	    if (cnv.isDisplayShopByValue()) {
	      headerShopByValue = getShopByValue(resource);
	    }
	    if (StringUtils.isNotBlank(cnv.getCategoryTitleLink()) && cnv.getCategoryTitleLink().startsWith("/content")) {
	      categoryLinksPojo = fetchCategoryPageDetails(cnv.getCategoryTitleLink(), categoryLinksPojo,
	          resource.getResourceResolver());
	      if (StringUtils.isNotBlank(headerShopByValue) && desktop.equals(cnv.getDevice())) {
	        String temp = categoryLinksPojo.getPageName();
	        categoryLinksPojo.setPageName(headerShopByValue + " " + temp);
	        LOGGER.debug("Current Page Name :  {}", temp);
	      }
	    } else {
	      if (StringUtils.isNotBlank(headerShopByValue) && desktop.equals(cnv.getDevice())) {
	        categoryLinksPojo.setPageName(headerShopByValue + " " + cnv.getCategoryTitle());
	      } else {
	        categoryLinksPojo.setPageName(cnv.getCategoryTitle());
	      }
	      if (StringUtils.isNotBlank(cnv.getCategoryTitleLink())) {
	        categoryLinksPojo.setPageUrl(GlobalUtils.checkLink(cnv.getCategoryTitleLink(), resource));
	        categoryLinksPojo.setUrlTarget(cnv.getLinkTargetCategory());
	      } else {
	        categoryLinksPojo.setPageUrl("#");
	      }
	    }
	    LOGGER.info("setCategoryDetails Method End");
	    return categoryLinksPojo;
	  }

  /**
   * 
   * @param path
   * @param categoryLinksPojo
   * @param resolver
   * @return
   */
  public SiteNavigationPojo fetchCategoryPageDetails(String path,
      SiteNavigationPojo categoryLinksPojo, ResourceResolver resolver) {

    Resource categoryResource = resolver.getResource(path);
    if (null != categoryResource) {
      Page categoryPage = categoryResource.adaptTo(Page.class);
      if (null != categoryPage) {
        categoryLinksPojo = fetchPageProperties(categoryPage);
      }
    }
    return categoryLinksPojo;

  }

  /**
   * 
   * @param resource
   * @param featuredLinksList
   * @return
   */
  public List<SiteNavigationPojo> checkFeaturedNode(Resource resource,
      List<SiteNavigationPojo> featuredLinksList) {
    LOGGER.info("checkFeaturedNode() start");
    Resource featuredResource = resource.getChild("featuredSection");
    if (featuredResource != null) {
      Node featuredNode = featuredResource.adaptTo(Node.class);
      Map<String, ValueMap> multifieldProperty;
      multifieldProperty = multifieldReader.propertyReader(featuredNode);
      if (multifieldProperty != null) {
        for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
          Optional.ofNullable(entry.getValue()).ifPresent(valueMap -> {
            String pagePath = GlobalUtils.getValueMapNodeValue(valueMap, "featuredLinks");
            boolean confettiEffectOnLink = GlobalUtils.getValueMapNodeValue(valueMap, "confettiEffect", false);
            SiteNavigationPojo featurePagePojo = new SiteNavigationPojo();

            LOGGER.debug("checkFeaturedNode(), pagePath: {},  confettiEffectOnLink: {} ", pagePath, confettiEffectOnLink);
            featurePagePojo = getFeaturePageDetails(pagePath, featurePagePojo,
                resource.getResourceResolver());
            featurePagePojo.setConfettiEffect(confettiEffectOnLink);
            LOGGER.debug("checkFeaturedNode() featurePagePojo {} ", featurePagePojo);
            featuredLinksList.add(featurePagePojo);
          });
        }
      }
    }
        LOGGER.info("checkFeaturedNode() end");
    return featuredLinksList;
  }

  /**
   * 
   * @param pagePath
   * @param featurePagePojo
   * @param resolver
   * @return
   */
  private SiteNavigationPojo getFeaturePageDetails(String pagePath,
      SiteNavigationPojo featurePagePojo, ResourceResolver resolver) {
    LOGGER.info("getFeaturePageDetails() --> start");
    Resource featurePageResource = resolver.getResource(pagePath);
    if (null != featurePageResource) {
      Page currentPage = featurePageResource.adaptTo(Page.class);
      if (null != currentPage) {
        featurePagePojo = fetchPageProperties(currentPage);
      }
    }
    LOGGER.debug("getFeaturePageDetails featurePagePojo {}",featurePagePojo);
    LOGGER.info("getFeaturePageDetails() --> end");
    return featurePagePojo;
  }

  /**
   * 
   * @param resource
   * @param imageSectionList
   * @return
   * @throws RepositoryException
   */
  public List<PromoImagePojo> getImageSection(Resource resource,
      List<PromoImagePojo> imageSectionList, int imageLimit) throws RepositoryException {
    Resource parentResource = resource.getParent();
    if (parentResource != null) {
      Iterator<Resource> childIterator = parentResource.listChildren();
      checkForImages(resource, childIterator, imageSectionList, imageLimit);
    }
    return imageSectionList;
  }

  /**
   * @param primaryNavPojo
   * @param templateType
   * @param aeForPrimaryNavTitle
   */
  public SiteNavigationPojo getTemplateVariationType(SiteNavigationPojo primaryNavPojo,
      String templateType, String aeForPrimaryNavTitle, boolean displayShopByValue, String device) {
    LOGGER.info("Method getTemplateVariationType Start");
    primaryNavPojo.setAdobeTrackingNameForPage(aeForPrimaryNavTitle);
    if (templateType.equals(Constants.FISHER_PRICE_BRAND_TEMPLATE)) {
      primaryNavPojo.setSubLinkClass("shop-by-brands");
      primaryNavPojo.setShopAllClass("shopAll");
    } else {
      primaryNavPojo.setSubLinkClass(Constants.EXPLORE);
      if (displayShopByValue
          && StringUtils.equals(device, Constants.FISHER_PRICE_DEVICETYPE_MOBILE)) {
        primaryNavPojo.setSubLinkClass(Constants.SHOP_BY_AGE);
      }
    }
    LOGGER.debug("SublinkClass :: {} ", primaryNavPojo.getSubLinkClass());
    LOGGER.info("Method getTemplateVariationType End");
    return primaryNavPojo;
  }
  
  public SiteNavigationPojo getTemplateVariationType(SiteNavigationPojo primaryNavPojo,
		  GlobalNavigationPojo cnv) {
	    LOGGER.info("Method getTemplateVariationType Start");
	    primaryNavPojo.setAdobeTrackingNameForPage(cnv.getAeForPrimaryNavTitle());
	    if (Constants.FISHER_PRICE_BRAND_TEMPLATE.equals(cnv.getTemplateType())) {
	      primaryNavPojo.setSubLinkClass("shop-by-brands");
	      primaryNavPojo.setShopAllClass("shopAll");
	    } else {
	      primaryNavPojo.setSubLinkClass(Constants.EXPLORE);
	      if (cnv.isDisplayShopByValue()
	          && StringUtils.equals(cnv.getDevice(), Constants.FISHER_PRICE_DEVICETYPE_MOBILE)) {
	        primaryNavPojo.setSubLinkClass(Constants.SHOP_BY_AGE);
	      }
	    }
	    LOGGER.debug("SublinkClass :: {} ", primaryNavPojo.getSubLinkClass());
	    LOGGER.info("Method getTemplateVariationType End");
	    return primaryNavPojo;
	  }  

  /**
   * @param resource
   * @param navigationalLinks
   * @param device
   * @param categoryTitle
   * @param categorySectionNavLinks
   * @param secondaryNavLinks
   * @param templateType
   * @return
   * @throws RepositoryException
   * @throws LoginException
   */
  public List<SiteNavigationPojo> getSecondaryCategoryTitle(Resource resource,
      String[] navigationalLinks, String device, String categoryTitle,
      List<SiteNavigationPojo> categorySectionNavLinks, List<SiteNavigationPojo> secondaryNavLinks,
      String templateType, String promoHoverImgPath) {

        LOGGER.info("start of getSecondaryCategoryTitle() Method");

    if (!GlobalUtils.isNullOrEmpty(navigationalLinks)) {
      categorySectionNavLinks = getSecondaryNavigationLinksList(resource, navigationalLinks,
            templateType, categorySectionNavLinks, device, promoHoverImgPath);
    }
        LOGGER.debug("getSecondaryCategoryTitle() categorySectionNavLinks {} ", categorySectionNavLinks);
    SiteNavigationPojo categoryLinksPojo = new SiteNavigationPojo();
    if (device.equals(Constants.FISHER_PRICE_DEVICETYPE_DESKTOP)) {
      categoryLinksPojo.setPageName(categoryTitle);
    } else if (device.equals(Constants.FISHER_PRICE_DEVICETYPE_MOBILE)) {
      categoryLinksPojo.setPageName("");
    }
    if (categorySectionNavLinks != null && !categorySectionNavLinks.isEmpty()) {
      categoryLinksPojo.setChildPageList(categorySectionNavLinks);
    }
    secondaryNavLinks.add(categoryLinksPojo);
    LOGGER.debug("GetSecondaryCategoryTitle {}", secondaryNavLinks);

    LOGGER.info("end of getSecondaryCategoryTitle() Method");
    return secondaryNavLinks;
  }
  
  public List<SiteNavigationPojo> getSecondaryCategoryTitle(Resource resource,
		  GlobalNavigationPojo cnv) {

	    if (!GlobalUtils.isNullOrEmpty(cnv.getNavigationalLinks())) {
	    cnv.setCategorySectionNavLinks(getSecondaryNavigationLinksList(resource, cnv.getNavigationalLinks(),
	          cnv.getTemplateType(), cnv.getCategorySectionNavLinks(), cnv.getDevice(),cnv.getPromoHoverImagePath()));
	    }
	    SiteNavigationPojo categoryLinksPojo = new SiteNavigationPojo();
	    if (Constants.FISHER_PRICE_DEVICETYPE_DESKTOP.equals(cnv.getDevice())) {
	      categoryLinksPojo.setPageName(cnv.getCategoryTitle());
	    } else if (Constants.FISHER_PRICE_DEVICETYPE_MOBILE.equals(cnv.getDevice())) {
	      categoryLinksPojo.setPageName("");
	    }
	    if (Objects.nonNull(cnv.getCategorySectionNavLinks()) && !cnv.getCategorySectionNavLinks().isEmpty()) {
	      categoryLinksPojo.setChildPageList(cnv.getCategorySectionNavLinks());
	    }
	    cnv.setSecondaryNavLinks(Optional.ofNullable(cnv.getSecondaryNavLinks()).orElse(new ArrayList<>()));
	    cnv.getSecondaryNavLinks().add(categoryLinksPojo);
	    
	    //secondaryNavLinks.add(categoryLinksPojo);

	    return cnv.getSecondaryNavLinks();
	  }

  /**
   * @param resource
   * @param navigationalLinks
   * @param templateType
   * @param categorySectionNavLinks
   * @param device
   * @throws RepositoryException
   * @throws LoginException
   */
  private List<SiteNavigationPojo> getSecondaryNavigationLinksList(Resource resource,
      String[] navigationalLinks, String templateType,
                                                                     List<SiteNavigationPojo> categorySectionNavLinks, String reqDeviceType, String promoImageHover) {
    ResourceResolver resolver = resource.getResourceResolver();
    if (templateType.equals(Constants.FISHER_PRICE_BRAND_TEMPLATE)) {
      setViewALLToPojo(resource, resource.getValueMap(), Constants.FISHER_PRICE_DEVICETYPE_MOBILE,
          categorySectionNavLinks, reqDeviceType);
      categorySectionNavLinks = getPageProperties(navigationalLinks, resolver,
                    navigationalLinks.length, categorySectionNavLinks, promoImageHover);
      setViewALLToPojo(resource, resource.getValueMap(), Constants.FISHER_PRICE_DEVICETYPE_DESKTOP,
          categorySectionNavLinks, reqDeviceType);
    } else if (templateType.equals(Constants.FISHER_PRICE_AGE_TEMPLATE)) {
      categorySectionNavLinks = getPageProperties(navigationalLinks, resolver, 2,
                    categorySectionNavLinks, promoImageHover);
    } else if (templateType.equals(Constants.FISHER_PRICE_EXPLORE_COLUMN_TWO)) {
      categorySectionNavLinks = getPageProperties(navigationalLinks, resolver, 4,
                    categorySectionNavLinks, promoImageHover);
    } else if (templateType.equals(Constants.FISHER_PRICE_EXPLORE_COLUMN_THREE)) {
      categorySectionNavLinks = getPageProperties(navigationalLinks, resolver, 6,
                    categorySectionNavLinks, promoImageHover);
    } else if (templateType.equals(Constants.FISHER_PRICE_EXPLORE_COLUMN_FOUR)) {
      categorySectionNavLinks = getPageProperties(navigationalLinks, resolver, 8,
                    categorySectionNavLinks, promoImageHover);
    }
    return categorySectionNavLinks;

  }

  /**
   * @param navigationalLinks
   * @param resourceResolver
   * @param categorySectionNavLinks
   * @param length
   * @param templateType
   * @throws RepositoryException
   * @throws LoginException
   */
  public List<SiteNavigationPojo> getPageProperties(String[] navigationalLinks,
      ResourceResolver resourceResolver, int limit,
                                                      List<SiteNavigationPojo> categorySectionNavLinks, String promoHoverImagePath) {

    LOGGER.info("start of getPageProperties() Method");
    int navLinksCount = 0;
    for (String navLinks : navigationalLinks) {
      Resource resource = resourceResolver.getResource(navLinks);
      if (null != resource) {
        Page currentPage = resource.adaptTo(Page.class);
        if (null != currentPage) {
          SiteNavigationPojo siteNavigationPojo = fetchPageProperties(currentPage);
          if (StringUtils.isNotEmpty(promoHoverImagePath)) {
               siteNavigationPojo.setPromoHoverImagePath(promoHoverImagePath);
          }
          categorySectionNavLinks.add(siteNavigationPojo);
          navLinksCount++;
        }
      }
      if (navLinksCount == limit) {
        break;
      }
    }
    return categorySectionNavLinks;
  }

  /**
   * @param resource
   * @param nodeValues
   * @param deviceType
   * @param categorySectionNavLinks
   * @param reqDeviceType
   */
  private void setViewALLToPojo(Resource resource, ValueMap nodeValues, String deviceType,
      List<SiteNavigationPojo> categorySectionNavLinks, String reqDeviceType) {
    String viewAllText = GlobalUtils.getValueMapNodeValue(nodeValues, "viewALL");
    String viewALLbuttonlink = GlobalUtils.getValueMapNodeValue(nodeValues, "viewALLbuttonlink");
    String viewALLtargetURL = GlobalUtils.getValueMapNodeValue(nodeValues, "viewALLtargetURL");
    String viewALLAlwaysText = GlobalUtils.getValueMapNodeValue(nodeValues, "viewALLAlwaysText");
    if (StringUtils.isNotEmpty(viewALLbuttonlink) && StringUtils.isNotEmpty(viewAllText)
        && reqDeviceType.equals(deviceType)) {
      SiteNavigationPojo viewAllPojo = new SiteNavigationPojo();
      categorySectionNavLinks.add(setViewAllDetails(viewAllPojo, viewAllText, viewALLbuttonlink,
          viewALLtargetURL, viewALLAlwaysText, resource));
    }
  }

  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

public SiteNavigationPojo setCategoryDetailsForContent(SiteNavigationPojo primaryNavPojo, Resource resource,
		GlobalNavigationPojo cnv) throws RepositoryException {
	LOGGER.info("setCategoryDetails Method Start");
    String headerShopByValue = StringUtils.EMPTY;
    if (cnv.isDisplayShopByValue()) {
      headerShopByValue = getShopByValue(resource);
    }
    if (StringUtils.isNotBlank(cnv.getPrimaryNavTitleLink()) && cnv.getPrimaryNavTitleLink().startsWith("/content")) {
    	primaryNavPojo = fetchCategoryPageDetails(cnv.getPrimaryNavTitleLink(), primaryNavPojo,
          resource.getResourceResolver());
      if (StringUtils.isNotBlank(headerShopByValue) && desktop.equals(cnv.getDevice())) {
        String temp = primaryNavPojo.getPageName();
        primaryNavPojo.setPageName(headerShopByValue + " " + temp);
        LOGGER.debug("Current Page Name :  {}", temp);
      }
    } else {
      if (StringUtils.isNotBlank(headerShopByValue) && desktop.equals(cnv.getDevice())) {
    	  primaryNavPojo.setPageName(headerShopByValue + " " + cnv.getPrimaryNavigationTitle());
      } else {
    	  primaryNavPojo.setPageName(cnv.getPrimaryNavigationTitle());
      }
      if (StringUtils.isNotBlank(cnv.getPrimaryNavTitleLink())) {
    	  primaryNavPojo.setPageUrl(GlobalUtils.checkLink(cnv.getPrimaryNavTitleLink(), resource));
    	  primaryNavPojo.setUrlTarget(cnv.getLinkTargetPrimaryNav());
      } else {
    	  primaryNavPojo.setPageUrl("#");
      }
    }
    LOGGER.info("setCategoryDetails Method End");
    return primaryNavPojo;
}
    @ObjectClassDefinition(name = "Child Page properties")
      public @interface Config {
         @AttributeDefinition(name = "Root path", description = "Please provide the rootpath of retail homepage. Default is /content/play/pollypocket")
         String rootPath() default "/content/play/pollypocket";
      }
}
