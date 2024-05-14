package com.mattel.global.core.model;

import com.mattel.global.core.pojo.BrandsBannerPojo;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.core.utils.PathUtils;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CTS. A Model class for Store Map Component.
 */

@Model(adaptables = Resource.class)

public class BrandsBannerModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(BrandsBannerModel.class);

  @Inject
  @Optional
  private Node mattelbrands;

  public void setMattelbrands(Node mattelbrands) {
    this.mattelbrands = mattelbrands;
  }

  @Inject
  @Optional
  private MultifieldReader multifieldReader;

  private List<BrandsBannerPojo> brandBanners;

  public void setBrandBanners(List<BrandsBannerPojo> brandBanners) {
    this.brandBanners = brandBanners;
  }

  /**
   * init method.
   */
  @PostConstruct
  protected void init() {
    LOGGER.debug("Init Method - Start");
    if (mattelbrands != null) {
      brandBanners = listBrandBanners();
    }
    LOGGER.debug("Init Method - End");
  }

  /**
   * Method for setting all the values in dialog field into Pojo.
   *
   * @return ArrayList.
   */
  private List<BrandsBannerPojo> listBrandBanners() {
    Map<String, ValueMap> brandsMap = multifieldReader.propertyReader(mattelbrands);
    List<BrandsBannerPojo> allBrands = new ArrayList<>();
    for (Map.Entry<String, ValueMap> entry : brandsMap.entrySet()) {
      BrandsBannerPojo brandsBannerPojo = new BrandsBannerPojo();
      brandsBannerPojo.setAlttext(entry.getValue().get("alttext", String.class));
      brandsBannerPojo.setTitle(entry.getValue().get("title", String.class));
      brandsBannerPojo.setBrandlogolink(entry.getValue().get("brandlogolink", String.class));
      brandsBannerPojo.setLogoImage(entry.getValue().get("logoImage", String.class));
      brandsBannerPojo.setRenderoption(entry.getValue().get("renderoption", String.class));
      if (null != brandsBannerPojo.getBrandlogolink()) {
        brandsBannerPojo.setExternal(PathUtils.isExternal(brandsBannerPojo.getBrandlogolink()));
      }
      LOGGER.debug("Pojo of Brand banner {}", brandsBannerPojo);
      allBrands.add(brandsBannerPojo);
    }
    LOGGER.info("List of Brand banner {}", allBrands);

    return allBrands;
  }

  public List<BrandsBannerPojo> getBrandBanners() {
    return brandBanners;
  }

  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }
}