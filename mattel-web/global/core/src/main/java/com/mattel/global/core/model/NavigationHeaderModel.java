package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.mattel.global.core.pojo.NavigationParentPagePojo;
import com.mattel.global.core.services.SiteNavigationService;
import com.mattel.global.core.utils.GlobalUtils;

/**
 * @author CTS SiteNavigation Model to Navigation.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavigationHeaderModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(NavigationHeaderModel.class);
	@Inject
	private SiteNavigationService siteNavigationService;

	@Self
	private SlingHttpServletRequest request;
	@Inject
	@Via("resource")
	private String pageUrl;

	@Inject
	@Via("resource")
	private String brandNavUrl;

	@Inject
	@Via("resource")
	private Boolean collectRootPage;

	@Inject
	@Via("resource")
	private String linkUrl;

    @Inject
    @Via("resource")
    private String depth;

	@Inject
	Resource resource;

	@Inject
	@Via("resource")
	private Boolean collectAllPages;

	private List<NavigationParentPagePojo> navItemsList = new ArrayList<>();

	/**
	 * The init method. It Will get the Child pages of Level1 and Level2 as per
	 * the requirement
	 */
	@PostConstruct
	protected void init() {
		LOGGER.info("init() method of NavigationHeaderModel class --> started");
		try {
			if (Objects.nonNull(resource)) {
				ResourceResolver resolver = resource.getResourceResolver();
				Resource rootPageResource = resolver.getResource(pageUrl);
				Page rootPage = null;
				if(Objects.isNull(depth)){
					LOGGER.debug("Depth is not defined");
					depth = collectAllPages ? "2" : "1";
				}
				if (Objects.nonNull(rootPageResource)) {
					rootPage = rootPageResource.adaptTo(Page.class);
					int depthVal = Integer.parseInt(depth);
					String currentPath = request.getPathInfo();
					LOGGER.debug("currentPath is {}", currentPath);
					Boolean checkRoot = false;
					if (Objects.nonNull(collectRootPage)) {
						LOGGER.debug("collectRootPage is not null");
						checkRoot = collectRootPage;
					}
					LOGGER.debug("checkRoot is {}", checkRoot);

                    navItemsList = siteNavigationService.getNavigationDetails(rootPage, depthVal, checkRoot, currentPath);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception occured in init method{}", e);
		}
		LOGGER.info("init() method of NavigationHeaderModel class --> ended");
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public List<NavigationParentPagePojo> getNavItemsList() {
		return navItemsList;
	}

	public String getBrandNavUrl() {
		return GlobalUtils.checkLink(brandNavUrl, resource);
	}

	public String getLinkUrl() {
		return GlobalUtils.checkLink(linkUrl, resource);
	}

	public void setTileGalleryAndLandingService(SiteNavigationService siteNavigationService) {
		this.siteNavigationService = siteNavigationService;
	}

	public void setRequest(SlingHttpServletRequest request) {
		this.request = request;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public boolean gettCollectAllPages() {
		return collectAllPages;
	}

}
