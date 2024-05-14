package com.mattel.ecomm.core.helper;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.coreservices.core.constants.Constant;

public class EcommHelper extends WCMUsePojo {
	private static final Logger LOGGER = LoggerFactory.getLogger(EcommHelper.class);
	private String pathURL;
	private boolean isRootShopPage;

	@Override
	public void activate() throws Exception {
		String text = get("text", String.class);
		Resource res = getResource();
		pathURL = checkLink(text, res);
		isRootShopPage = checkIfRootShopPage(text, res);
	}

	/**
	 * Method will return true if root page is shop landing page.
	 * @param pagePath pagePath
	 * @param res component Resource
	 * @return isRootPage flag
	 */
    public static boolean checkIfRootShopPage(String pagePath, Resource res) {
        LOGGER.info("checkIfRootShopPage - Start");
        LOGGER.debug("pagePath is - {}", pagePath);
        boolean isRootPage = false;
        if (Objects.nonNull(res)) {
            ResourceResolver resolver = res.getResourceResolver();
            Resource pageRes = resolver.resolve(pagePath);
            if (pageRes.getPath().startsWith("/content/")) {
                PageManager pgMgr = resolver.adaptTo(PageManager.class);
                if (Objects.nonNull(pgMgr) && Objects.nonNull(pgMgr.getPage(pageRes.getPath()))) {
                    Page page = pgMgr.getPage(pageRes.getPath());
                    Resource pageContentRes = page.getContentResource();
                    ValueMap vmap = pageContentRes.getValueMap();
                    if (Objects.nonNull(vmap.get("isRootShopPage", String.class))
                            && "true".equalsIgnoreCase(vmap.get("isRootShopPage", String.class))) {
                        isRootPage = true;
                    }
                }
            }
        }
        LOGGER.debug("checkIfRootShopPage flag is - {}", isRootPage);
        LOGGER.info("checkIfRootShopPage - End");
        return isRootPage;
    }

  
	/**
	 * Method will return the formatted URL based on the conditions
	 * 
	 * @param url
	 *     URL
	 * @param resource
	 *     Resource object : used to get resource resolver
	 * @return
	 */
	public static String checkLink(String url, Resource resource) {
		LOGGER.debug("Check Link Method Start");
		ResourceResolver resolver = null;
		if (null != resource) {
			resolver = resource.getResourceResolver();
		}
		if (url != null) {
		  String resolvedUrl = StringUtils.EMPTY;
			if (url.startsWith("/content") && !url.startsWith("/content/dam")) {
				if (url.contains("#")) {
					String[] tempURL = url.split("#");
					if (tempURL.length > 1) {
					  resolvedUrl = checkHashUrl(tempURL, resolver);
					}
				} else {
				  resolvedUrl = checkResolverMapping(url, resolver);
				}
				LOGGER.debug("Resolved URL : {}",resolvedUrl);
				return resolvedUrl;
			} else {
				LOGGER.debug("Link : {}",url);
				return url;
			}
		}
		LOGGER.debug("Link : Null");
		LOGGER.debug("Check Link Method End");
		return url;
	}

  /**
   * Method will return the formatted URL based on the conditions
   * 
   * @param url
   *    URL
   * @param resolver
   *    ResourceResolver 
   * @return
   */
  public static String checkResolverMapping(String url, ResourceResolver resolver) {

    LOGGER.info("checkResolverMapping Method Start");
    if (null != resolver) {
      url = resolver.map(url);
    }
    if (url.startsWith("/content")) {
      url = url + ".html";
    }
    LOGGER.debug("Url : {} ", url);
    LOGGER.info("checkResolverMapping Method End");
    return url;
  }

	/**
	 * 
	 * @param tempURL
	 * @param resolver
	 * @return
	 */
	private static String checkHashUrl(String[] tempURL, ResourceResolver resolver) {
		LOGGER.info("checkHashUrl Method Start");
		String url = tempURL[0];
		String param = tempURL[1];
		String hashText = checkResolverMapping(url, resolver);
		if (StringUtils.isNotBlank(param)) {
			hashText = hashText + "#" + param;
		}
		LOGGER.info("checkHashUrl Method End : {}",hashText);
		return hashText;
	}

	/**
	 * @param nodeValues
	 * @param properties
	 * @return
	 */
	public static String getValueMapNodeValue(ValueMap nodeValues, String properties) {
		return nodeValues.containsKey(properties) ? nodeValues.get(properties, String.class) : "";
	}

	/**
	 * @param navigationalLinks
	 * @return
	 */
	public static Boolean isNullOrEmpty(String[] navigationalLinks) {
		return navigationalLinks == null || navigationalLinks.length < 1;
	}

	/**
	 * Method to check for the property when an object has been Passed
	 * 
	 * @param value
	 * @return
	 */
	public static String checkPropertyObject(Object value) {
		return value != null ? value.toString() : "";
	}

	/**
	 * Method to fetch Property Value when Asset is Passed as Params
	 * 
	 * @param videoAsset
	 * @param property
	 * @return
	 */
	public static String checkForProperty(Asset videoAsset, String property) {
		return videoAsset.getMetadata(property) != null ? videoAsset.getMetadataValue(property) : "";
	}

	/**
	 * 
	 * @param node
	 * @param property
	 * @return This method checks and returns the property if available
	 * @throws RepositoryException
	 */
	public static String checkForProperty(Node node, String property) throws RepositoryException {
		return node.hasProperty(property) ? node.getProperty(property).getString() : "";

	}

	/**
	 * 
	 * @param page
	 * @param property
	 * @param defaultBoolean
	 * @return
	 */
	public static Boolean checkBooleanProperty(Page page, String property, Boolean defaultBoolean) {
		return page.getProperties().get(property, Boolean.class) != null
				? page.getProperties().get(property, Boolean.class)
				: defaultBoolean;
	}

	/**
	 * 
	 * @return pathURL
	 */
	public String getPathURL() {
		return pathURL;
	}
	
	/**
	 * 
	 * @return isRootShopPage
	 */
	public boolean isRootShopPage() {
    return isRootShopPage;
  }

  /**
	 * @param result
	 */
	public static void getLeakingResResolver(SearchResult result) {
		Iterator<Resource> resources = result.getResources();
		if (resources.hasNext()) {
			ResourceResolver leakingResResolver = resources.next().getResourceResolver();
			if (leakingResResolver.isLive()) {
				leakingResResolver.close();
			}
		}
	}

	/**
	 * fetch productSKUID from current url
	 * 
	 * @param request
	 * @return
	 */
	public static String fetchProductSKUId(SlingHttpServletRequest request) {
		LOGGER.info("fetchProductSKUId Method Start");
		String productSKUId = StringUtils.EMPTY;
		String resourcePath = request.getRequestURI();
		if (StringUtils.isNotEmpty(resourcePath) && !resourcePath.contains("jcr:content")
				&& !resourcePath.contains("/bin")) {
			int lastIndex = resourcePath.lastIndexOf('/');
			int htmlIndex = resourcePath.lastIndexOf(".html");
			productSKUId = resourcePath.substring(lastIndex + 1, htmlIndex);
			productSKUId = getProductSKUID(productSKUId);
		} else if (StringUtils.isNotEmpty(resourcePath) && !resourcePath.contains("/bin")) {
			String[] jcrContentIndex = resourcePath.split("/jcr:content");
			String productPagePath = jcrContentIndex[0];
			int lastIndex = productPagePath.lastIndexOf('/');
			productSKUId = productPagePath.substring(lastIndex + 1, productPagePath.length());
			productSKUId = getProductSKUID(productSKUId);
		} else if (StringUtils.isNotEmpty(resourcePath) && resourcePath.contains("/bin")) {
			resourcePath = request.getParameter("currentPath");
			int lastIndex = resourcePath.lastIndexOf('/');
			int htmlIndex = resourcePath.length();
			productSKUId = resourcePath.substring(lastIndex + 1, htmlIndex);
			productSKUId = getProductSKUID(productSKUId);
		}
		LOGGER.debug("fetchProductSKUId : {} ", productSKUId);
		LOGGER.info("fetchProductSKUId Method End");
		return productSKUId;
	}
	
	private static String getProductSKUID(String productSKUId) {
		String[] sepByDot = productSKUId.split("\\.");
		productSKUId = sepByDot[0];
		String[] sepByHyphen = productSKUId.split(Constant.HYPHEN);
		productSKUId = sepByHyphen[sepByHyphen.length - 1].toUpperCase();		
		LOGGER.debug("getProductSKUID : {} ", productSKUId);
		return productSKUId;
	}

	/**
	 * @param hit
	 * @param productProperty
	 * @return
	 * @throws RepositoryException
	 */
	public static String fetchProdCommerceNodeProperties(Hit hit, String productProperty) throws RepositoryException {
		return null != hit.getProperties().get(productProperty, String.class)
				? hit.getProperties().get(productProperty, String.class)
				: "";

	}

	/**
	 * 
	 * @param resource
	 * @return this method returns current brand name
	 */
	public static String getBrandName(Resource resource) {
		LOGGER.info("getBrandName method of Ecomm Helper starts");
		String path = resource.getPath();
		int index = path.indexOf("/jcr:content");
		path = index >= 0 ? path.substring(0, index) : path;
		if (!path.contains("experience-fragments")) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (pageManager != null && pageManager.getPage(path) != null) {
				Page currentPage = pageManager.getPage(path);
				if (currentPage.getAbsoluteParent(2).hasChild("language-masters")) {
					return currentPage.getAbsoluteParent(2).getName();
				} else if (currentPage.getAbsoluteParent(1).hasChild("language-masters")) {
					return currentPage.getAbsoluteParent(1).getName();
				}
			}
		} else if (path.contains("experience-fragments")) {
			String[] pathArray = path.split("/");
			return pathArray[3];
		} else {
			return StringUtils.EMPTY;
		}
		LOGGER.info("getBrandName method of Ecommm Helper end");
		return StringUtils.EMPTY;
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
		brandName = getBrandName(resource);

		int indexBrandName = currentPagePath.indexOf(brandName);
		sitesRootPath = currentPagePath.substring(0, indexBrandName + brandName.length());

		LOGGER.debug("sitesRootPath value of FisherPriceHelper is {}", sitesRootPath);
		String relativePath = "";
		PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
		if (null != pageManager) {
			Page currentPage = pageManager.getContainingPage(resource);
			if (currentPage.getDepth() < 6 + leftIndexShiftForSiteWOParentName(resource)) {
				return "";
			} else {
				String homePageName = currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName(resource))
						.getName();
				relativePath = currentPagePath.substring(currentPagePath.indexOf(Constant.SLASH + homePageName),
						currentPagePath.length() - 1);
			}
		}
		String tempString = currentPagePath.replace(sitesRootPath, "");
		LOGGER.debug("tempString value of FisherPriceHelper is {}", tempString);

		String countryLocalePath = "";
		if (StringUtils.isNotBlank(relativePath)) {
			countryLocalePath = tempString.substring(0, tempString.indexOf(relativePath));
		}
		LOGGER.debug("countryLocalePath value of FisherPriceHelper is {}", countryLocalePath);
		LOGGER.info("getRelativePath method of FisherPriceHelper end");
		return countryLocalePath;
	}

	/**
	 * @param currentPagePath
	 * @return boolean value to shift the index to the left for the sites having no
	 *         parent brand name
	 */
	public static int leftIndexShiftForSiteWOParentName(Resource resource) {
		LOGGER.info("leftIndexShiftForSiteWOParentName Method Start");
		String path = resource.getPath();
		int index = path.indexOf(Constant.SLASH_JCR_CONTENT);
		path = path.substring(0, index);
		if (!path.contains(Constant.EX_FRAGMENTS)) {
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (pageManager != null && pageManager.getPage(path) != null) {
				Page currentPage = pageManager.getPage(path);
				if (!currentPage.getAbsoluteParent(2).hasChild(Constant.LANG_MASTERS)) {
					return -1;
				}
			}
		}
		LOGGER.info("leftIndexShiftForSiteWOParentName Method End");
		return 0;
	}

	/**
	 * @param page
	 * @param property
	 * @return
	 */
	public static String productPageProperties(Page page, String property) {
		return null != page.getProperties().get(property, String.class)
				? page.getProperties().get(property, String.class)
				: "";
	}

	/**
	 * @param nodeProperties
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	public static Boolean getBooleanValuefromValueMap(ValueMap nodeProperties, String property, Boolean defaultValue) {
		return null != nodeProperties.get(property, Boolean.class) ? nodeProperties.get(property, Boolean.class)
				: defaultValue;

	}

	
	
	/**
	 * @param pagePath
	 * @return This method returns page locale
	 */
	public static String getPageLocale(String pagePath) {
		LOGGER.info("getPageLocale method of EcommHelper starts");
		String locale = "";
		String[] pagePathArray = pagePath.split("/");
		for (String pagepath : pagePathArray) {
			if (pagepath.contains("-") && pagepath.indexOf('-') == 2) {
				locale = pagepath;
				break;
			}
		}
		LOGGER.debug("locale : {}",locale);
		LOGGER.info("getPageLocale method of EcommHelper end");
		return locale;
	}

    /**
     * @param name
     * @return This method returns page name in camel case
     */
	public static String toCamelCase(String name) {
		LOGGER.info("toCamelCase method of EcommHelper starts");
		StringBuilder linkName = new StringBuilder(name.length());
		for (String title : name.split(" ")) {
			if (!title.isEmpty()) {
				linkName.append(Character.toUpperCase(title.charAt(0)));
				linkName.append(title.substring(1).toLowerCase());
			}
			if (linkName.length() != name.length()) {
				linkName.append(" ");
			}
		}
		LOGGER.info("toCamelCase method of EcommHelper end");
		return linkName.toString();
	}
	
	/**
	 * @param source
	 * @return String with HTML Tags for Special Charcters
	 */
	public static String convertSpecialCharacters (String source) {   
		LOGGER.debug("Convert String with Special Characters Method Start :: {}", source);
		Optional<String> src = Optional.ofNullable(source);		
		if (src.isPresent()) {
	    	source = src.get().replaceAll( "\u00B0", "<sup>\u00B0</sup>" ).replaceAll( "\u00a9", "<sup>\u00a9</sup>").replaceAll("\u00AE", "<sup>\u00AE</sup>" ).replaceAll( "\u2122", "<sup>\u2122</sup>" );
	    }else {
	    	source = StringUtils.EMPTY;
	    }
		LOGGER.debug("Converted String with Special Characters Method End :: {}", source);
	    return source;
	}
}