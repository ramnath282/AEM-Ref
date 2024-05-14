package com.mattel.global.core.model;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import com.day.cq.wcm.api.Page;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;

import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.services.ListingDetailService;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.master.core.constants.Constants;

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SocialLinkModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(SocialLinkModel.class);

  @Inject
  @Via("resource")
  @Default(values = "corp")
  private String siteName;

  @Inject
  @Via("resource")
  private String socialLinkLabel;

  @SlingObject
  SlingHttpServletRequest request;

  @Inject
  private ListingDetailService listingDetailService;

  @Inject
  @Via("resource")
  private Node categoryConfiguration;

  @Inject
  private MultifieldReader multifieldReader;

  private Set<String> tagList;

  private boolean isSocailLinkAuthored;

  @Inject
  Resource resource;

  @Inject
  Page currentPage;

  /**
   * init method to process social link configuration details
   */
  @PostConstruct protected void init() {
    LOGGER.info("SocialLinkModel Init Start");
    LOGGER.debug("currentPage path is {} ", currentPage.getPath());
    String[] selectors = request.getRequestPathInfo().getSelectors();
    String requestPath = request.getRequestPathInfo().getResourcePath();
    LOGGER.debug("Request path is {}", requestPath);
    String currentPagePath = currentPage.getPath();
    LOGGER.debug("Current page path is {}", currentPage.getPath());
    Object[] tags = null;
    if (ArrayUtils.isNotEmpty(selectors) && currentPagePath.contains("/news-detail")) {
      LOGGER.debug("selectors are {} and siteName is {}", selectors[0], siteName);
      tags = listingDetailService.getArticleTagList(selectors, siteName);
    } else {
      tags = currentPage.getProperties().get("cq:tags", String[].class);
    }
    setTags(tags);
    LOGGER.info("SocialLinkModel Init End");
  }

  private void setTags(Object[] tags) {
    LOGGER.info("SocialLinkModel setTags Start");
    if (ArrayUtils.isNotEmpty(tags)) {
      String[] tagArray = Arrays.asList(tags).toArray(new String[0]);
      if (ArrayUtils.isNotEmpty(tagArray)) {
        tagList = Arrays.stream(tagArray).collect(Collectors.toSet());
        LOGGER.debug("tagList:--> {}", tagList);
        if (Objects.nonNull(categoryConfiguration)) {
          isSocailLinkAuthored = checkIfSocailLinkIsAuthored(tagList, categoryConfiguration);
        }
      }
    }
    LOGGER.info("SocialLinkModel setTags End");
  }

  /**
   * This method checks if social links are authored for the respective tag
   * @param tagList
   * @param categoryConfiguration
   * @return
   */
  private boolean checkIfSocailLinkIsAuthored(Set<String> tagList, Node categoryConfiguration) {
    LOGGER.info("SocialLinkModel checkIfSocailLinkIsAuthored Start");
    Map<String, ValueMap> multifieldProperty;
    multifieldProperty = multifieldReader.propertyReader(categoryConfiguration);
    if (Objects.nonNull(multifieldProperty)) {
      for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
        String tagstr = entry.getValue().get(Constants.CATEGORY_TYPE, StringUtils.EMPTY);
        String nodepath = entry.getValue().get(Constants.NODEPATH, StringUtils.EMPTY);
        if (StringUtils.isNotBlank(tagstr) && StringUtils.isNotBlank(nodepath)) {
          try {
            String relativePath = nodepath
                .replace(categoryConfiguration.getPath() + Constants.SLASH, StringUtils.EMPTY);
            LOGGER.debug("relativePath after replacing the configuration node path is {}", relativePath);
            if (checkIfTagisConfigured(tagList, tagstr, categoryConfiguration, relativePath)) {
              isSocailLinkAuthored = true;
              LOGGER.debug("Social links are configured for the tag {}", tagstr);
              return isSocailLinkAuthored;
            }
          } catch (RepositoryException e) {
            LOGGER.error("RepositoryException occured in SocialLinkModel {}", e);
          }
        }
      }
    }
    LOGGER.info("SocialLinkModel checkIfSocailLinkIsAuthored End");
    return isSocailLinkAuthored;
  }

  /**
   * This method checks if configuration is present the tag and retruns the flag
   * @param tagList
   * @param tagstr
   * @param categoryConfiguration
   * @param relativePath
   * @return flag for tag configuration
   * @throws RepositoryException
   */
  private boolean checkIfTagisConfigured(Set<String> tagList, String tagstr, Node categoryConfiguration,
      String relativePath) throws RepositoryException {
    return tagList.stream().anyMatch(tagstr::equals) && categoryConfiguration
        .hasNode(relativePath + "/socialConfiguration/item0");
  }

  public String getSocialLinkLabel() {
    return socialLinkLabel;
  }

  public Set<String> getTagList() {
    return tagList;
  }

  public boolean isSocailLinkAuthored() {
    return isSocailLinkAuthored;
  }

  public void setSocailLinkAuthored(boolean isSocailLinkAuthored) {
    this.isSocailLinkAuthored = isSocailLinkAuthored;
  }

}
