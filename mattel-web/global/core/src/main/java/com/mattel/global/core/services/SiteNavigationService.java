package com.mattel.global.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.mattel.global.core.pojo.NavigationParentPagePojo;
import com.mattel.global.core.pojo.NavigationBasePagePojo;
import com.mattel.global.core.utils.GlobalUtils;

@Component(service = SiteNavigationService.class, immediate = true)
public class SiteNavigationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SiteNavigationService.class);

	private static final String NAV_THUMB_IMAGE = "navThumbImage";
	private static final String CQ_REDIRECT_TARGET = "cq:redirectTarget";
	private static final String CQ_REDIRECT_TARGET_OPTIONS = "cq:redirectTargetOption";
	private static final String ADOBE_TRACKING_NAME_FOR_PAGE = "adobeTrackingNameForPage";

	public static final String REDIRECTPATH = "redirectPath";
	public static final String REDIRECTTARGETOPTION = "redirectTargetOption";
	public static final String PAGEPATH = "path";
	public static final String CURRENTPATH = "currentPath";

	/**
	 * @param page
	 * @param currentPath
	 * @param isParent
	 * @param isRootPage
	 * @return NavigationBasePagePojo
	 */
	private NavigationParentPagePojo preparePojoForPage(Page page, String currentPath, boolean isParent, boolean isRootPage) {
		LOGGER.info("getRootPageDetails() method of SiteNavigationService class --> started");
		NavigationParentPagePojo pagePojo = new NavigationParentPagePojo();
		String pagePath = page.getPath();
		LOGGER.debug("Page Path is {}", pagePath);
		String pageTitle = page.getTitle();
		LOGGER.debug("title is {}", pageTitle);
		String pageName = page.getName();
		LOGGER.debug("Page Name is {}", pageName);
		String navTitle = page.getNavigationTitle();
		LOGGER.debug("navTitle is {}", navTitle);
		ValueMap pageProperies = page.getProperties();
		String thumbnailImg = pageProperies.get(NAV_THUMB_IMAGE,StringUtils.EMPTY);
		String redirectPath = pageProperies.get(CQ_REDIRECT_TARGET,StringUtils.EMPTY);
		String redirectTargetOption = pageProperies.get(CQ_REDIRECT_TARGET_OPTIONS,"_self");
		String adobeTrackingNameForPage = pageProperies.get(ADOBE_TRACKING_NAME_FOR_PAGE,StringUtils.EMPTY);
		pagePojo.setAdobeTrackingNameForPage(adobeTrackingNameForPage);
		pagePojo.setThumbnailImg(thumbnailImg);
		pagePojo.setName(pageName);
		pagePojo.setLinkingName(pageName);
		HashMap<String, String> childNavMap = new HashMap<>();
		childNavMap.put(REDIRECTPATH, redirectPath);
		childNavMap.put(REDIRECTTARGETOPTION, redirectTargetOption);
		childNavMap.put(PAGEPATH, pagePath);
		childNavMap.put(CURRENTPATH, currentPath);
		LOGGER.debug("childNavMap is {}", childNavMap);
		pagePojo = setNavigationDetails(pagePojo, isParent, pageTitle, navTitle, page.getContentResource(), isRootPage, childNavMap);
		LOGGER.debug("childPageItem is {}", pagePojo);
		LOGGER.info("getRootPageDetails() method of SiteNavigationService class --> ended");
		return pagePojo;
	}

	/**
	 * Method to fetch the Redirect Path and title or Navigation Title
	 *
	 * @param pagePojo
	 * @param isParent
	 * @param pageTitle
	 * @param navTitle
	 * @param pageResource
	 * @param isRootPage
	 * @param pagePropMap
	 * @return PagePojo containing the navigation details
	 */
	private NavigationParentPagePojo setNavigationDetails(NavigationParentPagePojo pagePojo, boolean isParent, String pageTitle, String navTitle,
			Resource pageResource, boolean isRootPage, HashMap<String, String> pagePropMap) {
		LOGGER.info("setNavigationDetails() method of SiteNavigationService class --> started");
		String redirectPath = pagePropMap.get(REDIRECTPATH);
		LOGGER.debug("redirectPath is {}", redirectPath);
		String path = pagePropMap.get(PAGEPATH);
		LOGGER.debug("path is {}", path);
		String currentPath = pagePropMap.get(CURRENTPATH);
		LOGGER.debug("currentPath is {}", currentPath);
		String redirectTargetOption =  pagePropMap.get(REDIRECTTARGETOPTION);
		LOGGER.debug("redirectTargetOption is {}", redirectTargetOption);
		if (!redirectPath.isEmpty()) {
			LOGGER.debug("redirectPath is empty");
			if (Boolean.TRUE.equals(isParent) && currentPath.contains(path)) {
				pagePojo.setPageActive(true);
			}
			pagePojo.setIsRedirect(true);
			pagePojo.setUrlTarget(redirectTargetOption);
			pagePojo.setPageUrl(GlobalUtils.checkLink(redirectPath, pageResource));
		} else {
			pagePojo.setPageActive(getCurrentPageActiveValue(path, isParent, currentPath, pageResource, isRootPage));
			pagePojo.setPageUrl(GlobalUtils.checkLink(path, pageResource));
		}
		if (Objects.nonNull(navTitle)) {
			LOGGER.debug("navTitle is not null");
			pagePojo.setPageName(navTitle);
		} else {
			pagePojo.setPageName(pageTitle);
		}
		LOGGER.debug("pagePojo is {}", pagePojo);
		LOGGER.info("setNavigationDetails() method of SiteNavigationService class --> ended");
		return pagePojo;
	}

	/**
	 * @param isParent
	 * @param path
	 * @param isRootPage
	 * @param currentPath
	 * @param resource
	 */
	private boolean getCurrentPageActiveValue(String path, boolean isParent, String currentPath,
			Resource resource, Boolean isRootPage) {
		LOGGER.info("getCurrentPageActiveValue() method of SiteNavigationService class --> started");
		boolean isPageActive = false;
		if (Boolean.TRUE.equals(isRootPage)) {
			String rootPagepath = GlobalUtils.checkLink(path, resource);
			LOGGER.debug("rootPagepath is {}", rootPagepath);
			if (isParent && currentPath.equals(rootPagepath)) {
				isPageActive = true;
			}
		} else {
			if (isParent && currentPath.contains(path)) {
				isPageActive = true;
			}
		}
		LOGGER.debug("isPageActive is {}", isPageActive);
		LOGGER.info("getCurrentPageActiveValue() method of SiteNavigationService class --> ended");
		return isPageActive;
	}



    /** This method will fetch the navigation details for provided root page path and depth
     * @param depth
     * @param checkRoot
     * @param rootPage
     * @param currentPath
     * @return navItemsList containing the navigation details
     */
    public List<NavigationParentPagePojo> getNavigationDetails(Page rootPage, int depth, Boolean checkRoot,
            String currentPath) {
        LOGGER.info("Start of getNavigationDetails in the SiteNavigationService");
        List<NavigationParentPagePojo> navItemsList = new ArrayList<>();
        if (Boolean.TRUE.equals(checkRoot)) {
            LOGGER.debug("Adding rootpage in the list");
            navItemsList.add(preparePojoForPage(rootPage, currentPath, true, true));
        }
        fetchChildPageDetails(rootPage, navItemsList, depth, currentPath);
        LOGGER.info("End of getNavigationDetails in the SiteNavigationService");
        return navItemsList;
    }

    /** This method will fetch the navigation links for all the depth levels and add in the final nav items list
     * @param depth
     * @param navItemsList
     * @param rootPage
     * @param currentPath
     */
    private void fetchChildPageDetails(Page rootPage, List<NavigationParentPagePojo> navItemsList, int depth,
            String currentPath) {
        LOGGER.info("Start of fetchChildPageDetails in the SiteNavigationService");
        List<NavigationBasePagePojo> childItemsList;
        if (Objects.nonNull(rootPage)) {
            LOGGER.debug("rootPage is not null");
            Iterator<Page> rootPageIterator = rootPage.listChildren(new PageFilter(), false);
            LOGGER.debug("Depth value in fetchChildPageDetails is {} ",depth);
            while (rootPageIterator.hasNext()) {
                int actualDepth = depth-1;
                childItemsList = new ArrayList<>();
                Page levelOneChildPage = rootPageIterator.next();
                NavigationParentPagePojo childPagePojo = preparePojoForPage(levelOneChildPage, currentPath, true,
                        false);
                if(actualDepth>=1) {
                    iterateChildPages(levelOneChildPage, currentPath, actualDepth,childItemsList);
                }
                if (!childItemsList.isEmpty()) {
                    LOGGER.debug("Child Item List is not empty {}", childItemsList);
                    childPagePojo.setChildPageList(childItemsList);
                }
                LOGGER.debug("ChildPagePojo is {}", childPagePojo);
                navItemsList.add(childPagePojo);
                LOGGER.debug("Final List of navigation items is {} ", navItemsList);
            }
        }
        LOGGER.info("End of fetchChildPageDetails in the SiteNavigationService");
    }

    /** This method will iterate if depth is greater than one and update the navigation list object accordingly.
     * @param depth
     * @param childItemsList
     * @param childPage
     * @param currentPath
     */
    private void iterateChildPages(Page childPage, String currentPath, int depth,
            List<NavigationBasePagePojo> childItemsList) {
        LOGGER.info("Start of iterateChildPages method in the SiteNavigationService");
            Iterator<Page> childPageIterator  = childPage.listChildren(new PageFilter(),false);
            List<NavigationBasePagePojo> nextLevelChildPageList;
            while (childPageIterator.hasNext()) {
                nextLevelChildPageList=new ArrayList<>();
                Page levelTwoChildPage = childPageIterator.next();
                NavigationParentPagePojo nextLevelChildPagePojo = preparePojoForPage(levelTwoChildPage, currentPath, false,
                        false);
                    LOGGER.debug("Path of next level child page is {}",levelTwoChildPage.getPath());
                if (depth > 1) {
                    iterateChildPages(levelTwoChildPage, currentPath, depth, nextLevelChildPageList);
                }
                if (!nextLevelChildPageList.isEmpty()) {
                    nextLevelChildPagePojo.setChildPageList(nextLevelChildPageList);
                }
                childItemsList.add(nextLevelChildPagePojo);
                LOGGER.debug("Updated Child item list is {}",childItemsList);
            }
            depth--;
            LOGGER.debug("End of iterateChildPages method in the SiteNavigationService");

    }
}