package com.mattel.ecomm.core.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ecomm.core.interfaces.MarketingContentProviderService;
import com.mattel.ecomm.core.pojos.shopify.ProductUIResponse;
import com.mattel.ecomm.core.utils.shopify.ProductUIAdapter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.interfaces.ProductQuickViewService;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;

/**
 * Service to return response UI compatible product response.
 */
@Component(service = ProductQuickViewService.class)
public class ProductQuickViewServiceImpl implements ProductQuickViewService {
  private static final String MARKETING_CONTENT_POSITIONTYPE = "default";
  private static final String SERVICE_NAME = "Product Quick View Service";
  private static final List<String> ALLOWED_KEYS = Arrays.asList(Constant.PART_NUMBER, Constant.REQUEST_DOMAIN_ID,
      Constant.REQUEST_STOREID, Constant.STORE_KEY, Constant.DOMAIN_KEY);
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductQuickViewServiceImpl.class);
  @Reference
  private ProductAvailability productAvailability;
  @Reference
  private MarketingContentProviderService marketingContentProviderService;

  @Override
  public Map<String, Object> getQuickViewProductData(Map<String, Object> requestMap, Map<String, Object> responseMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final long endTime;
    JSONObject response;
    final Map<String, Object> reqMap = requestMap.entrySet().stream()
        .filter(map -> ProductQuickViewServiceImpl.ALLOWED_KEYS.contains(map.getKey()))
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));

    ProductQuickViewServiceImpl.LOGGER.info("ProductQuickView Service, requestMap: {}", requestMap);
    response = callProductAvailabilityService(reqMap);

    if (Objects.nonNull(response)) {
      responseMap.put(Constant.RESPONSE_BODY, response.toString());
    }

    endTime = System.currentTimeMillis();
    ProductQuickViewServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ProductQuickViewServiceImpl.SERVICE_NAME,
        endTime - startTime);
    return responseMap;
  }

  /**
   * Invokes the product availability api.
   *
   * @param requestMap
   *          The request map containing product part number
   * @return Response for quick view modal.
   * @throws ServiceException
   */
  private JSONObject callProductAvailabilityService(Map<String, Object> requestMap) throws ServiceException {
    final ProductServiceResponse productServiceResponse = productAvailability.fetch(requestMap);

    ProductQuickViewServiceImpl.LOGGER.debug("Product Availability response : {}", productServiceResponse);
    if (null != productServiceResponse) {
      final ProductUIResponse shopifyProductUIResponse = formatProduct(productServiceResponse, requestMap);

      ProductQuickViewServiceImpl.LOGGER.debug("Product UI response : {}", shopifyProductUIResponse);
      return productAvailability.getResponseValueAsJson(shopifyProductUIResponse);
    }

    return null;
  }

  /**
   * Format the response received from Product availability api.
   *
   * @param productServiceResponse
   *          The Product Availability API service response.
   * @param requestMap
   *          The request map containing partnumber.
   * @return Response for quick view modal.
   */
  private ProductUIResponse formatProduct(ProductServiceResponse productServiceResponse,
      Map<String, Object> requestMap) {
    ProductQuickViewServiceImpl.LOGGER.info("ProductQuickViewService formatProduct Start");
    final Product product = productServiceResponse.getProduct();
    ProductUIResponse pdpProductUIResponse = null;

    if (Objects.nonNull(product)) {
      final String partnumber = (String) requestMap.get(Constant.PART_NUMBER);
      final String siteKey = (String) requestMap.get(Constant.STORE_KEY);
      final List<String> experienceFragmentPath = marketingContentProviderService.getContentFromTags(siteKey,
          partnumber, ProductQuickViewServiceImpl.MARKETING_CONTENT_POSITIONTYPE);

      ProductQuickViewServiceImpl.LOGGER.debug("Marketing content path: {}", experienceFragmentPath);
      pdpProductUIResponse = ProductUIAdapter.transformProduct(product, experienceFragmentPath);
    }

    ProductQuickViewServiceImpl.LOGGER.info("ProductQuickViewService formatProduct End");
    return pdpProductUIResponse;
  }
}
