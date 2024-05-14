package com.mattel.ecomm.pdp.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAttributeService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.ProductAttributesResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.Cookie;

import org.apache.http.Header;
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

@Component(service = ProductAttributeService.class)
@Designate(ocd = ProductAttributeServiceImpl.Config.class)
public class ProductAttributeServiceImpl implements ProductAttributeService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductAttributeServiceImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(ProductAttributesResponse.class);
  private static final String PART_NUMBER = "partnumber";
  private static final String PART_NUMBER_PLACEHOLDER = "PART_NUMBER";
  @Reference
  PropertyReaderService propertyReaderService;
  private String productAttributeServiceEndpoint;

  @SuppressWarnings("unchecked")
  @Override
  public ProductAttributesResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    ProductAttributeServiceImpl.LOGGER.info("{} - start", Constant.EXECUTE_SERVICE);
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
          dataMap.get(Constant.END_POINT_URL_KEY).toString(), requestCookies, null,
          httpClientContext);
      final int status = httpResponse.getStatusLine().getStatusCode();

      ProductAttributeServiceImpl.LOGGER.debug("Response status is {}", status);

      if (Objects.nonNull(httpResponse.getEntity()) && !isError(status)) {
        try (InputStream inputStream = httpResponse.getEntity().getContent()) {
          final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
          ProductAttributesResponse productAttributesResponse;
          productAttributesResponse = ProductAttributeServiceImpl.RESP_READER.readValue(inputStream);

          if (Objects.nonNull(cookieResheader) && cookieResheader.length > 0) {
            final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
            final String domain = (String) dataMap.get("domain");
            final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                httpClientContext.getCookieStore().getCookies());

            productAttributesResponse.setCookieList(cookieList);
          }

          ProductAttributeServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
              System.currentTimeMillis() - startTime);
          logServiceErrors(productAttributesResponse);
          return productAttributesResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();

        ProductAttributeServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
            endTime - startTime);
        generalExceptionHandling(status);
      }
    } catch (final IOException io) {
      final long endTime = System.currentTimeMillis();

      ProductAttributeServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
          endTime - startTime);
      ProductAttributeServiceImpl.LOGGER.error("Encountered error:", io);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }

    ProductAttributeServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
        System.currentTimeMillis() - startTime);
    return null;
  }

  @Override
  public ProductAttributesResponse fetch(Map<String, Object> requestMap) throws ServiceException {
    ProductAttributeServiceImpl.LOGGER.info("fetch - start");

    final long startTime = System.currentTimeMillis();
    final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
    final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
    final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
        cookieNames);
    final String storekey = (String) requestMap.get(Constant.STORE_KEY);
    final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
    final String partNumber = (String) requestMap.get(ProductAttributeServiceImpl.PART_NUMBER);
    final String storeId = propertyReaderService.getStoreId(storekey);
    final String domain = propertyReaderService.getCookieDomain(domainKey);
    final Map<String, Object> dataMap = new HashMap<>();
    String endPointUrl = productAttributeServiceEndpoint.replaceAll(Constant.STORE_ID_PLACEHOLDER,
        storeId);
    endPointUrl = endPointUrl.replaceAll(ProductAttributeServiceImpl.PART_NUMBER_PLACEHOLDER,
        transform(partNumber));
    endPointUrl = addLangIdToRequestUri(requestMap, endPointUrl);
    ProductAttributeServiceImpl.LOGGER.debug("EndPoint Url: {}", endPointUrl);
    final long endTime;

    dataMap.put("validCookieNames", cookieNames);
    dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
    dataMap.put(Constant.DOMAIN, domain);
    dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
    dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
    endTime = System.currentTimeMillis();

    ProductAttributeServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        getClass().getSimpleName(), endTime - startTime);
    return execute(null, dataMap);
  }

  @ObjectClassDefinition(name = "WCS Product Attribute Service Configurations", description = "Product Attribute Service Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "Product Attribute Service End Point", description = "Please Enter the Product Attribute Service End point in the format"
        + "http://domain/restendpoint/${storeId}/loginservice")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xfeed/pdpfeed?storeId=STORE_ID&responseFormat=json&partnumber=PART_NUMBER&langId=LANG_ID&profileName=X_AEMCatalogEntryAttributes";
  }

  @Activate
  public void activate(final Config config) {
    productAttributeServiceEndpoint = config.endPoint();
  }

  private String transform(Object obj) {
    return Optional.ofNullable(obj).orElse("").toString();
  }
}
