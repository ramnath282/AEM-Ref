package com.mattel.global.core.model;

import com.mattel.global.core.pojo.PrimaryPreferencesMattelBrandsPojo;
import com.mattel.global.core.services.MultifieldReader;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author CTS PrimaryPreferencesMattelBrands Model.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL) public class PrimaryPreferencesMattelBrandsModel {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(PrimaryPreferencesMattelBrandsModel.class);
  @Inject private MultifieldReader multifieldReader;

  @Inject private Node brandsList;

  @Self Resource resource;
  private List<PrimaryPreferencesMattelBrandsPojo> mattelBrandsList = new ArrayList<>();

  @PostConstruct protected void init() {
    LOGGER.debug("init method of DownloadAppModel start");
    if (null != resource && !resource.getPath().contains("/conf/")) {
      mattelBrandsList = populateMattelBrandsList(mattelBrandsList);
    }
    LOGGER.debug("init method of DownloadAppModel end");
  }

  /**
   * Method to fetch details for each brand.
   *
   * @param mattelBrandsList:mattelBrandsList list of mattel brands
   * @return List<PrimaryPreferencesMattelBrandsPojo>:mattelBrandsList
   * list of mattel brands
   */
  private List<PrimaryPreferencesMattelBrandsPojo> populateMattelBrandsList(
      List<PrimaryPreferencesMattelBrandsPojo> mattelBrandsList) {
    Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(brandsList);
    for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
      ValueMap val = entry.getValue();
      PrimaryPreferencesMattelBrandsPojo mattelBrand = new PrimaryPreferencesMattelBrandsPojo();
      mattelBrand.setTitle(val.get("brandTitle", String.class));
      mattelBrand.setDescription(val.get("brandDescription", String.class));
      mattelBrand.setPreferenceID(val.get("brandPreferenceID", String.class));
      mattelBrand.setAlwaysEnglish(val.get("alwaysEnglish", String.class));
      mattelBrandsList.add(mattelBrand);

    }
    LOGGER.debug("mattelBrandsList size of MattelBrandsListModel is {}", mattelBrandsList.size());
    LOGGER.debug("mattelBrandsList is {}", mattelBrandsList);
    return mattelBrandsList;
  }

  public List<PrimaryPreferencesMattelBrandsPojo> getMattelBrandsList() {
    return mattelBrandsList;
  }

  public void setMattelBrandsList(List<PrimaryPreferencesMattelBrandsPojo> mattelBrandsList) {
    this.mattelBrandsList = mattelBrandsList;
  }

  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public void setBrandsList(Node brandsList) {
    this.brandsList = brandsList;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

}