package com.mattel.global.core.model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.utils.GlobalPropertyReaderUtils;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ListingModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListingModel.class);

  @OSGiService
  private GlobalPropertyReaderUtils globalPropertyReaderUtils;

  @Inject
  private String seeMoreLabel;

  @Inject
  private String defaultImage;

  @Inject
  private String searchType;

  @Inject
  private Integer initialLoadCount;

  @Inject
  private String viewAllLabel;

  @Inject
  private Integer productLimit;
  
  @Inject
  private String filterSectionLabel;
  
  @Inject
  private String defaultFilterlabel;
  
  @Inject
  private String enableFilters;
  
  
  @Inject
  @Default(values = "corp")
  private String siteName;

  private String snpUrl;

  /**
   * The init method to process listing module details
   */
  @PostConstruct
  protected void init() {
    LOGGER.info("ListingModel init method -> Start");
    if (globalPropertyReaderUtils != null) {
      snpUrl = globalPropertyReaderUtils.getSnpUrl(siteName);
      LOGGER.debug("SnpUrl -> {}", snpUrl);
    }
    LOGGER.info("ListingModel init method -> End");
  }

  public String getSeeMoreLabel() {
    return seeMoreLabel;
  }

  public String getSiteName() {
    return siteName;
  }

  public Integer getInitialLoadCount() {
    return initialLoadCount;
  }

  public String getSnpUrl() {
    return snpUrl;
  }

  public String getSearchType() {
    return searchType;
  }

  public String getViewAllLabel() {
    return viewAllLabel;
  }

  public Integer getProductLimit() {
    return productLimit;
  }

  public String getDefaultImage() {
    return defaultImage;
  }
  
  public String getFilterSectionLabel() {
    return filterSectionLabel;
  }

  public String getDefaultFilterlabel() {
    return defaultFilterlabel;
  }

  public String getEnableFilters() {
    return enableFilters;
  }

}
