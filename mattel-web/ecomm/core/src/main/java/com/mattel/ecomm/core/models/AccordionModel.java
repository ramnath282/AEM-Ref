package com.mattel.ecomm.core.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.pojos.AccordianPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.core.utils.GiftCardSkuProcessor;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;

/**
 * @author CTS
 *
 */
@Model(adaptables = { Resource.class,
    SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AccordionModel {
  private static final Logger LOGGER = LoggerFactory.getLogger(AccordionModel.class);
  private static final String PART_NUMBER_ATTR = "partNumber";
  List<AccordianPojo> keyList = new ArrayList<>();
  @Inject
  @Via("resource")
  private Node keyMultifield;
  @Inject
  private MultifieldReader multifieldReader;
  @Inject
  ProductAvailability productAvailability;

  @SlingObject
  SlingHttpServletRequest request;

  private Map<String, String> attrs;

  @PostConstruct
  protected void init() {
    AccordionModel.LOGGER.info("AccordionModel Init Start");
    try {
      fetchProductInfo();
      if (!Objects.isNull(keyMultifield)) {
        final Map<String, ValueMap> multifieldProperty = multifieldReader
            .propertyReader(keyMultifield);
        for (final Map.Entry<String, ValueMap> entry : multifieldProperty.entrySet()) {
          final AccordianPojo accordianPojo = new AccordianPojo();
          final String keyset = Optional.ofNullable(entry.getValue())
              .map(v -> v.get("key", String.class)).orElse(StringUtils.EMPTY);
          final List<String> accordianNumberList = new ArrayList<>();
          if (StringUtils.isNotEmpty(keyset)) {
            final String[] key = keyset.split(",");
            for (final String multifieldKey : key) {
              accordianNumberList.add(multifieldKey.trim());
            }
          }
          accordianPojo.setAccordionKeyList(accordianNumberList);
          accordianPojo.setAccordianTitle(entry.getValue().get("accordianTitle", String.class));
          accordianPojo.setFlag(false);
          List<Boolean> flagList = setKeyPresentFlag(accordianPojo);
          if (flagList.contains(true)) {
            accordianPojo.setFlag(true);
          }
          keyList.add(accordianPojo);
        }
      }
      AccordionModel.LOGGER.debug("Accordion keyList : {} ", keyList);
      AccordionModel.LOGGER.info("AccordionModel Init end");
    } catch (final RuntimeException | ServiceException e) {
      AccordionModel.LOGGER.error("Exception occured while building accordion model", e);
      attrs = new HashMap<>();
    }

  }

  /**
   * The method fetch the product information from request
   * 
   * @throws ServiceException
   */
  private void fetchProductInfo() throws ServiceException {
    AccordionModel.LOGGER.info("AccordionModel fetchProductInfo Start");
    final String[] selectors = request.getRequestPathInfo().getSelectors();
    if (selectors.length >= 2) {
      final String siteKey = selectors[0];
      String partNumber = selectors[1];
      partNumber = GiftCardSkuProcessor.checkAndUnescapeDelimiter(partNumber);
      AccordionModel.LOGGER.debug("Domain and Store selector : {}", siteKey);
      AccordionModel.LOGGER.debug("Part Number : {}", partNumber);
      final Map<String, Object> requestMap = new HashMap<>();
      requestMap.put(Constant.STORE_KEY, siteKey);
      requestMap.put(Constant.DOMAIN_KEY, siteKey);
      requestMap.put(Constant.PART_NUMBER, partNumber);
      requestMap.put("partial", "attributes");
      final ProductServiceResponse productServiceResponse = productAvailability.fetch(requestMap);
      formatProduct(productServiceResponse, partNumber);
      LOGGER.debug("Product: {} accordion details : {}", new Object[] { partNumber, keyList });
      LOGGER.debug("Product: {} attributes : {}", new Object[] { partNumber, attrs });
    }
    AccordionModel.LOGGER.info("AccordionModel fetchProductInfo End");
  }

  /**
   * The method sets the flag based on descriptive attribute value
   * 
   * @param accordianPojo
   * @return flagList
   */
  private List<Boolean> setKeyPresentFlag(AccordianPojo accordianPojo) {
    AccordionModel.LOGGER.info("AccordionModel setKeyPresentFlag Start");
    List<Boolean> flagList = new ArrayList<>();
    for (String keys : accordianPojo.getAccordionKeyList()) {
      if (StringUtils.isNotEmpty(attrs.get(keys))) {
        flagList.add(true);
      }
    }
    AccordionModel.LOGGER.info("AccordionModel setKeyPresentFlag End");
    return flagList;
  }

  /**
   * The method assigns value to the attrs hashmap
   * 
   * @param productServiceResponse
   */
  private void formatProduct(ProductServiceResponse productServiceResponse, String partNumber) {
    AccordionModel.LOGGER.info("AccordionModel formatProduct Start");
    attrs = new LinkedHashMap<>();
    attrs.put(AccordionModel.PART_NUMBER_ATTR, partNumber);
    if (Objects.nonNull(productServiceResponse)
        && Objects.nonNull(productServiceResponse.getProduct())
        && Objects.nonNull(productServiceResponse.getProduct().getAttributes())) {
      final Map<String, Object> attributes = productServiceResponse.getProduct().getAttributes();
      attributes.forEach((key, val) -> {
        final String transformedVal = transform(val);
        attrs.put(key, transformedVal);
      });
    }
    AccordionModel.LOGGER.info("AccordionModel formatProduct End");
  }

  /**
   * The method transforms object to string
   * 
   * @param obj
   * @return value
   */
  @SuppressWarnings("unchecked")
  public static String transform(Object obj) {
    String value = "";
    if (Objects.nonNull(obj)) {
      if (obj instanceof String) {
        value = obj.toString();
      } else if (obj instanceof List) {
        final List<String> strArray = (List<String>) obj;
        if (!strArray.isEmpty()) {
          value = strArray.get(0);
        }
      }
    }
    return value;
  }

  public List<AccordianPojo> getKeyList() {
    return keyList;
  }

  public void setKeyList(List<AccordianPojo> keyList) {
    this.keyList = keyList;
  }

  public Node getKeyMultifield() {
    return keyMultifield;
  }

  public Map<String, String> getAttrs() {
    return attrs;
  }

  public void setMultifieldReader(MultifieldReader multifieldReader) {
    this.multifieldReader = multifieldReader;
  }

  public void setKeyMultifield(Node keyMultifield) {
    this.keyMultifield = keyMultifield;
  }

}