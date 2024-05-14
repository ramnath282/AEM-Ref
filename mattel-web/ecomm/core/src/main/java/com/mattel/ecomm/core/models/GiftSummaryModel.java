package com.mattel.ecomm.core.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mattel.ecomm.core.pojos.shopify.GTSummaryUIResponse;
import com.mattel.ecomm.core.services.GTCompositeImageService;
import com.mattel.ecomm.core.utils.shopify.GTSummaryPageUIAdapter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;

/**
 * @author CTS.
 * 
 *         A Model class for Carousel
 */

@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GiftSummaryModel {

  private static final String QUIZ_CONTAINER = "mattel/ecomm/shared/components/content/quizContainer";

  private static final Logger LOGGER = LoggerFactory.getLogger(GiftSummaryModel.class);

  @Inject
  @Via("resource")
  private String smallTrunkSku;

  @Inject
  private Page currentPage;

  @Inject
  @Via("resource")
  private String largeTrunkSku;

  @Inject
  @Via("resource")
  private Page letterPage;

  @Inject
  ProductAvailability productAvailability;

  @SlingObject
  SlingHttpServletRequest request;

  private Map<String, Object> requestMap = null;

  private ObjectWriter objWriter = null;
  private String smallTrunkResponseString = "";

  private String largeTrunkResponseString = "";

  private String baseImageUrl;
  private String[] layer1Url;
  private String layer2Url;
  private String layer3Url;
  private String layer4Url;
  private String layerCompParams;

  @Inject
  GTCompositeImageService gtCompositeImageService;

  @PostConstruct
  protected void init() {
    Cookie[] cookies = request.getCookies();
    requestMap = new HashMap<>();
    String storeKey = "ag_en";
    String langId = "-1";
    if (currentPage != null) {
      InheritanceValueMap inheritanceValueMap = new HierarchyNodeInheritanceValueMap(
          currentPage.getContentResource());
      LOGGER.debug("inHeritance map: {}", inheritanceValueMap);
      storeKey = inheritanceValueMap.getInherited("siteKey", String.class);
      langId = inheritanceValueMap.getInherited("langId", String.class);
      LOGGER.debug("Site key is: {}", storeKey);
    }
    objWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    requestMap.put(Constant.STORE_KEY, storeKey);
    requestMap.put(Constant.DOMAIN_KEY, storeKey);
    requestMap.put(Constant.REQUEST_COOKIES_KEY, cookies);
    requestMap.put(Constant.LANG_ID, langId);

    baseImageUrl = gtCompositeImageService.getBaseImageUrl();

    layer1Url = gtCompositeImageService.getLayer1Url();
    layer2Url = gtCompositeImageService.getLayer2Url();
    layer3Url = gtCompositeImageService.getLayer3Url();
    layer4Url = gtCompositeImageService.getLayer4Url();

    layerCompParams = gtCompositeImageService.getLayerCompParams();
  }

  /**
   * @return GTSummaryUIResponse
   */
  public GTSummaryUIResponse getSmallTrunkDetails() {
    requestMap.put(Constant.PART_NUMBER, smallTrunkSku);
    ProductServiceResponse productServiceResponse = null;
    GTSummaryUIResponse trunkUIResponse = null;

    try {

      productServiceResponse = productAvailability.fetch(requestMap);
      trunkUIResponse = formatProduct(productServiceResponse);
      smallTrunkResponseString = objWriter.writeValueAsString(trunkUIResponse);
    } catch (ServiceException e) {
      LOGGER.error(String.format("Service Exception Occured for small trunk sku:%s", smallTrunkSku), e);
    } catch (IOException e) {
      LOGGER.error(String.format("IOException Occured for small trunk sku:%s", smallTrunkSku), e);
    }
    return trunkUIResponse;

  }

  /**
   * @return GTSummaryUIResponse
   */
  public GTSummaryUIResponse getLargeTrunkDetails() {
    requestMap.put(Constant.PART_NUMBER, largeTrunkSku);
    ProductServiceResponse productServiceResponse = null;
    GTSummaryUIResponse trunkUIResponse = null;
    try {
      productServiceResponse = productAvailability.fetch(requestMap);
      trunkUIResponse = formatProduct(productServiceResponse);
      largeTrunkResponseString = objWriter.writeValueAsString(trunkUIResponse);
    } catch (ServiceException e) {
      LOGGER.error(String.format("Service Exception Occured for large trunk sku:%s", largeTrunkSku), e);
    } catch (IOException e) {
      LOGGER.error(String.format("IOException Occured for large trunk sku:%s", largeTrunkSku), e);
    }
    return trunkUIResponse;

  }

  /**
   * @return
   */
  public String getSmallTrunkResponseString() {
    return smallTrunkResponseString;
  }

  /**
   * @param smallTrunkResponseString
   *          the smallTrunkResponseString to set
   */
  public void setSmallTrunkResponseString(String smallTrunkResponseString) {
    this.smallTrunkResponseString = smallTrunkResponseString;
  }

  /**
   * @return the largeTrunkResponseString
   */
  public String getLargeTrunkResponseString() {
    return largeTrunkResponseString;
  }

  /**
   * @param largeTrunkResponseString
   *          the largeTrunkResponseString to set
   */
  public void setLargeTrunkResponseString(String largeTrunkResponseString) {
    this.largeTrunkResponseString = largeTrunkResponseString;
  }

  /**
   * @return the baseImageUrl
   */
  public String getBaseImageUrl() {
    return baseImageUrl;
  }

  /**
   * @return the layer1Url
   */
  public String[] getLayer1Url() {
    return layer1Url;
  }

  /**
   * @return the layer2Url
   */
  public String getLayer2Url() {
    return layer2Url;
  }

  /**
   * @return the layer3Url
   */
  public String getLayer3Url() {
    return layer3Url;
  }

  /**
   * @return the layer4Url
   */
  public String getLayer4Url() {
    return layer4Url;
  }

  /**
   * @return the layerCompParams
   */
  public String getLayerCompParams() {
    return layerCompParams;
  }

  /**
   * @return
   * 
   *         returns letterPagePath
   */
  public String getLetterPage() {
    String letterPagePath = StringUtils.EMPTY;
    if (Objects.nonNull(letterPage)) {
      Resource pageContentNode = letterPage.getContentResource().getChild("root");
      if (Objects.nonNull(pageContentNode)) {
        letterPagePath = getLetterNodePath(pageContentNode);
      }
    }
    return letterPagePath;

  }

  /**
   * 
   * Returns letterNode path using resource
   * 
   * @param pageContentNode
   *          letter page root node resource
   * @return
   */
  private String getLetterNodePath(Resource pageContentNode) {
    String letterPagePath = StringUtils.EMPTY;
    Iterator<Resource> rootChildren = pageContentNode.listChildren();
    while (rootChildren.hasNext()) {
      Resource rootChild = rootChildren.next();
      if (rootChild.isResourceType(QUIZ_CONTAINER)) {
        Resource letterNode = rootChild.getChild("columnone");
        if (Objects.nonNull(letterNode)) {
          letterPagePath = letterNode.getPath();
        }

      }
    }
    return letterPagePath;
  }

  
  /**
   * @param productServiceResponse
   * @return GTSummaryUIResponse
   */
  private GTSummaryUIResponse formatProduct(ProductServiceResponse productServiceResponse) {
    LOGGER.debug("Gift Summary formatProduct Start");
    Product product = productServiceResponse.getProduct();
    GTSummaryPageUIAdapter gtUIAdapter = new GTSummaryPageUIAdapter();
    return gtUIAdapter.transformProductToSingleSKU(product, this.productAvailability,
        this.requestMap);
  }

}
