package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.pojo.CategoryColumnPojo;
import com.mattel.global.core.pojo.PromoImagePojo;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.utils.NavigationUtil;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CategoryNavigationModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryNavigationModel.class);
    @Inject
    Resource resource;
    @Self
    ResourceResolver resolver;
    Boolean defaultBoolean = false;
    Boolean displayShopByValue;
    String categoryTitle = StringUtils.EMPTY;
    String categoryTitleLink = StringUtils.EMPTY;
    String aeForCategoryTitle = StringUtils.EMPTY;
    String linkTargetCategory = StringUtils.EMPTY;
    CategoryColumnPojo columnOneDetails = new CategoryColumnPojo();
    CategoryColumnPojo columnTwoDetails = new CategoryColumnPojo();
    CategoryColumnPojo columnThreeDetails = new CategoryColumnPojo();
    CategoryColumnPojo columnFourDetails = new CategoryColumnPojo();
    String desktopValue = "desktop";
    String device = desktopValue;
    @Inject
    private NavigationUtil navigationUtil;
    private List<SiteNavigationPojo> parentLinksList = new LinkedList<>();
    private List<SiteNavigationPojo> categoryNavLinks = new LinkedList<>();
    private List<SiteNavigationPojo> featuredLinksList = new LinkedList<>();
    private List<PromoImagePojo> imageSectionList = new LinkedList<>();
    private String headerShopByValue;

    @PostConstruct
    protected void init() {
        LOGGER.info("CategoryNavigationModel.init() --> start");

        categoryNavLinks.clear();
        parentLinksList.clear();
        featuredLinksList.clear();
        imageSectionList.clear();
        try {
            if (resource != null) {
                resolver = resource.getResourceResolver();
                ValueMap nodeValues = resource.adaptTo(ValueMap.class);
                if (null != nodeValues) {
                    fetchPropertyValues(nodeValues);
                    SiteNavigationPojo categoryLinksPojo = new SiteNavigationPojo();
                    categoryLinksPojo = navigationUtil.setCategoryDetails(categoryLinksPojo, resource,
                            displayShopByValue, categoryTitleLink, categoryTitle, linkTargetCategory, device);
                    parentLinksList = navigationUtil.fetchColumnLinks(columnOneDetails, parentLinksList,
                            resource, device);
                    parentLinksList = navigationUtil.fetchColumnLinks(columnTwoDetails, parentLinksList,
                            resource, device);
                    parentLinksList = navigationUtil.fetchColumnLinks(columnThreeDetails, parentLinksList,
                            resource, device);
                    parentLinksList = navigationUtil.fetchColumnLinks(columnFourDetails, parentLinksList,
                            resource, device);
                    if (!parentLinksList.isEmpty()) {
                        categoryLinksPojo.setChildPageList(parentLinksList);
                    }
                    categoryNavLinks.add(categoryLinksPojo);
                    featuredLinksList = navigationUtil.checkFeaturedNode(resource, featuredLinksList);
                    LOGGER.debug("featuredLinksList {}",featuredLinksList);
                    imageSectionList = navigationUtil.getImageSection(resource, imageSectionList, 4);

                }
            }

        } catch (NullPointerException e) {
            LOGGER.error("Null PointerException Occured {} ", e);
        } catch (RepositoryException e) {
            LOGGER.error("Repository Exception Occured {} ", e);
        }
        LOGGER.info("CategoryNavigationModel.init() --> end");
    }

    private void fetchPropertyValues(ValueMap nodeValues) {
        LOGGER.info("CategoryNavigationModel.fetchPropertyValues() --> start");
        displayShopByValue = nodeValues.containsKey("displayShopByValue")
                ? nodeValues.get("displayShopByValue", Boolean.class)
                : defaultBoolean;
        categoryTitle = nodeValues.get("categoryTitle", String.class);
        categoryTitleLink = nodeValues.get("categoryTitleLink", String.class);
        linkTargetCategory = nodeValues.get("linkTargetCategory", String.class);

        columnOneDetails = navigationUtil.fetchColumnDetails(nodeValues, "One");
        columnTwoDetails = navigationUtil.fetchColumnDetails(nodeValues, "Two");
        columnThreeDetails = navigationUtil.fetchColumnDetails(nodeValues, "Three");
        columnFourDetails = navigationUtil.fetchColumnDetails(nodeValues, "Four");

        LOGGER.info("CategoryNavigationModel.fetchPropertyValues() --> end");
    }

    public List<SiteNavigationPojo> getCategoryNavLinks() {
        return categoryNavLinks;
    }

    public void setCategoryNavLinks(List<SiteNavigationPojo> categoryNavLinks) {
        this.categoryNavLinks = categoryNavLinks;
    }

    public List<SiteNavigationPojo> getFeaturedLinksList() {
        return featuredLinksList;
    }

    public void setFeaturedLinksList(List<SiteNavigationPojo> featuredLinksList) {
        this.featuredLinksList = featuredLinksList;
    }

    public List<PromoImagePojo> getImageSectionList() {
        return imageSectionList;
    }

    public void setImageSectionList(List<PromoImagePojo> imageSectionList) {
        this.imageSectionList = imageSectionList;
    }

    public String getHeaderShopByValue() {
        return headerShopByValue;
    }

    public void setHeaderShopByValue(String headerShopByValue) {
        this.headerShopByValue = headerShopByValue;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ResourceResolver getResolver() {
        return resolver;
    }

    public void setResolver(ResourceResolver resolver) {
        this.resolver = resolver;
    }

    public NavigationUtil getNavigationUtil() {
        return navigationUtil;
    }

    public void setNavigationUtil(NavigationUtil navigationUtil) {
        this.navigationUtil = navigationUtil;
    }

}
