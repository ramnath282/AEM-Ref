package com.mattel.ecomm.pdp.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.StoreAvailability;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.StoreAvailabilityRequest;
import com.mattel.ecomm.coreservices.core.pojos.StoreAvailabilityResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

/**
 * @author CTS Store Availability Service implementation
 *
 */
@Component(service = StoreAvailability.class)
@Designate(ocd = StoreAvailabilityImpl.Config.class)
public class StoreAvailabilityImpl implements StoreAvailability {

  private static final Logger LOGGER = LoggerFactory.getLogger(StoreAvailabilityImpl.class);
  private static final ObjectReader REQ_READER = ResourceMapper
      .getReaderInstance(StoreAvailabilityRequest.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(StoreAvailabilityResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String storeAvailabilityEndPoint;
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String EXECUTE_SERVICE = "Execute";

  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    StoreAvailabilityImpl.LOGGER.info("Start of StoreAvailability execute service");
    final long startTime = System.currentTimeMillis();
    try (CloseableHttpClient client = createCustomClient(dataMap)) {
      final StoreAvailabilityRequest storeAvailabilityRequest = (StoreAvailabilityRequest) baseRequest;
      final Map<String, String> requestHeaders = new HashMap<>();
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse;
      final int status;
      requestHeaders.put("Accept", "application/json");
      requestHeaders.put("Content-type", "application/json");
      httpResponse = HttpRequestHandler.post(client,
          dataMap.get(StoreAvailabilityImpl.END_POINT_URL_KEY).toString(), null,
          requestHeaders, storeAvailabilityRequest, httpClientContext);
      status = httpResponse.getStatusLine().getStatusCode();
      StoreAvailabilityImpl.LOGGER.debug("Status is {}", status);
      if (null != httpResponse.getEntity() && !isError(status)) {
        try (InputStream inputStream = httpResponse.getEntity().getContent()) {
          final StoreAvailabilityResponse storeAvailabilityResponse = StoreAvailabilityImpl.RESP_READER
              .readValue(inputStream);
          StoreAvailabilityImpl.LOGGER.debug("Store Availability service Response {}",
              storeAvailabilityResponse);
          final long endTime = System.currentTimeMillis();
          StoreAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
              StoreAvailabilityImpl.EXECUTE_SERVICE, endTime - startTime);
          logServiceErrors(storeAvailabilityResponse);
          return storeAvailabilityResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();
        StoreAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
            StoreAvailabilityImpl.EXECUTE_SERVICE, endTime - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException e) {
      StoreAvailabilityImpl.LOGGER.error("Error Encountered {}", e);
      final long endTime = System.currentTimeMillis();
      StoreAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Execure Exception",
          endTime - startTime);
    }
    final long endTime = System.currentTimeMillis();
    StoreAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        StoreAvailabilityImpl.EXECUTE_SERVICE, endTime - startTime);
    return null;
  }

  @Override
  public StoreAvailabilityResponse getStoreAvailability(Map<String, Object> requestMap)
      throws ServiceException {
    StoreAvailabilityImpl.LOGGER.info("Start of StoreAvailability getStoreAvailability service");
    final long startTime = System.currentTimeMillis();
    try {
      final StoreAvailabilityRequest storeAvailabilityRequest = StoreAvailabilityImpl.REQ_READER
          .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
      StoreAvailabilityImpl.LOGGER.debug("GetStoreAvailability Request is {}",
          storeAvailabilityRequest);
      final String storekey = (String) requestMap.get(Constant.STORE_KEY);
      final String storeId = propertyReaderService.getStoreId(storekey);
      final String endPointUrl = storeAvailabilityEndPoint
          .replaceAll(StoreAvailabilityImpl.STORE_ID_PLACEHOLDER, storeId);
      final Map<String, Object> dataMap = new HashMap<>();
      dataMap.put(StoreAvailabilityImpl.END_POINT_URL_KEY, endPointUrl);
      dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
      final long endTime = System.currentTimeMillis();
      StoreAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "GetStoreAvailability",
          endTime - startTime);
      return (StoreAvailabilityResponse) execute(storeAvailabilityRequest, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();
      StoreAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          "GetStoreAvailability Exception", endTime - startTime);
      StoreAvailabilityImpl.LOGGER.error("Error Encountered {}", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  @ObjectClassDefinition(name = "WCS Store Availability Service Configurations", 
      description = "Store Availability Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "StoreAvailability End Point", 
        description = "Please Enter the StoreAvailability End point in the format"
        + "http://domain/restendpoint/${storeId}/inventoryavailability")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/mattelInventoryAvailability/getStoreAvailability?updateCookies=true";
  }

  @Activate
  public void activate(Config config) {
    storeAvailabilityEndPoint = config.endPoint();
  }
}
