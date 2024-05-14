package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import com.mattel.ecomm.core.pojos.BreadcrumbDetails;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductDetailBreadcrumbModel {
  private static final String PATH_SEPERATOR = "/";
  private static final String CATEGORY_DELIMITER = "-";
  private static final String REGEX_SPACE = " ";
  private static final String REGEX_CONTIGUOUS_SPACE = "\\s+";
  private static final String REGEX_SPECIAL_CHARACTERS = "[\\.\\?\\'\\+\\*\\^\"!#:,_&-]";
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailBreadcrumbModel.class);
  List<BreadcrumbDetails> breadcrumbLinks = new LinkedList<>();
  @SlingObject
  private SlingHttpServletRequest request;
  @Inject
  Resource resource;
  @Inject
  PropertyReaderService propertyReaderService;
  @Inject
  GetProductTypeService getProductType;
  @Inject
  private Page currentPage;

  @PostConstruct
  protected void init() {
    try {
      final String[] selectors = request.getRequestPathInfo().getSelectors();
      final String siteKey;
      final String pdpLink;

      ProductDetailBreadcrumbModel.LOGGER.info("PDPBreadcrumbModel Init Start");

      if (selectors.length >= 3) {
        siteKey = selectors[0];

        if (selectors.length == 4) {
          pdpLink = selectors[3];
        } else {
          pdpLink = selectors[2];
        }
        if(Objects.nonNull(currentPage)){
          final PageManager pageManager = currentPage.getPageManager();
          String rootLandingPagePath = EcomUtil.getInheritedProperty(currentPage.getContentResource(), "rootLandingPage");
          ProductDetailBreadcrumbModel.LOGGER.debug("Root Landing Page Configured is : {}",rootLandingPagePath);
          if(Objects.nonNull(pageManager)){
            setBreadCrumbs(siteKey, pdpLink,rootLandingPagePath,currentPage,pageManager);
            ProductDetailBreadcrumbModel.LOGGER.debug("Product detail page breadcrumbs : {}",
                    breadcrumbLinks);
            }
        }
      } else {
        ProductDetailBreadcrumbModel.LOGGER.error("Invalid request for bread crumbs encountered");
        breadcrumbLinks.clear();
      }
    } catch (final RuntimeException re) {
      ProductDetailBreadcrumbModel.LOGGER
          .error("Unknown exception occured while building product details bread crumb model", re);
      breadcrumbLinks.clear();
    }
  }

  private void setBreadCrumbs(String siteKey, String pdpLink, String rootLandingPagePath,Page currentPage,PageManager pageManager) {
    if (!StringUtils.isEmpty(siteKey) && !StringUtils.isEmpty(pdpLink)) {
      final Map<String, String> properties = getProductType.getProductType(pdpLink, siteKey);
      final StringBuilder rootPath = new StringBuilder();
      Page rootLandingPage = null;
      if(StringUtils.isNotBlank(rootLandingPagePath)){
    	rootLandingPage =  pageManager.getPage(rootLandingPagePath);
      } else {
    	rootLandingPage = currentPage.getAbsoluteParent(3);
      }
      breadcrumbLinks.clear();

      if (Objects.nonNull(rootLandingPage)) {
    	ProductDetailBreadcrumbModel.LOGGER.debug("Final Root Landing Page is : {}",rootLandingPage.getPath());
        final BreadcrumbDetails homePageBreadCrumb = new BreadcrumbDetails();
        homePageBreadCrumb.setTitle(rootLandingPage.getTitle());
        homePageBreadCrumb.setPagePath(EcomUtil.checkLink(rootLandingPage.getPath(), resource));
        breadcrumbLinks.add(homePageBreadCrumb);
      }

      if (!StringUtils.isEmpty(propertyReaderService.getProductCategoryPath(siteKey))) {
        rootPath.append(propertyReaderService.getProductCategoryPath(siteKey));
      }

      if (!StringUtils.isEmpty(properties.get(Constant.BREADCRUMB_CANONICAL_TAG))) {
        final BreadcrumbDetails canonicalCategoryBreadCrumb = buildCanonicalCategoryBreadCrumb(
            properties, rootPath);
        breadcrumbLinks.add(canonicalCategoryBreadCrumb);
      }

      if (!StringUtils.isEmpty(properties.get(Constant.PRODUCT_NAME_TAG))) {
        final BreadcrumbDetails productBreadCrumb = new BreadcrumbDetails();

        productBreadCrumb.setTitle(properties.get(Constant.PRODUCT_NAME_TAG));
        breadcrumbLinks.add(productBreadCrumb);
      }
    } else {
      ProductDetailBreadcrumbModel.LOGGER
          .error("Invalid sitekey and pdplink encountered while building bread crumbs");
      breadcrumbLinks.clear();
    }
  }

  private BreadcrumbDetails buildCanonicalCategoryBreadCrumb(final Map<String, String> properties,
      final StringBuilder rootPath) {
    final BreadcrumbDetails canonicalCategoryBreadCrumb = new BreadcrumbDetails();
    String canonicalCategoryLink = properties.get(Constant.BREADCRUMB_CANONICAL_TAG).toLowerCase()
        .trim();

    canonicalCategoryLink = canonicalCategoryLink
        .replaceAll(ProductDetailBreadcrumbModel.REGEX_SPECIAL_CHARACTERS, "");

    if (canonicalCategoryLink.contains(ProductDetailBreadcrumbModel.REGEX_SPACE)) {
      canonicalCategoryLink = canonicalCategoryLink.replaceAll(
          ProductDetailBreadcrumbModel.REGEX_CONTIGUOUS_SPACE,
          ProductDetailBreadcrumbModel.CATEGORY_DELIMITER);
    }

    canonicalCategoryBreadCrumb.setTitle(properties.get(Constant.BREADCRUMB_CANONICAL_TAG));
    canonicalCategoryBreadCrumb.setPagePath(
        rootPath.toString() + ProductDetailBreadcrumbModel.PATH_SEPERATOR + canonicalCategoryLink);
    return canonicalCategoryBreadCrumb;
  }

  public List<BreadcrumbDetails> getBreadcrumbLinks() {
    return breadcrumbLinks;
  }

  public void setBreadcrumbLinks(List<BreadcrumbDetails> breadcrumbLinks) {
    this.breadcrumbLinks = breadcrumbLinks;
  }

}
