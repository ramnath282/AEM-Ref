package com.mattel.ecomm.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.core.pojos.PDPBreadcrumbPojo;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = {Resource.class,
        SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PDPBreadcrumbModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(PDPBreadcrumbModel.class);
    @Inject
    Resource resource;

    String productTitle = StringUtils.EMPTY;
    String pagePath = StringUtils.EMPTY;
    List<PDPBreadcrumbPojo> list = new ArrayList<>();
    private PDPBreadcrumbPojo mobilePdpBreadcrumbPojo = new PDPBreadcrumbPojo();
    @SlingObject
    private SlingHttpServletRequest request;
    @Inject
    private ProductService productService;
    private String productSKUId = StringUtils.EMPTY;
    private String home = "/home";
    private String canonicalUrl = "No Canonical URL";

    /**
     * The init method to fetch the product information from Commerce
     */
    @PostConstruct
    protected void init() {
        LOGGER.info("Start of PDPBreadcrumbModel init method");
        pagePath = resource.getPath();
        if (!resource.getPath().contains("/conf/")) {
            PageManager pageManager;
            Page page;
            Page parentProductPage;
            pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
            if (null != pageManager) {
                page = productService.getCurrentPagePath(pageManager, resource, request);
                if (null != page) {
                    parentProductPage = page.getAbsoluteParent(5);
                    productSKUId = EcommHelper.fetchProductSKUId(request);
                    LOGGER.debug("productSKUId : {}",productSKUId);
                    if (StringUtils.isNotBlank(productSKUId)) {
                        getProductPropertiesBasedOnSKUId(parentProductPage);
                    }
                }
            }
        }
        LOGGER.info("End of PDPBreadcrumbModel init method");
    }

    private void getProductPropertiesBasedOnSKUId(Page parentProductPage) {
        LOGGER.info("Start of getProductPropertiesBasedOnSKUId method");
        ValueMap productProperties = productService.fetchProductProperties(parentProductPage,
                resource, productSKUId);
        if (null != productProperties) {
            canonicalUrl = EcommHelper.getValueMapNodeValue(productProperties, "canonicalCategory");
            productTitle = EcommHelper.getValueMapNodeValue(productProperties, "jcr:title");
            if (null != canonicalUrl) {
            	LOGGER.debug("canonicalUrl : {}",canonicalUrl);
                getBreadcrumbLinks(canonicalUrl);
            }
        }
        LOGGER.info("End of getProductPropertiesBasedOnSKUId method");
    }

    private void getBreadcrumbLinks(String canonicalUrl) {
        LOGGER.info("Start of getBreadcrumbLinks method");
        getHomePage();
        String[] links = canonicalUrl.split(">");
        String cat = "brand".equalsIgnoreCase(links[0].trim().toLowerCase()) ? "brands" : links[0].trim().toLowerCase();
        StringBuilder rootPathBuilder = new StringBuilder();
        String rootPath = pagePath.split(home)[0] + "/home/shop/" + cat;
        int counter = 0;
        for (String pageName : links) {
            PDPBreadcrumbPojo pdpBreadcrumbPojo = new PDPBreadcrumbPojo();
            if (counter > 0) {
                LOGGER.debug("Root Path is {}", rootPath);
                pdpBreadcrumbPojo.setTitle(EcommHelper.toCamelCase(getPageTitle(rootPath + "/" + getNodeName(pageName))));
                pdpBreadcrumbPojo.setPageLink(EcommHelper.checkLink(rootPath + "/" + getNodeName(pageName), resource));
                if (!StringUtils.isEmpty(pdpBreadcrumbPojo.getTitle())) {
                    LOGGER.debug("Name of the page is {} ", pdpBreadcrumbPojo.getTitle());
                    list.add(pdpBreadcrumbPojo);
                }
                rootPath = rootPathBuilder.append(rootPath).append(Constant.SLASH_CHAR).append(getNodeName(pageName)).toString();
            }
            counter++;
        }
        if (!list.isEmpty()) {
            mobilePdpBreadcrumbPojo.setTitle(EcommHelper.toCamelCase(list.get(list.size() - 1).getTitle()));
            mobilePdpBreadcrumbPojo.setPageLink(EcommHelper.checkLink(list.get(list.size() - 1).getPageLink(), resource));
        }
        getProductPage();
        LOGGER.info("End of getBreadcrumbLinks method");
    }

    private String getNodeName(String pageName) {
        return pageName.toLowerCase().trim().replaceAll("[^a-zA-Z0-9]+", "-");
    }

    private void getProductPage() {
        LOGGER.info("Start of getProductPage method");
        PDPBreadcrumbPojo pdpBreadcrumbPojo = new PDPBreadcrumbPojo();
        pdpBreadcrumbPojo.setTitle(EcommHelper.toCamelCase(EcommHelper.convertSpecialCharacters(productTitle)));
        pdpBreadcrumbPojo.setPageLink("javascript:void(0);");
        list.add(pdpBreadcrumbPojo);
        LOGGER.debug("pdpBreadcrumbPojo : {}",pdpBreadcrumbPojo);
        LOGGER.info("End of getProductPage method");
    }

    private void getHomePage() {
        LOGGER.info("Start of getHomePage method");
        String[] pageUrl = pagePath.split(home);
        PDPBreadcrumbPojo pdpBreadcrumbPojo = new PDPBreadcrumbPojo();
        pdpBreadcrumbPojo.setTitle(EcommHelper.toCamelCase(getPageTitle(pageUrl[0] + home)));
        pdpBreadcrumbPojo.setPageLink(EcommHelper.checkLink(pageUrl[0] + "/home", resource));
        list.add(pdpBreadcrumbPojo);
        LOGGER.debug("pdpBreadcrumbPojo : {}",pdpBreadcrumbPojo);
        LOGGER.info("End of getHomePage method");
    }

    private String getPageTitle(String pagePath) {
        LOGGER.info("Start of getPageTitle method");
        PageManager pageManager;
        String homePageTitle = StringUtils.EMPTY;
        pageManager = resource.getResourceResolver().adaptTo(PageManager.class);
        if (null != pageManager) {
            LOGGER.debug("Page path is {} ", pagePath);
            Page page = pageManager.getPage(pagePath);
            if (null != page) {
                homePageTitle = EcommHelper.convertSpecialCharacters(page.getTitle());
            }
        }
        LOGGER.debug("Page title is {}", homePageTitle);
        LOGGER.info("End of getPageTitle method");
        return homePageTitle;
    }

    public String getProductSKUId() {
        return productSKUId;
    }

    public void setProductSKUId(String productSKUId) {
        this.productSKUId = productSKUId;
    }

    public List<PDPBreadcrumbPojo> getList() {
        return list;
    }

    public void setList(List<PDPBreadcrumbPojo> list) {
        this.list = list;
    }

    public String getCanonicalUrl() {
        return canonicalUrl;
    }

    public void setCanonicalUrl(String canonicalUrl) {
        this.canonicalUrl = canonicalUrl;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public PDPBreadcrumbPojo getMobilePdpBreadcrumbPojo() {
        return mobilePdpBreadcrumbPojo;
    }

    public void setMobilePdpBreadcrumbPojo(PDPBreadcrumbPojo mobilePdpBreadcrumbPojo) {
        this.mobilePdpBreadcrumbPojo = mobilePdpBreadcrumbPojo;
    }
}