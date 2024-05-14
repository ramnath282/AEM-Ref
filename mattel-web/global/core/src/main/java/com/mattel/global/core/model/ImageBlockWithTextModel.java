package com.mattel.global.core.model;

import com.mattel.global.core.pojo.ImageBlockWithTextPojo;
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
 * @author CTS.
 */

@Model(adaptables = Resource.class)
public class ImageBlockWithTextModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageBlockWithTextModel.class);

  /**
   * 
   */
  @Inject
  @Optional
  private Node textImageDetails;

  @Inject
  @Optional
  private MultifieldReader multifieldReader;

  private List<ImageBlockWithTextPojo> imageWithTextPojos = new ArrayList<>();

  /**
   * Init method.
   */
  @PostConstruct
  protected void init() {
    LOGGER.info("Image with Text Init Method start");
    imageWithTextPojos = getProperties();
    LOGGER.info("Image with Text Init Method Ends");
  }

  /**
   * Method for setting all the values in dialog field into Pojo.
   *
   * @return ArrayList.
   */
  private List<ImageBlockWithTextPojo> getProperties() {
    LOGGER.info("Get properties method starts");
    Map<String, ValueMap> hashMap = multifieldReader.propertyReader(textImageDetails);
    LOGGER.info("Map from service ={}", hashMap);
    List<ImageBlockWithTextPojo> imageWithTextPojoList = new ArrayList<>();
    if (null != hashMap) {
      for (Map.Entry<String, ValueMap> mapEntry : hashMap.entrySet()) {
        ImageBlockWithTextPojo imageWithTextPojo = new ImageBlockWithTextPojo();
        imageWithTextPojo.setImageUrl(mapEntry.getValue().get("image", String.class));
        imageWithTextPojo.setImageAltText(mapEntry.getValue().get("imageAlttext", String.class));
        imageWithTextPojo.setCtaLabel(mapEntry.getValue().get("ctalabel", String.class));
        imageWithTextPojo.setCtaUrl(mapEntry.getValue().get("ctaurl", String.class));
        imageWithTextPojo.setTitle(mapEntry.getValue().get("title", String.class));
        imageWithTextPojo.setSubTitle(mapEntry.getValue().get("subTitle", String.class));
        imageWithTextPojo.setDescription(mapEntry.getValue().get("description", String.class));
        imageWithTextPojo.setTargetUrl(mapEntry.getValue().get("targetUrl", String.class));
        imageWithTextPojo.setImageLink(mapEntry.getValue().get("imageLink", String.class));
        if(null != imageWithTextPojo.getCtaUrl()){
        	imageWithTextPojo.setExternal(PathUtils.isExternal(imageWithTextPojo.getCtaUrl()));
        }
        LOGGER.debug("Pojo of Image Block text is {}", imageWithTextPojo);
        imageWithTextPojoList.add(imageWithTextPojo);
      }
    }
    LOGGER.debug("Final List returned for Image block with text is {}", imageWithTextPojoList);
    LOGGER.info("Get properties method Ends");
    return imageWithTextPojoList;
  }

  public List<ImageBlockWithTextPojo> getImageWithTextPojos() {
    return imageWithTextPojos;
  }
  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public void setTextImageDetails(Node textImageDetails) {
    this.textImageDetails = textImageDetails;
  }
}
