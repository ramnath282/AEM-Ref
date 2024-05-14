package com.mattel.play.core.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.constants.Constants;
import com.mattel.play.core.utils.PlaySiteConfigurationUtils;
import com.mattel.play.core.utils.PropertyReaderUtils;

public class PlayHelper extends WCMUsePojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayHelper.class);
    private static final String EXPERIENCE_FRAGMENTS = "experience-fragments";
    private static final String LANGUAGE_MASTERS = "language-masters";
    private static final String RESCUE_HEROES = "rescue-heroes";
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
        if (null != resource) {
            resolver = resource.getResourceResolver();
        }
        if (text != null && text.startsWith("/content") && !text.startsWith("/content/dam") && !text.contains(".html")) {
            if (text.contains("#")) {
                String[] tempURL = text.split("#");
                if (tempURL.length > 1) {
                    String url = tempURL[0];
                    String param = tempURL[1];
                    text = checkResolverMapping(url, resolver);
                    text = StringUtils.isNotBlank(param) ? text + "#" + param : text;
                }
            } else {
                text = checkResolverMapping(text, resolver);
            }
        }

        return text;
    }

    /**
     * @param text
     * @param resolver
     * @return
     */
    public static String checkResolverMapping(String text, ResourceResolver resolver) {
        if (null != resolver) {
            text = resolver.map(text);
        }
        if (text.startsWith("/content")) {
            text = text + ".html";
        }
        return text;
    }

    /**
     * @param path
     * @return this method returns current brand name for mattel-play sites
     */
    public static String getBrandName(String path) {
        LOGGER.info("getBrandName method of PlayHelper starts");
        String playPath = PropertyReaderUtils.getPlayPath();
        String rescuePath = PlaySiteConfigurationUtils.getRescueRootPath();
        String fpPath = PlaySiteConfigurationUtils.getFpRootPath();
        String playExpFragmentPath = PlaySiteConfigurationUtils.getExpFragmentRootPath();
        String fpExpFragmentPath = PlaySiteConfigurationUtils.getFpExpFragmentRootPath();
        String rescueExpFragmentPath = PlaySiteConfigurationUtils.getRescueExpFgmtRootPath();
        if (path.contains(playPath) && !path.equalsIgnoreCase(playPath + Constants.JCR_CONTENT)) {
            return fetchSubstring(path, playPath);
        } else if (path.contains(playExpFragmentPath)) {
            return fetchSubstring(path, playExpFragmentPath);
        } else if (path.contains(fpExpFragmentPath) && !path.contains(rescueExpFragmentPath) || path.contains(fpPath)) {
            return getFPBrandName(path);
        } else if (path.contains(rescueExpFragmentPath) || path.contains(rescuePath)) {
            return getRescueBrandName(path);
        }
        LOGGER.info("getBrandName method of PlayHelper end");
        return "";
    }

    /**
     * @param resource
     * @return this method returns current brand name for sites other than
     * mattel-play sites
     */
    public static String getBrandName(Resource resource) {
        LOGGER.info("getBrandName method of PlayHelper starts");
        String path = resource.getPath();
        int index = path.indexOf("/jcr:content");
        path = path.substring(0, index);
        if (!path.contains(PlayHelper.EXPERIENCE_FRAGMENTS)) {
            PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
            if (Objects.nonNull(pageManager) && Objects.nonNull(pageManager.getPage(path))) {
                Page currentPage = pageManager.getPage(path);
                if (currentPage.getAbsoluteParent(2).hasChild(PlayHelper.LANGUAGE_MASTERS)) {
                    return currentPage.getAbsoluteParent(2).getName();
                } else if (currentPage.getAbsoluteParent(1).hasChild(PlayHelper.LANGUAGE_MASTERS)) {
                    return currentPage.getAbsoluteParent(1).getName();
                }
            }
        } else if (path.contains(PlayHelper.EXPERIENCE_FRAGMENTS) && path.contains(PlayHelper.RESCUE_HEROES)) {
            String[] pathArray = path.split("/");
            return pathArray[4];
        } else if (path.contains(PlayHelper.EXPERIENCE_FRAGMENTS)) {
            String[] pathArray = path.split("/");
            return pathArray[3];
        } else {
            return "";
        }
        LOGGER.info("getBrandName method of PlayHelper end");
        return "";
    }

    @SuppressWarnings("unused")
    private static String getFPBrandName(String path) {
        LOGGER.info("getFPBrandName method of PlayHelper starts");
        String fpRootPath = PlaySiteConfigurationUtils.getFpRootPath();
        String fpExpFragmentPath = PlaySiteConfigurationUtils.getFpExpFragmentRootPath();
        if (path.contains(fpRootPath) && !path.equalsIgnoreCase(fpRootPath + Constants.JCR_CONTENT)) {
            return fetchSubstring(path, fpRootPath);
        } else if (path.contains(fpExpFragmentPath)) {
            return fetchSubstring(path, fpExpFragmentPath);
        }
        LOGGER.info("getFPBrandName method of PlayHelper end");
        return "";
    }

    private static String fetchSubstring(String path, String startPath) {
        int start = startPath.length();
        int end = path.indexOf('/', start);
        return path.substring(start, end);
    }

    /**
     * @param path
     * @return this method returns current brand name
     */
    public static String getRescueBrandName(String path) {
        LOGGER.info("getRescueBrandName method of PlayHelper starts");
        String playPath = PropertyReaderUtils.getRescuePath();
        String rootExpFragmentPath = PlaySiteConfigurationUtils.getFpExpFragmentRootPath();
        if (path.contains(playPath) && !path.equalsIgnoreCase(playPath + Constants.JCR_CONTENT)) {
            return fetchSubstring(path, playPath);
        } else if (path.contains(rootExpFragmentPath)) {
            return fetchSubstring(path, rootExpFragmentPath);
        }
        LOGGER.info("getRescueBrandName method of PlayHelper end");
        return "";
    }

    /**
     * @param imagePath
     * @param requestResolver
     * @return this method returns current brand name
     */
    public static String getAssetMetadataValue(String imagePath, ResourceResolver requestResolver,
                                               String propertyName) {
        String propertyValue = "";
        if (StringUtils.isNotBlank(imagePath)) {
            Resource imageRes = requestResolver.getResource(imagePath);
            if (null != imageRes) {
                Asset imageAsset = imageRes.adaptTo(Asset.class);
                if (null != imageAsset) {
                    propertyValue = null != imageAsset.getMetadataValue(propertyName)
                            ? imageAsset.getMetadataValue(propertyName)
                            : "";
                }

            }
        }
        return propertyValue;
    }

    /**
     * @param slideCount
     * @param slideshowValueMapping
     * @param brand
     * @return
     */
    public static String getSlideCount(String slideCount, String[] slideshowValueMapping, String brand) {
        if (null != slideshowValueMapping && slideshowValueMapping.length > 0 && null != brand) {
            for (String mapping : slideshowValueMapping) {
                if (mapping.contains(":") && mapping.split(":").length > 1) {
                    String brandName = mapping.split(":")[0];
                    String count = mapping.split(":")[1];
                    if (brand.equals(brandName)) {
                        slideCount = count;
                    }
                }
            }
        }
        return slideCount;
    }

    /**
     * getting country and locale
     *
     * @param currentPagePath
     * @return countryLocalePath
     */
    public static String getRelativePath(String currentPagePath, Resource resource) {
        LOGGER.info("getRelativePath method of PlayHelper starts");

        String sitesRootPath;
        String brandName;
        if (PlayHelper.checkIfSiteisMattelPlay(resource.getPath())) {
            brandName = PlayHelper.getBrandName(resource.getPath());
        } else {
            brandName = PlayHelper.getBrandName(resource);
        }
        if (StringUtils.isNotBlank(brandName) && currentPagePath.contains(PropertyReaderUtils.getPlayPath())) {
            sitesRootPath = PropertyReaderUtils.getPlayPath() + brandName;
        } else if (StringUtils.isNotBlank(PlayHelper.getRescueBrandName(currentPagePath))
                && currentPagePath.contains(PlaySiteConfigurationUtils.getRescueRootPath())) {
            sitesRootPath = PropertyReaderUtils.getRescuePath() + PlayHelper.getRescueBrandName(currentPagePath);
        } else if (StringUtils.isNotBlank(PlayHelper.getRescueBrandName(currentPagePath))
                && !currentPagePath.contains(PlaySiteConfigurationUtils.getRescueRootPath())
                && currentPagePath.contains(PlaySiteConfigurationUtils.getFpRootPath())) {
            String tempRoot = PlaySiteConfigurationUtils.getFpRootPath();
            sitesRootPath = tempRoot.substring(0, tempRoot.length() - 1);
        } else {
            int indexBrandName = currentPagePath.indexOf(brandName);
            sitesRootPath = currentPagePath.substring(0, indexBrandName + brandName.length());
        }

		LOGGER.debug("sitesRootPath value of PlayHelper is {}", sitesRootPath);
		String relativePath = "";
			PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
			if (null != pageManager) {
				Page currentPage = pageManager.getContainingPage(resource);
				String homePageName = currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName(resource))
						.getName();
				relativePath = currentPagePath.substring(currentPagePath.indexOf(Constants.SLASH + homePageName),
						currentPagePath.length() - 1);
			}
		String tempString = currentPagePath.replace(sitesRootPath, "");
		LOGGER.debug("tempString value of PlayHelper is {}", tempString);

        String countryLocalePath = "";
        if (StringUtils.isNotBlank(relativePath)) {
            countryLocalePath = tempString.substring(0, tempString.indexOf(relativePath));
        }
        LOGGER.debug("countryLocalePath value of PlayHelper is {}", countryLocalePath);
        LOGGER.info("getRelativePath method of PlayHelper end");
        return countryLocalePath;

    }

    /**
     * @param pagePath
     * @return This method returns page locale
     */
    public static String getPageLocale(String pagePath) {
        LOGGER.info("getPageLocale method of PlayHelper starts");
        String locale = "";
        String[] pagePathArray = pagePath.split("/");
        for (String pagepath : pagePathArray) {
            if (pagepath.contains("-") && pagepath.indexOf('-') == 2) {
                locale = pagepath;
            }
        }
        LOGGER.info("getPageLocale method of PlayHelper end");
        return locale;
    }

    /**
     * @param pagePath
     * @return This method returns page locale for Rescue Heroes Exp Fragments
     */
    public static String getExpFrLocaleRH(String pagePath) {
        LOGGER.info("getExpFrLocaleRH method of PlayHelper starts");
        String locale = "";
        if (pagePath.contains(PlayHelper.RESCUE_HEROES) && pagePath.contains(PlayHelper.EXPERIENCE_FRAGMENTS)) {
            String[] pagePathArray = pagePath.split("/");
            for (String pagepath : pagePathArray) {
                if (pagepath.contains("_") && pagepath.contains("-") && (pagepath.indexOf('-') - pagepath.indexOf('_')) == 3) {
                    locale = pagepath.substring(pagepath.indexOf('_') + 1);
                }
            }
        }
        LOGGER.info("getExpFrLocaleRH method of PlayHelper end");
        return locale;
    }

    /**
     * @param sourcePath
     * @param session
     * @param queryBuilder
     * @return
     */
    public static SearchResult getCountryNodesByLanguage(String sourcePath, Session session,
                                                         QueryBuilder queryBuilder) {
        LOGGER.info("getCountryNodesByLanguage method of PlayHelper starts");
        Map<String, String> querrymap = new HashMap<>();
        querrymap.put("path", sourcePath);
        querrymap.put("type", "cq:PageContent");
        querrymap.put("property", "jcr:language");
        querrymap.put("property.operation", "exists");
        querrymap.put("p.nodedepth", "2");
        querrymap.put("p.hits", "full");
        querrymap.put("p.limit", "-1");
        Query pageQuery = queryBuilder.createQuery(PredicateGroup.create(querrymap), session);
        LOGGER.info("getCountryNodesByLanguage method of PlayHelper end");
        return null != pageQuery ? pageQuery.getResult() : null;
    }

    /**
     * @return pathURL
     */
    public String getPathURL() {
        return pathURL;
    }

    /**
     * @param resolver
     * @param currPageRootRes
     * @return productThumbnailGridNode
     */
    public static String checkProductThumbnailExpFragemnt(ResourceResolver resolver, Resource currPageRootRes) {

        LOGGER.info("checkProductThumbnailExpFragemnt method of PlayHelper starts");
        String productThumbnailGridNode = "";
        String expFragmentResource = "cq/experience-fragments/editor/components/experiencefragment";
        String slingResourceType = "sling:resourceType";
        String frgamentPath = "fragmentPath";
        if (null != currPageRootRes) {
            Node currPageRootNode = currPageRootRes.adaptTo(Node.class);
            if (null != currPageRootNode) {
                try {
                    NodeIterator iter = currPageRootNode.getNodes();
                    if (null != iter) {
                        productThumbnailGridNode = fetchProductThumbnailExpFragment(iter, slingResourceType,
                                expFragmentResource, frgamentPath, resolver);
                    }

                } catch (RepositoryException e) {
                    LOGGER.error("RepositoryException Occured {} ", e);
                }
            }
        }
        LOGGER.info("checkProductThumbnailExpFragemnt method of PlayHelper end");
        return productThumbnailGridNode;
    }

    /**
     * @param iter
     * @param slingResourceType
     * @param expFragmentResource
     * @param frgamentPath
     * @param resolver
     * @return productThumbnailGridNode
     * @throws RepositoryException
     */
    private static String fetchProductThumbnailExpFragment(NodeIterator iter, String slingResourceType,
                                                           String expFragmentResource, String frgamentPath, ResourceResolver resolver) throws RepositoryException {
        LOGGER.info("fetchProductThumbnailExpFragment method of PlayHelper starts");
        String productThumbnailGridNode = "";
        while (iter.hasNext()) {
            Node node = iter.nextNode();
            if (node.hasProperty(slingResourceType) && null != node.getProperty(slingResourceType)) {
                String resType = node.getProperty(slingResourceType).getValue().toString();
                if (expFragmentResource.equalsIgnoreCase(resType)) {
                    String fragmentPath = node.hasProperty(frgamentPath)
                            ? node.getProperty(frgamentPath).getValue().toString()
                            : "";
                    LOGGER.debug("fragmentPath value of PlayHelper is {}", fragmentPath);
                    if (StringUtils.isNotBlank(fragmentPath)) {
                        Resource expFragRes = resolver.getResource(fragmentPath + "/jcr:content/root");
                        productThumbnailGridNode = checkProductThumbnailGridPath(expFragRes, productThumbnailGridNode);
                    }
                }
            }
        }
        LOGGER.info("fetchProductThumbnailExpFragment method of PlayHelper end");
        return productThumbnailGridNode;
    }

    /**
     * @param expFragRes
     * @param productThumbnailGridNode
     * @return productThumbnailGridNode
     * @throws RepositoryException
     */
    private static String checkProductThumbnailGridPath(Resource expFragRes, String productThumbnailGridNode)
            throws RepositoryException {
        LOGGER.info("checkProductThumbnailGridPath method of PlayHelper starts");
        if (null != expFragRes) {
            Node expFragNode = expFragRes.adaptTo(Node.class);
            if (null != expFragNode && expFragNode.hasNode("productthumbnailgrid")) {
                productThumbnailGridNode = expFragNode.getNode("productthumbnailgrid").getPath();
            }
        }
        LOGGER.info("checkProductThumbnailGridPath method of PlayHelper end");
        return productThumbnailGridNode;

    }

    /**
     * @param node
     * @param property
     * @return This method checks and returns the property if available
     * @throws RepositoryException
     */
    public static String checkForProperty(Node node, String property) throws RepositoryException {
        return node.hasProperty(property) ? node.getProperty(property).getString() : "";

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

    public static Boolean checkBooleanProperty(Page page, String property, Boolean defaultBoolean) {
        return page.getProperties().get(property, Boolean.class) != null
                ? page.getProperties().get(property, Boolean.class)
                : defaultBoolean;
    }

    /**
     * @param page
     * @param property
     * @return This method checks and returns the property if available
     */
    public static String checkForProperty(Page page, String property) {
        return page.getProperties("navThumbAlt") != null ? page.getProperties().get(property, String.class) : "";
    }

    public static String fetchLocaleFromDam(String path, Resource resource) {
        String damLocale = "";
        if (path != null) {
            String brandName;
            if (PlayHelper.checkIfSiteisMattelPlay(resource.getPath())) {
                brandName = PlayHelper.getBrandName(resource.getPath());
            } else {
                brandName = PlayHelper.getBrandName(resource);
            }
            String replaceText = PropertyReaderUtils.getPlayDamPath() + brandName
                    + PropertyReaderUtils.getVideoDamPath();
            path = path.replace(replaceText, "");
            damLocale = path.split("/")[0];
        }
        return damLocale;

    }

    public static String getHomePagePath(Resource resource) {
        String homePagePath = null;
        PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
        Page page = null;
        if (pageManager != null) {
            page = pageManager.getContainingPage(resource);
        }
        if (page != null) {
            homePagePath = page.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName(resource)).getPath();
            LOGGER.debug("homePagePath is - {}", homePagePath);
        }
        return homePagePath;
    }

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
     * @param nodeValues
     * @param properties
     * @return
     */
    public static String getValueMapNodeVale(ValueMap nodeValues, String properties) {
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
	 * @param currentPagePath
	 * @return boolean value to check if the resource is of mattel-play or not
	 */
	public static boolean checkIfSiteisMattelPlay(String path) {
		boolean isPlaySite = false;
		if (path.contains(PropertyReaderUtils.getPlayPath())
				|| path.contains(PlaySiteConfigurationUtils.getExpFragmentRootPath())) {
			isPlaySite = true;
		}
		return isPlaySite;
	}

    /**
     * @return boolean value to shift the index to the left for the sites having no
     * parent brand name
     */
    public static int leftIndexShiftForSiteWOParentName(Resource resource) {
        String path = resource.getPath();
        int index = path.indexOf("/jcr:content");
        path = path.substring(0, index);
        if (!path.contains(PlayHelper.EXPERIENCE_FRAGMENTS)) {
            PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
            String pagePath = pageManager == null ? "" : pageManager.getPage(path).getPath();
            if (pageManager != null && pagePath != null) {
                Page currentPage = pageManager.getPage(path);
                if (currentPage.getAbsoluteParent(1).hasChild(PlayHelper.LANGUAGE_MASTERS)) {
                    return -1;
                }
            }
        }
        return 0;
    }
}
