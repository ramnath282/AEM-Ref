package com.mattel.ecomm.core.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.utils.NavigationUtil;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BrandAndCategoryListingModel {
	private static final Logger LOGGER = LoggerFactory.getLogger(BrandAndCategoryListingModel.class);
	@Inject
	private Resource resource;
	@SlingObject
	private SlingHttpServletRequest request;
	@Inject
	@Via("resource")
	@Optional
	private String listFrom;
	@Inject
	@Via("resource")
	@Optional
	private String[] pageList;

	@Inject
	private NavigationUtil navigationService;
	ResourceResolver resolver;
	private List<SiteNavigationPojo> pagesList = new LinkedList<>();

	@PostConstruct
	protected void init() {
		LOGGER.debug("Start Init BrandAndCategoryListingModel");
		if (resource != null && !resource.getPath().contains("/conf/")) {
			pagesList.clear();
			resolver = request.getResourceResolver();
			PageManager pageManager = resolver.adaptTo(PageManager.class);
			if (pageManager != null && listFrom != null) {
				fetchPageList(pageManager);
			}
		}
		LOGGER.debug("End of init method of BrandAndCategoryListingModel ");
	}

	private void fetchPageList(PageManager pageManager) {
		LOGGER.debug("Start fetchPageList Method");
		if (listFrom.equals("children")) {
			Page currentPage = pageManager.getContainingPage(resource);
			if (currentPage != null) {
				Iterator<Page> rootPageIterator = currentPage.listChildren(new PageFilter(), false);
				while (rootPageIterator.hasNext()) {
					Page childPage = rootPageIterator.next();
					SiteNavigationPojo childPojo = navigationService.fetchPageProperties(childPage);
					pagesList.add(childPojo);
				}
			}
		} else if (listFrom.equals("static") && !EcommHelper.isNullOrEmpty(pageList)) {
			fetchStaticPageList();
		}
		LOGGER.debug("End fetchPageList Method");
	}

	private void fetchStaticPageList() {
		LOGGER.debug("Start fetchStaticPageList Method");
		for (String navLinks : pageList) {
			Resource childResource = resolver.getResource(navLinks);
			if (null != childResource) {
				Page currentPage = childResource.adaptTo(Page.class);
				if (null != currentPage) {
					SiteNavigationPojo siteNavigationPojo = navigationService.fetchPageProperties(currentPage);
					pagesList.add(siteNavigationPojo);
				}
			}
		}
		LOGGER.debug("End fetchStaticPageList Method");
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setRequest(SlingHttpServletRequest request) {
		this.request = request;
	}

	public void setResolver(ResourceResolver resolver) {
		this.resolver = resolver;
	}

	public List<SiteNavigationPojo> getPagesList() {
		return pagesList;
	}

	public void setListFrom(String listFrom) {
		this.listFrom = listFrom;
	}

	public void setNavigationService(NavigationUtil navigationService) {
		this.navigationService = navigationService;
	}

	public void setPageList(String[] pageList) {
		this.pageList = pageList;
	}

}
