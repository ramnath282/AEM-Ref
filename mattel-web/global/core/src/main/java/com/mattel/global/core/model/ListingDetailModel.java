package com.mattel.global.core.model;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.pojo.ItemListPojo;
import com.mattel.global.core.services.ListingDetailService;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ListingDetailModel {

  @SlingObject
  SlingHttpServletRequest request;

  @Inject
  private ListingDetailService listingDetailService;

  @Inject
  @Via("resource")
  private String downloadMedia;

  @Inject
  @Via("resource")
  private String viewLabel;

  @Inject
  @Via("resource")
  private String downloadPdfLabel;

  @Inject
  @Via("resource")
  private String downloadAllFilesLabel;

  @Inject
  @Via("resource")
  @Default(values = "corp")
  private String siteName;

  private ItemListPojo itemListPojo;

  private static final Logger LOGGER = LoggerFactory.getLogger(ListingDetailModel.class);

  /**
   * init method to process content fragment
   */
  @PostConstruct
  protected void init() {
    LOGGER.info("ListingDetailModel Init -> Start");
    String[] selectors = request.getRequestPathInfo().getSelectors();
    if (Objects.nonNull(listingDetailService) && ArrayUtils.isNotEmpty(selectors)) {
      LOGGER.debug("selectors : {}, sitename : {} ", selectors[0], siteName);
      itemListPojo = listingDetailService.getFragmentDetails(selectors, siteName);
    }
    LOGGER.info("ListingDetailModel Init -> End");
  }

  public ItemListPojo getItemListPojo() {
    return itemListPojo;
  }

  public String getDownloadMedia() {
    return downloadMedia;
  }

  public String getViewLabel() {
    return viewLabel;
  }

  public String getDownloadPdfLabel() {
    return downloadPdfLabel;
  }

  public String getDownloadAllFilesLabel() {
    return downloadAllFilesLabel;
  }

  public String getSiteName() {
    return siteName;
  }

}
