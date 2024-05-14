package com.mattel.fisherprice.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.day.cq.wcm.api.PageFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.JsonObject;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.constants.ParentPageType;
import com.mattel.fisherprice.core.helper.FisherPriceHelper;
import com.mattel.fisherprice.core.pojos.HrefLangPojo;
import com.mattel.fisherprice.core.services.MultifieldReader;
import com.mattel.fisherprice.core.utils.FisherPriceConfigurationUtils;
import com.mattel.fisherprice.core.utils.FisherPriceUtils;
import com.mattel.fisherprice.core.utils.PropertyReaderUtils;

@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FisherPricePageModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(FisherPricePageModel.class);

    @SlingObject
    private SlingHttpServletRequest request;

    @OSGiService
    private FisherPriceUtils fisherPriceUtils;

    @Inject
    private MultifieldReader multifieldReader;

    public void setMultifieldReader(MultifieldReader multifieldReader) {
        this.multifieldReader = multifieldReader;
    }

    public void setPropertyReaderUtils(PropertyReaderUtils propertyReaderUtils) {
        this.propertyReaderUtils = propertyReaderUtils;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Inject
    private PropertyReaderUtils propertyReaderUtils;

    @Inject
    private Resource resource;

    @Inject
    private Page currentPage;

    private String canonicalUrl = StringUtils.EMPTY;

    private List<HrefLangPojo> hrefLangList = new ArrayList<>();
    String languageMasters = "language-masters";
    private static String slashHome = "/home";
    private static String cqRedirectTarget = "cq:redirectTarget";
    private String propertiesJson;
    private String scriptUrl;
    private String clientlibCategory;
    private String headerPath;
    private String footerPath;
    private String gameLandingGridPath;
    private String characterCategoryFilterPath;
    private String retailerInterstitialPath;
    private String leavingInterstitialPath;
    private String interstitialApp;
    private String productThumbnailGridFragPath;
    private String siteCountry;
    private String productCategoryFilterPath;
    private String productDetailGridTitleFragPath;
    private String pageLocale;
    private String keywordCommaSeparated;
    private String brandName;
    private String siteSection;
    private String siteSubSection;
    private String rescueBrandName;
    private String title;
    String homePagePath;

    private String pageName;
    private String parentPageType;
    String currentPagePath;
    int leftIndexShiftForSiteWOParentName;
    private String productId;
    static final String DOTHTML = ".html";
    private String currentProductPath = StringUtils.EMPTY;

    private String primaryTag;
    List<Tag> tagObjList = null;

    /**
     * The init method to fetch the page level properties
     */
    @PostConstruct
    protected void init() {

        LOGGER.info("Start of FisherPricePageModel page Model init");
        if (Objects.nonNull(resource) && !resource.getPath().contains("/conf/")) {
            leftIndexShiftForSiteWOParentName = FisherPriceHelper.leftIndexShiftForSiteWOParentName(resource);
            pageLocale = FisherPriceHelper.getPageLocale(resource.getPath());
            brandName = FisherPriceHelper.getBrandName(resource);
            Resource analyticsNodeResource = resource.getResourceResolver()
                    .getResource(resource.getPath() + "/analyticsProperties");
            Node analyticsNode = null;
            Map<String, ValueMap> stringValueMapLinkedHashMap = new HashMap<>();
            JsonObject jsonObject = new JsonObject();
            if (Objects.nonNull(analyticsNodeResource)) {
                analyticsNode = analyticsNodeResource.adaptTo(Node.class);
                getPropertiesJsonResponseFromAnalyticsNode(analyticsNode, stringValueMapLinkedHashMap, jsonObject);
            }
            scriptUrl = propertyReaderUtils.getScriptUrl();
            LOGGER.debug("ScriptURL from init of FisherPricePageModel {}", scriptUrl);
            fetchTitleAndSeoDetails();
            /* SEO configurations start */
            if (!resource.getPath().contains(FisherPriceConfigurationUtils.getRootErrorPageName()) && !resource
                .getPath().contains(FisherPriceConfigurationUtils.getExpFragmentRootPath())) {
                getHrefLangPropertyList();
            }
            if (!resource.getPath().equalsIgnoreCase(PropertyReaderUtils.getFisherPricePath() + Constants.JCR_CONTENT)
                    && !resource.getPath().contains(FisherPriceConfigurationUtils.getExpFragmentRootPath())) {

                String sitesRootPath = "";
                int indexBrandName = resource.getPath().indexOf(brandName);
                sitesRootPath = resource.getPath().substring(0, indexBrandName + brandName.length());
                LOGGER.debug("sitesRootPath is {}", sitesRootPath);

                String currentResPath = resource.getPath();
                LOGGER.debug("currentResPath is {}", currentResPath);

                currentPagePath = currentResPath.replace("jcr:content", "");
                LOGGER.debug("currentPagePath is {}", currentPagePath);
                fetchSiteCountry(currentPagePath, resource);

                fetchTagsForArticlePage();

                getAllExperienceFragmentPaths(sitesRootPath, currentPagePath);

            }
            clientlibCategory = FisherPriceUtils.getInheritedProperty(resource,"clientlibCategory");
            clientlibCategory = checkClientLibCategory();

            getPageNameAndParentPageType(resource);
            fetchCanonicalUrl();
        }
        LOGGER.info("End of FisherPricePageModel page Model init");
    }

    /**
     * Method to fetch the value of canonical url authored in the page
     */
    private void fetchCanonicalUrl() {
        LOGGER.info("Start of fetchCanonicalUrl in FisherPricePageModel");
        LOGGER.debug("Resource path is {}",resource.getPath());
        ValueMap properties = resource.adaptTo(ValueMap.class);
        if (Objects.nonNull(properties)) {
            LOGGER.debug("Value map is not null");
            String canonicalUrlAuthored = StringUtils.isNotBlank(properties.get("canonicalUrl", String.class))
                    ? properties.get("canonicalUrl", String.class) : "";
            LOGGER.debug("Canonical url is {}",canonicalUrlAuthored);
            canonicalUrl = currentPagePath;
            if (StringUtils.isNotBlank(canonicalUrlAuthored)) {
                canonicalUrl = canonicalUrlAuthored;
            }
            canonicalUrl = FisherPriceHelper.checkLink(canonicalUrl, resource);
        }
        LOGGER.debug("canonicalUrl is {}",canonicalUrl);
        LOGGER.info("End of fetchCanonicalUrl in FisherPricePageModel");
    }

    /**
     * @param analyticsNode
     * @param stringValueMapLinkedHashMap
     * @param jsonObject
     */
    private void getPropertiesJsonResponseFromAnalyticsNode(Node analyticsNode,
            Map<String, ValueMap> stringValueMapLinkedHashMap, JsonObject jsonObject) {
        LOGGER.info("Start of getPropertiesJsonResponseFromAnalyticsNode method");
        if (Objects.nonNull(analyticsNode)) {
            stringValueMapLinkedHashMap = multifieldReader.propertyReader(analyticsNode);
        }
        for (Map.Entry<String, ValueMap> mapEntry : stringValueMapLinkedHashMap.entrySet()) {
            jsonObject.addProperty(mapEntry.getValue().get("propertyName").toString(),
                    mapEntry.getValue().get("propertyValue").toString());
        }

        propertiesJson = jsonObject.toString();
        LOGGER.info("End of getPropertiesJsonResponseFromAnalyticsNode method");
    }

    /**
     * Method to fetch the Client Library Category
     *
     * @return clientlibCategory
     */
    private String checkClientLibCategory() {
        LOGGER.info("checkCliendLibCatery method of FisherPricePageModel ------> Start");
        if (resource.getPath().contains("rescue-heroes")) {
            clientlibCategory = FisherPriceConfigurationUtils.getClientlibRootCategoryName() + brandName;
        } else {
            if (multifieldReader.checkIfClientLibExists(Constants.CLIENTLIB_DOT + brandName) && StringUtils
                    .isBlank(clientlibCategory)) {
                clientlibCategory = Constants.CLIENTLIB_DOT + brandName;
            } else if (multifieldReader
                    .checkIfClientLibExists(Constants.CLIENTLIB_DOT + FisherPriceHelper.getParentBrandName(resource))
                    && StringUtils.isBlank(clientlibCategory)) {
                clientlibCategory = Constants.CLIENTLIB_DOT + FisherPriceHelper.getParentBrandName(resource);
            } else {
                clientlibCategory = "global.base";
            }
        }
        LOGGER.info("checkCliendLibCatery method of FisherPricePageModel ------> End");
        return clientlibCategory;
    }

    /**
     * get All fragment paths
     *
     * @param sitesRootPath   Site Root Path
     * @param currentPagePath current Page Path
     */
    private void getAllExperienceFragmentPaths(String sitesRootPath, String currentPagePath) {
        LOGGER.info("start of getAllExperienceFragmentPaths method");

        if (currentPagePath.contains(sitesRootPath)) {
            String countryLocalePath = FisherPriceHelper.getRelativePath(currentPagePath, resource);
            headerPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath, headerPath,
                    FisherPriceConfigurationUtils.getHeaderExpFragmentName());

            LOGGER.debug("headerPath is {} ", headerPath);

            footerPath = getFooterExpFragmentPathDetails(currentPagePath, countryLocalePath, footerPath,
                    FisherPriceConfigurationUtils.getFooterExpFragmentName(),
                    FisherPriceConfigurationUtils.getGlobalfooterExpFragmentName());

            LOGGER.debug("footerPath is {} ", footerPath);

            retailerInterstitialPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath,
                    retailerInterstitialPath, FisherPriceConfigurationUtils.getRetailerInterstitialPath());

            LOGGER.debug("retailerInterstitialPath is {} ", retailerInterstitialPath);

            leavingInterstitialPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath,
                    leavingInterstitialPath, FisherPriceConfigurationUtils.getLeavingInterstitialPath());

            LOGGER.debug("leavingInterstitialPath is {} ", leavingInterstitialPath);

            interstitialApp = getExpFragmentPathDetails(currentPagePath, countryLocalePath, interstitialApp,
                    FisherPriceConfigurationUtils.getInterstitialApp());

            LOGGER.debug("interstitialApp is {} ", interstitialApp);
        }
        LOGGER.info("getAllExperienceFragmentPaths method of FisherPricePageModel ------> End");

    }

    /**
     * @param currentPagePath   current Page Path
     * @param countryLocalePath country Locale Path
     * @param path              Experience Fragment Path
     * @param expFragmentName   Experience Fragment Name
     * @return path Experience Fragment Path
     */
    private String getExpFragmentPathDetails(String currentPagePath, String countryLocalePath, String path,
            String expFragmentName) {
        LOGGER.info("Start getExpFragmentPathDetails method");
        if (countryLocalePath.split(Constants.SLASH).length == 3 && !currentPagePath.contains(languageMasters)) {
            if (countryLocalePath.split(Constants.SLASH)[2].contains("-")
                    && countryLocalePath.split(Constants.SLASH)[2].indexOf('-') == 2) {
                String localeString = countryLocalePath.split(Constants.SLASH)[2];
                String locale = localeString.split("-")[0];
                path = getExpFragmentPath(countryLocalePath, expFragmentName, locale);

                path = handleNullResource(path, expFragmentName, locale, localeString);
            }
        } else if (countryLocalePath.split(Constants.SLASH).length == 3 && currentPagePath.contains(languageMasters)) {
            String locale = countryLocalePath.split(Constants.SLASH)[2];
            path = getExpFragmentPath(expFragmentName, locale);

            path = handleNullResource(path, expFragmentName, locale, "");
        } else {
            path = getExpFragmentPath(expFragmentName, "en");
        }
        LOGGER.info("End getExpFragmentPathDetails method");

        return path;
    }

    /**
     * @param currentPagePath   current Page Path
     * @param countryLocalePath country Locale Path
     * @param expFragmentName   Experience Fragment Name
     * @return path Experience Fragment Path
     */
    private String getFooterExpFragmentPathDetails(String currentPagePath, String countryLocalePath, String footerPath,
            String expFragmentName, String globalFooterExpFragName) {
        LOGGER.info("Start getExpFragmentPathDetails method");
        if (countryLocalePath.split(Constants.SLASH).length == 3 && !currentPagePath.contains(languageMasters)) {
            if (countryLocalePath.split(Constants.SLASH)[2].contains("-")
                    && countryLocalePath.split(Constants.SLASH)[2].indexOf('-') == 2) {
                String localeString = countryLocalePath.split(Constants.SLASH)[2];
                String locale = localeString.split("-")[0];
                boolean isBrandExcluded = checkIfBrandisExcluded();
                if ("us".equals(countryLocalePath.split(Constants.SLASH)[1]) && !isBrandExcluded) {
                    footerPath = FisherPriceConfigurationUtils.getExpFragmentRootPath() + globalFooterExpFragName
                            + Constants.SLASH + Constants.MASTER + Constants.JCR_CONTENT_ROOT;
                } else {
                    footerPath = getExpFragmentPath(countryLocalePath, expFragmentName, locale);
                }
                footerPath = handleNullResource(footerPath, expFragmentName, locale, localeString);
            }
        } else if (countryLocalePath.split(Constants.SLASH).length == 3 && currentPagePath.contains(languageMasters)) {
            String locale = countryLocalePath.split(Constants.SLASH)[2];
            footerPath = getExpFragmentPath(expFragmentName, locale);
            footerPath = handleNullResource(footerPath, expFragmentName, locale, "");
        } else {
            footerPath = getExpFragmentPath(expFragmentName, "en");
        }
        LOGGER.info("End getExpFragmentPathDetails method");

        return footerPath;
    }

    /**
     * @return
     */
    private boolean checkIfBrandisExcluded() {
        String[] excludedBrands = FisherPriceConfigurationUtils.getExcludedBrandsFooter();
        if (Objects.nonNull(excludedBrands) && excludedBrands.length > 0) {
            for (String brand : excludedBrands) {
                if (brand.equalsIgnoreCase(brandName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * check and handle null resource condition
     *
     * @param path            Experience Fragment Path
     * @param expFragmentName Experience Fragment Name
     * @return path Experience Fragment Path
     */
    private String handleNullResource(String path, String expFragmentName, String locale, String localeString) {
        LOGGER.info("Start handleNullResource method");
        if (Objects.isNull(resource.getResourceResolver().getResource(path))) {
            path = FisherPriceConfigurationUtils.getExpFragmentRootPath() + brandName + Constants.SLASH + localeString
                    .replace('-', '_') + Constants.SLASH + expFragmentName + Constants.SLASH + expFragmentName
                    + Constants.UNDERSCORE + localeString + Constants.JCR_CONTENT_ROOT;
            if (Objects.isNull(resource.getResourceResolver().getResource(path))) {
                path = getExpFragmentPath(expFragmentName, locale);
                if (Objects.isNull(resource.getResourceResolver().getResource(path))) {
                    path = getExpFragmentPath(expFragmentName, "en");
                }
            }

        }
        LOGGER.info("End handleNullResource method");
        return path;
    }

    /**
     * get the Experience Fragment Path
     *
     * @param expFragmentName
     * @param locale
     * @return
     */
    private String getExpFragmentPath(String expFragmentName, String locale) {
        return FisherPriceConfigurationUtils.getExpFragmentRootPath() + brandName + Constants.SLASH + locale
                + Constants.SLASH + expFragmentName + Constants.SLASH + Constants.MASTER + Constants.JCR_CONTENT_ROOT;
    }

    /**
     * get the Experience Fragment Path
     *
     * @param countryLocalePath
     * @param expFragmentName
     * @param locale
     * @return
     */
    private String getExpFragmentPath(String countryLocalePath, String expFragmentName, String locale) {
        return FisherPriceConfigurationUtils.getExpFragmentRootPath() + brandName + Constants.SLASH + locale
                + Constants.SLASH + expFragmentName + Constants.SLASH + expFragmentName + Constants.UNDERSCORE
                + countryLocalePath.split(Constants.SLASH)[2] + Constants.JCR_CONTENT_ROOT;
    }

    /**
     * Method to get the Home Page SEO Title
     *
     * @param resource
     * @param childPageFlag
     * @return
     */
    private String getHomePageTitle(Resource resource, boolean childPageFlag) {
        String homePagetitle = null;
        Resource homePageResource = resource.getResourceResolver()
                .getResource(homePagePath + Constants.SLASH_JCR_CONTENT);
        if (Objects.nonNull(homePageResource)) {
            ValueMap properties = homePageResource.adaptTo(ValueMap.class);
            if (Objects.nonNull(properties)) {
                if (childPageFlag) {

                    homePagetitle = properties.get("globalSeoTitle", "");

                } else {
                    homePagetitle = properties.get("seoTitle", "");
                }

                LOGGER.debug("homePagetitle is {}", homePagetitle);
            }
        }

        return homePagetitle;
    }

    /**
     * @param path
     * @return
     */
    private boolean isHomepage(String path) {

        return path.equalsIgnoreCase(homePagePath);
    }

    /**
     * This method will set the hrefland properties as below.
     * As part of this functionality, we are setting the localised urls for the corresponding page opened.
     * e.g. If we open homepage in the en-us locale then in source, we are setting the urls of
     * homepage in all the other locales.
     * urls will be shown only if we have set the language property (jcr:language) in the
     * language node (/content/fisher-price/us/en-us in case of en-us locale)
     * of live copy and also corresponding page is available.
     * if any of the above condition is false then hreflang will not set.
     * if a page is hidden in nav then that page will not be shown in the hreflang.
     * if the language node of live copy has property hide in nav = true then any page of that
     * locale will not be shown in the hreflang.
     */
    private void getHrefLangPropertyList() {
        LOGGER.info("start of getHrefLangPropertyList method");
        String currentResPath = resource.getPath();
        String currentPath = currentResPath.replace("jcr:content", "");
        String countryLocale = FisherPriceHelper.getRelativePath(currentPath, resource);
        if (StringUtils.isNotBlank(countryLocale)) {
            String tempPath = currentPath.substring(currentPath.indexOf(countryLocale), currentPath.length() - 1);
            String relativePath = tempPath.replace(countryLocale, "");
            String sitesRootPath = Constants.CONTENT + brandName;
            ResourceResolver resolver = request.getResourceResolver();
            PageManager pageManager = resolver.adaptTo(PageManager.class);
            if(Objects.nonNull(pageManager)) {
                Page siteRootPage = pageManager.getPage(sitesRootPath);
                if(Objects.nonNull(siteRootPage)) {
                    Iterator<Page> rootPageIterator = siteRootPage.listChildren();
                    if (Objects.nonNull(rootPageIterator)) {
                        currentPageHrefLang(currentResPath);
                        fetchHrefLangList(rootPageIterator, relativePath, pageManager);
                    }
                }
            }
        }
        LOGGER.info("End of getHrefLangPropertyList method");
    }

    /**
     * Set the currentpage in the hreflang attribute
     *
     * @param currentResourcePath
     */
    private void currentPageHrefLang(String currentResourcePath) {
        LOGGER.info("Start of currentPageHrefLang in FisherPricePageModel class");
        PageManager pgmgr = resource.getResourceResolver().adaptTo(PageManager.class);
        LOGGER.debug("Current resource Path and Relative paths are {}",currentResourcePath);
        String currentPath = currentResourcePath.replace(Constants.SLASH_JCR_CONTENT, "");
        if (Objects.nonNull(pgmgr) && isPageExists(currentResourcePath)) {
            HrefLangPojo hrefLangPojo = new HrefLangPojo();
            hrefLangPojo.setUrl(currentPath);
            hrefLangPojo.setLocale(FisherPriceHelper.getPageLocale(currentPath));
            hrefLangList.add(hrefLangPojo);
        }
        LOGGER.info("End of currentPageHrefLang in FisherPricePageModel class");
    }

    /**
     * @param currentResourcePath
     * @return
     */
    private boolean isPageExists(String currentResourcePath) {
        LOGGER.info("Start of isPublished method in the FisherPricePageModel class");
        boolean pageExists = false;
        LOGGER.debug("Checking the replication status for page {}",currentResourcePath);
        Resource currentRes = resource.getResourceResolver().getResource(currentResourcePath);
        if(Objects.nonNull(currentRes) && !ResourceUtil.isNonExistingResource(currentRes)) {
            pageExists = true;
        }
        LOGGER.info("End of isPublished method in the FisherPricePageModel class");
        return pageExists;
    }

    /**
     * This method fetches the list of all the hreflanglist page nodes (language nodes)
     * @param rootPageIterator
     * @param relativePath
     * @param pageManager
     */
    private void fetchHrefLangList(Iterator<Page> rootPageIterator, String relativePath,
        PageManager pageManager) {
        LOGGER.info("Start of fetchHrefLangList method of FisherPricePageModel");
        while (rootPageIterator.hasNext()) {
            Page siteRootPage = rootPageIterator.next();
            if(!siteRootPage.getPath().contains("language-masters")) {
                LOGGER.debug("siteRootPage is {}",siteRootPage.getPath());
                Iterator<Page> pageIterator = siteRootPage.listChildren(new PageFilter(), false);
                while (pageIterator.hasNext()) {
                    Page childPage = pageIterator.next();
                    String pagepath = childPage.getPath()
                        .replace(Constants.SLASH_JCR_CONTENT, "");
                    String currentPagePathRes = pagepath + relativePath + "/jcr:content";
                    boolean enableHrefLang = checkHrefLangEligibility(pagepath, pageManager,
                        relativePath);
                    LOGGER.debug("Page Locale is {}",FisherPriceHelper.getPageLocale(pagepath));
                    ValueMap valueMap = childPage.getProperties();
                    setHrefLangList(valueMap,enableHrefLang,relativePath,currentPagePathRes,pagepath);
                }
            }
        }
        LOGGER.debug("Final hrefLangList is  {}",hrefLangList);
        LOGGER.info("End of fetchHrefLangList method of FisherPricePageModel");
    }

    /**
     * This method sets the hreflang list based on the language property set
     * @param valueMap
     * @param enableHrefLang
     * @param relativePath
     * @param currentPagePathRes
     * @param pagepath
     */
    private void setHrefLangList(ValueMap valueMap, boolean enableHrefLang, String relativePath,
        String currentPagePathRes, String pagepath) {
        LOGGER.info("Start of setHrefLangList method of FisherPricePageModel");
        if(Objects.nonNull(valueMap)) {
            String property = valueMap.get("jcr:language", String.class);
            if (StringUtils.isNotBlank(property) && enableHrefLang && !FisherPriceHelper.getPageLocale(pagepath).equalsIgnoreCase(pageLocale)
                && isPageExists(currentPagePathRes)) {
                HrefLangPojo hrefLangPojo = new HrefLangPojo();
                hrefLangPojo.setUrl(pagepath + relativePath);
                hrefLangPojo.setLocale(FisherPriceHelper.getPageLocale(pagepath));
                hrefLangList.add(hrefLangPojo);
                LOGGER.debug("Size of hrefLangList is {}", hrefLangList.size());
            }
        }
        LOGGER.info("End of setHrefLangList method of FisherPricePageModel");
    }

    /**
     * @param pagepath
     * @param pgmgr
     * @param relativePath
     * @return
     */
    private boolean checkHrefLangEligibility(String pagepath, PageManager pgmgr, String relativePath) {
        boolean isHreflangUrl = true;
        if (Objects.nonNull(pgmgr) && Objects.nonNull(pgmgr.getPage(pagepath))) {
            Page currentPage = pgmgr.getPage(pagepath);
            Page localepage = pgmgr.getPage(pagepath + relativePath);
            if (Objects.nonNull(currentPage)) {
                String homePPath = pagepath + slashHome;
                Page homepage = pgmgr.getPage(homePPath);
                if (Objects.nonNull(homepage) && Objects.nonNull(homepage.getProperties())) {
                    ValueMap map = homepage.getProperties();
                    if (map.containsKey(cqRedirectTarget) && Objects.nonNull(map.get(cqRedirectTarget))) {
                        LOGGER.debug("cq:redirectTarget property is set and its value is-> {}",
                                map.get(cqRedirectTarget));
                        isHreflangUrl = false;
                    }
                }
            }
            if (Objects.isNull(localepage)) {
                isHreflangUrl = false;
            }
        }
        return isHreflangUrl;
    }

    /**
     * Method to fetch the Page Title and SEO Properties
     */
    private void fetchTitleAndSeoDetails() {
        PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
        if (Objects.nonNull(pageManager)) {
            Page currentPage = pageManager.getContainingPage(resource);
            if (Objects.nonNull(currentPage) && Objects
                    .nonNull(currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName))) {
                Page homePage = currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName);
                homePagePath = homePage.getPath();
                if (currentPage.getDepth() > 6 && Objects
                        .nonNull(currentPage.getAbsoluteParent(6 + leftIndexShiftForSiteWOParentName))) {
                    siteSection = currentPage.getAbsoluteParent(6 + leftIndexShiftForSiteWOParentName).getName();
                    if (currentPage.getDepth() > 7 && Objects
                            .nonNull(currentPage.getAbsoluteParent(7 + leftIndexShiftForSiteWOParentName))) {
                        siteSubSection = currentPage.getAbsoluteParent(7 + leftIndexShiftForSiteWOParentName).getName();
                    }
                } else {
                    siteSection = currentPage.getName();
                }
                fetchtitleDetails(currentPage);
                Resource seoKeywordsNodeResource = resource.getResourceResolver()
                        .getResource(resource.getPath() + "/metaKeywords");
                fetchSeoDetails(seoKeywordsNodeResource);

            }
        }
    }

    /**
     * Method to fetch the primary tag from article pages for analytics
     */
    private void fetchTagsForArticlePage() {
        LOGGER.info("Start of fetchTagsForArticlePage method");
        if (Objects.nonNull(currentPage)) {
            Locale locale = FisherPriceUtils.getPageLocale(currentPage);
            primaryTag = fisherPriceUtils.getTagData(currentPage, Constants.PRIMARY_TAG, Constants.TAG_TITLE,
                    Constants.COMMA, locale);
            LOGGER.debug("Primary Tag : {}", primaryTag);
        }
        LOGGER.info("END of fetchTagsForArticlePage method");
    }

    /**
     * @param resource
     */
    private void getPageNameAndParentPageType(Resource resource) {
        PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
        if (Objects.nonNull(pageManager)) {
            Page currentPage = pageManager.getContainingPage(resource);
            if (Objects.nonNull(currentPage) && Objects
                    .nonNull(currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName))) {
                currentPagePath = currentPage.getPath();
                Page homePage = currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName);
                homePagePath = homePage.getPath();
                getParentPageTypeAndPageName(currentPage, currentPagePath, resource);
                getMetaPageName(currentPage);
            }
        }
    }

    /**
     * @param currentPage
     * @throws RepositoryException
     */
    private void getParentPageTypeAndPageName(Page currentPage, String currentPagePath, Resource resource) {
        LOGGER.info("Start of getParentPageTypeAndPageName method");
        StringBuilder tempName = new StringBuilder();
        tempName.append(FisherPriceHelper.getBrandName(resource) + ":" + fetchSiteCountry(currentPagePath, resource));
        parentPageType = fetchParentPageType(currentPage);
        LOGGER.info("End of getParentPageTypeAndPageName method");
    }

    /**
     * This method provides current page type
     * @param currentPage
     * @return
     */
    private String fetchParentPageType(Page currentPage) {
        String pageResourceType = currentPage.getProperties().get("sling:resourceType", String.class);
        if (Objects.nonNull(pageResourceType)) {
            parentPageType = ParentPageType.fetch(pageResourceType, currentPage);
        }

        return parentPageType;
    }

    /**
     * This method is used to get the analytics data of current Page
     * @param currentPage
     * @throws RepositoryException
     */

    private void getMetaPageName(Page currentPage) {
        LOGGER.info("getMetaPageName method of FisherPricePageModel ----> Start");
        String pageResourceType = currentPage.getProperties().get("sling:resourceType", String.class);
        productId = currentPage.getProperties().get("productSKUId", String.class);
        if (Objects.nonNull(pageResourceType)) {
            getCurrentPageDetailsForAnalytics(currentPage, pageResourceType);
        }
        LOGGER.info("getMetaPageName method of FisherPricePageModel ----> End");
    }

    /**
     * get Analytics details for current Page
     * @param currentPage
     * @param pageResourceType
     */
    private void getCurrentPageDetailsForAnalytics(Page currentPage, String pageResourceType) {
        String currentBrandName = FisherPriceHelper.getBrandName(resource).equalsIgnoreCase("fisher-price") ?
                "FP" :
                FisherPriceHelper.getBrandName(resource);
        LOGGER.debug("brandName is {}", currentBrandName);
        if (pageResourceType.contains(Constants.HOMEPAGE_TEMPLATE)) {
            pageName = currentBrandName + ":" + siteCountry.toUpperCase() + ":Shop:" + currentPage.getName();
        } else if (pageResourceType.contains(Constants.CONTENTPAGE_TEMPLATE)) {
            pageName = currentBrandName + ":" + siteCountry.toUpperCase() + ":Shop:" + currentPage.getName();
        } else if (pageResourceType.contains(Constants.PDP_TEMPLATE)) {
            currentProductPath = request.getPathInfo();
            currentProductPath = currentProductPath.replace(DOTHTML, "");
            String upDatedProductPath = currentProductPath.substring(currentProductPath.lastIndexOf("/product"),
                    currentProductPath.length() - 6);
            StringBuilder tempName = new StringBuilder();
            tempName = currentProductPath.contains("/productfinder") ?
                    tempName.append("/product" + upDatedProductPath) :
                    tempName.append(upDatedProductPath);
            String updatedName = tempName.toString().replace("/productfinder", "").replace("/", ":");
            pageName = currentBrandName + ":" + siteCountry.toUpperCase() + updatedName.replace("-", " ");
        } else if (pageResourceType.contains(Constants.PLP_TEMPLATE)
                && !(currentPage.getPath().contains(Constants.SEARCH_RESULTS))) {
            StringBuilder tempPageName = new StringBuilder();
            String pagePath = currentPage.getPath();
            String upDatedProductPath = pagePath.substring(pagePath.lastIndexOf("/shop"), pagePath.length());
            tempPageName.append(upDatedProductPath.replace("/", ":"));
            pageName = currentBrandName + ":" + siteCountry.toUpperCase() + tempPageName.toString();
        } else if (pageResourceType.contains(Constants.PLP_TEMPLATE)
                && currentPage.getPath().contains(Constants.SEARCH_RESULTS)) {
            StringBuilder tempPageName = new StringBuilder();
            String pagePath = currentPage.getPath();
            String upDatedProductPath = pagePath.contains(Constants.SEARCH_RESULTS) ?
                    pagePath.substring(pagePath.lastIndexOf(pageLocale), pagePath.length()) :
                    pagePath.substring(pagePath.lastIndexOf("/shop"), pagePath.length());
            tempPageName.append(upDatedProductPath.replace("/", ":"));
            pageName = currentBrandName + ":" + siteCountry.toUpperCase() + ":" + currentPage.getName();
        } else if (pageResourceType.contains(Constants.SUBSCRIPTION_PAGE) || pageResourceType
                .contains(Constants.LANDING_PAGE_TEMPLATE) || pageResourceType
                .contains(Constants.ARTICLE_DETAILS_PAGE_TEMPLATE) || pageResourceType
                .contains(Constants.CATEGORY_LANDING_PAGE_TEMPLATE)) {
            pageName = currentBrandName + ":" + siteCountry.toUpperCase() + ":" + currentPage.getName();
        }
        LOGGER.debug("Final page name value in meta tag is {}", pageName);
    }

    /**
     * Method to fetch the title Details
     *
     * @param currentPage
     */
    private void fetchtitleDetails(Page currentPage) {
        LOGGER.info("fetchtitleDetails method of FisherPricePageModel ----> Start");
        boolean ishomePage = isHomepage(currentPage.getPath());

        if (ishomePage) {

            if (Objects.nonNull(getHomePageTitle(resource, true)) && Objects
                    .nonNull(getHomePageTitle(resource, false))) {
                title = getHomePageTitle(resource, true) + " | " + getHomePageTitle(resource, false);
            } else if (Objects.isNull(getHomePageTitle(resource, true))) {
                title = getHomePageTitle(resource, false);
            } else if (Objects.isNull(getHomePageTitle(resource, false))) {
                title = getHomePageTitle(resource, true);
            }

        } else {

            ValueMap prop = resource.adaptTo(ValueMap.class);
            String currentPageTitle = null;
            if (Objects.nonNull(prop)) {
                currentPageTitle = prop.get("seoTitle", "");
            }
            String homePageTitle = getHomePageTitle(resource, true);

            if (Objects.nonNull(homePageTitle) && Objects.nonNull(currentPageTitle)) {

                title = currentPageTitle + " | " + homePageTitle;

            } else if (Objects.isNull(homePageTitle)) {
                title = currentPageTitle;
            } else {
                title = homePageTitle;
            }
        }
        LOGGER.info("fetchtitleDetails method of FisherPricePageModel ----> End");
    }

    /**
     * Method to fetch the SEO Details
     *
     * @param seoKeywordsNodeResource
     */
    private void fetchSeoDetails(Resource seoKeywordsNodeResource) {
        LOGGER.info("fetchSeoDetails method of FisherPricePageModel ----> Start");
        List<String> keywordsList = new ArrayList<>();
        Node seoNode = null;
        Map<String, ValueMap> seoLinkedHashMap = new HashMap<>();
        if (Objects.nonNull(seoKeywordsNodeResource)) {
            seoNode = seoKeywordsNodeResource.adaptTo(Node.class);
            if (null != seoNode) {
                seoLinkedHashMap = multifieldReader.propertyReader(seoNode);
            }
            for (Map.Entry<String, ValueMap> mapEntry : seoLinkedHashMap.entrySet()) {
                keywordsList.add(mapEntry.getValue().get("keyword").toString());
            }

            keywordCommaSeparated = String.join(",", keywordsList);

        }
        LOGGER.info("fetchSeoDetails method of FisherPricePageModel ----> End");
    }

    /**
     * @param currentPagePath
     * @return
     */
    private String fetchSiteCountry(String currentPagePath, Resource resource) {
        String tempPath = FisherPriceHelper.getRelativePath(currentPagePath, resource);
        if (!tempPath.isEmpty()) {
            LOGGER.debug("Relative Path is {}", tempPath);
            siteCountry = tempPath.substring(tempPath.indexOf('/') + 1, tempPath.lastIndexOf('/'));
        }
        return siteCountry;
    }

    public String getPropertiesJson() {
        return propertiesJson;
    }

    public String getTitle() {
        return title;
    }

    public String getScriptUrl() {
        return scriptUrl;
    }

    public String getKeywordCommaSeparated() {
        return keywordCommaSeparated;
    }

    /**
     * @return This method return list of hreflang property list
     */
    public List<HrefLangPojo> getHrefLangList() {
        return hrefLangList;
    }

    /**
     * @return Clientlib Category
     */
    public String getClientlibCategory() {
        return clientlibCategory;
    }

    /**
     * @return gameLandingGridPath Game Landing Grid Exp fragment Path
     */
    public String getGameLandingGridPath() {
        return gameLandingGridPath;
    }

    /**
     * @return characterCategoryFilterPath character Category Filter Exp fragment
     * Path
     */
    public String getCharacterCategoryFilterPath() {
        return characterCategoryFilterPath;
    }

    /**
     * @return headerPath Header Experience Fragment path
     */
    public String getHeaderPath() {
        return headerPath;
    }

    /**
     * @return footerPath Footer Experience Fragment path
     */
    public String getFooterPath() {
        return footerPath;
    }

    public String getRetailerInterstitialPath() {
        return retailerInterstitialPath;
    }

    public String getProductThumbnailGridFragPath() {
        return productThumbnailGridFragPath;
    }

    public void setProductThumbnailGridFragPath(String productThumbnailGridFragPath) {
        this.productThumbnailGridFragPath = productThumbnailGridFragPath;
    }

    public String getProductCategoryFilterPath() {
        return productCategoryFilterPath;
    }

    public void setProductCategoryFilterPath(String productCategoryFilterPath) {
        this.productCategoryFilterPath = productCategoryFilterPath;
    }

    public String getProductDetailGridTitleFragPath() {
        return productDetailGridTitleFragPath;
    }

    public void setProductDetailGridTitleFragPath(String productDetailGridTitleFragPath) {
        this.productDetailGridTitleFragPath = productDetailGridTitleFragPath;
    }

    public String getSiteCountry() {
        return siteCountry;
    }

    public String getLeavingInterstitialPath() {
        return leavingInterstitialPath;
    }

    public String getInterstitialApp() {
        return interstitialApp;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setHeaderPath(String headerPath) {
        this.headerPath = headerPath;
    }

    public void setHomePagePath(String homePagePath) {
        this.homePagePath = homePagePath;
    }

    public String getParentPageType() {
        return parentPageType;
    }

    public String getPageName() {
        return pageName;
    }

    public String getRescueBrandName() {
        return rescueBrandName;
    }

    public String getPageLocale() {
        return pageLocale;
    }

    public String getSiteSection() {
        return siteSection;
    }

    public String getHomePagePath() {
        return homePagePath;
    }

    public String getProductId() {
        return productId;
    }

    public String getSiteSubSection() {
        return siteSubSection;
    }

    public String getCurrentProductPath() {
        return null != currentProductPath ? currentProductPath : "";
    }

    public void setRequest(SlingHttpServletRequest request) {
        this.request = request;
    }

    public String getPrimaryTag() {
        return primaryTag;
    }

    public String getCurrentPagePath() {
        return FisherPriceHelper.checkLink(currentPagePath, resource);
    }

    public String getShortUrlSiteDomain() {
        String domain = FisherPriceConfigurationUtils.getShortUrlSiteDomain();
        return StringUtils.isNotEmpty(domain) ? domain : "";
    }
    public String getCanonicalUrl() {
        return canonicalUrl;
    }
}