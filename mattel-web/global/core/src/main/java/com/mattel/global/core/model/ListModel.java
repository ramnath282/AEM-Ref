package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.models.List;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.day.cq.wcm.api.Page;
import com.drew.lang.annotations.NotNull;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.core.utils.NavigationUtil;
import com.mattel.global.master.core.constants.Constants;

/**
 * @author CTS
 *
 */
@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ListModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListModel.class);
  private static final String STATIC_PAGE_LIST = "static";
  private static final String CHILD_LIST = "child";
  private static final String SORT_ORDER_ASC = "asc";
  private static final String SORT_ORDER_DESC = "desc";
  private static final String ORDER_BY_TITLE = "title";
  private static final String ORDER_BY_DATE = "modified";

  @Self
  @Via(type = ResourceSuperType.class)
  private List list;

  @Inject
  private Resource resource;

  @Inject
  @Via("resource")
  private String[] contentPages;

  @Inject
  @Via("resource")
  private String sortOrder;

  @Inject
  @Via("resource")
  private String orderBy;

  @Inject
  @Via("resource")
  private String maxItems;

  @Inject
  @Via("resource")
  private String linkText;

  @Inject
  @Via("resource")
  private String showMoreText;

  @Inject
  @Via("resource")
  private String showLessText;

  @Inject
  @Via("resource")
  private String image;

  @Inject
  private NavigationUtil navigationService;

  private ResourceResolver resourceResolver;

  private String backgroundImagePath;

  private java.util.List<SiteNavigationPojo> childPagesList;
  private java.util.List<SiteNavigationPojo> staticPagesList;

  @PostConstruct
  protected void init() {
    LOGGER.info("Start of init method of ListModel");
    if (null != resource) {
      resourceResolver = resource.getResourceResolver();
      backgroundImagePath = GlobalUtils.setBackgroundPath(resourceResolver, image);

    }
    childPagesList = new ArrayList<>();
    staticPagesList = new ArrayList<>();
    fetchChilPagedList();
    fetchStaticPageList();
    LOGGER.info("End of init method of ListModel ");
  }

  /**
   * this method is for adding pages to respective list for option BUILD USING
   * CHILD PAGES
   */
  private void fetchChilPagedList() {
    LOGGER.info("fetchChilPagedList -> Start : BUILD USING CHILD PAGES");
    Collection<ListItem> listItems = list.getListItems();
    listItems.forEach(item -> addPageToList(item.getPath(), ListModel.CHILD_LIST));
    LOGGER.info("fetchChilPagedList -> End");
  }

  /**
   * this method is for adding pages to respective list for option BUILD USING
   * FIXED LIST
   */
  public void fetchStaticPageList() {
    LOGGER.info("fetchStaticPageList -> Start : BUILD USING FIXED LIST");
    if (Objects.nonNull(contentPages)) {
      java.util.List<String> staticPagesPathList = Arrays.asList(contentPages);
      staticPagesPathList.forEach(item -> addPageToList(item, ListModel.STATIC_PAGE_LIST));
      orderAndSortStaticList(staticPagesList);
    }
    LOGGER.info("fetchStaticPageList -> End");
  }

  /**
   * @param pagePath
   *          page path for which we needs to get all required properties
   * @param listFor
   *          this value either static/child
   */
  private void addPageToList(String pagePath, String listFor) {
	LOGGER.info("addPageToList -> Start");
    LOGGER.debug("addPageToList parameters : PagePath {} , ListFor : {}", pagePath, listFor);
    if (StringUtils.isNotBlank(pagePath)) {
      Page page = getPageFromPath(pagePath);
      if (Objects.nonNull(page)) {
        if (ListModel.STATIC_PAGE_LIST.equalsIgnoreCase(listFor)) {
          staticPagesList.add(navigationService.fetchPageProperties(page));
        } else if (StringUtils.isNotBlank(pagePath)) {
          childPagesList.add(navigationService.fetchPageProperties(page));
        }
      }
    }
    LOGGER.info("addPageToList -> End");
  }

  /**
   * @param pagePath
   *          pagePath page path for which we needs to get all required
   *          properties
   * @return
   */
  private Page getPageFromPath(String pagePath) {
	LOGGER.info("getPageFromPath -> Start");
    LOGGER.debug("page path parameter is : {}", pagePath);
    if (StringUtils.isNotBlank(pagePath) && Objects.nonNull(resourceResolver)) {
      Resource pageResource = resourceResolver.resolve(pagePath);
      LOGGER.info("getPageFromPath -> End");
      return Optional.ofNullable(pageResource).map(pr -> pr.adaptTo(Page.class)).orElse(null);
    }
    LOGGER.info("getPageFromPath -> End");
    return null;
  }

  /**
   * method sorts list of objects based on ORDERBY and SORTBY parameter
   * 
   * @param staticPagesList
   *          list to sort based on parameters
   */
  private void orderAndSortStaticList(java.util.List<SiteNavigationPojo> staticPagesList) {
    LOGGER.info("orderAndSortStaticList -> Start");
    LOGGER.debug("orderAndSortStaticList params are : Orderby {}, Sortby {} ", orderBy, sortOrder);
    if (SORT_ORDER_ASC.equals(sortOrder) && ORDER_BY_TITLE.equals(orderBy)) {
      staticPagesList.sort((l1, l2) -> l1.getPageName().compareTo(l2.getPageName()));
    } else if (SORT_ORDER_DESC.equals(sortOrder) && ORDER_BY_TITLE.equals(orderBy)) {
      staticPagesList.sort((l1, l2) -> l2.getPageName().compareTo(l1.getPageName()));
    } else if (SORT_ORDER_ASC.equals(sortOrder) && ORDER_BY_DATE.equals(orderBy)) {
      staticPagesList
          .sort((l1, l2) -> l1.getLastModifiedDate().compareTo(l2.getLastModifiedDate()));
    } else if (SORT_ORDER_DESC.equals(sortOrder) && ORDER_BY_DATE.equals(orderBy)) {
      staticPagesList
          .sort((l1, l2) -> l2.getLastModifiedDate().compareTo(l1.getLastModifiedDate()));
    }
    LOGGER.info("orderAndSortStaticList -> End");
  }

  public @NotNull Collection<ListItem> getListItems() {
    return list.getListItems();
  }

  public java.util.List<SiteNavigationPojo> getChildPagesList() {
    return childPagesList;
  }

  /**
   * getter method for static page list which contains logic for checking
   * maxItems and returning only particular number of objects;
   * 
   * @return
   */
  public java.util.List<SiteNavigationPojo> getStaticPagesList() {
    LOGGER.info("getStaticPagesList -> start");
    LOGGER.debug("Max Items -> {}", maxItems);
    if (StringUtils.isNotEmpty(maxItems)) {
      long maxItemNum = Long.parseLong(maxItems);
      LOGGER.info("getStaticPagesList -> End");
      return staticPagesList.stream().limit(maxItemNum).collect(Collectors.toList());
    }
    LOGGER.info("getStaticPagesList -> End");
    return staticPagesList;
  }

  public String getBackgroundImagePath() {
    return backgroundImagePath;
  }

  public String getShowMoreText() {
    return GlobalUtils.removeTags(showMoreText, Constants.REMOVE_TAGS,
        Constants.EMPTY_ARRAY_STRING);
  }

  public String getShowLessText() {
    return GlobalUtils.removeTags(showLessText, Constants.REMOVE_TAGS,
        Constants.EMPTY_ARRAY_STRING);
  }

  public String getLinkText() {
    return GlobalUtils.removeTags(linkText, Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING);
  }
}
