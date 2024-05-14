package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.pojos.BreadcrumbPojo;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = { Resource.class,
	    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BreadcrumbModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(BreadcrumbModel.class);
    List<BreadcrumbPojo> breadcrumbLinks = new LinkedList<>();
    
    @Inject
    Resource resource;

	@Inject
	Page currentPage;
	
    String plpSubcategory;
    String plpProductCategory;
    String alignBreadcrumb;
    BreadcrumbPojo mobileBreadcrumbPojo = new BreadcrumbPojo();

    @PostConstruct
    protected void init() {
        LOGGER.info("init method of BreadcrumbModel called");
        try { 
            if (Objects.nonNull(resource) && !resource.getPath().contains(Constant.CONF)) {
                breadcrumbLinks.clear();
                if (Objects.nonNull(currentPage)) {
                    fetchBreadCrumbLinks(currentPage);
                    checkAlignment(currentPage);
                }
            }        	
        } catch(RuntimeException e) {
        	LOGGER.error("Exception occured while creating BreadcrumbModel", e);
        }
        LOGGER.info("init method of BreadcrumbModel ended");
    }
    
	/**
	 * Prepares the breadcrumbLinks LinkedList<>
	 * 
	 * @param currentPage
	 */
    private void fetchBreadCrumbLinks(Page currentPage) {
        LOGGER.info("fetchBreadCrumbLinks method called");
        String currentPagePath = currentPage.getPath();
        LOGGER.debug("currentPagePath is {}", currentPagePath);
        int pageDepth = currentPage.getDepth();
        LOGGER.debug("pageDepth for currentPage is {}", pageDepth);
        int incrCounterFromHomeLevel = 4;
        Page currTempPage;
        while (incrCounterFromHomeLevel < pageDepth) {
            BreadcrumbPojo breadcrumb = new BreadcrumbPojo();
            currTempPage = currentPage.getAbsoluteParent(incrCounterFromHomeLevel);
            if (!(currTempPage.getName().equals(Constant.PRODUCT_FINDER)) && !(currTempPage.getName().equals("shop"))) {
                String title = EcommHelper.convertSpecialCharacters(currTempPage.getTitle());
                LOGGER.debug("title for currTempPage is {}", title);
                String navigationTitle = EcommHelper.convertSpecialCharacters(currTempPage.getNavigationTitle());
                LOGGER.debug("navigationTitle for currTempPage is {}", navigationTitle);
                breadcrumb.setTitle(EcommHelper.toCamelCase(StringUtils.isNotEmpty(navigationTitle) ? navigationTitle : title));
                breadcrumb.setAdobeTrackingName(currTempPage.getProperties().get("adobeTrackingNameForPage", String.class));
                if (currentPage.getPath().equalsIgnoreCase(currTempPage.getPath())) {
				  breadcrumb.setPageLink("javascript:void(0);");
				} else {
				  breadcrumb.setPageLink(EcommHelper.checkLink(currTempPage.getPath(), resource));
				}
                LOGGER.debug("breadcrumb is {}", breadcrumb);
                breadcrumbLinks.add(breadcrumb);
            } 
            incrCounterFromHomeLevel++;
        }
        if (breadcrumbLinks.size() > 1) {
            mobileBreadcrumbPojo.setTitle(EcommHelper.toCamelCase(breadcrumbLinks.get(breadcrumbLinks.size() - 2).getTitle()));
            mobileBreadcrumbPojo.setAdobeTrackingName(breadcrumbLinks.get(breadcrumbLinks.size() - 2).getAdobeTrackingName());
            mobileBreadcrumbPojo.setPageLink(breadcrumbLinks.get(breadcrumbLinks.size() - 2).getPageLink());
            LOGGER.debug("mobileBreadcrumbPojo is {}", mobileBreadcrumbPojo);
        }
        if (breadcrumbLinks.size() > 2) {
            setPlpSubcategory(EcommHelper.toCamelCase(breadcrumbLinks.get(breadcrumbLinks.size() - 1).getTitle()));
            setPlpProductCategory(EcommHelper.toCamelCase(breadcrumbLinks.get(breadcrumbLinks.size() - 2).getTitle()));
        } else if(breadcrumbLinks.size() == 2){
            setPlpProductCategory(EcommHelper.toCamelCase(breadcrumbLinks.get(breadcrumbLinks.size() - 1).getTitle()));
            setPlpSubcategory(StringUtils.EMPTY);
        } else {
            setPlpProductCategory(StringUtils.EMPTY);
            setPlpSubcategory(StringUtils.EMPTY);
        }
        LOGGER.debug("plpSubcategory is {}", getPlpSubcategory());
        LOGGER.debug("plpProductCategory is {}", getPlpProductCategory());
        LOGGER.info("fetchBreadCrumbLinks method ended");
    }

	/**
	 * Sets the alignment for BreadCrumb based on the resourceType of the page
	 * 
	 * @param currentPage
	 */
    private void checkAlignment(Page currentPage) {
        LOGGER.info("checkAlignment method called");
        String resourceType = currentPage.getProperties().get("sling:resourceType", String.class);
        LOGGER.debug("resourceType for currentPage is {}", resourceType);
        alignBreadcrumb = Objects.nonNull(resourceType) && resourceType.contains("productlisting") ? "breadcrumb-center" : " ";
        LOGGER.debug("value of alignBreadcrumb is {}", alignBreadcrumb);
        LOGGER.info("checkAlignment method ended");
    }
    
    public List<BreadcrumbPojo> getBreadcrumbLinks() {
        return breadcrumbLinks;
    }

    public String getAlignBreadcrumb() {
        return alignBreadcrumb;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public BreadcrumbPojo getMobileBreadcrumbPojo() {
        return mobileBreadcrumbPojo;
    }

    public void setMobileBreadcrumbPojo(BreadcrumbPojo mobileBreadcrumbPojo) {
        this.mobileBreadcrumbPojo = mobileBreadcrumbPojo;
    }

    public String getPlpSubcategory() {
        return plpSubcategory;
    }

    public void setPlpSubcategory(String plpSubcategory) {
        this.plpSubcategory = plpSubcategory;
    }

    public String getPlpProductCategory() {
        return plpProductCategory;
    }

    public void setPlpProductCategory(String plpProductCategory) {
        this.plpProductCategory = plpProductCategory;
    }
}
