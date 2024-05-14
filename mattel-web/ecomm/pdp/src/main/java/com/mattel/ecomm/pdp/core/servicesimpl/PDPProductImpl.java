package com.mattel.ecomm.pdp.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.PDPProduct;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = PDPProduct.class)
@Designate(ocd = PDPProductImpl.Config.class)
public class PDPProductImpl implements PDPProduct {

  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final String PART_NUMBER_PLACEHOLDER = "PART_NUMBER";
  private static final String EXECUTE_SERVICE = "execute";
  private static final String PART_NUMBER = "partnumber";
  private static final Logger LOGGER = LoggerFactory.getLogger(PDPProductImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(PDPResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String getPDPProductDataEndpoint;

  @SuppressWarnings("unchecked")
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    PDPProductImpl.LOGGER.info("{} - start", PDPProductImpl.EXECUTE_SERVICE);
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
      final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
          dataMap.get(PDPProductImpl.END_POINT_URL_KEY).toString(), requestCookies, null,
          httpClientContext);
      final int status = httpResponse.getStatusLine().getStatusCode();
      PDPProductImpl.LOGGER.debug("Response status is {}", status);
      if (null != httpResponse.getEntity() && !isError(status)) {
        try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
          final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
          PDPResponse pdpResponse;
          pdpResponse = PDPProductImpl.RESP_READER.readValue(inputStream);
          if (null != cookieResheader && cookieResheader.length > 0) {
            final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
            final String domain = (String) dataMap.get("domain");
            final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                httpClientContext.getCookieStore().getCookies());
            pdpResponse.setCookieList(cookieList);
          }
          PDPProductImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PDPProductImpl.EXECUTE_SERVICE,
              (System.currentTimeMillis() - startTime));
          logServiceErrors(pdpResponse);
          return pdpResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();
        PDPProductImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PDPProductImpl.EXECUTE_SERVICE,
            endTime - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException io) {
      final long endTime = System.currentTimeMillis();
      PDPProductImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PDPProductImpl.EXECUTE_SERVICE,
          endTime - startTime);
      PDPProductImpl.LOGGER.error("Encountered error:", io);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }
    final long endTime = System.currentTimeMillis();
    PDPProductImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, PDPProductImpl.EXECUTE_SERVICE,
        endTime - startTime);
    return null;
  }

  @Override
  public PDPResponse fetch(Map<String, Object> requestMap) throws ServiceException {
    PDPProductImpl.LOGGER.info("fetch - start");
    final long startTime = System.currentTimeMillis();
    final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
    final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
    final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects,
        cookieNames);
    final String storekey = (String) requestMap.get(Constant.STORE_KEY);
    final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
    final String partNumber = (String) requestMap.get(PDPProductImpl.PART_NUMBER);
    final String storeId = propertyReaderService.getStoreId(storekey);
    final String domain = propertyReaderService.getCookieDomain(domainKey);
    final Map<String, Object> dataMap = new HashMap<>();
    String endPointUrl = getPDPProductDataEndpoint.replaceAll(PDPProductImpl.STORE_ID_PLACEHOLDER,
        storeId);
    endPointUrl = endPointUrl.replaceAll(PDPProductImpl.PART_NUMBER_PLACEHOLDER,
        transform(partNumber));
    endPointUrl = addLangIdToRequestUri(requestMap, endPointUrl);
    final long endTime;
    dataMap.put("validCookieNames", cookieNames);
    dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
    dataMap.put("domain", domain);
    dataMap.put(PDPProductImpl.END_POINT_URL_KEY, endPointUrl);
    dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
    endTime = System.currentTimeMillis();
    PDPProductImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "PDPProduct", endTime - startTime);
    return (PDPResponse) execute(null, dataMap);
  }

  @ObjectClassDefinition(name = "WCS PDPProduct Configurations", 
      description = "PDPProduct Configuration for WCS Vendor")
  public @interface Config {
    @AttributeDefinition(name = "PDPProduct End Point", 
        description = "Please Enter the PDPProduct End point in the format"
        + "http://domain/restendpoint/${storeId}/loginservice")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xfeed/pdpfeed?storeId=STORE_ID&responseFormat=json&partnumber=PART_NUMBER&langId=LANG_ID";
  }

  @Activate
  public void activate(final Config config) {
    getPDPProductDataEndpoint = config.endPoint();
  }

  private String transform(Object obj) {
    return Optional.ofNullable(obj).orElse("").toString();
  }

}
