package com.mattel.ag.retail.core.model;

import com.mattel.ag.retail.core.pojos.StoreDetailsPojo;
import com.mattel.ag.retail.core.services.ChildPageProperties;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author CTS.
 * A Model class for Location Selector
 */

@Model(adaptables = Resource.class)

public class LocationSelectionModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(LocationSelectionModel.class);
  @Inject
  @Optional
  ChildPageProperties childPageProperties;
  List<StoreDetailsPojo> storeDetailsPojos;
  List<StoreDetailsPojo> localLocations = new ArrayList<>();
  List<StoreDetailsPojo> internationalLocations = new ArrayList<>();

  public List<StoreDetailsPojo> getStoreDetailsPojos() {
    return storeDetailsPojos;
  }

  public void setStoreDetailsPojos(List<StoreDetailsPojo> storeDetailsPojos) {
    this.storeDetailsPojos = storeDetailsPojos;
  }

  public List<StoreDetailsPojo> getLocalLocations() {
    return localLocations;
  }

  public void setLocalLocations(List<StoreDetailsPojo> localLocations) {
    this.localLocations = localLocations;
  }

  public List<StoreDetailsPojo> getInternationalLocations() {
    return internationalLocations;
  }

  public void setInternationalLocations(List<StoreDetailsPojo> internationalLocations) {
    this.internationalLocations = internationalLocations;
  }

  @PostConstruct
  protected void init() {
    storeDetailsPojos = childPageProperties.getpages();
    for (StoreDetailsPojo storeDetailsPojo : storeDetailsPojos) {

      LOGGER.debug("titles of pages are {}", storeDetailsPojo.getPageTitle());
      if (storeDetailsPojo.isInternational()) {
        internationalLocations.add(storeDetailsPojo);
      } else {
        localLocations.add(storeDetailsPojo);
      }
    }
    localLocations.sort((StoreDetailsPojo t1, StoreDetailsPojo t2) -> t1.getPageTitle()
        .toUpperCase().compareTo(t2.getPageTitle().toUpperCase()));

    internationalLocations.sort((StoreDetailsPojo t1, StoreDetailsPojo t2) -> t1.getPageTitle()
        .toUpperCase().compareTo(t2.getPageTitle().toUpperCase()));


  }

  public void setChildPageProperties(ChildPageProperties childPageProperties) {
    this.childPageProperties = childPageProperties;
  }
}
