package com.mattel.ecomm.pdp.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAddOnDetailsService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.ProductAddOnResponse;
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

@Component(service = ProductAddOnDetailsService.class)
@Designate(ocd = ProductAddOnDetailsServiceImpl.Config.class)
public class ProductAddOnDetailsServiceImpl implements ProductAddOnDetailsService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductAddOnDetailsServiceImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(ProductAddOnResponse.class);
  private static final String PART_NUMBER = "partnumber";
  private static final String PART_NUMBER_PLACEHOLDER = "PART_NUMBER";
  @Reference
  PropertyReaderService propertyReaderService;
  private String productAddonDetailsServiceEndpoint;

  @SuppressWarnings("unchecked")
  @Override
  public ProductAddOnResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    ProductAddOnDetailsServiceImpl.LOGGER.info("{} - start", Constant.EXECUTE_SERVICE);
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
          dataMap.get(Constant.END_POINT_URL_KEY).toString(), requestCookies, null,
          httpClientContext);
      final int status = httpResponse.getStatusLine().getStatusCode();

      ProductAddOnDetailsServiceImpl.LOGGER.debug("Response status is {}", status);

      if (Objects.nonNull(httpResponse.getEntity()) && !isError(status)) {
        try (InputStream inputStream = httpResponse.getEntity().getContent()) {
          final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
          ProductAddOnResponse productAddOnResponse;
          productAddOnResponse = ProductAddOnDetailsServiceImpl.RESP_READER.readValue(inputStream);

          if (Objects.nonNull(cookieResheader) && cookieResheader.length > 0) {
            final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
            final String domain = (String) dataMap.get("domain");
            final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                httpClientContext.getCookieStore().getCookies());

            productAddOnResponse.setCookieList(cookieList);
          }

          ProductAddOnDetailsServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
              Constant.EXECUTE_SERVICE, System.currentTimeMillis() - startTime);
          logServiceErrors(productAddOnResponse);
          return productAddOnResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();

        ProductAddOnDetailsServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
            Constant.EXECUTE_SERVICE, endTime - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException io) {
      final long endTime = System.currentTimeMillis();

      ProductAddOnDetailsServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          Constant.EXECUTE_SERVICE, endTime - startTime);
      ProductAddOnDetailsServiceImpl.LOGGER.error("Encountered error:", io);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }

    ProductAddOnDetailsServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        Constant.EXECUTE_SERVICE, System.currentTimeMillis() - startTime);
    return null;
  }

  @Override
  public ProductAddOnResponse fetch(Map<String, Object> requestMap) throws ServiceException {
    ProductAddOnDetailsServiceImpl.LOGGER.info("fetch - start");

    final long startTime = System.currentTimeMillis();
    final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
    final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
    final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
        cookieNames);
    final String storekey = (String) requestMap.get(Constant.STORE_KEY);
    final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
    final String partNumber = (String) requestMap.get(ProductAddOnDetailsServiceImpl.PART_NUMBER);
    final String storeId = propertyReaderService.getStoreId(storekey);
    final String domain = propertyReaderService.getCookieDomain(domainKey);
    final Map<String, Object> dataMap = new HashMap<>();
    String endPointUrl = productAddonDetailsServiceEndpoint
        .replaceAll(Constant.STORE_ID_PLACEHOLDER, storeId);
    endPointUrl = endPointUrl.replaceAll(ProductAddOnDetailsServiceImpl.PART_NUMBER_PLACEHOLDER,
        transform(partNumber));
    endPointUrl = addLangIdToRequestUri(requestMap, endPointUrl);
    final long endTime;

    dataMap.put("validCookieNames", cookieNames);
    dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
    dataMap.put(Constant.DOMAIN, domain);
    dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
    dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
    endTime = System.currentTimeMillis();

    ProductAddOnDetailsServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        getClass().getSimpleName(), endTime - startTime);
    return execute(null, dataMap);
  }

  @ObjectClassDefinition(name = "WCS ProductAddOnDetailsService Configurations", description = "ProductAddOnDetailsService Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "ProductAddOnDetailsService End Point", description = "Please Enter the Product ProductAddOnDetailsService End point in the format"
        + "http://domain/restendpoint/${storeId}/pdpfeed")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xfeed/pdpfeed?storeId=STORE_ID&responseFormat=json&updateCookies=true&partnumber=PART_NUMBER&langId=LANG_ID&profileName=X_AEMCatalogEntryAssociations";
  }

  @Activate
  public void activate(final Config config) {
    productAddonDetailsServiceEndpoint = config.endPoint();
  }

  private String transform(Object obj) {
    return Optional.ofNullable(obj).orElse("").toString();
  }
}
