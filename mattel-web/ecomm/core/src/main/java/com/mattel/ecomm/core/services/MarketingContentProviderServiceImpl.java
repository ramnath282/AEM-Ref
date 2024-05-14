package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.RangeIterator;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.ecomm.core.interfaces.MarketingContentProviderService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = MarketingContentProviderService.class)
public class MarketingContentProviderServiceImpl implements MarketingContentProviderService {
  @Reference
  GetResourceResolver getResourceResolver;
  @Reference
  PropertyReaderService propertyReaderService;

  private static final String MARKETING_CONTENT_POSITIONTAG="positionTags";
  private static final String DEFAULT_POSITIONTYPE="default";
  private static final Logger LOGGER = LoggerFactory.getLogger(MarketingContentProviderServiceImpl.class);

  @Override
  public List<String> getContentFromTags(String siteKey, String skuId, String positionTag) {
    ResourceResolver resolver = getResourceResolver.getResourceResolver();
    List<String> contentPaths = new ArrayList<>();

    try{
    String targetPath = propertyReaderService.getExperienceFragmentRootPath(siteKey);
    String tagRootPath = propertyReaderService.getTagRootPaths(siteKey);
    LOGGER.debug("Tag Root Path is {}", tagRootPath);
    LOGGER.debug("Target Root path {}", targetPath);
    LOGGER.debug("Sku Id is {}", skuId);
        if (StringUtils.isNotBlank(targetPath) && StringUtils.isNotBlank(skuId) && resolver != null) {
            final TagManager tagManager = resolver.adaptTo(TagManager.class);
            if (Objects.nonNull(tagManager)) {
              Tag tag = tagManager.resolve(tagRootPath + Constant.SLASH + skuId);
              if (Objects.nonNull(tag)) {
                final String tagArr = tag.getTagID();
                LOGGER.debug("Tag Id is {}", tagArr);
                handleTaggedResources(positionTag, contentPaths, targetPath, tagRootPath,
                    tagManager, tagArr);
              }
            }
          }
    } finally{
        if (null != resolver && resolver.isLive()) {
            resolver.close();
        }
    }
    return contentPaths;
  }

  /**
   * @param positionTag
   * @param contentPaths
   * @param targetPath
   * @param tagRootPath
   * @param tagManager
   * @param tagArr
   */
  private void handleTaggedResources(String positionTag, List<String> contentPaths,
      String targetPath, String tagRootPath, final TagManager tagManager, final String tagArr) {
    LOGGER.info("Start of handleTaggedResources method");
    RangeIterator<Resource> itr = tagManager.find(tagArr);
    while (itr.hasNext()) {
      Resource taggedResource = itr.next();
      if (taggedResource.getPath().contains(targetPath)) {
        String[] resourceTags = taggedResource.getValueMap().get("cq:tags", String[].class);
        Tag positionTagObj = tagManager.resolve(tagRootPath + Constant.SLASH
            + MARKETING_CONTENT_POSITIONTAG + Constant.SLASH + positionTag);
        LOGGER.debug("positionTagObj Id is {}", positionTagObj);
        handlePostionTag(positionTag, contentPaths, taggedResource, resourceTags, positionTagObj);
      }
    }
    LOGGER.info("End of handleTaggedResources method");
  }

  /**This method checks if the SKU tagged experience fragment has position tag authored 
   * and display logic based on the position tag.
   * @param positionTag
   * @param contentPaths
   * @param taggedResource
   * @param resourceTags
   * @param positionTagObj
   */
  private void handlePostionTag(String positionTag, List<String> contentPaths,
      Resource taggedResource, String[] resourceTags, Tag positionTagObj) {
    LOGGER.info("Start of handlePostionTag method");
    if (Objects.nonNull(positionTag) && Objects.nonNull(positionTagObj)) {
      if (positionTag.equals(DEFAULT_POSITIONTYPE)) {
        handleDefaultPosition(contentPaths, taggedResource, resourceTags, positionTagObj);
      } else if(Arrays.asList(resourceTags).contains(positionTagObj.getTagID())){
        contentPaths.add(taggedResource.getPath() + Constant.ROOT);
      }
    } else {
      contentPaths.add(taggedResource.getPath() + Constant.ROOT);
    }
    LOGGER.info("End of handlePostionTag method");
  }

  /**
   * This method handles the scenario when position 1 is authored or no position 
   * is authored in the component. It also handles the condition when no position 
   * tag is authored in experience fragment.
   * @param contentPaths
   * @param taggedResource
   * @param resourceTags
   * @param positionTagObj
   */
  private void handleDefaultPosition(List<String> contentPaths, Resource taggedResource,
      String[] resourceTags, Tag positionTagObj) {
    LOGGER.info("Start of handleDefaultPosition method");
    Boolean selectDefault = true;
    for (String tagString : resourceTags) {
      if (tagString.equals(positionTagObj.getTagID())) {
        selectDefault = true;
      } else if (tagString.contains("position")) {
        selectDefault = false;
      }
    }
    if (selectDefault) {
      contentPaths.add(taggedResource.getPath() + Constant.ROOT);
    }
    LOGGER.info("End of handleDefaultPosition method");
  }
}
