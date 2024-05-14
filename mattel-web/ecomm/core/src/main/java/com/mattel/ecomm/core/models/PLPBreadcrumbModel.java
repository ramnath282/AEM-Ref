package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.pojos.BreadcrumbDetails;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PLPBreadcrumbModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(PLPBreadcrumbModel.class);
    List<BreadcrumbDetails> breadcrumbLinks = new LinkedList<>();
    @Inject
    Resource resource;
    @Inject
	private Page currentPage;

    @PostConstruct
    protected void init() {
        LOGGER.info("this is start of init method of BreadcrumbModel");
        if (resource != null && !resource.getPath().contains(Constant.CONF)) {
            breadcrumbLinks.clear();
            if (Objects.nonNull(currentPage)) {
                PageManager pageManager = currentPage.getPageManager();
                String rootLandingPagePath = EcomUtil.getInheritedProperty(currentPage.getContentResource(), "rootLandingPage");
                Page landingPage = null;
                String currentPagePath = currentPage.getPath();
                LOGGER.debug("currentPagePath is {}",currentPagePath);
                Page rootCategoryPage = currentPage.getAbsoluteParent(4);
                if(StringUtils.isNotBlank(currentPagePath) && Objects.nonNull(rootCategoryPage)){
                	LOGGER.debug("rootCategoryPage is {}",rootCategoryPage.getPath());
                	if(StringUtils.isNotBlank(rootLandingPagePath) && Objects.nonNull(pageManager)){
                        LOGGER.debug("rootLandingPagePath is {}",rootLandingPagePath);
                        landingPage = pageManager.getPage(rootLandingPagePath);
                    } else if(Objects.nonNull(rootCategoryPage.getParent())){
                    	landingPage = rootCategoryPage.getParent();
                    }
                	fetchBreadCrumbLinks(currentPage, currentPagePath,rootCategoryPage,breadcrumbLinks,landingPage);
                    LOGGER.debug("Product listing page breadcrumbs : {}", breadcrumbLinks);
                }
            }
        }
    }

    private void fetchBreadCrumbLinks(Page currentPage, String currentPagePath,Page rootCategoryPage,List<BreadcrumbDetails> breadcrumbLinks,Page rootLandingPage) {
        int pageDepth = currentPage.getDepth();
        int catPageDepth = rootCategoryPage.getDepth();
        addRootPageToBreadCrumb(rootLandingPage,breadcrumbLinks);
        Page tempPage;
        
        while (catPageDepth < pageDepth) {
            tempPage = currentPage.getAbsoluteParent(catPageDepth);
            if (!tempPage.getPath().equals(currentPagePath)) {
                BreadcrumbDetails breadcrumb = getBreadcrumbPageDetails(tempPage);
                breadcrumbLinks.add(breadcrumb);
            } 
            catPageDepth++;
        }
        
        BreadcrumbDetails currentPageBreadcrumb = getBreadcrumbPageDetails(currentPage);
        breadcrumbLinks.add(currentPageBreadcrumb);
    }

    private BreadcrumbDetails getBreadcrumbPageDetails(Page page) {
        BreadcrumbDetails breadcrumb = new BreadcrumbDetails();
        breadcrumb.setTitle(page.getTitle());
        breadcrumb.setPageLink(EcomUtil.getPlpPageLink(page.getPath(), resource));
        breadcrumb.setPagePath(page.getPath());
        if(page.getProperties().containsKey(Constant.HIDE_IN_NAV) && StringUtils.equals(page.getProperties().get(Constant.HIDE_IN_NAV, String.class), "true")){
        	breadcrumb.setHideInNav(true);
        }
        return breadcrumb;
    }

    private void addRootPageToBreadCrumb(Page rootLandingPage,List<BreadcrumbDetails> breadcrumbLinks) {
    	if(Objects.nonNull(rootLandingPage)){
    		LOGGER.debug("Landing Page is : {}", rootLandingPage.getPath());
    		BreadcrumbDetails rootbreadcrumb = new BreadcrumbDetails();
            rootbreadcrumb.setTitle(rootLandingPage.getTitle());
            rootbreadcrumb.setPageLink(EcomUtil.checkLink(rootLandingPage.getPath(), resource));
            rootbreadcrumb.setPagePath(rootLandingPage.getPath());
            breadcrumbLinks.add(rootbreadcrumb);
    	}
    }

    public List<BreadcrumbDetails> getBreadcrumbLinks() {
        return breadcrumbLinks;
    }

    public void setBreadcrumbLinks(List<BreadcrumbDetails> breadcrumbLinks) {
        this.breadcrumbLinks = breadcrumbLinks;
    }

}
