package com.mattel.informational.core.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.informational.core.constants.Constants;

public class InformationalHelper extends WCMUsePojo {
	private static final Logger LOGGER = LoggerFactory.getLogger(InformationalHelper.class);
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
	  LOGGER.info("checkLink method of InformationalHelper -> starts");
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
				LOGGER.debug("Final transformed URL is : {}", text);
				LOGGER.info("checkLink method of InformationalHelper-> end");
				return text;
			} else {
			    LOGGER.debug("Final transformed URL is : {}", text);
			    LOGGER.info("checkLink method of InformationalHelper -> end");
				return text;
			}
		}
		LOGGER.debug("Final transformed URL is : {}", text);
		LOGGER.info("checkLink method of InformationalHelper -> end");
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
	    LOGGER.info("checkResolverMapping method of InformationalHelper -> starts");
		if (Objects.nonNull( resolver)) {
			text = resolver.map(text);
		}
		if (text.startsWith(Constants.SLASH_CONTENT)) {
			text = text + Constants.DOT_HTML;
		}
		LOGGER.debug("Resolver Mapped URL is : {}", text);
		LOGGER.info("checkResolverMapping method of InformationalHelper -> end");
		return text;
	}

	/**
	 * 
	 * @param tempURL
	 * @param resolver
	 * @return
	 */
	private static String checkHashUrl(String[] tempURL, ResourceResolver resolver) {
	    LOGGER.info("checkHashUrl method of InformationalHelper -> starts");
		String url = tempURL[0];
		String param = tempURL[1];
		String hashText = checkResolverMapping(url, resolver);
		if (StringUtils.isNotBlank(param)) {
			hashText = hashText + Constants.HASH + param;
			LOGGER.debug("checkHashUrl is : {}", hashText);
		}
		LOGGER.info("checkHashUrl method of InformationalHelper -> end");
		return hashText;
	}

	/**
	 * @param currentPagePath
	 * @return boolean value to shift the index to the left for the sites having no
	 *         parent brand name
	 */
	public static int leftIndexShiftForSiteWOParentName(Resource resource) {
	    LOGGER.info("leftIndexShiftForSiteWOParentName method of InformationalHelper -> starts");
		String path = resource.getPath();
		LOGGER.debug("Resource Path : {}", path);
		int index = path.indexOf(Constants.SLASH_JCR_CONTENT);
		path = path.substring(0, index);
		if (!path.contains(Constants.EX_FRAGMENTS)) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (Objects.nonNull(pageManager) && Objects.nonNull(pageManager.getPage(path))) {
				Page currentPage = pageManager.getPage(path);
				if (!currentPage.getAbsoluteParent(2).hasChild(Constants.LANG_MASTERS)) {
				    LOGGER.info("leftIndexShiftForSiteWOParentName method of InformationalHelper -> End");
					return -1;
				}
			}
		}
		LOGGER.info("leftIndexShiftForSiteWOParentName method of InformationalHelper-> End");
		return 0;
	}

	/**
	 * method returns the BrandName of the currentPage/Site
	 * 
	 * @param resource
	 * @return this method returns current brand name
	 */
	public static String getBrandName(Resource resource) {
		LOGGER.info("getBrandName method of InformationalHelper -> starts");
		String path = resource.getPath();
		LOGGER.debug("Resource Path : {}", path);
		int index = path.indexOf(Constants.SLASH_JCR_CONTENT);
		path = path.substring(0, index);
		if (!path.contains(Constants.EX_FRAGMENTS)) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (Objects.nonNull(pageManager) && Objects.nonNull(pageManager.getPage(path))) {
				Page currentPage = pageManager.getPage(path);
				if (currentPage.getAbsoluteParent(2).hasChild(Constants.LANG_MASTERS)) {
				    LOGGER.info("getBrandName method of InformationalHelper ->end");
					return currentPage.getAbsoluteParent(2).getName();
				} else if (currentPage.getAbsoluteParent(1).hasChild(Constants.LANG_MASTERS)) {
				    LOGGER.info("getBrandName method of InformationalHelper ->end");
				    return currentPage.getAbsoluteParent(1).getName();
				}
			}
		} else if (path.contains(Constants.EX_FRAGMENTS)) {
			String[] pathArray = path.split(Constants.SLASH);
			LOGGER.info("getBrandName method of InformationalHelper-> end");
			return pathArray[3];
		} else {
		    LOGGER.info("getBrandName method of InformationalHelper-> end");
			return StringUtils.EMPTY;
		}
		LOGGER.info("getBrandName method of InformationalHelper -> end");
		return StringUtils.EMPTY;
	}

	/**
	 * 
	 * @param resource
	 * @return this method returns the parent brand name
	 */
	public static String getParentBrandName(Resource resource) {
		LOGGER.info("getParentBrandName method of InformationalHelper -> starts");
		String parentPath = resource.getPath();
		LOGGER.debug("Parent Resource Path : {}", parentPath);
		int index = parentPath.indexOf(Constants.SLASH_JCR_CONTENT);
		parentPath = parentPath.substring(0, index);
		if (!parentPath.contains(Constants.EX_FRAGMENTS)) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (Objects.nonNull(pageManager) && Objects.nonNull(pageManager.getPage(parentPath))) {
				Page currentPage = pageManager.getPage(parentPath);
				if (currentPage.getAbsoluteParent(2).hasChild(Constants.LANG_MASTERS)) {
				    LOGGER.info("getParentBrandName method of InformationalHelper-> end");
				    return currentPage.getAbsoluteParent(1).getName();
				} else {
				    LOGGER.info("getParentBrandName method of InformationalHelper-> end");
					return StringUtils.EMPTY;
				}
			}
		} else if (parentPath.contains(Constants.EX_FRAGMENTS)) {
			splitPath(parentPath);
		} else {
		    LOGGER.info("getParentBrandName method of InformationalHelper -> end");
			return StringUtils.EMPTY;
		}
		LOGGER.info("getParentBrandName method of InformationalHelper -> end");
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
		LOGGER.info("getPageLocale method of InformationalHelper -> starts");
		String locale = StringUtils.EMPTY;
		String[] pagePathArray = pagePath.split(Constants.SLASH);
		for (String pagepath : pagePathArray) {
			if (pagepath.contains(Constants.HYPHEN) && pagepath.indexOf('-') == 2) {
				locale = pagepath;
			}
		}
		LOGGER.info("getPageLocale method of InformationalHelper -> end");
		return locale;
	}

	/**
	 * getting country and locale
	 * 
	 * @param currentPagePath
	 * @return countryLocalePath
	 */
	public static String getRelativePath(String currentPagePath, Resource resource) {
		LOGGER.info("getRelativePath method of InformationalHelper -> starts");

		String sitesRootPath;
		String brandName;
		brandName = InformationalHelper.getBrandName(resource);

		int indexBrandName = currentPagePath.indexOf(brandName);
		sitesRootPath = currentPagePath.substring(0, indexBrandName + brandName.length());

		LOGGER.debug("SiteRootPath : {}", sitesRootPath);
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
		LOGGER.debug("TempString : {}", tempString);

		String countryLocalePath = StringUtils.EMPTY;
		if (StringUtils.isNotBlank(relativePath)) {
			countryLocalePath = tempString.substring(0, tempString.indexOf(relativePath));
		}
		LOGGER.debug("CountryLocalePath : {}", countryLocalePath);
		LOGGER.info("getRelativePath method of InformationalHelper -> end");
		return countryLocalePath;
	}

	/**
	 * @param nodeName
	 *            Node Name
	 * @param resourceResolver
	 *            Resource Resolver
	 * @return isHideNavTrue flag
	 */
	public static SearchResult getCountryNodesByLanguage(String sourcePath, Session session,
			QueryBuilder queryBuilder) {
		LOGGER.info("getCountryNodesByLanguage method of InformationalHelper -> starts, SourcePath : {}", sourcePath);
		Map<String, String> querrymap = new HashMap<>();
		querrymap.put("path", sourcePath);
		querrymap.put("type", "cq:PageContent");
		querrymap.put("property", "jcr:language");
		querrymap.put("property.operation", "exists");
		querrymap.put("p.nodedepth", "2");
		querrymap.put("p.hits", "full");
		querrymap.put("p.limit", "-1");
		Query pageQuery = queryBuilder.createQuery(PredicateGroup.create(querrymap), session);
		LOGGER.info("getCountryNodesByLanguage method of InformationalHelper -> end, Query Generated, Result will be returned");
		return Objects.nonNull( pageQuery) ? pageQuery.getResult() : null;
	}

	public String getPathURL() {
		return pathURL;
	}
}
