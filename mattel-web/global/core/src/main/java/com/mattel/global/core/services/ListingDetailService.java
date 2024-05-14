package com.mattel.global.core.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.FragmentData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mattel.global.core.pojo.AttachmentDetailsPojo;
import com.mattel.global.core.pojo.ItemListPojo;
import com.mattel.global.core.utils.PropertyUtils;

@Component(service = ListingDetailService.class, immediate = true)
@Designate(ocd = ListingDetailService.Config.class)
public class ListingDetailService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ListingDetailService.class);
  private String[] cfPath;
  final Gson gson = new Gson();
  final Type attachmentType = new TypeToken<AttachmentDetailsPojo>() {
  }.getType();
  private static final String TITLE = "title";
  private static final String MEDIA_CONTENT = "mediaContent";
  private static final String CONTENT = "content";

  private static final String ATTACHMENT_ZIP = "attachmentZip";
  private static final String PUB_DATE = "pubDate";

  private static final String ATTACHMENT_DETAILS = "attachments";
  private static final String HTML_TAGS = "\\<.*?\\>";
  private static final String HTML_QUOT = "&quot;";
  private static final String HTML_SPACE = "&nbsp;";
  private static final String QUOT = "\"";
  private static final String READ_WRITE = "readwriteservice";

  @Reference
  private ResourceResolverFactory resolverFactory;

  @ObjectClassDefinition(name = "Listing Details properties")
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
    LOGGER.info("ListingDetailService getFragmentDetails:--> START");
    if (StringUtils.isNotBlank(getCfPath(siteName)) && StringUtils.isNotBlank(selectors[0])) {
      String contentFragmentPath = getCfPath(siteName) + selectors[0];
      LOGGER.debug("Full Content fragment path:--> {}", contentFragmentPath);
      ItemListPojo itemListPojo = generateContentFragmentDetails(contentFragmentPath);
      LOGGER.info("ListingDetailService getFragmentDetails:--> END");
      return itemListPojo;
    }
    return null;
  }

  /**
   * getFragmentDetails method to generate fragment path based on article
   * selectors
   * 
   * @param selectors
   * @param siteName
   * @return Object[]
   */
  public Object[] getArticleTagList(String[] selectors, String siteName) {
    ResourceResolver resourceResolver = null;
    Object[] categoryAray = {};
    try {
      LOGGER.info("ListingDetailService getArticleTagList:--> START");
      if (StringUtils.isNotBlank(getCfPath(siteName)) && StringUtils.isNotBlank(selectors[0])) {
        String contentFragmentPath = getCfPath(siteName) + selectors[0];
        LOGGER.debug("Full Content fragment path:--> {}", cfPath);
        Map<String, Object> map = new HashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, READ_WRITE);
        resourceResolver = resolverFactory.getServiceResourceResolver(map);
        LOGGER.debug("resourceResolver:--> {}", resourceResolver);
        categoryAray = getCategoryTagDetails(resourceResolver.getResource(contentFragmentPath));

      }
    } catch (Exception e) {
      LOGGER.error("ListingDetailService ERROR while fetching category tag details {}", e);
    } finally {
      if (Objects.nonNull(resourceResolver)) {
        resourceResolver.close();
      }
    }
    LOGGER.info("ListingDetailService getArticleTagList:--> End");
    return categoryAray;

  }

  /**
   * generateContentFragmentDetails method to read content fragment by path
   * 
   * @param cfPath
   * @return ItemListPojo
   */
  private ItemListPojo generateContentFragmentDetails(String cfPath) {
    LOGGER.info("ListingDetailService generateContentFragmentDetails:--> START");
    ResourceResolver resourceResolver = null;
    ItemListPojo itemListPojo = null;
    try {
      Map<String, Object> map = new HashMap<>();
      map.put(ResourceResolverFactory.SUBSERVICE, READ_WRITE);
      if (Objects.nonNull(resolverFactory)) {
        resourceResolver = resolverFactory.getServiceResourceResolver(map);
        LOGGER.debug("resourceResolver:--> {}", resourceResolver);
        Resource fragmentResource = resourceResolver.getResource(cfPath);
        LOGGER.debug("fragmentResource Resource:--> {}", fragmentResource);
        itemListPojo = processFragment(fragmentResource);
        LOGGER.debug("generateContentFragmentDetails itemListPojo:--> {}", itemListPojo);
      }
    } catch (Exception e) {
      LOGGER.error("ListingDetailService ERROR while fetching content fragment details {}", e);
    } finally {
      if (Objects.nonNull(resourceResolver)) {
        resourceResolver.close();
      }
    }
    LOGGER.info("ListingDetailService generateContentFragmentDetails:--> End");
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
        itemListPojo.setTitle(processElementDetail(fragment, TITLE));
        itemListPojo.setMediaContent(processElementDetail(fragment, MEDIA_CONTENT));
        itemListPojo.setAttachmentZip(processElementDetail(fragment, ATTACHMENT_ZIP));
        itemListPojo.setContent(processElementDetail(fragment, CONTENT));
        itemListPojo.setPubDate(processElementDetail(fragment, PUB_DATE));
        String[] attachmentList = getArrayElementDetail(fragment, ATTACHMENT_DETAILS);
        List<AttachmentDetailsPojo> attachmentDetailList = new ArrayList();
        LOGGER.debug(" attachmentList:--> {}", attachmentList);
        if (Objects.nonNull(attachmentList) && ArrayUtils.isNotEmpty(attachmentList)) {
          Arrays.stream(attachmentList).forEach(element -> {
            LOGGER.debug(" attachment element json:--> {}", element);
            if(Objects.nonNull(element)){
              attachmentDetailList.add(gson.fromJson(removeHtmlTags(element), attachmentType));
            }
          });
          itemListPojo.setAttachmentDetails(attachmentDetailList);
        }

      }
    }
    return itemListPojo;
  }

  /**
   *
   * @param fragmentResource
   * @return
   */
  private Object[] getCategoryTagDetails(Resource fragmentResource) {
    Object[] categoryArray = {};
    if (Objects.nonNull(fragmentResource)) {
      ContentFragment fragment = fragmentResource.adaptTo(ContentFragment.class);
      if (Objects.nonNull(fragment)) {
        Map<String, Object> metaMap = fragment.getMetaData();
        if (Objects.nonNull(metaMap)) {
          LOGGER.debug(" cq:tags:--> {}", metaMap);
          categoryArray = (Object[]) metaMap.get("cq:tags");
        }
      }
    }

    return categoryArray;
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

  /**
   * getArrayElementDetail method to parse json string array to object
   * 
   * @param fragment
   * @param elementName
   * @return elementsList
   */
  private String[] getArrayElementDetail(ContentFragment fragment, String elementName) {
    LOGGER.info("getArrayElementDetail:--> START");
    String[] elementsList = null;
    try {
      if (fragment.hasElement(elementName)) {
        LOGGER.debug("has element:--> {}", elementName);
        ContentElement contentElement = fragment.getElement(elementName);
        if (Objects.nonNull(contentElement)) {
          FragmentData fragmentData = contentElement.getValue();
          if (Objects.nonNull(fragmentData)) {
            elementsList = fragmentData.getValue(String[].class);
          }
          LOGGER.debug("elementsList:--> {}", elementsList);
        }
      }
    } catch (Exception e) {
      LOGGER.error("getArrayElementDetail  ERROR while parsing json  content fragment data:--> {}",
          e);
    }
    LOGGER.info("getArrayElementDetail:--> END");
    return elementsList;
  }

  /**
   * removeHtmlTags method to remove all html tags from string
   * 
   * @param htmlString
   * @return htmlString
   */
  private String removeHtmlTags(String htmlString) {
    if (StringUtils.isNotBlank(htmlString)) {
      htmlString = htmlString.replaceAll(HTML_TAGS, StringUtils.EMPTY);
      htmlString = htmlString.replaceAll(HTML_SPACE, StringUtils.EMPTY);
      htmlString = htmlString.replaceAll(HTML_QUOT, QUOT);
    }
    return htmlString;
  }

  public String getCfPath(String siteName) {
    return PropertyUtils.getValueFromKeyMappings(cfPath, siteName);
  }

  public void setCfPath(String[] cfPath) {
    this.cfPath = cfPath;
  }

}
