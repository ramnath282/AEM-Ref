package com.mattel.global.core.model;

import com.day.cq.commons.Externalizer;
import com.mattel.global.core.pojo.CarouselPojo;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.core.utils.PathUtils;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CTS. A Model class for Carousel.
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class CarouselModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(CarouselModel.class);
  List<CarouselPojo> carouselItemList = new ArrayList<>();
  @Inject
  private Node carouselList;
  @Inject
  private MultifieldReader multifieldReader;
  
  @Reference
  Externalizer externalizer;

  @PostConstruct
  protected void init() {

    LOGGER.info("Carousel Model init Start");
    if (carouselList != null) {
      LOGGER.debug("calling multifield service");
      Map<String, ValueMap> multifieldProperty = multifieldReader.propertyReader(carouselList);
      for (Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
        CarouselPojo carouselItems = new CarouselPojo();
        carouselItems.setHeading(entry.getValue().get("heading", String.class));
        carouselItems.setSubHeading(entry.getValue().get("subHeading", String.class));
        carouselItems.setImage(entry.getValue().get("image", String.class));
        carouselItems.setImageAlttext(entry.getValue().get("imageAlttext", String.class));
        carouselItems.setOverlayHeading(entry.getValue().get("overlayHeading", String.class));
        carouselItems.setOverlaySubHeading(entry.getValue().get("overlaySubHeading", String.class));
        carouselItems.setCtaLabel(entry.getValue().get("ctaLabel", String.class));
        carouselItems.setCtaLink(entry.getValue().get("ctaLink", String.class));
        if(null != carouselItems.getCtaLink()){
        carouselItems.setExternal(PathUtils.isExternal(carouselItems.getCtaLink()));
        }
        carouselItems.setCtaAltText(entry.getValue().get("ctaAltText", String.class));
        carouselItems.setTextPositioning(entry.getValue().get("textPositioning", String.class));
        carouselItems.setCtaPositioning(entry.getValue().get("ctaPositioning", String.class));
        carouselItems.setCtaStyling(entry.getValue().get("ctaStyling", String.class));
        carouselItems.setShowArrow(entry.getValue().get("setShowArrow", String.class));
        carouselItems.setShowPagination(entry.getValue().get("showPagination", String.class));
        carouselItems.setTextBackground(entry.getValue().get("textBackground", String.class));
        carouselItems.setCtaRenderoption(entry.getValue().get("ctaRenderoption", String.class));
        LOGGER.debug("carouselItems{} ", carouselItems);
        carouselItemList.add(carouselItems);
      }
    }
    LOGGER.info("Carousel Model init end");
  }

  /**
   * @return This method return list of Carousel Pojo
   */
  public List<CarouselPojo> getCarouselItemsList() {
    return carouselItemList;
  }

  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public void setExternalizer(Externalizer externalizer) {
    this.externalizer = externalizer;
  }

  public void setCarouselItemList(List<CarouselPojo> carouselItemList) {
    this.carouselItemList = carouselItemList;
  }

  public void setCarouselList(Node carouselList) {
    this.carouselList = carouselList;
  }
}
