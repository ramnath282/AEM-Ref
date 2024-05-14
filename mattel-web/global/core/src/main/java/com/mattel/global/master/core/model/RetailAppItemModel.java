package com.mattel.global.master.core.model;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.global.core.services.RetailerDetailService;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;

@Model(adaptables = {
    Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class RetailAppItemModel extends CTABaseModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(RetailAppItemModel.class);

  @Inject
  @Default(values = StringUtils.EMPTY)
  private String selectAppOrRetailer;

  @Inject
  @Default(values = StringUtils.EMPTY)
  private String appOrRetailPath;

  @Inject
  private RetailerDetailService retailerDetailService;

  private String interstitialType;

  private String interstitialSelectionFragmentPath;

  private Boolean isFirstCTA = false;

  private static final String REGEX = "^(http://.*|https://.*)$";

  private String desktopLogo;

  private String mobileLogo;

  private String retailerAltName;

  private String retailerName;

  @PostConstruct
  protected void init() {
    LOGGER.info("RETAIL ITEM MODEL :: Init Method Start");
    if (StringUtils.isNotBlank(selectAppOrRetailer) && StringUtils.isNotBlank(appOrRetailPath)) {
      LOGGER.debug("Retailer Path and URL :: {} : {} ", selectAppOrRetailer, appOrRetailPath);
      Map<String, String> retailerDetail = null;
      retailerDetail = retailerDetailService.getRetailerDetails(selectAppOrRetailer);
      if (Objects.nonNull(retailerDetail) && !retailerDetail.isEmpty()) {
        Pattern pattern = Pattern.compile(REGEX);
        if (!pattern.matcher(appOrRetailPath).matches()) {
          appOrRetailPath = retailerDetail.get(Constants.RETAILER_STEM_URL).concat(appOrRetailPath);
          LOGGER.debug("appOrRetailPath :: {}", appOrRetailPath);
        }
        desktopLogo = retailerDetail.get(Constants.RETAILER_DESKTOP_LOGO);
        mobileLogo = retailerDetail.get(Constants.RETAILER_MOBILE_LOGO);
        retailerAltName = retailerDetail.get(Constants.RETAILER_ALT_NAME);
        retailerName = retailerDetail.get(Constants.RETAILER_NAME);
      }
    }
    LOGGER.debug("useInterstitial :: {}", useInterstitial);
    linkText = GlobalUtils.removeTags(linkText, Constants.REMOVE_TAGS,
        Constants.EMPTY_ARRAY_STRING);
    LOGGER.debug("Button Text :: {}", linkText);
    LOGGER.info("RETAIL ITEM MODEL :: Init Method End");
  }

  public String getInterstitialType() {
    return interstitialType;
  }

  public String getInterstitialSelectionFragmentPath() {
    return interstitialSelectionFragmentPath;
  }

  public Boolean getIsFirstCTA() {
    return isFirstCTA;
  }

  public String getSelectAppOrRetailer() {
    return selectAppOrRetailer;
  }

  public String getAppOrRetailPath() {
    return appOrRetailPath;
  }

  public String getDesktopLogo() {
    return desktopLogo;
  }

  public String getMobileLogo() {
    return mobileLogo;
  }

  public String getRetailerAltName() {
    return retailerAltName;
  }

  public String getRetailerName() {
    return retailerName;
  }
}
