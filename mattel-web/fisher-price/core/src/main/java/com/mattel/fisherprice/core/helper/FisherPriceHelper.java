package com.mattel.fisherprice.core.helper;

import java.util.Objects;


import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.fisherprice.core.constants.Constants;

public class FisherPriceHelper extends WCMUsePojo {
	private static final Logger LOGGER = LoggerFactory.getLogger(FisherPriceHelper.class);
	private String pathURL;

	@Override
	public void activate() throws Exception {
		String text = get("text", String.class);
		Resource res = getResource();
		pathURL = checkLink(text, res);
	}

	/**
	 * Method will return the formatted URL based on the conditions
	 * 
	 * @param text
	 * @return
	 */
	public static String checkLink(String text, Resource resource) {
		ResourceResolver resolver = null;
		if (Objects.nonNull( resource)) {
			resolver = resource.getResourceResolver();
		}
		if (Objects.nonNull(text)) {
			if (text.startsWith(Constants.SLASH_CONTENT) && !text.startsWith(Constants.SLASH_CONTENT_SLASH_DAM)) {
				if (text.contains(Constants.HASH)) {
					String[] tempURL = text.split(Constants.HASH);
					if (tempURL.length > 1) {
						text = checkHashUrl(tempURL, resolver);
					}
				} else {
					text = checkResolverMapping(text, resolver);
				}
				return text;
			} else {
				return text;
			}
		}
		return text;
	}

	/**
	 * method appends dot html to path which contains /content
	 * 
	 * @param text
	 * @param resolver
	 * @return
	 */
	public static String checkResolverMapping(String text, ResourceResolver resolver) {
		if (Objects.nonNull( resolver)) {
			text = resolver.map(text);
		}
		if (text.startsWith(Constants.SLASH_CONTENT)) {
			text = text + Constants.DOT_HTML;
		}
		return text;
	}

	/**
	 * 
	 * @param tempURL
	 * @param resolver
	 * @return
	 */
	private static String checkHashUrl(String[] tempURL, ResourceResolver resolver) {
		String url = tempURL[0];
		String param = tempURL[1];
		String hashText = checkResolverMapping(url, resolver);
		if (StringUtils.isNotBlank(param)) {
			hashText = hashText + Constants.HASH + param;
		}
		return hashText;
	}

	/**
	 * @param resource
	 * @return boolean value to shift the index to the left for the sites having no
	 *         parent brand name
	 */
	public static int leftIndexShiftForSiteWOParentName(Resource resource) {
		String path = resource.getPath();
		int index = path.indexOf(Constants.SLASH_JCR_CONTENT);
		path = path.substring(0, index);
		if (!path.contains(Constants.EX_FRAGMENTS)) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (Objects.nonNull(pageManager) && Objects.nonNull(pageManager.getPage(path))) {
				Page currentPage = pageManager.getPage(path);
				if (!currentPage.getAbsoluteParent(2).hasChild(Constants.LANG_MASTERS)) {
					return -1;
				}
			}
		}
		return 0;
	}

	/**
	 * method returns the BrandName of the currentPage/Site
	 * 
	 * @param resource
	 * @return this method returns current brand name
	 */
	public static String getBrandName(Resource resource) {
		LOGGER.info("getBrandName method of FisherPriceHelper starts");
		String path = resource.getPath();
		int index = path.indexOf(Constants.SLASH_JCR_CONTENT);
		path = path.substring(0, index);
		if (!path.contains(Constants.EX_FRAGMENTS)) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (Objects.nonNull(pageManager) && Objects.nonNull(pageManager.getPage(path))) {
				Page currentPage = pageManager.getPage(path);
				if (currentPage.getAbsoluteParent(2).hasChild(Constants.LANG_MASTERS)) {
					return currentPage.getAbsoluteParent(2).getName();
				} else if (currentPage.getAbsoluteParent(1).hasChild(Constants.LANG_MASTERS)) {
					return currentPage.getAbsoluteParent(1).getName();
				}
			}
		} else if (path.contains(Constants.EX_FRAGMENTS) && path.contains(Constants.RESCUE_HEROES)) {
			String[] pathArray = path.split(Constants.SLASH);
			return pathArray[4];
		} else if (path.contains(Constants.EX_FRAGMENTS)) {
			String[] pathArray = path.split(Constants.SLASH);
			return pathArray[3];
		} else {
			return StringUtils.EMPTY;
		}
		LOGGER.info("getBrandName method of FisherPriceHelper end");
		return StringUtils.EMPTY;
	}

	/**
	 * 
	 * @param resource
	 * @return this method returns the parent brand name
	 */
	public static String getParentBrandName(Resource resource) {
		LOGGER.info("getBrandName method of FisherPriceHelper starts");
		String parentPath = resource.getPath();
		int index = parentPath.indexOf(Constants.SLASH_JCR_CONTENT);
		parentPath = parentPath.substring(0, index);
		if (!parentPath.contains(Constants.EX_FRAGMENTS)) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (Objects.nonNull(pageManager) && Objects.nonNull(pageManager.getPage(parentPath))) {
				Page currentPage = pageManager.getPage(parentPath);
				if (currentPage.getAbsoluteParent(2).hasChild(Constants.LANG_MASTERS)) {
					return currentPage.getAbsoluteParent(1).getName();
				} else {
					return StringUtils.EMPTY;
				}
			}
		} else if (parentPath.contains(Constants.EX_FRAGMENTS) && parentPath.contains(Constants.RESCUE_HEROES)) {
			splitPath(parentPath);
		} else if (parentPath.contains(Constants.EX_FRAGMENTS)) {
			splitPath(parentPath);
		} else {
			return StringUtils.EMPTY;
		}
		LOGGER.info("getBrandName method of FisherPriceHelper end");
		return StringUtils.EMPTY;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	private static String splitPath(String path) {
		String[] pathArray = path.split(Constants.SLASH);
		return pathArray[3];
	}

	/**
	 * @param pagePath
	 * @return This method returns page locale
	 */
	public static String getPageLocale(String pagePath) {
		LOGGER.info("getPageLocale method of FisherPriceHelper starts");
		String locale = StringUtils.EMPTY;
		String[] pagePathArray = pagePath.split(Constants.SLASH);
		for (String pagepath : pagePathArray) {
			if (pagepath.contains(Constants.HYPHEN) && pagepath.indexOf('-') == 2) {
				locale = pagepath;
			}
		}
		LOGGER.info("getPageLocale method of FisherPriceHelper end");
		return locale;
	}

	/**
	 * getting country and locale
	 * 
	 * @param currentPagePath
	 * @return countryLocalePath
	 */
	public static String getRelativePath(String currentPagePath, Resource resource) {
		LOGGER.info("getRelativePath method of FisherPriceHelper starts");

		String sitesRootPath;
		String brandName;
		brandName = FisherPriceHelper.getBrandName(resource);

		int indexBrandName = currentPagePath.indexOf(brandName);
		sitesRootPath = currentPagePath.substring(0, indexBrandName + brandName.length());

		LOGGER.debug("sitesRootPath value of FisherPriceHelper is {}", sitesRootPath);
		String relativePath = StringUtils.EMPTY;
		PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
		if (Objects.nonNull( pageManager)) {
			Page currentPage = pageManager.getContainingPage(resource);
			if (currentPage.getDepth() < 6 + leftIndexShiftForSiteWOParentName(resource)) {
				return StringUtils.EMPTY;
			} else {
				String homePageName = currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName(resource))
						.getName();
				relativePath = currentPagePath.substring(currentPagePath.indexOf(Constants.SLASH + homePageName),
						currentPagePath.length() - 1);
			}
		}
		String tempString = currentPagePath.replace(sitesRootPath, StringUtils.EMPTY);
		LOGGER.debug("tempString value of FisherPriceHelper is {}", tempString);

		String countryLocalePath = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(relativePath)) {
			countryLocalePath = tempString.substring(0, tempString.indexOf(relativePath));
		}
		LOGGER.debug("countryLocalePath value of FisherPriceHelper is {}", countryLocalePath);
		LOGGER.info("getRelativePath method of FisherPriceHelper end");
		return countryLocalePath;
	}

	public String getPathURL() {
		return pathURL;
	}
}
