package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.google.gson.JsonObject;
import com.mattel.play.core.constants.Constants;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.pojos.HrefLangPojo;
import com.mattel.play.core.services.MultifieldReader;
import com.mattel.play.core.services.ProductGalleryAndLandingService;
import com.mattel.play.core.utils.PlaySiteConfigurationUtils;
import com.mattel.play.core.utils.PropertyReaderUtils;

/**
 * @author CTS. Model for CRM Analytics Attributes Defined in Page Properties.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PlayPageModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayPageModel.class);
    private static final String RESCUE_HEROES = "rescue-heroes";
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
    ProductGalleryAndLandingService productGalleryAndLandingService;

    public void setProductGalleryAndLandingService(ProductGalleryAndLandingService productGalleryAndLandingService) {
        this.productGalleryAndLandingService = productGalleryAndLandingService;
    }

    @Self
    private Resource resource;

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
    private String businessSiteName;
    private String siteCountry;
    private String productCategoryFilterPath;
    private String productDetailGridTitleFragPath;
    private String pageLocale;
    private String keywordCommaSeparated;
    private String brandName;
    private String adobeTrackingNameForPage;
    private String rescueBrandName;
    private String title;
    String homePagePath;
    private String pageName;
    private String parentPageType;
    String currentPagePath;
    String rescueParentName;
    String expFrLocaleRH = "";
    String ifSiteisMattelPlay = "";
    int leftIndexShiftForSiteWOParentName;
    private String expFragmentRootPath;
    private String mattelBrand;

    /**
     * The init method to fetch the page level properties
     */
    @PostConstruct
    protected void init() {
        LOGGER.info("Start of PlayPageModel page Model init");
        if (null != resource && !resource.getPath().contains("conf/play/settings/wcm/templates")) {
          try{
        	leftIndexShiftForSiteWOParentName = PlayHelper.leftIndexShiftForSiteWOParentName(resource);
            pageLocale = PlayHelper.getPageLocale(resource.getPath());
            setUpBrandName();
            Resource analyticsNodeResource = resource.getResourceResolver()
                    .getResource(resource.getPath() + "/analyticsProperties");
            Node analyticsNode = null;
            Map<String, ValueMap> stringValueMapLinkedHashMap = new HashMap<>();
            JsonObject jsonObject = new JsonObject();
            businessSiteName = brandName;
            if (analyticsNodeResource != null) {
                analyticsNode = analyticsNodeResource.adaptTo(Node.class);
                getPropertiesJsonResponseFromAnalyticsNode(analyticsNode, stringValueMapLinkedHashMap, jsonObject);
            }
            LOGGER.debug("Name of the brand is {}", PlayHelper.getBrandName(resource.getPath()));
            setUpScriptUrl();
            LOGGER.debug("ScriptURL from init of PlayPageModel {}", scriptUrl);
            /* SEO configurations start */
            fetchTitleAndSeoDetails();
            /* Experience Configurations start here */
            expFragmentRootPath = productGalleryAndLandingService.getCurrentBrandExpFragmentRootPath(brandName, "experience-fragments", resource.getPath());
            mattelBrand = productGalleryAndLandingService.getCurrentBrandExpFragmentRootPath(brandName, "brandsite", resource.getPath());
            if (PlayHelper.checkIfSiteisMattelPlay(resource.getPath())
                    && !resource.getPath().contains(PlaySiteConfigurationUtils.getRootErrorPageName())
                    && !resource.getPath().contains(expFragmentRootPath)) {
                getHrefLangPropertyList();
            }
            fetchExperienceFragments();

            InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(resource);
            clientlibCategory = inheritanceValueMap.getInherited("clientlibCategory", String.class);
            clientlibCategory = checkClientLibCategory();
            adobeTrackingNameForPage = inheritanceValueMap.getInherited("adobeTrackingNameForPage", String.class);
            getPageNameAndParentPageType(resource);
            /* SEO configurations end */
            rescueBrandName = PlayHelper.getRescueBrandName(resource.getPath());
            setUpRescueParentName();
          }catch (NullPointerException e) {
              LOGGER.error("Exception occurred in the init method of PlayPageModel {} ", e);
          }
        }
        LOGGER.info("End of PlayPageModel page Model init");
    }

    private void setUpRescueParentName() {
        if (Objects.nonNull(resource) && resource.getPath().contains(PropertyReaderUtils.getRescuePath())) {
            Resource rescueResource = resource.getParent();
            if (Objects.nonNull(rescueResource)) {
                Page rescueParentPage = rescueResource.adaptTo(Page.class);
                if (Objects.nonNull(rescueParentPage)) {
                    rescueParentName = rescueParentPage.getAbsoluteParent(1).getName() != null
                            ? rescueParentPage.getAbsoluteParent(1).getName()
                            : "";
                }
            }
        }
    }

    private void fetchExperienceFragments() {
        if (!resource.getPath().equalsIgnoreCase(mattelBrand + Constants.JCR_CONTENT)
                && !resource.getPath().contains(expFragmentRootPath)) {

            String sitesRootPath = "";
            if (PlayHelper.checkIfSiteisMattelPlay(resource.getPath())) {
                sitesRootPath = mattelBrand + PlayHelper.getBrandName(resource.getPath());
            } else {
                int indexBrandName = resource.getPath().indexOf(brandName);
                sitesRootPath = resource.getPath().substring(0, indexBrandName + brandName.length());
            }
            LOGGER.debug("sitesRootPath is {}", sitesRootPath);
            String currentResPath = resource.getPath();
            LOGGER.debug("currentResPath is {}", currentResPath);
            currentPagePath = currentResPath.replace("jcr:content", "");
            LOGGER.debug("currentPagePath is {}", currentPagePath);
            fetchSiteCountry(currentPagePath); 
            getAllExperienceFragmentPaths(sitesRootPath, currentPagePath);
        }
    }

    private void setUpScriptUrl() {
        if (PlayHelper.getBrandName(resource.getPath()).equals("whyplay")
                || PlayHelper.getRescueBrandName(resource.getPath()).equals(PlayPageModel.RESCUE_HEROES)) {
            scriptUrl = propertyReaderUtils.getWhyplayScriptUrl();
        } else if (resource.getPath().contains("/mattel/")) {
            String brand = PlayHelper.getBrandName(resource);
            String[] mattelScriptUrl = PropertyReaderUtils.getMattelScriptUrl();
            LOGGER.debug("Brand name is {}", brand);
            for (int i = 0; i < mattelScriptUrl.length; i++) {
                if (mattelScriptUrl[i].startsWith(brand)) {
                    LOGGER.debug("Script Url Is {}", mattelScriptUrl[i]);
                    scriptUrl = mattelScriptUrl[i].substring(mattelScriptUrl[i].lastIndexOf(':') + 1);
                    LOGGER.debug("Final Script URL is {}", scriptUrl);
                }
            }
        } else {
            scriptUrl = propertyReaderUtils.getScriptUrl();
        }
    }

    private void setUpBrandName() {
        if (PlayHelper.checkIfSiteisMattelPlay(resource.getPath())) {
            brandName = PlayHelper.getBrandName(resource.getPath());
        } else {
            brandName = PlayHelper.getBrandName(resource);
        }
    }

    private void getPropertiesJsonResponseFromAnalyticsNode(Node analyticsNode,
                                                            Map<String, ValueMap> stringValueMapLinkedHashMap, JsonObject jsonObject) {
        if (null != analyticsNode) {
            stringValueMapLinkedHashMap = multifieldReader.propertyReader(analyticsNode);
        }
        for (Map.Entry<String, ValueMap> mapEntry : stringValueMapLinkedHashMap.entrySet()) {
            jsonObject.addProperty(mapEntry.getValue().get("propertyName").toString(),
                    mapEntry.getValue().get("propertyValue").toString());
        }

        propertiesJson = jsonObject.toString();
    }

    /**
     * Method to fetch the Client Library Category
     *
     * @return clientlibCategory
     */
    private String checkClientLibCategory() {
        LOGGER.info("checkCliendLibCatery method of PlayPageModel ------> Start");
        if (PlayHelper.checkIfSiteisMattelPlay(resource.getPath())) {
            if (StringUtils.isBlank(clientlibCategory)) {
                clientlibCategory = PlaySiteConfigurationUtils.getClientlibRootCategoryName() + brandName;
            }
        } else if (resource.getPath().contains(PlayPageModel.RESCUE_HEROES)) {
            clientlibCategory = PlaySiteConfigurationUtils.getClientlibRootCategoryName() + brandName;
        } else {
            if (StringUtils.isBlank(clientlibCategory)) {
                clientlibCategory = "clientlib." + brandName;
            }
        }
        LOGGER.info("checkCliendLibCatery method of PlayPageModel ------> End");
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
            String countryLocalePath = PlayHelper.getRelativePath(currentPagePath, resource);
            headerPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath, headerPath,
                    PlaySiteConfigurationUtils.getHeaderExpFragmentName());

            LOGGER.debug("headerPath is {} ", headerPath);

            footerPath = getFooterExpFragmentPathDetails(currentPagePath, countryLocalePath, footerPath,
                    PlaySiteConfigurationUtils.getFooterExpFragmentName(),
                    PlaySiteConfigurationUtils.getGlobalfooterExpFragmentName());

            LOGGER.debug("footerPath is {} ", footerPath);
            gameLandingGridPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath, gameLandingGridPath,
                    PlaySiteConfigurationUtils.getGameLandingExpFragmentName());

            LOGGER.debug("gameLandingGridPath is {} ", gameLandingGridPath);

            retailerInterstitialPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath,
                    retailerInterstitialPath, PlaySiteConfigurationUtils.getRetailerInterstitialPath());

            LOGGER.debug("retailerInterstitialPath is {} ", retailerInterstitialPath);

            leavingInterstitialPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath,
                    leavingInterstitialPath, PlaySiteConfigurationUtils.getLeavingInterstitialPath());

            LOGGER.debug("leavingInterstitialPath is {} ", leavingInterstitialPath);

            interstitialApp = getExpFragmentPathDetails(currentPagePath, countryLocalePath, interstitialApp,
                    PlaySiteConfigurationUtils.getInterstitialApp());

            LOGGER.debug("interstitialApp is {} ", interstitialApp);

            characterCategoryFilterPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath,
                    characterCategoryFilterPath, PlaySiteConfigurationUtils.getCategoryFilterExpFragmentName());

            LOGGER.debug("characterCategoryFilterPath is {} ", characterCategoryFilterPath);

            productThumbnailGridFragPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath,
                    productThumbnailGridFragPath, PlaySiteConfigurationUtils.getProductThumbnailGridExpFragmentName());

            LOGGER.debug("productThumbnailGridFragPath is {} ", productThumbnailGridFragPath);

            productCategoryFilterPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath,
                    productCategoryFilterPath, PlaySiteConfigurationUtils.getProductCategoryFilterExpFragmentName());

            LOGGER.debug("productCategoryFilterPath is {} ", productCategoryFilterPath);

            productDetailGridTitleFragPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath,
                    productDetailGridTitleFragPath, PlaySiteConfigurationUtils.getProductDetailTitleExpFragmentName());

            LOGGER.debug("productDetailGridTitleFragPath is {} ", productDetailGridTitleFragPath);

            PlaySiteConfigurationUtils.getCategoryFilterExpFragmentName();
        }
        LOGGER.info("getAllExperienceFragmentPaths method of PlayPageModel ------> End");

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
                path = expFragmentRootPath + brandName + Constants.SLASH + locale
                        + Constants.SLASH + expFragmentName + Constants.SLASH + expFragmentName + Constants.UNDERSCORE
                        + countryLocalePath.split(Constants.SLASH)[2] + Constants.JCR_CONTENT_ROOT;

                path = handleNullResource(path, expFragmentName, locale);
            }
        } else if (countryLocalePath.split(Constants.SLASH).length == 3 && currentPagePath.contains(languageMasters)) {
            String locale = countryLocalePath.split(Constants.SLASH)[2];
            path = expFragmentRootPath + brandName + Constants.SLASH + locale
                    + Constants.SLASH + expFragmentName + Constants.SLASH + Constants.MASTER
                    + Constants.JCR_CONTENT_ROOT;

            path = handleNullResource(path, expFragmentName, locale);
        } else {
            path = expFragmentRootPath + brandName + Constants.SLASH + "en"
                    + Constants.SLASH + expFragmentName + Constants.SLASH + Constants.MASTER
                    + Constants.JCR_CONTENT_ROOT;
        }
        LOGGER.info("End getExpFragmentPathDetails method");

        return path;
    }

    /**
     * @param currentPagePath   current Page Path
     * @param countryLocalePath country Locale Path
     * @param footerExpPath     Experience Fragment Path
     * @param expFragmentName   Experience Fragment Name
     * @return path Experience Fragment Path
     */
    private String getFooterExpFragmentPathDetails(String currentPagePath, String countryLocalePath, String footerExpPath,
                                                   String expFragmentName, String globalFooterExpFragName) {
        LOGGER.info("Start getExpFragmentPathDetails method");
        if (countryLocalePath.split(Constants.SLASH).length == 3 && !currentPagePath.contains(languageMasters)) {
            if (countryLocalePath.split(Constants.SLASH)[2].contains("-")
                    && countryLocalePath.split(Constants.SLASH)[2].indexOf('-') == 2) {
                String localeString = countryLocalePath.split(Constants.SLASH)[2];
                String locale = localeString.split("-")[0];
                boolean isBrandExcluded = checkIfBrandisExcluded();
                if ("us".equals(countryLocalePath.split(Constants.SLASH)[1]) && !isBrandExcluded) {
                    footerExpPath = expFragmentRootPath + globalFooterExpFragName
                            + Constants.SLASH + Constants.MASTER + Constants.JCR_CONTENT_ROOT;
                } else {
                    footerExpPath = expFragmentRootPath + brandName + Constants.SLASH + locale
                            + Constants.SLASH + expFragmentName + Constants.SLASH + expFragmentName
                            + Constants.UNDERSCORE + countryLocalePath.split(Constants.SLASH)[2]
                            + Constants.JCR_CONTENT_ROOT;
                }
                footerExpPath = handleNullResource(footerExpPath, expFragmentName, locale);
            }
        } else if (countryLocalePath.split(Constants.SLASH).length == 3 && currentPagePath.contains(languageMasters)) {
            String locale = countryLocalePath.split(Constants.SLASH)[2];
            footerExpPath = expFragmentRootPath + brandName + Constants.SLASH + locale
                    + Constants.SLASH + expFragmentName + Constants.SLASH + Constants.MASTER
                    + Constants.JCR_CONTENT_ROOT;
            footerExpPath = handleNullResource(footerExpPath, expFragmentName, locale);
        } else {
            footerExpPath = expFragmentRootPath + brandName + Constants.SLASH + "en"
                    + Constants.SLASH + expFragmentName + Constants.SLASH + Constants.MASTER
                    + Constants.JCR_CONTENT_ROOT;
        }
        LOGGER.info("End getExpFragmentPathDetails method");

        return footerExpPath;
    }

    private boolean checkIfBrandisExcluded() {
        String[] excludedBrands = PlaySiteConfigurationUtils.getExcludedBrandsFooter();
        if (null != excludedBrands && excludedBrands.length > 0) {
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
    private String handleNullResource(String path, String expFragmentName, String locale) {
        LOGGER.info("Start handleNullResource method");
        if (null == resource.getResourceResolver().getResource(path)) {
            path = expFragmentRootPath + brandName + Constants.SLASH + locale
                    + Constants.SLASH + expFragmentName + Constants.SLASH + Constants.MASTER
                    + Constants.JCR_CONTENT_ROOT;
            if (null == resource.getResourceResolver().getResource(path)) {
                path = expFragmentRootPath + brandName + Constants.SLASH + "en"
                        + Constants.SLASH + expFragmentName + Constants.SLASH + Constants.MASTER
                        + Constants.JCR_CONTENT_ROOT;
            }
        }
        LOGGER.info("End handleNullResource method");
        return path;
    }

    /**
     * @param currentPagePath
     *            Current Page Path
     * @return relativePath Relative country and locale path
     *
     *
     *         private String getRelativePath(String currentPagePath) {
     *         LOGGER.info("Start getRelativePath method"); String relativePath =
     *         ""; if (currentPagePath.contains(Constants.HOME_URL)) { relativePath
     *         = currentPagePath.substring(currentPagePath.indexOf(slashHome),
     *         currentPagePath.length() - 1); } else if
     *         (!currentPagePath.contains(Constants.HOME_URL) &&
     *         currentPagePath.contains(PlaySiteConfigurationUtils.getRootErrorPageName()))
     *         { relativePath = currentPagePath.substring(
     *         currentPagePath.indexOf(PlaySiteConfigurationUtils.getRootErrorPageName()),
     *         currentPagePath.length() - 1); }
     *
     *         LOGGER.debug("RelativePath is {}", relativePath);
     *
     *         LOGGER.info("End getRelativePath method"); return relativePath;
     *
     *         }
     */

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
        if (homePageResource != null) {
            ValueMap properties = homePageResource.adaptTo(ValueMap.class);
            if (properties != null) {
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

    private boolean isHomepage(String path) {

        return path.equalsIgnoreCase(homePagePath);
    }

    /**
     * get all hreflang properties for a page
     */
    private void getHrefLangPropertyList() {
        LOGGER.info("start of getHrefLangPropertyList method");
        String currentResPath = resource.getPath();
        String currentpagePath = currentResPath.replace("jcr:content", "");
        String countryLocale = PlayHelper.getRelativePath(currentpagePath, resource);
        if (StringUtils.isNotBlank(countryLocale)) {
            String tempPath = currentpagePath.substring(currentpagePath.indexOf(countryLocale),
                    currentpagePath.length() - 1);
            String relativePath = tempPath.replace(countryLocale, "");
            String sitesRootPath = PropertyReaderUtils.getPlayPath() + brandName;
            Session session = resource.getResourceResolver().adaptTo(Session.class);
            QueryBuilder queryBuilder = resource.getResourceResolver().adaptTo(QueryBuilder.class);
            SearchResult result = PlayHelper.getCountryNodesByLanguage(sitesRootPath, session, queryBuilder);
            if (null != result) {
                setUpHrefLangPojo(result, relativePath);
                Iterator<Resource> resources = result.getResources();
                if (resources.hasNext()) {
                    ResourceResolver leakingResResolver = resources.next().getResourceResolver();
                    if (leakingResResolver.isLive()) {
                        leakingResResolver.close();
                    }
                }

            }
        }

        LOGGER.info("End of getHrefLangPropertyList method");
    }

    private void setUpHrefLangPojo(SearchResult result, String relativePath) {
        for (Hit hit : result.getHits()) {
            try {
                if (null != hit.getPath()) {
                    PageManager pgmgr = resource.getResourceResolver().adaptTo(PageManager.class);
                    getHrefList(pgmgr, hit, relativePath);
                }
            } catch (RepositoryException e) {
                LOGGER.error("Exception occurred in the setUpHrefLangPojo method of PlayPageModel {} ", e);
            }
        }
    }

    private void getHrefList(PageManager pgmgr, Hit hit, String relativePath) {
        if (null != pgmgr) {
            String pagepath = null;
            try {
                pagepath = hit.getPath().replace(Constants.SLASH_JCR_CONTENT, "");
                boolean enableHrefLang = checkHrefLangEligibility(pagepath, pgmgr, relativePath);
                if (enableHrefLang) {
                    HrefLangPojo hrefLangPojo = new HrefLangPojo();
                    hrefLangPojo.setUrl(pagepath + relativePath);
                    hrefLangPojo.setLocale(PlayHelper.getPageLocale(pagepath));
                    hrefLangList.add(hrefLangPojo);
                    LOGGER.debug("Size of hrefLangList is {}", hrefLangList.size());
                }
            } catch (RepositoryException e) {
                LOGGER.error("Exception occurred in the setUpHrefLangPojo method of PlayPageModel {} ", e);
            }
        }
    }

    private boolean checkHrefLangEligibility(String pagepath, PageManager pgmgr, String relativePath) {
        boolean isHreflangUrl = true;
        if (null != pgmgr && null != pgmgr.getPage(pagepath)) {
            Page currentPage = pgmgr.getPage(pagepath);
            Page localepage = pgmgr.getPage(pagepath + relativePath);
            if (null != currentPage) {
                String homePPath = pagepath + slashHome;
                Page homepage = pgmgr.getPage(homePPath);
                if (null != homepage && null != homepage.getProperties()) {
                    ValueMap map = homepage.getProperties();
                    if (map.containsKey(cqRedirectTarget) && null != map.get(cqRedirectTarget)) {
                        LOGGER.debug("cq:redirectTarget property is set and its value is-> {}",
                                map.get(cqRedirectTarget));
                        isHreflangUrl = false;
                    }
                }
            }
            if (null == localepage) {
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
        if (pageManager != null) {
            Page currentPage = pageManager.getContainingPage(resource);
            if (null != currentPage && currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName) != null) {
                Page homePage = currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName);
                homePagePath = homePage.getPath();
                fetchtitleDetails(currentPage);
                Resource seoKeywordsNodeResource = resource.getResourceResolver()
                        .getResource(resource.getPath() + "/metaKeywords");
                fetchSeoDetails(seoKeywordsNodeResource);

            }
        }
    }

    private void getPageNameAndParentPageType(Resource resource) {
        PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
        if (null != pageManager) {
            Page currentPage = pageManager.getContainingPage(resource);
            if (null != currentPage && null != currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName)) {
                currentPagePath = currentPage.getPath();
                Page homePage = currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName);
                homePagePath = homePage.getPath();
                getParentPageTypeAndPageName(currentPage, homePage, currentPagePath);
            }
        }
    }

    /**
     * @param currentPage
     * @param homePage
     */
    private void getParentPageTypeAndPageName(Page currentPage, Page homePage, String currentPagePath) {
        LOGGER.info("Start of getParentPageTypeAndPageName method");
        if (StringUtils.isNotBlank(currentPagePath) && StringUtils.isNotBlank(homePagePath)
                && currentPagePath.equalsIgnoreCase(homePagePath)
                && !currentPagePath.contains(PlaySiteConfigurationUtils.getFpRootPath())
                && currentPagePath.contains(PlayPageModel.RESCUE_HEROES)) {
            pageName = currentPage.getName();
            parentPageType = currentPage.getName();
        } else if (currentPagePath.contains(PlaySiteConfigurationUtils.getFpRootPath())
                && !currentPagePath.contains(PlayPageModel.RESCUE_HEROES)) {
            fetchRescuepageTypenName(currentPage, currentPagePath);
        } else {
            pageName = currentPage.getName();
            Iterator<Page> rootPageIterator = homePage.listChildren();
            while (rootPageIterator.hasNext()) {
                Page childPage = rootPageIterator.next();
                if (pageName.equals(childPage.getName())) {
                    parentPageType = pageName;
                    break;
                } else {
                    parentPageType = currentPage.getParent().getName();
                }
            }
        }
        LOGGER.info("End of getParentPageTypeAndPageName method");
    }

    private void fetchRescuepageTypenName(Page currentPage, String currentPagePath) {
        StringBuilder tempName = new StringBuilder();
        tempName.append(PlayHelper.getBrandName(resource) + ":" + fetchSiteCountry(currentPagePath));
        int pageDepth = currentPage.getDepth();
        int absoluteHome = 4;
        if (StringUtils.isNotBlank(currentPage.getPath()) && Objects.nonNull(currentPage.getAbsoluteParent(absoluteHome)) && currentPage.getPath().equals(currentPage.getAbsoluteParent(absoluteHome).getPath())) {
            parentPageType = "corporate page";
        } else {
            parentPageType = "category index";
        }
        Page tempPage;
        while (absoluteHome < pageDepth) {
            LOGGER.info("Absolute Home is{}", absoluteHome);
            tempPage = currentPage.getAbsoluteParent(absoluteHome);
            tempName.append(":" + tempPage.getName());
            absoluteHome++;
            LOGGER.info("Absolute {}:Depth{}", absoluteHome, pageDepth);
        }
        pageName = tempName.toString();

    }

    /**
     * Method to fetch the title Details
     *
     * @param currentPage
     */
    private void fetchtitleDetails(Page currentPage) {
        LOGGER.info("fetchtitleDetails method of PlayPageModel ----> Start");
        boolean ishomePage = isHomepage(currentPage.getPath());

        if (ishomePage) {

            if (getHomePageTitle(resource, true) != null && getHomePageTitle(resource, false) != null) {
                title = getHomePageTitle(resource, true) + " : " + getHomePageTitle(resource, false);
            } else if (getHomePageTitle(resource, true) == null) {
                title = getHomePageTitle(resource, false);
            } else if (getHomePageTitle(resource, false) == null) {
                title = getHomePageTitle(resource, true);
            }

        } else {

            ValueMap prop = resource.adaptTo(ValueMap.class);
            String currentPageTitle = null;
            if (prop != null) {
                currentPageTitle = prop.get("seoTitle", "");
            }
            String homePageTitle = getHomePageTitle(resource, true);

            if (homePageTitle != null && currentPageTitle != null) {

                title = currentPageTitle + " : " + homePageTitle;

            } else if (homePageTitle == null) {
                title = currentPageTitle;
            } else {
                title = homePageTitle;
            }
        }
        LOGGER.info("fetchtitleDetails method of PlayPageModel ----> End");
    }

    /**
     * Method to fetch the SEO Details
     *
     * @param seoKeywordsNodeResource
     */
    private void fetchSeoDetails(Resource seoKeywordsNodeResource) {
        LOGGER.info("fetchSeoDetails method of PlayPageModel ----> Start");
        List<String> keywordsList = new ArrayList<>();
        Node seoNode = null;
        Map<String, ValueMap> seoLinkedHashMap = new HashMap<>();
        if (seoKeywordsNodeResource != null) {
            seoNode = seoKeywordsNodeResource.adaptTo(Node.class);
            if (null != seoNode) {
                seoLinkedHashMap = multifieldReader.propertyReader(seoNode);
            }
            for (Map.Entry<String, ValueMap> mapEntry : seoLinkedHashMap.entrySet()) {
                keywordsList.add(mapEntry.getValue().get("keyword").toString());
            }

            keywordCommaSeparated = String.join(",", keywordsList);

        }
        LOGGER.info("fetchSeoDetails method of PlayPageModel ----> End");
    }

    private String fetchSiteCountry(String currentPagePath) {
        String tempPath = PlayHelper.getRelativePath(currentPagePath, resource);
        if (StringUtils.isNotBlank(tempPath)) {
            LOGGER.info("Relative Path is {}", tempPath);
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

    public String getBusinessSiteName() {
        return businessSiteName;
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

    public String getRescueParentName() {
        return rescueParentName;
    }

    public String getPageLocale() {
        return pageLocale;
    }

    public String getAdobeTrackingNameForPage() {
        return adobeTrackingNameForPage;
    }

    public String getHomePagePath() {
        return homePagePath;
    }

    public String getExpFrLocaleRH() {
        return PlayHelper.getExpFrLocaleRH(resource.getPath());
    }

    public String getIfSiteisMattelPlay() {
        if (PlayHelper.checkIfSiteisMattelPlay(resource.getPath())) {
            return "true";
        } else {
            return "false";
        }
    }
}
