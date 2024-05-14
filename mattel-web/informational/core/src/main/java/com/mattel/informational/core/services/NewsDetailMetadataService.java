package com.mattel.informational.core.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.google.gson.Gson;
import com.mattel.informational.core.constants.Constants;
import com.mattel.informational.core.pojos.ItemListPojo;
import com.mattel.informational.core.utils.InformationalUtils;

@Component(service = NewsDetailMetadataService.class, immediate = true)
@Designate(ocd = NewsDetailMetadataService.Config.class)
public class NewsDetailMetadataService {
  private static final Logger LOGGER = LoggerFactory.getLogger(NewsDetailMetadataService.class);
  private String[] cfPath;
  final Gson gson = new Gson();
  
  @Reference
  private ResourceResolverFactory resolverFactory;

  @ObjectClassDefinition(name = "Informational News Listing Details properties")
  public @interface Config {
    @AttributeDefinition(name = "Content fragment  path", description = "Please provide content fragment path to read feed data")
    String[] cfPath() default StringUtils.EMPTY;
  }

  @Activate
  public void activate(final Config config) {
    cfPath = config.cfPath();
    LOGGER.debug("cfPath {}", cfPath);
  }

  /**
   *
   * @param selectors
   * @param siteName
   * @return
   */
  public ItemListPojo getFragmentDetails(String[] selectors, String siteName) {
    LOGGER.info("Informational NewsDeatilMetadataService getFragmentDetails:--> START");
    if (StringUtils.isNotBlank(getCfPath(siteName)) && StringUtils.isNotBlank(selectors[0])) {
      String contentFragmentPath = getCfPath(siteName) + selectors[0];
      LOGGER.debug("Full Content fragment path:--> {}", contentFragmentPath);
      ItemListPojo itemListPojo = generateContentFragmentDetails(contentFragmentPath);
      LOGGER.info("Informational NewsDeatilMetadataService getFragmentDetails:--> END");
      return itemListPojo;
    }
    return null;
  }

  /**
   * generateContentFragmentDetails method to read content fragment by path
   * 
   * @param cfPath
   * @return ItemListPojo
   */
  private ItemListPojo generateContentFragmentDetails(String cfPath) {
    LOGGER.info("Informational NewsDeatilMetadataService generateContentFragmentDetails:--> START");
    ResourceResolver resourceResolver = null;
    ItemListPojo itemListPojo = null;
    try {
      Map<String, Object> map = new HashMap<>();
      map.put(ResourceResolverFactory.SUBSERVICE, Constants.READWRITESERVICE);
      if (Objects.nonNull(resolverFactory)) {
        resourceResolver = resolverFactory.getServiceResourceResolver(map);
        LOGGER.debug("resourceResolver:--> {}", resourceResolver);
        Resource fragmentResource = resourceResolver.getResource(cfPath);
        LOGGER.debug("fragmentResource Resource:--> {}", fragmentResource);
        itemListPojo = processFragment(fragmentResource);
        LOGGER.debug("generateContentFragmentDetails itemListPojo:--> {}", itemListPojo);
      }
    } catch (Exception e) {
      LOGGER.error("Informational NewsDeatilMetadataService ERROR while fetching content fragment details {}", e);
    } finally {
      if (Objects.nonNull(resourceResolver)) {
        resourceResolver.close();
      }
    }
    LOGGER.info("Informational NewsDeatilMetadataService generateContentFragmentDetails:--> End");
    return itemListPojo;
  }

  /**
   *
   * @param fragmentResource
   * @return
   */
  private ItemListPojo processFragment(Resource fragmentResource) {
    ItemListPojo itemListPojo = null;
    if (Objects.nonNull(fragmentResource)) {
      ContentFragment fragment = fragmentResource.adaptTo(ContentFragment.class);
      LOGGER.debug("processFragment fragment:--> {}", fragment);
      if (Objects.nonNull(fragment)) {
        itemListPojo = new ItemListPojo();
        itemListPojo.setTitle(processElementDetail(fragment, Constants.TITLE));
        itemListPojo.setDescription(processElementDetail(fragment, Constants.DESCRIPTION));
        itemListPojo.setImage(processElementDetail(fragment, Constants.MEDIA_CONTENT));
      }
    }
    return itemListPojo;
  }

  /**
   * processElementDetail method to parse element details from content fragment
   * 
   * @param fragment
   * @param elementName
   * @return elementValue
   */
  private String processElementDetail(ContentFragment fragment, String elementName) {
    String elementValue;
    LOGGER.debug("elementName:--> {}", elementName);
    elementValue = Objects.nonNull(fragment.getElement(elementName))
        ? fragment.getElement(elementName).getContent()
        : StringUtils.EMPTY;
    LOGGER.debug("elementValue:--> {}", elementValue);
    return elementValue;
  }

  public String getCfPath(String siteName) {
    return InformationalUtils.getValueFromKeyMappings(cfPath, siteName);
  }

  public void setCfPath(String[] cfPath) {
    this.cfPath = cfPath;
  }

}
