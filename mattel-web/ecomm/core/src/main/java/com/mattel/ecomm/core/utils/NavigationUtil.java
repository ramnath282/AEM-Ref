package com.mattel.ecomm.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.pojos.CategoryColumnPojo;
import com.mattel.ecomm.core.pojos.PromoImagePojo;
import com.mattel.ecomm.core.pojos.SiteNavigationPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Component(service = NavigationUtil.class, immediate = true)
@Designate(ocd = NavigationUtil.Config.class)
public class NavigationUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(NavigationUtil.class);
	Boolean defaultBoolean = false;
	String desktop = "desktop";
	String splLink="spl-link";
	
	@Reference
	private MultifieldReader multifieldReader;

	/**
	 * 
	 * @param page
	 * @return
	 */
	public SiteNavigationPojo fetchPageProperties(Page page) {
		LOGGER.info("Site Navgation Pojo Method Start");
		SiteNavigationPojo pagePojo = new SiteNavigationPojo();
		String pageTitle = EcommHelper.convertSpecialCharacters(page.getTitle());
		LOGGER.debug("pageTitle : {}",pageTitle);
		String navigationTitle = EcommHelper.convertSpecialCharacters(page.getNavigationTitle());
		LOGGER.debug("navigationTitle : {}",navigationTitle);
		checkTitle(pageTitle, navigationTitle, pagePojo);
		String pageUrl = page.getPath();
		String redirectUrl = EcommHelper.checkPropertyObject(page.getProperties().get("cq:redirectTarget"));
		String redirectTarget = EcommHelper.checkPropertyObject(page.getProperties().get("cq:redirectTargetOption"));
		Boolean isLinkable = EcommHelper.checkBooleanProperty(page, "cq:isNotLinkable", defaultBoolean);
		pagePojo.setNotLinkable(isLinkable);
		Boolean isSpecialLink = EcommHelper.checkBooleanProperty(page, "cq:isSpecialLink", defaultBoolean);
		pagePojo.setSpecialLink(isSpecialLink ? splLink : "");
		checkUrl(pageUrl, redirectUrl, pagePojo, page.getContentResource(), redirectTarget);
		if (StringUtils.isNotEmpty(EcommHelper.checkPropertyObject(page.getProperties().get("navThumbImage")))) {
			pagePojo.setThumbnailImg(EcommHelper.checkPropertyObject(page.getProperties().get("navThumbImage")));
			pagePojo.setThumbnailAltText(EcommHelper.checkPropertyObject(page.getProperties().get("navThumbAlt")));
		}
		Object adobeTrackingNameForPage = page.getProperties().get("adobeTrackingNameForPage");
		pagePojo.setAdobeTrackingNameForPage(EcommHelper.checkPropertyObject(adobeTrackingNameForPage));
		LOGGER.debug("Site Navgation Pojo Method End : {}",pagePojo);
		LOGGER.info("Site Navgation Pojo Method End");
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
	private void checkUrl(String pageUrl, String redirectUrl, SiteNavigationPojo pagePojo, Resource resource,
			String redirectTarget) {
		LOGGER.info("checkUrl Method Start");
		if (!redirectUrl.isEmpty()) {
			pagePojo.setIsRedirect(true);
			pagePojo.setUrlTarget(redirectTarget);
			pagePojo.setPageUrl(EcommHelper.checkLink(redirectUrl, resource));
		} else {
			pagePojo.setPageUrl(EcommHelper.checkLink(pageUrl, resource));
		}
		LOGGER.debug("Site Navgation Pojo Method End : {}",pagePojo);
		LOGGER.info("checkUrl Method End");
	}

	/**
	 * 
	 * @param pageTitle
	 * @param navigationTitle
	 * @param pagePojo
	 */
	private void checkTitle(String pageTitle, String navigationTitle, SiteNavigationPojo pagePojo) {
		LOGGER.info("checkTitle Method Start");
		if (!StringUtils.isEmpty(navigationTitle)) {
			pagePojo.setPageName(navigationTitle);
		} else {
			pagePojo.setPageName(pageTitle);
		}
		LOGGER.debug("Title : {}",pagePojo.getPageName());
		LOGGER.info("checkTitle Method End");
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
	public SiteNavigationPojo setViewAllDetails(SiteNavigationPojo viewAllPojo, String viewAllText, String viewAllLink,
			String linkTargetForViewAll, String aeForViewAll, Resource resource) {
		LOGGER.info("setViewAllDetails Method Start");
		if (viewAllLink != null) {
			viewAllPojo.setPageName(viewAllText);
			viewAllPojo.setPageUrl(EcommHelper.checkLink(viewAllLink, resource));
			viewAllPojo.setUrlTarget(linkTargetForViewAll);
			viewAllPojo.setAdobeTrackingNameForPage(aeForViewAll);
			viewAllPojo.setSpecialLink(splLink);
		}
		LOGGER.debug("Set View ALl Details : {}",viewAllPojo);
		LOGGER.info("setViewAllDetails Method End");
		return viewAllPojo;

	}

	/**
	 * 
	 * @param childNode
	 * @return
	 * @throws RepositoryException
	 */
	public PromoImagePojo getImageProperties(Node childNode, Resource childResource) throws RepositoryException {
		LOGGER.info("getImageProperties Method Start");
		PromoImagePojo promoImagePojo = new PromoImagePojo();
		ValueMap nodeValues = childResource.adaptTo(ValueMap.class);
		if (childNode.hasProperty("fileReference") && null != nodeValues) {
			promoImagePojo.setPromoImagePath(EcommHelper.checkForProperty(childNode, "fileReference"));
			promoImagePojo.setPromoImageAltText(EcommHelper.checkForProperty(childNode, "promoAltText"));
			promoImagePojo.setPromoHeader(EcommHelper.checkForProperty(childNode, "promoTitle"));
			Boolean checkBoxLink = nodeValues.containsKey("checkBoxLink")
					? nodeValues.get("checkBoxLink", Boolean.class)
					: defaultBoolean;
			if (checkBoxLink) {
				promoImagePojo.setCheckBoxLink(checkBoxLink);
			} else {
				promoImagePojo.setCheckBoxLink(checkBoxLink);
				promoImagePojo.setTitleColourType(EcommHelper.checkForProperty(childNode, "titleColourType"));
			}
			promoImagePojo.setPromoDescription(EcommHelper.checkForProperty(childNode, "promoDescription"));
			promoImagePojo.setImageCrop(EcommHelper.checkForProperty(childNode, "imageCrop"));
			promoImagePojo.setImageRotate(EcommHelper.checkForProperty(childNode, "imageRotate"));
			promoImagePojo.setAlwaysEnglish(EcommHelper.checkForProperty(childNode, "alwaysEnglish"));
			if (childNode.hasProperty("promoButtonLink")) {
				String promoButtonLink = EcommHelper.checkForProperty(childNode, "promoButtonLink");
				promoImagePojo.setPromoUrl(EcommHelper.checkLink(promoButtonLink, childResource));
				promoImagePojo.setPromoTarget(EcommHelper.checkForProperty(childNode, "promoTargetURL"));
				promoImagePojo.setPromoCTAText(EcommHelper.checkForProperty(childNode, "promoButton"));
			}
			if (childNode.hasNode("cq:responsive/default")) {
				Node defaultNode = childNode.getNode("cq:responsive/default");
				if (defaultNode.hasProperty("width")) {
					promoImagePojo.setGridNum(EcommHelper.checkForProperty(defaultNode, "width"));
				}
			} else {
				promoImagePojo.setGridNum("12");
			}
		}
		LOGGER.debug("Image Properties  : {}",promoImagePojo);
		LOGGER.info("getImageProperties Method End");
		return promoImagePojo;

	}

	@ObjectClassDefinition(name = "Child Page properties")
	public @interface Config {
		@AttributeDefinition(name = "Root path", description = "Please provide the rootpath of retail homepage. Default is /content/play/pollypocket")
		String rootPath() default "/content/play/pollypocket";
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
		LOGGER.info("checkForImages Method Start");
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
				if (getImage && childResource.getResourceType().contains(Constant.IMAGE_RESOURCE_TYPE)) {
					LOGGER.info("Getting Image");
					fetchImages(childNode, childResource, imageSectionList);
					count++;
				} else {
					getImage = false;
				}
			}
		}
		LOGGER.info("checkForImages Method End");
	}

	/**
	 * 
	 * @param childNode
	 * @param childResource
	 * @param imageSectionList
	 * @throws RepositoryException
	 */
	private void fetchImages(Node childNode, Resource childResource, List<PromoImagePojo> imageSectionList)
			throws RepositoryException {
		LOGGER.info("fetchImages Method Start");
		PromoImagePojo imagePojo = getImageProperties(childNode, childResource);
		if (StringUtils.isNotEmpty(imagePojo.getPromoImagePath())) {
			imageSectionList.add(imagePojo);
		}
		LOGGER.debug("Image Details :{}",imagePojo);
		LOGGER.info("fetchImages Method End");
	}

	/**
	 * 
	 * @param resource
	 * @return
	 * @throws RepositoryException
	 */
	public String getShopByValue(Resource resource) throws RepositoryException {
		LOGGER.info("getShopByValue Method Start");
		Resource parentResource = resource.getParent();
		if (Objects.nonNull(parentResource)) {
			LOGGER.debug("Parent Resource : {}",parentResource.getPath());
			Resource headerResource = parentResource.getParent();
			if (null != headerResource) {
				Node headerNode = headerResource.adaptTo(Node.class);
				if (headerNode != null
						&& headerResource.getResourceType().contains(Constant.SITEHEADER_RESOURCE_TYPE)) {
					LOGGER.info("Fetching Value : {}",headerNode.getPath());
					return EcommHelper.checkForProperty(headerNode, "shopByValue");
				}
			}
		}
		LOGGER.info("getShopByValue Method End");
		return StringUtils.EMPTY;
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
		LOGGER.info("fetchCategoryPageTitle Method Start");
		Resource categoryResource = resolver.getResource(subCatLink);
		if (null != categoryResource) {
			Page categoryPage = categoryResource.adaptTo(Page.class);
			if (null != categoryPage) {
				String pageTitle = categoryPage.getTitle();
				String navigationTitle = categoryPage.getNavigationTitle();
				if (StringUtils.isBlank(subCatTitle)) {
					columnPojo.setPageName(navigationTitle != null ? EcommHelper.convertSpecialCharacters(navigationTitle) : EcommHelper.convertSpecialCharacters(pageTitle));
				}
				Boolean isLinkable = EcommHelper.checkBooleanProperty(categoryPage, "cq:isNotLinkable", defaultBoolean);
				columnPojo.setNotLinkable(isLinkable);
				Boolean isSpecialLink = EcommHelper.checkBooleanProperty(categoryPage, "cq:isSpecialLink",
						defaultBoolean);
				columnPojo.setSpecialLink(isSpecialLink ? "splLink" : " ");
			}
		}
		LOGGER.debug("fetchCategoryPageTitle Method End : {}",columnPojo);
		LOGGER.info("fetchCategoryPageTitle Method End");
		return columnPojo;

	}

	/**
	 * 
	 * @param pagePath
	 * @param pagePojo
	 * @param resolver
	 * @return
	 */
	public SiteNavigationPojo getPageDetails(String pagePath, SiteNavigationPojo pagePojo, ResourceResolver resolver) {
		LOGGER.info("getPageDetails Method Start");
		Resource featurePageResource = resolver.getResource(pagePath);
		if (null != featurePageResource) {
			Page currentPage = featurePageResource.adaptTo(Page.class);
			if (null != currentPage) {
				pagePojo = fetchPageProperties(currentPage);
			}
		}
		LOGGER.debug("getPageDetails Method End : {}",pagePojo);
		LOGGER.info("getPageDetails Method End");
		return pagePojo;
	}

	/**
	 * 
	 * @param nodeValues
	 * @param column
	 * @return
	 */
	public CategoryColumnPojo fetchColumnDetails(ValueMap nodeValues, String column) {
		LOGGER.info("fetchColumnDetails Method Start");
		CategoryColumnPojo columnPojo = new CategoryColumnPojo();
		columnPojo.setSubCatTitle(nodeValues.get("sCategory" + column, String.class));
		columnPojo.setSubCatLink(nodeValues.get("sCategoryLink" + column, String.class));
		columnPojo.setSubCatUrlTarget(nodeValues.get("sCategoryUrlTarget" + column, String.class));
		columnPojo.setSubCatAeText(nodeValues.get("aeForScategory" + column, String.class));
		columnPojo.setParentPage(nodeValues.get("parentPage" + column, String.class));
		columnPojo.setColumnListFrom(nodeValues.get("column" + column + "ListFrom", String.class));
		columnPojo.setViewAllText(nodeValues.get("viewAll" + column, String.class));
		columnPojo.setViewAllLink(nodeValues.get("viewAllLink" + column, String.class));
		columnPojo.setLinkTargetForViewAll(nodeValues.get("linkTargetForViewAll" + column, String.class));
		columnPojo.setAeForViewAll(nodeValues.get("aeForViewAll" + column, String.class));
		columnPojo.setParentPageList(nodeValues.get("column" + column + "Pages", String[].class));
		LOGGER.debug("fetchColumnDetails Method End :{}",columnPojo);
		LOGGER.info("fetchColumnDetails Method End");
		return columnPojo;

	}

	/**
	 * 
	 * @param viewAllPojo
	 * @param columnDetailsPojo
	 * @param resource
	 * @return
	 */
	public SiteNavigationPojo setViewAllDetails(SiteNavigationPojo viewAllPojo, CategoryColumnPojo columnDetailsPojo,
			Resource resource) {
		LOGGER.info("setViewAllDetails Method Start");
		String viewAllLink = columnDetailsPojo.getViewAllLink();
		if (viewAllLink != null) {
			viewAllPojo.setPageName(columnDetailsPojo.getViewAllText());
			viewAllPojo.setPageUrl(EcommHelper.checkLink(viewAllLink, resource));
			viewAllPojo.setUrlTarget(columnDetailsPojo.getLinkTargetForViewAll());
			viewAllPojo.setAdobeTrackingNameForPage(columnDetailsPojo.getAeForViewAll());
			viewAllPojo.setSpecialLink(splLink);
		}
		LOGGER.debug("setViewAllDetails Method End : {}",viewAllPojo);
		LOGGER.info("setViewAllDetails Method End");
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
		LOGGER.info("fetchColumnLinks method Start");
		SiteNavigationPojo columnPojo = checkLinkOptions(columnDetails, device, resource);
		String subCatTitle = EcommHelper.convertSpecialCharacters(columnDetails.getSubCatTitle());
		String subCatLink = columnDetails.getSubCatLink();
		String subCatUrlTarget = columnDetails.getSubCatUrlTarget();
		String subCatAeText = columnDetails.getSubCatAeText();
		columnPojo.setPageName(StringUtils.isNotBlank(subCatTitle) ? subCatTitle : "Title Here");
		if (StringUtils.isNotBlank(subCatLink)) {
			columnPojo.setPageUrl(EcommHelper.checkLink(subCatLink, resource));
			if (StringUtils.isBlank(subCatTitle)) {
				fetchCategoryPageTitle(columnPojo, subCatLink, subCatTitle, resource.getResourceResolver());
			}
			columnPojo.setUrlTarget(subCatUrlTarget);
			columnPojo.setAdobeTrackingNameForPage(subCatAeText);
		}
		if (StringUtils.isNotBlank(subCatTitle) || StringUtils.isNotBlank(subCatLink)) {
			parentLinksList.add(columnPojo);
		}
		LOGGER.debug("Fetch Column Links : {}", columnPojo);
		LOGGER.info("fetchColumnLinks method End");
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
		LOGGER.info("checkLinkOptions method Start");
		SiteNavigationPojo columnPojo = new SiteNavigationPojo();
		String columnListFrom = columnDetailsPojo.getColumnListFrom();
		if (columnListFrom != null) {
			if (columnListFrom.equals("children")) {
				columnPojo = fetchChildLinks(columnDetailsPojo, device, resource);
			} else if (columnListFrom.equals("static")) {
				columnPojo = fetchFixedList(columnDetailsPojo, device, resource);
			}
		}
		LOGGER.debug("checkLinkOptions : {}",columnPojo);
		LOGGER.info("checkLinkOptions method End");
		return columnPojo;

	}

	/**
	 * 
	 * @param columnDetailsPojo
	 * @param device
	 * @param resource
	 * @return
	 */
	private SiteNavigationPojo fetchChildLinks(CategoryColumnPojo columnDetailsPojo, String device, Resource resource) {
		LOGGER.info("fetchChildLinks method Start");
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
		LOGGER.debug("fetchChildLinks method End : {}", parentPojo);
		LOGGER.info("fetchChildLinks method End");
		return parentPojo;

	}

	/**
	 * 
	 * @param columnDetailsPojo
	 * @param device
	 * @param resource
	 * @return
	 */
	private SiteNavigationPojo fetchFixedList(CategoryColumnPojo columnDetailsPojo, String device, Resource resource) {
		LOGGER.info("fetchFixedList method Start");
		SiteNavigationPojo columnPojo = new SiteNavigationPojo();
		List<SiteNavigationPojo> childPageList = new ArrayList<>();
		if (device.equals("mobile")) {
			handleViewAll(childPageList, resource, columnDetailsPojo);
		}
		String[] parentPageList = columnDetailsPojo.getParentPageList();
		ResourceResolver resolver = resource.getResourceResolver();
		if (!EcommHelper.isNullOrEmpty(parentPageList)) {
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
		LOGGER.debug("fetchFixedList method End : {}",columnPojo);
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
		LOGGER.info("fetchChildPageDetails method Start");
		Iterator<Page> rootPageIterator = currentPage.listChildren(new PageFilter(), false);
		List<SiteNavigationPojo> childPageList = new ArrayList<>();
		if (device.equals("mobile")) {
			handleViewAll(childPageList, resource, columnDetailsPojo);
		}
		while (rootPageIterator.hasNext()) {
			Page childPage = rootPageIterator.next();
			SiteNavigationPojo childPojo = fetchPageProperties(childPage);
			LOGGER.debug("Child Details : {}",childPojo);
			childPageList.add(childPojo);
		}
		if (device.equals(desktop)) {
			handleViewAll(childPageList, resource, columnDetailsPojo);
		}

		if (!childPageList.isEmpty()) {
			parentPojo.setChildPageList(childPageList);
		}
		LOGGER.info("fetchChildPageDetails method End");
	}

	/**
	 * 
	 * @param childPageList
	 * @param resource
	 * @param columnDetailsPojo
	 */
	private void handleViewAll(List<SiteNavigationPojo> childPageList, Resource resource,
			CategoryColumnPojo columnDetailsPojo) {
		LOGGER.info("handleViewAll method Start");
		SiteNavigationPojo viewAllPojo = new SiteNavigationPojo();
		viewAllPojo = setViewAllDetails(viewAllPojo, columnDetailsPojo, resource);

		childPageList.add(viewAllPojo);
		LOGGER.debug("handleViewAll : {} ", viewAllPojo);
		LOGGER.info("handleViewAll method End");

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
	public SiteNavigationPojo setCategoryDetails(SiteNavigationPojo categoryLinksPojo, Resource resource,
			Boolean displayShopByValue, String categoryTitleLink, String categoryTitle, String linkTargetCategory,
			String device) throws RepositoryException {
		LOGGER.info("setCategoryDetails Method Start");
		String headerShopByValue = StringUtils.EMPTY;
		if (displayShopByValue) {
			headerShopByValue = getShopByValue(resource);
			LOGGER.debug("headerShopByValue : {}",headerShopByValue);
		}
		if (StringUtils.isNotBlank(categoryTitleLink) && categoryTitleLink.startsWith("/content")) {
			categoryLinksPojo = fetchCategoryPageDetails(categoryTitleLink, categoryLinksPojo,
					resource.getResourceResolver());
			if (StringUtils.isNotBlank(headerShopByValue) && device.equals(desktop)) {
				String temp = categoryLinksPojo.getPageName();
				categoryLinksPojo.setPageName(headerShopByValue + " " + temp);
				LOGGER.debug("Current Page Name :  {}",temp);
			}
		} else {
			if (StringUtils.isNotBlank(headerShopByValue) && device.equals(desktop)) {
				categoryLinksPojo.setPageName(headerShopByValue + " " + categoryTitle);
			} else {
				categoryLinksPojo.setPageName(categoryTitle);
			}
			if (StringUtils.isNotBlank(categoryTitleLink)) {
				categoryLinksPojo.setPageUrl(EcommHelper.checkLink(categoryTitleLink, resource));
				categoryLinksPojo.setUrlTarget(linkTargetCategory);
			} else {
				categoryLinksPojo.setPageUrl("#");
			}
		}
		LOGGER.debug("setCategoryDetails method End : {}",categoryLinksPojo);
		return categoryLinksPojo;
	}

	/**
	 * 
	 * @param path
	 * @param categoryLinksPojo
	 * @param resolver
	 * @return
	 */
	public SiteNavigationPojo fetchCategoryPageDetails(String path, SiteNavigationPojo categoryLinksPojo,
			ResourceResolver resolver) {
		LOGGER.info("fetchCategoryPageDetails method Start");
		Resource categoryResource = resolver.getResource(path);
		if (null != categoryResource) {
			Page categoryPage = categoryResource.adaptTo(Page.class);
			if (null != categoryPage) {
				categoryLinksPojo = fetchPageProperties(categoryPage);
			}
		}
		LOGGER.debug("fetchCategoryPageDetails : {}",categoryLinksPojo);
		LOGGER.info("fetchCategoryPageDetails method End");
		return categoryLinksPojo;

	}

	/**
	 * 
	 * @param resource
	 * @param featuredLinksList
	 * @return
	 */
	public List<SiteNavigationPojo> checkFeaturedNode(Resource resource, List<SiteNavigationPojo> featuredLinksList) {
		LOGGER.info("checkFeaturedNode method Start");
		Resource featuredResource = resource.getChild("featuredSection");
		if (featuredResource != null) {
			Node featuredNode = featuredResource.adaptTo(Node.class);
			Map<String, ValueMap> multifieldProperty;
			multifieldProperty = multifieldReader.propertyReader(featuredNode);
			if (multifieldProperty != null) {
				for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
					String pagePath = entry.getValue().get("featuredLinks", String.class);
					SiteNavigationPojo featurePagePojo = new SiteNavigationPojo();
					featurePagePojo = getFeaturePageDetails(pagePath, featurePagePojo, resource.getResourceResolver());
					featuredLinksList.add(featurePagePojo);
					LOGGER.debug("Featured Details : {}",featurePagePojo);
				}
			}
		}
		LOGGER.info("checkFeaturedNode method End");
		return featuredLinksList;
	}

	/**
	 * 
	 * @param pagePath
	 * @param featurePagePojo
	 * @param resolver
	 * @return
	 */
	private SiteNavigationPojo getFeaturePageDetails(String pagePath, SiteNavigationPojo featurePagePojo,
			ResourceResolver resolver) {
		LOGGER.info("getFeaturePageDetails method Start");
		Resource featurePageResource = resolver.getResource(pagePath);
		if (null != featurePageResource) {
			Page currentPage = featurePageResource.adaptTo(Page.class);
			if (null != currentPage) {
				featurePagePojo = fetchPageProperties(currentPage);
			}
		}
		LOGGER.info("getFeaturePageDetails method End");
		return featurePagePojo;
	}

	/**
	 * 
	 * @param resource
	 * @param imageSectionList
	 * @return
	 * @throws RepositoryException
	 */
	public List<PromoImagePojo> getImageSection(Resource resource, List<PromoImagePojo> imageSectionList, int imageLimit)
			throws RepositoryException {
		LOGGER.info("getImageSection method Start");
		Resource parentResource = resource.getParent();
		if (parentResource != null) {
			Iterator<Resource> childIterator = parentResource.listChildren();
			checkForImages(resource, childIterator, imageSectionList, imageLimit);
		}
		LOGGER.info("getImageSection method End");
		return imageSectionList;
	}
	
	/**
	 * @param primaryNavPojo
	 * @param templateType
	 * @param aeForPrimaryNavTitle 
	 */
	public SiteNavigationPojo getTemplateVariationType(SiteNavigationPojo primaryNavPojo, String templateType, String aeForPrimaryNavTitle,boolean displayShopByValue, String device) {
		LOGGER.info("Method getTemplateVariationType Start");
		primaryNavPojo.setAdobeTrackingNameForPage(aeForPrimaryNavTitle);
		if (templateType.equals(Constant.FISHER_PRICE_BRAND_TEMPLATE)) {
			primaryNavPojo.setSubLinkClass("shop-by-brands");
			primaryNavPojo.setShopAllClass("shopAll");
		} else {
			primaryNavPojo.setSubLinkClass(Constant.EXPLORE);
			if(displayShopByValue && StringUtils.equals(device, Constant.FISHER_PRICE_DEVICETYPE_MOBILE)) {
				primaryNavPojo.setSubLinkClass(Constant.SHOP_BY_AGE);				
			}	
		} 
		LOGGER.debug("SublinkClass :: {} " , primaryNavPojo.getSubLinkClass());
		LOGGER.debug("Method getTemplateVariationType End : {}",primaryNavPojo);
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
	public List<SiteNavigationPojo> getSecondaryCategoryTitle(Resource resource, String[] navigationalLinks, String device, String categoryTitle, List<SiteNavigationPojo> categorySectionNavLinks, List<SiteNavigationPojo> secondaryNavLinks, String templateType) {
		LOGGER.info("getSecondaryCategoryTitle method Start");
		if (!EcommHelper.isNullOrEmpty(navigationalLinks)) {
			categorySectionNavLinks = getSecondaryNavigationLinksList(resource, navigationalLinks, templateType, categorySectionNavLinks, device);
		}
		SiteNavigationPojo categoryLinksPojo = new SiteNavigationPojo();
		if (device.equals(Constant.FISHER_PRICE_DEVICETYPE_DESKTOP)) {
			categoryLinksPojo.setPageName(categoryTitle);
		} else if (device.equals(Constant.FISHER_PRICE_DEVICETYPE_MOBILE)) {
			categoryLinksPojo.setPageName("");
		}
		if (categorySectionNavLinks != null && !categorySectionNavLinks.isEmpty()) {
			categoryLinksPojo.setChildPageList(categorySectionNavLinks);
		}
		secondaryNavLinks.add(categoryLinksPojo);
		LOGGER.debug("Secondary Title : {}",categoryLinksPojo);
		LOGGER.info("getSecondaryCategoryTitle method End");
		return secondaryNavLinks;
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
	private List<SiteNavigationPojo> getSecondaryNavigationLinksList(Resource resource, String[] navigationalLinks, String templateType, List<SiteNavigationPojo> categorySectionNavLinks, String reqDeviceType) {
		LOGGER.info("getSecondaryNavigationLinksList method Start");
		ResourceResolver resolver = resource.getResourceResolver();
		if (templateType.equals(Constant.FISHER_PRICE_BRAND_TEMPLATE)) {
			setViewALLToPojo(resource, resource.getValueMap(), Constant.FISHER_PRICE_DEVICETYPE_MOBILE, categorySectionNavLinks, reqDeviceType);
			categorySectionNavLinks = getPageProperties(navigationalLinks, resolver, navigationalLinks.length, categorySectionNavLinks);
			setViewALLToPojo(resource, resource.getValueMap(), Constant.FISHER_PRICE_DEVICETYPE_DESKTOP, categorySectionNavLinks, reqDeviceType);
		} else if (templateType.equals(Constant.FISHER_PRICE_AGE_TEMPLATE)) {
			categorySectionNavLinks = getPageProperties(navigationalLinks, resolver, 2, categorySectionNavLinks);
		} else if (templateType.equals(Constant.FISHER_PRICE_EXPLORE_COLUMN_TWO)) {
			categorySectionNavLinks = getPageProperties(navigationalLinks, resolver, 4, categorySectionNavLinks);
		} else if (templateType.equals(Constant.FISHER_PRICE_EXPLORE_COLUMN_THREE)) {
			categorySectionNavLinks = getPageProperties(navigationalLinks, resolver, 6, categorySectionNavLinks);
		} else if (templateType.equals(Constant.FISHER_PRICE_EXPLORE_COLUMN_FOUR)) {
			categorySectionNavLinks = getPageProperties(navigationalLinks, resolver, 8, categorySectionNavLinks);
		}
		LOGGER.info("getSecondaryNavigationLinksList method End");
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
	public List<SiteNavigationPojo> getPageProperties(String[] navigationalLinks, ResourceResolver resourceResolver, int limit, List<SiteNavigationPojo> categorySectionNavLinks) {

		LOGGER.info("start of getPageProperties() Method");
		int navLinksCount = 0;
		for (String navLinks : navigationalLinks) {
			Resource resource = resourceResolver.getResource(navLinks);
			if (null != resource) {
				Page currentPage = resource.adaptTo(Page.class);
				if (null != currentPage) {
					SiteNavigationPojo siteNavigationPojo = fetchPageProperties(currentPage);
					categorySectionNavLinks.add(siteNavigationPojo);
					navLinksCount++;
				}
			}
			if (navLinksCount == limit) {
				break;
			}
		}
		LOGGER.info("End of getPageProperties() Method");
		return categorySectionNavLinks;
	}
	
	/**
	 * @param resource 
	 * @param nodeValues
	 * @param deviceType
	 * @param categorySectionNavLinks 
	 * @param reqDeviceType 
	 */
	private void setViewALLToPojo(Resource resource, ValueMap nodeValues, String deviceType, List<SiteNavigationPojo> categorySectionNavLinks, String reqDeviceType) {
		LOGGER.info("setViewALLToPojo Method Start");
		String viewAllText = EcommHelper.getValueMapNodeValue(nodeValues, "viewALL");
		String viewALLbuttonlink = EcommHelper.getValueMapNodeValue(nodeValues, "viewALLbuttonlink");
		String viewALLtargetURL = EcommHelper.getValueMapNodeValue(nodeValues, "viewALLtargetURL");
		String viewALLAlwaysText = EcommHelper.getValueMapNodeValue(nodeValues, "viewALLAlwaysText");
		if (StringUtils.isNotEmpty(viewALLbuttonlink) && StringUtils.isNotEmpty(viewAllText)
				&& reqDeviceType.equals(deviceType)) {
			SiteNavigationPojo viewAllPojo = new SiteNavigationPojo();
			categorySectionNavLinks.add(setViewAllDetails(viewAllPojo, viewAllText, viewALLbuttonlink,
					viewALLtargetURL, viewALLAlwaysText, resource));
		}
		LOGGER.info("setViewALLToPojo Method End");
	}
	
	public void setMultifieldReader(MultifieldReader multifieldReader) {
		this.multifieldReader = multifieldReader;
	}
}
