package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.pojos.NavigationPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

/**
 * @author CTS
 *
 */
@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavigationModel extends BasePageModel {

  @Self
  @Via("resource")
  private Resource resource;

  @SlingObject
  SlingHttpServletRequest slinghttprequest;
  
  @Inject
  @Default(values = "/content/ag/en")
  private String navRootPath;

  List<NavigationPojo> navLinksList = new ArrayList<>();
  List<NavigationPojo> externalStoreLocations = new ArrayList<>();

  @Inject
  @Optional
  @Via("resource")
  private Node navigationLinks;

  @Inject
  @Optional
  @Via("resource")
  private Node externalStores;

  @Inject
  @Via("resource")
  private String myAccountLink;

  @Inject
  @Via("resource")
  private String agSignInLink;

  @Inject
  @Via("resource")
  private String agRewardsLink;

  @Inject
  @Via("resource")
  private String shopLinkUrl;

  @Inject
  @Via("resource")
  private String shoppingBagLink;

  @Inject
  @Via("resource")
  private String helpLink;

  @Inject
  @Via("resource")
  @Default(values = "https://www.americangirl.com/shop/ag/email-signup")
  private String signUpUrl;
  
  @Inject
  @Via("resource")
  private String signoutLink;

  @Inject
  private MultifieldReader multifieldReader;
  
  @Inject
  PropertyReaderService propertyReaderService;
  
  @Inject
  private Page currentPage;

  @Inject
  @Via("resource")
  @Default(values = "/content/ag/en/retail")
  private String externalStoreRootPath;

  private String locationTitle;

  private Page rootPage;
  
  private String shopifyDomain;

  private Map<String, Map<String, String>> firstLevel;

  private Map<String, Map<String, String>> secondLevel;

  private Map<String, Map<String, String>> thirdLevel;

  private static final String HIDE_IN_NAV = "hideInNav";
  private static final String SKIP_NAV = "skipnav";
  private static final String STOP_NAV = "stopnav";
  private static final String SHOPPING_BAG_LINK = "shoppingBagLink";
  private static final String SHOPPING_LINK_URL = "shopLinkUrl";
  private static final String HELP_LINK = "helpLink";
  private static final String NAV_CATEGORIES = "categories";
  private static final Logger LOGGER = LoggerFactory.getLogger(NavigationModel.class);

  @PostConstruct
  protected void init() {
    NavigationModel.LOGGER.info("NavigationModel init method - Start");
    NavigationModel.LOGGER.debug("navigation resource is {}", resource);
    PageManager pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
    Page externalStoreRootPage = pageManager.getPage(externalStoreRootPath);
    locationTitle = setPageTitle(externalStoreRootPage);
    rootPage = pageManager.getPage(navRootPath);
    firstLevel = new LinkedHashMap<>();
    secondLevel = new LinkedHashMap<>();
    thirdLevel = new LinkedHashMap<>();
    setNavigationHierarchyData();
    if (navigationLinks != null) {
      NavigationModel.LOGGER.debug("inside if loop");
      Map<String, ValueMap> staticNavLinks = multifieldReader.propertyReader(navigationLinks);
      for (Map.Entry<String, ValueMap> entry : staticNavLinks.entrySet()) {
        NavigationPojo navigationPojo = new NavigationPojo();
        NavigationModel.LOGGER.debug("Navigation Link text{}",
            entry.getValue().get("navLinkText", String.class));
        navigationPojo.setNavLinkText(entry.getValue().get("navLinkText", String.class));

        String navLink = entry.getValue().get("navLinkUrl", String.class);
        if (StringUtils.isNotBlank(navLink)) {
          NavigationModel.LOGGER.debug("Navigation Link URL{}", navLink);
          if (navLink.contains(NAV_CATEGORIES)) {
            navigationPojo.setNavLinkUrl(EcomUtil.getPlpPageLink(navLink, resource));
          } else {
            navigationPojo.setNavLinkUrl(EcomUtil.checkLink(navLink, resource));
          }
          NavigationModel.LOGGER.debug("Navigation Link from navigationPojo{}",
              navigationPojo.getNavLinkUrl());
        }

        NavigationModel.LOGGER.debug("Open link in {}",
            entry.getValue().get("navLinkOpenIn", String.class));
        navigationPojo.setNavLinkOpenIn(entry.getValue().get("navLinkOpenIn", String.class));
        NavigationModel.LOGGER.debug("Navigation Links {} ", navigationLinks);
        navLinksList.add(navigationPojo);
      }
    }
    if (Objects.nonNull(currentPage) && Objects.nonNull(currentPage.getContentResource())) {
        InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(
                    currentPage.getContentResource());
        String sitekey = StringUtils.isNotBlank(inheritanceValueMap.getInherited("siteKey", String.class))
        		    ? inheritanceValueMap.getInherited("siteKey", String.class) : "ag_en";
        LOGGER.debug("Site key is: {}", sitekey);
        shopifyDomain = propertyReaderService.getShopifyDomain(sitekey);
        LOGGER.debug("shopifyDomain is: {}", shopifyDomain);
    }
    setExternalStoreLocations();
    NavigationModel.LOGGER.info("NavigationModel init method - End");
  }

  /**
   * This methods sets the external store location data
   */
  private void setExternalStoreLocations() {
    NavigationModel.LOGGER.info("setExternalStoreLocations -> Start");
    if (Objects.nonNull(externalStores)) {
      Map<String, ValueMap> externalStoresMap = multifieldReader.propertyReader(externalStores);
      for (Map.Entry<String, ValueMap> entry : externalStoresMap.entrySet()) {
        NavigationPojo navigationPojo = new NavigationPojo();
        LOGGER.debug("External Store location: {}",
            entry.getValue().get("Store location", String.class));
        navigationPojo.setStoreLocation(entry.getValue().get("storeLocation", String.class));
        String externalStoreLink = entry.getValue().get("storeLink", String.class);
        if (StringUtils.isNotBlank(externalStoreLink)) {
          LOGGER.debug("External Store Link: {}", externalStoreLink);
          navigationPojo.setStoreLink(EcomUtil.checkLink(externalStoreLink, resource));
        }
        LOGGER.debug("External Store Links {} ", externalStores);
        externalStoreLocations.add(navigationPojo);
      }
    }
    NavigationModel.LOGGER.info("setExternalStoreLocations -> End");
  }

  /**
   * this method sets first level navigation link information in a map
   */
  private void setNavigationHierarchyData() {
    NavigationModel.LOGGER.info("setNavigationHierarchyData -> Start");
    Map<String, String> map = new LinkedHashMap<>();
    if (null != rootPage) {
      String rootPageName = setPageTitle(rootPage);
      Iterator<Page> firstLevelPagesItr = rootPage.listChildren();
      while (firstLevelPagesItr.hasNext()) {
        Page firstLevelPage = firstLevelPagesItr.next();
        if (!checkNavAttributes(firstLevelPage, NavigationModel.HIDE_IN_NAV)) {
          if (!checkNavAttributes(firstLevelPage, NavigationModel.SKIP_NAV)) {
            handleNonSkipInNavPages(map, firstLevelPage, 1);
          } else {
            handleSkipInNavPages(map, firstLevelPage, 1);
          }
        }
      }
      firstLevel.put(rootPageName, map);
    }
    LOGGER.debug("first level page are : {}", firstLevel);
    LOGGER.debug("second level page are : {}", secondLevel);
    LOGGER.debug("third level page are : {}", thirdLevel);
    NavigationModel.LOGGER.info("setNavigationHierarchyData -> End");
  }

  /**
   * 
   * @param firstLevelPage
   *          first level AEM page
   * @param firstLevelPageName
   *          first level AEM page name
   */
  private void setSecondLevelPages(Page firstLevelPage, String firstLevelPageName) {
    NavigationModel.LOGGER.info("setSecondLevelPages - Start");
    Map<String, String> map = new LinkedHashMap<>();
    if (null != firstLevelPage) {
      Iterator<Page> secondLevelPagesItr = firstLevelPage.listChildren();
      while (secondLevelPagesItr.hasNext()) {
        Page secondLevelPage = secondLevelPagesItr.next();
        NavigationModel.LOGGER.debug("second Level Page name is {}", secondLevelPage.getName());
        if (!checkNavAttributes(secondLevelPage, NavigationModel.HIDE_IN_NAV)) {
          if (!checkNavAttributes(secondLevelPage, NavigationModel.SKIP_NAV)) {
            handleNonSkipInNavPages(map, secondLevelPage, 2);
          } else {
            handleSkipInNavPages(map, secondLevelPage, 2);
          }
        }
      }
      secondLevel.put(firstLevelPageName, map);
    }
    NavigationModel.LOGGER.info("setSecondLevelPages - End");
  }

  /**
   * 
   * @param secondLevelPage
   *          second level AEM page
   * @param secondLevelPageName
   *          second level AEM page name
   */
  private void setThirdLevelPages(Page secondLevelPage, String secondLevelPageName) {
    NavigationModel.LOGGER.info("setThirdLevelPages - Start");
    Map<String, String> map = new LinkedHashMap<>();
    if (null != secondLevelPage) {
      Iterator<Page> thirdLevelPagesItr = secondLevelPage.listChildren();
      while (thirdLevelPagesItr.hasNext()) {
        Page thirdLevelPage = thirdLevelPagesItr.next();
        if (!checkNavAttributes(thirdLevelPage, NavigationModel.HIDE_IN_NAV)) {
          NavigationModel.LOGGER.debug("third Level Page name is {}", thirdLevelPage.getName());
          if (!checkNavAttributes(thirdLevelPage, NavigationModel.SKIP_NAV)) {
            handleNonSkipInNavPages(map, thirdLevelPage, 3);
          } else {
            handleSkipInNavPages(map, thirdLevelPage, 3);
          }
        }
      }
    }
    thirdLevel.put(secondLevelPageName, map);
    NavigationModel.LOGGER.info("setThirdLevelPages - End");
  }

  /**
   * This method handles the links for page which have skip in navigation functionality enabled
   * 
   * @param map
   *          page information map
   * @param page
   *          AEM page
   * @param level
   *          level of navigation for the page
   */
  private void handleSkipInNavPages(Map<String, String> map, Page page, int level) {
    NavigationModel.LOGGER.info("handleSkipInNavPages for level {} - Start", level);
    if (checkNavAttributes(page, NavigationModel.STOP_NAV)) {
      NavigationModel.LOGGER
          .debug("both skip and stop in nav have been checked.stop in nav will take precendece");
      String pageName = setPageTitle(page);
      String pageUrl = getPageLink(page);
      map.put(pageName, pageUrl);
      Map<String, String> emptymap = new LinkedHashMap<>();
      if (level == 1) {
        secondLevel.put(pageName, emptymap);
      } else if (level == 2) {
        thirdLevel.put(pageName, emptymap);
      }
    } else {
      NavigationModel.LOGGER.debug("Skip in nav enabled for level {} pages. retrieving child pages",
          level);
      Iterator<Page> middLevelPagesItr = page.listChildren();
      while (middLevelPagesItr.hasNext()) {
        Page middLevelPage = middLevelPagesItr.next();
        NavigationModel.LOGGER.debug("level {} page name is : {}", level, middLevelPage.getName());
        if (!checkNavAttributes(middLevelPage, NavigationModel.HIDE_IN_NAV)) {
          handleMidLevelPageIteration(map, level, middLevelPage);
        }
      }
    }
    NavigationModel.LOGGER.info("handleSkipInNavPages for level {} - End", level);
  }

  /**
   * This method handles mid level page checks for handleSkipInNavPages method
   * 
   * @param map
   *          page information map
   * @param level
   *          level of navigation for the page
   * @param middLevelPage
   *          mid level AEM page
   */
  private void handleMidLevelPageIteration(Map<String, String> map, int level, Page middLevelPage) {
    NavigationModel.LOGGER.info("handleMidLevelPageIteration - Start");
    if (!checkNavAttributes(middLevelPage, NavigationModel.SKIP_NAV)) {
      NavigationModel.LOGGER.debug("Skip in nav is not enabled for child page for level {} ",level);
      String middLevelPageName = setPageTitle(middLevelPage);
      String middLevelPageUrl = getPageLink(middLevelPage);
      map.put(middLevelPageName, middLevelPageUrl);
      if (!checkNavAttributes(middLevelPage, NavigationModel.STOP_NAV)) {
        NavigationModel.LOGGER.debug("Stop in nav is not enabled for child page for level {} ",level);
        if (level == 1) {
          setSecondLevelPages(middLevelPage, middLevelPageName);
        } else if (level == 2) {
          setThirdLevelPages(middLevelPage, middLevelPageName);
        }
      } else {
        NavigationModel.LOGGER.debug("Stop in nav is enabled for child page for level {} ",level);
        Map<String, String> emptymap = new LinkedHashMap<>();
        if (level == 1) {
          secondLevel.put(middLevelPageName, emptymap);
        } else if (level == 2) {
          thirdLevel.put(middLevelPageName, emptymap);
        }
      }
    } else if (level == 2) {
      NavigationModel.LOGGER.debug("Skip in nave enabled for level 2 page");
      handleSkipInNavPages(map, middLevelPage, 3);
    }
    NavigationModel.LOGGER.info("handleMidLevelPageIteration - End");
  }

  /**
   * This method handles the links for page which do not have skip in navigation functionality
   * enabled. it calls appropriate method based on level
   * 
   * @param map
   *          page information map
   * @param page
   *          AEM page
   * @param level
   *          level of navigation for the page
   */
  private void handleNonSkipInNavPages(Map<String, String> map, Page page, int level) {
    NavigationModel.LOGGER.info("handleNonSkipInNavPages for level {} - Start", level);
    NavigationModel.LOGGER.debug("Skip in nav not enabled for level {} pages", level);
    NavigationModel.LOGGER.debug("level {} page name is : {}", level, page.getName());
    String pageName = setPageTitle(page);
    String pageUrl = getPageLink(page);
    map.put(pageName, pageUrl);
    if (!checkNavAttributes(page, NavigationModel.STOP_NAV)) {
      if (level == 1) {
        setSecondLevelPages(page, pageName);
      } else if (level == 2) {
        setThirdLevelPages(page, pageName);
      }
    } else {
      Map<String, String> emptymap = new LinkedHashMap<>();
      if (level == 1) {
        secondLevel.put(pageName, emptymap);
      } else if (level == 2) {
        thirdLevel.put(pageName, emptymap);
      }
    }
    NavigationModel.LOGGER.info("handleNonSkipInNavPages for level {} - End", level);
  }

  /**
   * This method returns the link for based on path and check of URL mapping and vanity URL
   * 
   * @param link
   *          link type
   * @param linkName
   *          actual link
   * @return URL final URL
   */
  private String getLink(String link, String linkName) {
    NavigationModel.LOGGER.info("getLink - Start");
    String url = StringUtils.EMPTY;
    if (StringUtils.isNotBlank(link)) {
      url = EcomUtil.checkLink(link, resource);
      NavigationModel.LOGGER.debug("{} link {}", linkName, link);
      NavigationModel.LOGGER.debug("{} vanity url {}", linkName, url);
    }
    NavigationModel.LOGGER.info("getLink - End");
    return url;
  }

  /**
   * This method returns the link for based on the check if it PLP page or not
   * 
   * @param page
   *          AEM page
   * @return pageUrl page URL
   */
  private String getPageLink(Page page) {
    NavigationModel.LOGGER.info("getPageLink - Start");
    String pageUrl;
    String pagePath = page.getPath();
    if (Objects.nonNull(page.getTemplate())) {
      if (pagePath.contains(NAV_CATEGORIES)) {
        pageUrl = EcomUtil.getPlpPageLink(pagePath, resource);
      } else {
        pageUrl = EcomUtil.checkLink(pagePath, resource);
      }
    } else {
      pageUrl = "#";
    }
    NavigationModel.LOGGER.info("getPageLink - End");
    return pageUrl;
  }

  /**
   * This method check if page has the attribute. If yes then returns true
   * 
   * @param page
   *          AEM page
   * @param attrName
   *          attribute name
   * @return true/false
   */
  private boolean checkNavAttributes(Page page, String attrName) {
    NavigationModel.LOGGER.info("checkNavAttributes - Start");
    Resource pageResource = page.getContentResource();
    boolean isAttrPresent = false;
    if (Objects.nonNull(pageResource)) {
      ValueMap valueMap = pageResource.getValueMap();
      String navValue = valueMap.get(attrName, String.class);
      if (StringUtils.isNotEmpty(navValue) && "true".equalsIgnoreCase(navValue)) {
        isAttrPresent = true;
      }
    }
    NavigationModel.LOGGER.info("checkNavAttributes - End");
    return isAttrPresent;
  }

  /**
   * This method gets the page title based on below priority. Navigation Title > Title > Name
   * 
   * @param page
   *          AEM Page
   * @return title Page title
   */
  private String setPageTitle(Page page) {
    NavigationModel.LOGGER.info("setPageTitle - Start");
    String title = StringUtils.EMPTY;
    if (Objects.nonNull(page)) {
      if (StringUtils.isNotEmpty(page.getNavigationTitle())) {
        title = page.getNavigationTitle();
      } else if (StringUtils.isNotEmpty(page.getTitle())) {
        title = page.getTitle();
      } else{
        title = page.getName();
      }
    }
    NavigationModel.LOGGER.info("setPageTitle - End");
    return title;
  }

  public Map<String, Map<String, String>> getFirstLevel() {
    return firstLevel;
  }

  public void setFirstLevel(Map<String, Map<String, String>> firstLevel) {
    this.firstLevel = firstLevel;
  }

  public Map<String, Map<String, String>> getSecondLevel() {
    return secondLevel;
  }

  public void setSecondLevel(Map<String, Map<String, String>> secondLevel) {
    this.secondLevel = secondLevel;
  }

  public Map<String, Map<String, String>> getThirdLevel() {
    return thirdLevel;
  }

  public void setThirdLevel(Map<String, Map<String, String>> thirdLevel) {
    this.thirdLevel = thirdLevel;
  }

  public String getMyAccountLink() {
    return EcomUtil.checkLink(myAccountLink, resource);

  }

  public String getAgRewardsLink() {
    return EcomUtil.checkLink(agRewardsLink, resource);
  }

  public String getAgSignInLink() {
    return EcomUtil.checkLink(agSignInLink, resource);
  }

  public String getSignUpUrl() {
    return EcomUtil.checkLink(signUpUrl, resource);
  }
  
  public String getSignoutLink() {
    return EcomUtil.checkLink(signoutLink, resource);
  }

  public String getLocationTitle() {
    return locationTitle;
  }
  
  public String getShopifyDomain() {
    return shopifyDomain;
  }

  @Override
  public boolean isDisableClientLibs() {
    return checkClientLibsSelector(slinghttprequest);
  }

  /**
   * @return the rootPage
   */
  public Page getRootPage() {
    return rootPage;
  }

  /**
   * @return the navLinksList
   */
  public List<NavigationPojo> getNavLinksList() {
    return navLinksList;
  }

  /**
   * @param navLinksList
   *          the navLinksList to set
   */
  public void setNavLinksList(List<NavigationPojo> navLinksList) {
    this.navLinksList = navLinksList;
  }

  /**
   * @return the multifieldReader
   */
  public MultifieldReader getMultifieldReader() {
    return multifieldReader;
  }

  /**
   * @param multifieldReader
   *          the multifieldReader to set
   */
  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public Node getNavigationLinks() {
    return navigationLinks;
  }

  public void setNavigationLinks(Node navigationLinks) {
    this.navigationLinks = navigationLinks;
  }

  /**
   * @return the externalStores
   */
  public Node getExternalStores() {
    return externalStores;
  }

  /**
   * @param externalStores
   *          the externalStores to set
   */
  public void setExternalStores(Node externalStores) {
    this.externalStores = externalStores;
  }

  /**
   * @return the externalStoreLocations
   */
  public List<NavigationPojo> getExternalStoreLocations() {
    return externalStoreLocations;
  }

  /**
   * @param externalStoreLocations
   *          the externalStoreLocations to set
   */
  public void setExternalStoreLocations(List<NavigationPojo> externalStoreLocations) {
    this.externalStoreLocations = externalStoreLocations;
  }

  public String getShoppingBagLink() {
    return getLink(shoppingBagLink, NavigationModel.SHOPPING_BAG_LINK);
  }

  public String getShopLinkUrl() {
    return getLink(shopLinkUrl, NavigationModel.SHOPPING_LINK_URL);
  }

  public String getHelpLink() {
    return getLink(helpLink, NavigationModel.HELP_LINK);
  }

}
