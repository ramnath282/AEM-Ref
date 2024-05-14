package com.mattel.ecomm.pdp.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductInventoryCheckService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.InventoryCheckRequest;
import com.mattel.ecomm.coreservices.core.pojos.InventoryCheckResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Product Inventory Check Service implementation.
 */
@Component(service = ProductInventoryCheckService.class)
@Designate(ocd = ProductInventoryCheckServiceImpl.Config.class)
public class ProductInventoryCheckServiceImpl implements ProductInventoryCheckService {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ProductInventoryCheckServiceImpl.class);
  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(InventoryCheckRequest.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(InventoryCheckResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String productInventoryAvailabilityEndpoint;

  @Override
  public InventoryCheckResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
        final InventoryCheckRequest inventoryCheckRequest = (InventoryCheckRequest) baseRequest;
        final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
        final Map<String, String> requestHeaders = new HashMap<>();
        final HttpResponse httpResponse;
        final int status;

        requestHeaders.put("Accept", Constant.CONTENT_TYPE_APPLICATION_JSON);
        requestHeaders.put("Content-type", Constant.CONTENT_TYPE_APPLICATION_JSON);
        httpResponse = HttpRequestHandler.post(httpClient, dataMap.get(Constant.END_POINT_URL_KEY).toString(),
                null, requestHeaders, inventoryCheckRequest, httpClientContext);
        status = httpResponse.getStatusLine().getStatusCode();

        ProductInventoryCheckServiceImpl.LOGGER.debug("Response status is {}", status);

        if (!isError(status)) {
            try (InputStream inputStream = httpResponse.getEntity().getContent()) {
                final InventoryCheckResponse inventoryCheckResponse = ProductInventoryCheckServiceImpl.RESP_READER
                        .readValue(inputStream);

                ProductInventoryCheckServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                        System.currentTimeMillis() - startTime);
                logServiceErrors(inventoryCheckResponse);
                return inventoryCheckResponse;
            }
        } else {
          ProductInventoryCheckServiceImpl.LOGGER.error("Recieved response with error status: {} in {}", status,
                    Constant.EXECUTE_SERVICE);
          ProductInventoryCheckServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                    System.currentTimeMillis() - startTime);
            generalExceptionHandling(status, httpResponse.getEntity());
        }
    } catch (final IOException e) {
        final long endTime = System.currentTimeMillis();

        ProductInventoryCheckServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                endTime - startTime);
        ProductInventoryCheckServiceImpl.LOGGER.error("Encountered error:", e);
        throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }

    return null;
  }

  @Override
  public InventoryCheckResponse fetch(Map<String, Object> requestMap) throws ServiceException {
    final long startTime = System.currentTimeMillis();

    try {
      final InventoryCheckRequest inventoryCheckRequest = ProductInventoryCheckServiceImpl.REQ_READER
          .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
      final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
      final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
      final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
          cookieNames);
      final String storekey = (String) requestMap.get(Constant.STORE_KEY);
      final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
      final String storeId = propertyReaderService.getStoreId(storekey);
      final String domain = propertyReaderService.getCookieDomain(domainKey);
      final String endPointUrl = productInventoryAvailabilityEndpoint
          .replaceAll(Constant.STORE_ID_PLACEHOLDER, storeId);
      final Map<String, Object> dataMap = new HashMap<>();
      final long endTime;

      dataMap.put("validCookieNames", cookieNames);
      dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
      dataMap.put("domain", domain);
      dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
      dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
      endTime = System.currentTimeMillis();
      ProductInventoryCheckServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          getClass().getSimpleName(), endTime - startTime);
      return execute(inventoryCheckRequest, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      ProductInventoryCheckServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          getClass().getSimpleName(), endTime - startTime);
      ProductInventoryCheckServiceImpl.LOGGER.error("Error Encountered {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  @ObjectClassDefinition(name = "WCS ProductInventoryCheckService Configurations", description = "ProductInventoryCheckService Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "ProductInventoryCheckService End Point", description = "Please Enter the ProductInventoryCheckService End point in the format"
        + "http://domain/restendpoint/${storeId}/inventoryavailability")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/mattelInventoryAvailability/getProductInventory?updateCookies=true&collate=Y";
  }

  @Activate
  public void activate(final Config config) {
    productInventoryAvailabilityEndpoint = config.endPoint();
  }
}
