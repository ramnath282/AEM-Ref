package com.mattel.ecomm.core.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;

/**
 * @author CTS. Service for properties configuration of play site.
 */
public class EcomUtil extends WCMUsePojo{
  private static final Logger LOGGER = LoggerFactory.getLogger(EcomUtil.class);
  private String pageUrl;
  private  String dynamicTemplate;
  

  @Override
  public void activate() {
    String url = get("url", String.class);
    Resource res = getResource();
    pageUrl = checkLink(url,res);
    String template=get("template",String.class);
    dynamicTemplate=getSpanAddedString(template);
    LOGGER.debug("pageUrl is {}", pageUrl);
    LOGGER.debug("template is {}", template);
    LOGGER.debug("dynamicTemplate is active {}", dynamicTemplate);
  }
  
  /**
   * Method will return the formatted URL based on the conditions
   * 
   * @param url
   * @return
   */
  public static String checkLink(String url,Resource resource) {
    if(null!=resource){
      ResourceResolver resolver  = resource.getResourceResolver();
      if (StringUtils.isNotBlank(url)) {
        if (url.startsWith(Constant.CONTENT) && !url.startsWith("/content/dam")) {
          url = checkResolverMapping(url, resolver);
          return url;
        } else {
          return url;
        }
      }
    }
    return url;
  }
  
  /**
   * To Build canonical tag for seo optimization.
   */
  public static String buildCanonicalTag(final Map<String, String> seoPropertiesMap,
    SlingHttpServletRequest request) {
  String canonicalTag;
  if (StringUtils.isNotBlank(seoPropertiesMap.get(Constant.SEO_CANONICAL_TAGS))) {
    canonicalTag = seoPropertiesMap.get(Constant.SEO_CANONICAL_TAGS);
  } else if (request.getRequestPathInfo().getSelectors().length >= 3
    && request.getRequestURL().lastIndexOf("/") != -1) {
    final String[] selectors = request.getRequestPathInfo().getSelectors();
    final String pdpLink;

    if (selectors.length == 4) {
    pdpLink = selectors[3];
    } else {
    pdpLink = selectors[2];
    }
    canonicalTag = request.getRequestURL().substring(0,
      request.getRequestURL().lastIndexOf("/") + 1) + pdpLink;
  } else {
    canonicalTag = "";
  }

  LOGGER.debug("Canonical tag/url :{}", canonicalTag);
  return canonicalTag;
  }
  
  /**
   * Method will return the formatted URL based on the conditions
   * 
   * @param url
   * @return
   */
  public static String getPlpPageLink(String url,Resource resource) {
    if(null!=resource){
      ResourceResolver resolver  = resource.getResourceResolver();
      if (StringUtils.isNotBlank(url)) {
        if (url.startsWith(Constant.CONTENT) && !url.startsWith("/content/dam")) {
          url = checkPlpResolverMapping(url, resolver);
          return url;
        } else {
          return url;
        }
      }
    }
    return url;
  }

  /**
   * @param url
   * @param resolver
   * @return
   */
  private static String checkResolverMapping(String url, ResourceResolver resolver) {
    if(null!=resolver){
      url = resolver.map(url);
    }
    if(url.startsWith(Constant.CONTENT) && null!=resolver){
      PageManager pgMgr = resolver.adaptTo(PageManager.class);
      if(null!=pgMgr){
        Page page = pgMgr.getPage(url);
        if(page != null) {
          if (StringUtils.isNotBlank(page.getVanityUrl())) {
            url = page.getVanityUrl();
          } else if (null != page.getProperties() && page.getProperties().containsKey("cq:redirectTarget")) {
            url = page.getProperties().get("cq:redirectTarget", String.class);
          } else {
            url = url + ".html";
          }
        }
      }
    }
    return url;
  }
  
  /**
   * @param url
   * @param resolver
   * @return
   */
  private static String checkPlpResolverMapping(String url, ResourceResolver resolver) {
    url = resolver.map(url);
    if (url.startsWith(Constant.CONTENT)) {
      PageManager pgMgr = resolver.adaptTo(PageManager.class);
      if (Objects.nonNull(pgMgr) && Objects.nonNull(pgMgr.getPage(url))) {
        Page page = pgMgr.getPage(url);
        if (StringUtils.isNotBlank(page.getVanityUrl())) {
          if (StringUtils.startsWith(page.getVanityUrl(), "/shop/c")) {
            url = page.getVanityUrl();
          } else {
            url = "/shop/c" + page.getVanityUrl();
          }
        } else {
          url = url + ".html";
        }
      }
    }
    return url;
  }
  
  /**
   * @param pagePath
   * @param resource
   * @return
   */
  public static String getPageLink(String pagePath, Resource resource) {
    String pageurl = null;
    if(!StringUtils.isBlank(pagePath)){
      if (pagePath.contains("categories")) {
           pageurl = EcomUtil.getPlpPageLink(pagePath, resource);
      } else {
           pageurl = EcomUtil.checkLink(pagePath, resource);
      }
    }
    return pageurl;
  }
  
  public String getSpanAddedString(String template)
  {
    LOGGER.debug("dynamicTemplate is constant {}", Constant.GIVER_NAME);
    if(StringUtils.contains(template, Constant.GIVER_NAME))
      template = StringUtils.replace(template, Constant.GIVER_NAME, Constant.GIVER_SPANVALUE);
    if(StringUtils.contains(template, Constant.RECIPIENT_NAME))
      template = StringUtils.replace(template, Constant.RECIPIENT_NAME, Constant.RECIPIENT_SPANVALUE);
    if(StringUtils.contains(template, Constant.OCCASSION))
      template = StringUtils.replace(template, Constant.OCCASSION, Constant.OCCASSION_SPANVALUE);
    if(StringUtils.contains(template, Constant.ASPIRATION))
      template = StringUtils.replace(template, Constant.ASPIRATION, Constant.ASPIRATION_SPANVALUE);
    if(StringUtils.contains(template, Constant.ATTRIBUTES))
      template = StringUtils.replace(template, Constant.ATTRIBUTES, Constant.ATTRIBUTES_SPANVALUE);
    if(StringUtils.contains(template, Constant.DOLLNAME))
        template = StringUtils.replace(template, Constant.DOLLNAME, Constant.DOLLNAME_SPANVALUE);
    return template;
  }
  public String getDynamicTemplate() {  
    LOGGER.debug("dynamicTemplate is method {}", dynamicTemplate);
    return dynamicTemplate;
    }
  public String getPageUrl() {
    return pageUrl;
  }
  	/**
	 * Method to generate dynamic Id.
	 * @return componentNameTrimed
	 */
	public String getId() {
		String id = String.valueOf(Math.abs(getResource().getPath().hashCode() - 1));	
		Resource compResource= get("resource", Resource.class);
		String componentName=compResource.getName();
		return componentName.substring(0,8)+id;
	}

  /**
   * This method provides AT Property authored at site level configuration
   *
   * @param resource
   * @return property
   */
    public static String getInheritedProperty(Resource resource, String property) {
      LOGGER.info("Start of getInheritedAtProperty method of FisherPriceUtil");
      final InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(resource);
      LOGGER.debug("Inherited property value is  {}",
              inheritanceValueMap.getInherited(property, String.class));
      LOGGER.info("End of getInheritedAtProperty method of FisherPriceUtil");
      return inheritanceValueMap.getInherited(property, String.class);
    }

  /**
   * This method list of excluded property or page paths
   *
   * @param excludeMap
   * @param propertyName
   * @param excludeList
   * @param resource
   * @return excludeList
   */
  public static ArrayList<String> setExcludeList(Map<String, ValueMap> excludeMap, String propertyName,
      ArrayList<String> excludeList, Resource resource) {
    for (Map.Entry<String, ValueMap> entry : excludeMap.entrySet()) {
      String excludedString = entry.getValue().get(propertyName, String.class);
      if(propertyName.equalsIgnoreCase("pagePath")){
        String url = EcomUtil.getPageLink(excludedString, resource);
        if(!StringUtils.isBlank(url)){
          excludeList.add(url);
        }
      }else{
        excludeList.add(excludedString);
      }
    }
    return excludeList;
  }
}
