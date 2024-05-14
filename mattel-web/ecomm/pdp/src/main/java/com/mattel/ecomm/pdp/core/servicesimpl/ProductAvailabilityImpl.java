package com.mattel.ecomm.pdp.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.ResponseBody;
import com.mattel.ecomm.coreservices.core.pojos.shopify.BaseErrorResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = ProductAvailability.class)
@Designate(ocd = ProductAvailabilityImpl.Config.class)
/**
 * Product Availability API to fetch details of product from Shopify PIM.
 */
public class ProductAvailabilityImpl implements ProductAvailability {
  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String PART_NUMBER_PLACEHOLDER = "PART_NUMBER";
  private static final String EXECUTE_SERVICE = "ProductAvailability - execute";
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductAvailabilityImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(ProductServiceResponse.class);
  private static final ObjectWriter ERR_WRITER = ResourceMapper.getWriterInstance(BaseErrorResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String productAPIEndpoint;

  @SuppressWarnings("unchecked")
  @Override
  public ProductServiceResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
    ProductAvailabilityImpl.LOGGER.info("{} - start", ProductAvailabilityImpl.EXECUTE_SERVICE);
    final long startTime = System.currentTimeMillis();
    final String url = dataMap.get(ProductAvailabilityImpl.END_POINT_URL_KEY).toString();

    try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
      final Map<String, String> requestHeaders = new HashMap<>();
      final HttpResponse httpResponse = HttpRequestHandler.get(httpClient, url, null, requestHeaders,
          (Map<String, String>) dataMap.get("reqParams"), null);
      final int status = httpResponse.getStatusLine().getStatusCode();

      if (null != httpResponse.getEntity() && !isError(status)) {
        ProductAvailabilityImpl.LOGGER.info("Response status is {}", status);

        try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
          final ProductServiceResponse productServiceResponse = ProductAvailabilityImpl.RESP_READER
              .readValue(inputStream);

          ProductAvailabilityImpl.LOGGER.debug("request uri: {}, product service response: {}", url,
              productServiceResponse);
          ProductAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ProductAvailabilityImpl.EXECUTE_SERVICE,
              (System.currentTimeMillis() - startTime));
          return productServiceResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();
        ProductAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ProductAvailabilityImpl.EXECUTE_SERVICE,
            endTime - startTime);
        generalExceptionHandling(url, status, httpResponse.getEntity());
      }
    } catch (final IOException | URISyntaxException io) {
      final long endTime = System.currentTimeMillis();
      ProductAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ProductAvailabilityImpl.EXECUTE_SERVICE,
          endTime - startTime);
      ProductAvailabilityImpl.LOGGER.error("Encountered error:", io);
      buildError(String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR), "Internal Error Occured");
    }

    final long endTime = System.currentTimeMillis();
    ProductAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, ProductAvailabilityImpl.EXECUTE_SERVICE,
        endTime - startTime);
    return null;
  }

  @Override
  public ProductServiceResponse fetch(Map<String, Object> requestMap) throws ServiceException {
    ProductAvailabilityImpl.LOGGER.info("fetch - start");
    final long startTime = System.currentTimeMillis();
    final String storekey = (String) requestMap.get(Constant.STORE_KEY);
    final String partNumber = (String) requestMap.get(Constant.PART_NUMBER);

    if (StringUtils.isNotEmpty(partNumber)) {
      final Map<String, Object> dataMap = new HashMap<>();
      String endPointUrl = productAPIEndpoint.replaceAll(ProductAvailabilityImpl.PART_NUMBER_PLACEHOLDER,
          transform(partNumber).toUpperCase());
      endPointUrl = addLangIdToRequestUri(requestMap, endPointUrl);
      final long endTime;

      final Map<String, String> requestParameters = new HashMap<>();
      Optional.ofNullable((String) requestMap.get("partial")).filter(StringUtils::isNotEmpty)
          .ifPresent(v -> requestParameters.put("partial", v));
      Optional.ofNullable((String) requestMap.get("view")).filter(StringUtils::isNotEmpty)
          .ifPresent(v -> requestParameters.put("view", v));
      dataMap.put("reqParams", requestParameters);
      dataMap.put(ProductAvailabilityImpl.END_POINT_URL_KEY, endPointUrl);
      dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
      endTime = System.currentTimeMillis();
      ProductAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "ProductAvailability - fetch",
          endTime - startTime);
      return execute(null, dataMap);
    } else {
      ProductAvailabilityImpl.LOGGER.error("Missing or invalid partnumber, request cannot be processed");
      ProductAvailabilityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "ProductAvailability - fetch",
          System.currentTimeMillis() - startTime);
      buildError(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST),
          "Missing or invalid partnumber, request cannot be processed");
    }

    return null;
  }

  private void buildError(String status, String message) throws ServiceException {
    final ResponseBody error = new ResponseBody();
    final BaseErrorResponse errorResponse = new BaseErrorResponse();
    errorResponse.setError(message);

    try {
      error.setContent(ProductAvailabilityImpl.ERR_WRITER.writeValueAsString(errorResponse));
      error.setContentType(Constant.CONTENT_TYPE_APPLICATION_JSON);
      throw new ServiceException(status, error, true);
    } catch (final IOException e) {
      ProductAvailabilityImpl.LOGGER.error("Encountered error:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }
  }

  @ObjectClassDefinition(name = "Shopify ProductAvailability Configurations", description = "ProductAvailability API Configuration for Shopify")
  public @interface Config {
    @AttributeDefinition(name = "Shopify ProductAvailability End Point", description = "Please Enter the ProductAvailability End point in the format"
        + "/product/PART_NUMBER")
    String endPoint() default "https://api-dtc.platform.mattel/v1/ag/productcustomer/product/PART_NUMBER";
  }

  @Activate
  public void activate(final Config config) {
    productAPIEndpoint = config.endPoint();
  }

  private String transform(Object obj) {
    return Optional.ofNullable(obj).orElse(StringUtils.EMPTY).toString();
  }

  @Override
  public boolean isError(int status) {
    switch (status) {
      case 200:
      case 201:
      case 202:
        return false;
      default:
        return true;
    }
  }

  public void generalExceptionHandling(String requesturi, int status, HttpEntity entity) throws ServiceException {
    LOGGER.error("Shopify Product API Service call:{}, error status: {}", requesturi, status);

    if (Objects.nonNull(entity)) {
      String content = null;
      ContentType contentType = null;

      try {
        content = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        contentType = ContentType.getOrDefault(entity);
      } catch (final Exception e) {
        LOGGER.error(
            String.format("For request: %s, Unable to parse response body for service error handling", requesturi), e);
      }

      if (StringUtils.isNotBlank(content)) {
        final ResponseBody responseBody = new ResponseBody();

        responseBody.setContent(content);
        Optional.ofNullable(contentType).map(ContentType::getMimeType).ifPresent(responseBody::setContentType);
        throw new ServiceException(Integer.toString(status), responseBody, true);
      }
    }
  }
}
