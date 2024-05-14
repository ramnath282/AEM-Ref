/**
 *
 */
package com.mattel.ecomm.login.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductKeywordSuggestionService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.ProductKeywordSuggestionResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@Component(service = ProductKeywordSuggestionService.class)
@Designate(ocd = ProductKeywordSuggestionServiceImpl.Config.class)
public class ProductKeywordSuggestionServiceImpl implements ProductKeywordSuggestionService {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ProductKeywordSuggestionServiceImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(ProductKeywordSuggestionResponse.class);
  private static final String PRODUCT_KEYWORD_SUGGESTION_SERVICE = "productKeywordSuggestionService";
  private static final String CATALOG_ID = "catalogId";
  private static final String CONTRACT_ID = "contractId";
  private static final String SEARCH_TERM = "searchTerm";
  @Reference
  PropertyReaderService propertyReaderService;
  private String productKeywordSuggestionServiceEndpoint;

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mattel.ecomm.coreservices.core.interfaces.BaseService#execute(com.mattel.ecomm.coreservices
   * .core.pojos.BaseRequest, java.util.Map)
   */
  @Override
  public ProductKeywordSuggestionResponse execute(BaseRequest baseRequest,
      Map<String, Object> dataMap) throws ServiceException {
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      final Map<String, String> requestParameters = new HashMap<>();
      final HttpResponse httpResponse;
      final int status;

      requestParameters.put(Constant.LANG_ID, dataMap.get(Constant.LANG_ID).toString());
      requestParameters.put(ProductKeywordSuggestionServiceImpl.CATALOG_ID,
          dataMap.get(ProductKeywordSuggestionServiceImpl.CATALOG_ID).toString());
      requestParameters.put(ProductKeywordSuggestionServiceImpl.CONTRACT_ID,
          dataMap.get(ProductKeywordSuggestionServiceImpl.CONTRACT_ID).toString());
      requestParameters.put(ProductKeywordSuggestionServiceImpl.SEARCH_TERM,
          dataMap.get(ProductKeywordSuggestionServiceImpl.SEARCH_TERM).toString());
      httpResponse = HttpRequestHandler.get(httpClient,
          dataMap.get(Constant.END_POINT_URL_KEY).toString(), null, null, requestParameters, null);
      status = httpResponse.getStatusLine().getStatusCode();

      ProductKeywordSuggestionServiceImpl.LOGGER.debug("Response status is {}", status);

      if (!isError(status)) {
        try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
          final ProductKeywordSuggestionResponse productKeywordSuggestionResponse = ProductKeywordSuggestionServiceImpl.RESP_READER
              .readValue(inputStream);

          ProductKeywordSuggestionServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
              ProductKeywordSuggestionServiceImpl.PRODUCT_KEYWORD_SUGGESTION_SERVICE,
              System.currentTimeMillis() - startTime);
          return productKeywordSuggestionResponse;
        }
      } else {
        ProductKeywordSuggestionServiceImpl.LOGGER.error(
            "Recieved response with error status: {} in {}", status,
            ProductKeywordSuggestionServiceImpl.PRODUCT_KEYWORD_SUGGESTION_SERVICE);
        ProductKeywordSuggestionServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
            ProductKeywordSuggestionServiceImpl.PRODUCT_KEYWORD_SUGGESTION_SERVICE,
            System.currentTimeMillis() - startTime);
        generalExceptionHandling(status);
      }
    } catch (IOException | URISyntaxException e) {
      ProductKeywordSuggestionServiceImpl.LOGGER.error(String.format("Encountered error in %s:",
          ProductKeywordSuggestionServiceImpl.PRODUCT_KEYWORD_SUGGESTION_SERVICE), e);
      ProductKeywordSuggestionServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          ProductKeywordSuggestionServiceImpl.PRODUCT_KEYWORD_SUGGESTION_SERVICE,
          System.currentTimeMillis() - startTime);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mattel.ecomm.coreservices.core.interfaces.ProductKeywordSuggestionService#search(java.util.
   * Map)
   */
  @Override
  public ProductKeywordSuggestionResponse search(Map<String, Object> requestMap)
      throws ServiceException {
    ProductKeywordSuggestionServiceImpl.LOGGER.info("fetch - start");

    final long startTime = System.currentTimeMillis();
    final String storekey = (String) requestMap.get(Constant.STORE_KEY);
    final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
    final String catalogId = (String) requestMap
        .get(ProductKeywordSuggestionServiceImpl.CATALOG_ID);
    final String langId = (String) requestMap.get(Constant.LANG_ID);
    final String contractId = (String) requestMap
        .get(ProductKeywordSuggestionServiceImpl.CONTRACT_ID);
    final String searchTerm = (String) requestMap
        .get(ProductKeywordSuggestionServiceImpl.SEARCH_TERM);
    final String storeId = propertyReaderService.getStoreId(storekey);
    final String domain = propertyReaderService.getCookieDomain(domainKey);
    final Map<String, Object> dataMap = new HashMap<>();
    final String endPointUrl = productKeywordSuggestionServiceEndpoint
        .replaceAll(Constant.STORE_ID_PLACEHOLDER, storeId.trim());
    final long endTime;

    dataMap.put(Constant.DOMAIN, domain);
    dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
    dataMap.put(Constant.LANG_ID, langId);
    dataMap.put(ProductKeywordSuggestionServiceImpl.CATALOG_ID, catalogId);
    dataMap.put(ProductKeywordSuggestionServiceImpl.CONTRACT_ID, contractId);
    dataMap.put(ProductKeywordSuggestionServiceImpl.SEARCH_TERM, searchTerm);
    endTime = System.currentTimeMillis();

    ProductKeywordSuggestionServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        getClass().getSimpleName(), endTime - startTime);
    return execute(null, dataMap);
  }

  @ObjectClassDefinition(name = "WCS Product Suggestion Service Configurations", description = "Product Suggestion Service Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "Product Suggestion Service End Point", description = "Please Enter the Product Suggestion Service End point in the format"
        + "http://domain/restendpoint/${storeId}/productkeywordsuggestionservice")
    String endPoint() default "https://mqa.services.commerce.mattel.com/search/resources/store/STORE_ID/sitecontent/keywordSuggestionsByTerm/*";
  }

  @Activate
  public void activate(final Config config) {
    productKeywordSuggestionServiceEndpoint = config.endPoint();
  }
}
