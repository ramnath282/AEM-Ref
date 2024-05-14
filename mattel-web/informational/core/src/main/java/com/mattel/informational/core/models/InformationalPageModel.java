package com.mattel.informational.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.mattel.informational.core.constants.Constants;
import com.mattel.informational.core.enums.ParentPageType;
import com.mattel.informational.core.helper.InformationalHelper;
import com.mattel.informational.core.pojos.HrefLangPojo;
import com.mattel.informational.core.services.MultifieldReader;
import com.mattel.informational.core.utils.InformationalConfigurationUtils;
import com.mattel.informational.core.utils.InformationalUtils;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class InformationalPageModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(InformationalPageModel.class);

    @Inject
    private MultifieldReader multifieldReader;

    public void setMultifieldReader(MultifieldReader multifieldReader) {
        this.multifieldReader = multifieldReader;
    }

    @Inject
    private Resource resource;
    
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    @Inject
    private Page currentPage;
    
    @SlingObject
    SlingHttpServletRequest request;

    private List<HrefLangPojo> hrefLangList = new ArrayList<>();//
    String languageMasters = "language-masters";
    private String scriptUrl;
    private String clientlibCategory;
    private String headerPath;
    private String footerPath;
    private String retailerInterstitialPath;
    private String leavingInterstitialPath;
    private String interstitialApp;
    private String keywordCommaSeparated;
    private String brandName;
    private String siteSection;
    private String siteSubSection;

    protected String title;
    private String homePagePath;
    private String expFragmentRootPath;
    private String siteCountry = StringUtils.EMPTY;
    private String pageName;
    private String parentPageType;
    private String currentPagePath;
    int leftIndexShiftForSiteWOParentName;
    private String description;
    protected String seoImage;
    protected String baseImage;
    
    @PostConstruct
    protected void init() {

        LOGGER.info("Start of InformationalPageModel page Model init");
        if (Objects.nonNull(resource) && !resource.getPath().contains("/conf/") && Objects.nonNull(currentPage)) {
            leftIndexShiftForSiteWOParentName = InformationalHelper.leftIndexShiftForSiteWOParentName(resource);
            brandName = InformationalHelper.getBrandName(resource);
            scriptUrl = InformationalConfigurationUtils.getScriptUrl();
            LOGGER.debug("ScriptURL from init of InformationalPageModel {}", scriptUrl);
            fetchTitleAndSeoDetails();
            fetchSEOImageDetails();
            expFragmentRootPath = InformationalUtils.getCurrentBrandExpFragmentRootPath(brandName,"experience-fragments", resource.getPath());
            
            if (!resource.getPath().contains(InformationalConfigurationUtils.getRootErrorPageName())
                    && !resource.getPath().contains(expFragmentRootPath)) {
                hrefLangList = InformationalUtils.getHrefLangPropertyList(brandName,resource);
            }
            
            String sitesRootPath = "";
            int indexBrandName = resource.getPath().indexOf(brandName);
            sitesRootPath = resource.getPath().substring(0, indexBrandName + brandName.length());
            LOGGER.debug("sitesRootPath is {}", sitesRootPath);
            
            if (!resource.getPath().equalsIgnoreCase(sitesRootPath+Constants.SLASH+Constants.JCR_CONTENT)
                    && !resource.getPath().contains(expFragmentRootPath)) {
                
                currentPagePath = currentPage.getPath();
                LOGGER.debug("currentPagePath is {}", currentPagePath);
                getAllExperienceFragmentPaths(sitesRootPath, currentPagePath);
            }
            InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(resource);
            clientlibCategory = inheritanceValueMap.getInherited("clientlibCategory", String.class);
            description = currentPage.getDescription();
        }
        LOGGER.info("End of InformationalPageModel page Model init");
    }

    /**
     * This method gets all the dynamic experience fragment paths which 
     * are used in the site. The paths will be generated based on the 
     * configuration added for each fragment name
     *
     * @param sitesRootPath   Site Root Path
     * @param currentPagePath current Page Path
     */
    private void getAllExperienceFragmentPaths(String sitesRootPath, String currentPagePath) {
        LOGGER.info("start of getAllExperienceFragmentPaths method");

        if (currentPagePath.contains(sitesRootPath)) {
            String countryLocalePath = InformationalHelper.getRelativePath(currentPagePath, resource);
            headerPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath, headerPath,
                    InformationalConfigurationUtils.getHeaderExpFragmentName());

            LOGGER.debug("headerPath is {} ", headerPath);

            footerPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath, footerPath,
                    InformationalConfigurationUtils.getFooterExpFragmentName());

            LOGGER.debug("footerPath is {} ", footerPath);

            retailerInterstitialPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath,
                    retailerInterstitialPath, InformationalConfigurationUtils.getRetailerInterstitialPath());

            LOGGER.debug("retailerInterstitialPath is {} ", retailerInterstitialPath);

            leavingInterstitialPath = getExpFragmentPathDetails(currentPagePath, countryLocalePath,
                    leavingInterstitialPath, InformationalConfigurationUtils.getLeavingInterstitialPath());

            LOGGER.debug("leavingInterstitialPath is {} ", leavingInterstitialPath);

            interstitialApp = getExpFragmentPathDetails(currentPagePath, countryLocalePath, interstitialApp,
                    InformationalConfigurationUtils.getInterstitialApp());

            LOGGER.debug("interstitialApp is {} ", interstitialApp);
        }
        LOGGER.info("getAllExperienceFragmentPaths method of InformationalPageModel ------> End");

    }

    /**
     * This method gets the dynamic experience fragment path based on name
     * configured, country and locale of the site.
     * 
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
     * This method handles the condition when resource for dynamically 
     * generated experience fragment path is null. Falls back to master 
     * version of English locale fragment if site specific is not present.
     * @param path
     * @param expFragmentName
     * @param locale
     * @param localeString
     * @return
     */
    private String handleNullResource(String path, String expFragmentName, String locale, String localeString) {
        LOGGER.info("Start handleNullResource method");
        if (Objects.isNull(resource.getResourceResolver().getResource(path))) {
            path = expFragmentRootPath + brandName + Constants.SLASH
                    + localeString.replace('-', '_') + Constants.SLASH + expFragmentName + Constants.SLASH
                    + expFragmentName + Constants.UNDERSCORE + localeString + Constants.JCR_CONTENT_ROOT;
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
     * This method generates the exp fragment path based on fragment name 
     * and locale
     * @param expFragmentName
     * @param locale
     * @return
     */
    private String getExpFragmentPath(String expFragmentName, String locale) {
        return expFragmentRootPath + brandName + Constants.SLASH + locale
                + Constants.SLASH + expFragmentName + Constants.SLASH + Constants.MASTER
                + Constants.JCR_CONTENT_ROOT;
    }

    /**
     * This method generates the experience fragment path based on country-locale 
     * path, fragment name and locale 
     * @param countryLocalePath
     * @param expFragmentName
     * @param locale
     * @return
     */
    private String getExpFragmentPath(String countryLocalePath, String expFragmentName, String locale) {
        return expFragmentRootPath + brandName + Constants.SLASH + locale
                + Constants.SLASH + expFragmentName + Constants.SLASH + expFragmentName + Constants.UNDERSCORE
                + countryLocalePath.split(Constants.SLASH)[2] + Constants.JCR_CONTENT_ROOT;
    }

    /**
     * This method returns the transformed SEO compliant Home page title
     * @param resource page resource
     * @param childPageFlag Child Page Flag
     * @return homePagetitle home page title
     */
    protected String getHomePageTitle(Resource resource, boolean childPageFlag) {
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
   * Method to get the Home Page SEO Image
   * 
   * @return void
   */
  public void fetchSEOImageDetails() {
   LOGGER.info("fetchSEOImageDetails method of InformationalPageModel ----> Start");
   String requestURL = request.getRequestURL().toString();
   String scheme_domain = requestURL.replace(request.getRequestURI(), "");
   if (Objects.nonNull(currentPage.getContentResource())) {
      seoImage = StringUtils.isNotBlank(currentPage.getProperties().get("metaNameImage", String.class))
              ? currentPage.getProperties().get("metaNameImage", String.class)
              : fetchFallbackImage(scheme_domain, resource);
      if (seoImage.startsWith("/content/dam")) {
        seoImage = scheme_domain + seoImage;
      }
    }
    LOGGER.info("fetchSEOImageDetails method of InformationalPageModel ----> End");
  }

  /**
   * Method returns the fallback image configured at the root level
   * 
   * @param resource page resource
   * @return baseImage Base Image
   */
  protected String fetchFallbackImage(String scheme_domain, Resource resource) {
    baseImage = "";
    Page homePage = currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName);
    if (Objects.nonNull(currentPage.getContentResource())) {
      ValueMap homePageProperties = homePage.getContentResource().adaptTo(ValueMap.class);
      if (Objects.nonNull(homePageProperties)
          && StringUtils.isNotBlank(homePageProperties.get("baseMetaImage", String.class))) {
        baseImage = homePageProperties.get("baseMetaImage", String.class);
        if (baseImage.startsWith("/content/dam")) {
          baseImage = scheme_domain + baseImage;
        }
      }
    }
    return baseImage;
  }

    /**
     * This methods compares the page path with home page and returns the flag if it is home page
     * @param path
     * @return is home page flag
     */
    private boolean isHomepage(String path) {
        return path.equalsIgnoreCase(homePagePath);
    }

    /**
     * This method fetches the Page Title, SEO information and site information
     * needed for Analytics
     */
    private void fetchTitleAndSeoDetails() {
        if (Objects.nonNull(currentPage)
                && Objects.nonNull(currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName))) {
            Page homePage = currentPage.getAbsoluteParent(5 + leftIndexShiftForSiteWOParentName);
            homePagePath = homePage.getPath();
            parentPageType = fetchParentPageType(currentPage);
            siteCountry = fetchSiteCountry(currentPage.getPath(),resource);
            if (currentPage.getDepth() > 6
                    && Objects.nonNull(currentPage.getAbsoluteParent(6 + leftIndexShiftForSiteWOParentName))) {
                siteSection = currentPage.getAbsoluteParent(6 + leftIndexShiftForSiteWOParentName).getName();
                if (currentPage.getDepth() > 7
                        && Objects.nonNull(currentPage.getAbsoluteParent(7 + leftIndexShiftForSiteWOParentName))) {
                    siteSubSection = currentPage.getAbsoluteParent(7 + leftIndexShiftForSiteWOParentName).getName();
                }
            } else {
                siteSection = currentPage.getName();
            }
            fetchtitleDetails(currentPage);
            Resource seoKeywordsNodeResource = resource.getResourceResolver()
                    .getResource(resource.getPath() + "/metaKeywords");
            fetchSeoDetails(seoKeywordsNodeResource);
            getMetaPageName(currentPage);
        }
    }

    /**
     * This method returns the parent page type used for analytics
     * @param currentPage current Page
     * @return parentPageType parent page type string
     */
    private String fetchParentPageType(Page currentPage) {
        String pageResourceType = currentPage.getProperties().get(Constants.SLING_RESOURCETYPE, String.class);
        if (Objects.nonNull(pageResourceType)) {
            parentPageType = ParentPageType.fetch(pageResourceType);
        }
        return parentPageType;
    }


    /**
     * This method returns the meta page name Needed for analytics
     * @param currentPage
     */
    private void getMetaPageName(Page currentPage) {
        LOGGER.info("getMetaPageName method of InformationalPageModel ----> Start");
        String pageResourceType = currentPage.getProperties().get("sling:resourceType", String.class);
        if (Objects.nonNull(pageResourceType)) {
            String currentBrandName = InformationalHelper.getBrandName(resource).equalsIgnoreCase("mattel-corporate") ? "CORP" : InformationalHelper.getBrandName(resource);
            LOGGER.debug("brandName is {}", currentBrandName);
            if (pageResourceType.contains(Constants.HOMEPAGE_TEMPLATE) || pageResourceType.contains(Constants.CONTENTPAGE_TEMPLATE)) {
                pageName = currentBrandName + ":" + fetchSiteCountry(currentPage.getPath(),resource).toUpperCase() + ":" + currentPage.getName();
                LOGGER.debug("Final page name value in meta tag is {}", pageName);
            }
        }
        LOGGER.info("getMetaPageName method of InformationalPageModel ----> End");
    }

    /**
     * This method generates the page title based on SEO requirement in the
     * format "Page Title | Global SEO Title" for all pages. And in
     * "Global SEO Title | Page Title" for Home page
     * 
     * @param currentPage
     *            Current page Object
     */
    private void fetchtitleDetails(Page currentPage) {
        LOGGER.info("fetchtitleDetails method of InformationalPageModel ----> Start");
        boolean ishomePage = isHomepage(currentPage.getPath());

        if (ishomePage) {
            if (Objects.nonNull(getHomePageTitle(resource, true))
                    && Objects.nonNull(getHomePageTitle(resource, false))) {
                title = getHomePageTitle(resource, true) + " | " + getHomePageTitle(resource, false);
            } else if (Objects.isNull(getHomePageTitle(resource, true))) {
                title = getHomePageTitle(resource, false);
            } else if (Objects.isNull(getHomePageTitle(resource, false))) {
                title = getHomePageTitle(resource, true);
            }
            LOGGER.debug("Page Title for home page is : {}",title);

        } else {
            ValueMap prop = resource.adaptTo(ValueMap.class);
            String currentPageTitle = null;
            if (Objects.nonNull(prop)) {
                String templatePath = currentPage.getTemplate().getPath();
                currentPageTitle = StringUtils.equalsIgnoreCase(templatePath, Constants.NEWS_ARTICLE_TEMPLATE_PATH) ? currentPage.getTitle() :prop.get("seoTitle", "");
            }
            String homePageTitle = getHomePageTitle(resource, true);
            if (Objects.nonNull(homePageTitle) && Objects.nonNull(currentPageTitle)) {
                title = currentPageTitle + " | " + homePageTitle;
            } else if (Objects.isNull(homePageTitle)) {
                title = currentPageTitle;
            } else {
                title = homePageTitle;
            }
            LOGGER.debug("Page Title for child page is : {}",title);
        }
        LOGGER.info("fetchtitleDetails method of InformationalPageModel ----> End");
    }


    /**
     * This method gets all the metadata keyword list configured in the page
     * @param seoKeywordsNodeResource
     */
    private void fetchSeoDetails(Resource seoKeywordsNodeResource) {
        LOGGER.info("fetchSeoDetails method of InformationalPageModel ----> Start");
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
        LOGGER.info("fetchSeoDetails method of InformationalPageModel ----> End");
    }


    /**
     * This method returns the country of the current site. 
     * @param currentPagePath current Page Path
     * @param resource resource
     * @return siteCountry country of the current site. 
     */
    private String fetchSiteCountry(String currentPagePath, Resource resource) {
        LOGGER.info("fetchSiteCountry method of InformationalPageModel ----> Start");
        String tempPath = InformationalHelper.getRelativePath(currentPagePath, resource);
        if (!tempPath.isEmpty()) {
            LOGGER.debug("Relative Path is {}", tempPath);
            siteCountry = tempPath.substring(tempPath.indexOf('/') + 1, tempPath.lastIndexOf('/'));
        }
        LOGGER.info("fetchSiteCountry method of InformationalPageModel ----> Start");
        return siteCountry;
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


    public List<HrefLangPojo> getHrefLangList() {
        return hrefLangList;
    }


    public String getClientlibCategory() {
        return clientlibCategory;
    }

    public String getHeaderPath() {
        return headerPath;
    }


    public String getFooterPath() {
        return footerPath;
    }

    public String getRetailerInterstitialPath() {
        return retailerInterstitialPath;
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

    public String getSiteSection() {
        return siteSection;
    }

    public String getHomePagePath() {
        return homePagePath;
    }

    public String getSiteSubSection() {
        return siteSubSection;
    }

    public String getSiteCountry() {
        return siteCountry;
    }

    public String getCurrentPagePath() {
        return InformationalHelper.checkLink(currentPagePath, resource);
  }

    public String getDescription() {
        return description;
  }

    public void setDescription(String description) {
        this.description = description;
  }

    public String getSeoImage() {
        return seoImage;
  }

    public void setSeoImage(String seoImage) {
        this.seoImage = seoImage;
  }

    public String getBaseImage() {
        return baseImage;
  }

}
