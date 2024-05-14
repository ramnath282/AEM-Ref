package com.mattel.ecomm.shippingmodes.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ShippingModes;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ShippingModesRequest;
import com.mattel.ecomm.coreservices.core.pojos.ShippingModesResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.Cookie;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
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

@Component(service = ShippingModes.class)
@Designate(ocd = ShippingModesImpl.Config.class)
public class ShippingModesImpl implements ShippingModes {
  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
  private static final Logger LOGGER = LoggerFactory.getLogger(ShippingModesImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(ShippingModesResponse.class);
  @Reference
  PropertyReaderService propertyReaderService;

  private String shippingModesEndPoint;

  @SuppressWarnings({ "unchecked" })
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final String methodName = "execute";
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

      final HttpGet getMethod = new HttpGet(
          dataMap.get(ShippingModesImpl.END_POINT_URL_KEY).toString());
      final List<String> reqCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
      if (!reqCookies.isEmpty()) {
        reqCookies.forEach(reqCookie -> {
          getMethod.addHeader("Cookie", reqCookie);
          ShippingModesImpl.LOGGER
              .debug("String value of Cookie object added in request header is {}", reqCookie);
        });
      }
      final HttpClientContext httpContext = HttpRequestHandler.buildHttpContext();
      final HttpResponse httpResponse = httpClient.execute(getMethod, httpContext);
      final int status = httpResponse.getStatusLine().getStatusCode();
      ShippingModesImpl.LOGGER.debug("Status is {}", status);
      final Header responseHeaderAttribute = getMethod.getFirstHeader("content-encoding");
      String responseEncodingType = null;

      final Optional<Header> optResponseHeaderAttribute = Optional
          .ofNullable(responseHeaderAttribute);
      if (optResponseHeaderAttribute.isPresent()) {
        responseEncodingType = optResponseHeaderAttribute.get().getValue();
        ShippingModesImpl.LOGGER.debug("Content encoding type {}", responseEncodingType);
      }
      InputStream inputStream = httpResponse.getEntity().getContent();
      ShippingModesResponse shippingModesResponse = null;
      final boolean isError = isError(status);
      if (!isError) {
        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
        final String domain = (String) dataMap.get("domain");
        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
            httpContext.getCookieStore().getCookies());
        if ("gzip".equals(responseEncodingType)) {
          inputStream = new GZIPInputStream(inputStream);
          shippingModesResponse = ShippingModesImpl.RESP_READER.readValue(inputStream);
        } else {
          shippingModesResponse = ShippingModesImpl.RESP_READER.readValue(inputStream);
        }

        final Optional<ShippingModesResponse> optShippingModesResponse = Optional
            .ofNullable(shippingModesResponse);
        if (optShippingModesResponse.isPresent()) {
          optShippingModesResponse.get().setCookieList(cookieList);
        }

      } else {
        final long endTime = System.currentTimeMillis();
        ShippingModesImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Recent order History execute",
            endTime - startTime);
        generalExceptionHandling(status);
      }
      final long endTime = System.currentTimeMillis();
      ShippingModesImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
      return shippingModesResponse;
    } catch (final IOException io) {
      final long endTime = System.currentTimeMillis();
      ShippingModesImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
      ShippingModesImpl.LOGGER.error("IO Exception Occured:", io);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }
  }

  @Override
  public ShippingModesResponse getShippingModes(Map<String, Object> requestMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final String methodName = "getShippingModes";
    final ShippingModesRequest shippingModesRequest = new ShippingModesRequest();
    final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
    final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
    final List<String> reqCookies = CookieUtils.getRequestCookieList(requestCookieObjects,
        cookieNames);
    final String storekey = (String) requestMap.get(Constant.STORE_KEY);
    final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
    final String storeId = propertyReaderService.getStoreId(storekey);
    final String domain = propertyReaderService.getCookieDomain(domainKey);
    final String endPointUrl = shippingModesEndPoint
        .replaceAll(ShippingModesImpl.STORE_ID_PLACEHOLDER, storeId);
    final Map<String, Object> dataMap = new HashMap<>();
    dataMap.put("validCookieNames", cookieNames);
    dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
    dataMap.put("domain", domain);
    dataMap.put(ShippingModesImpl.END_POINT_URL_KEY, endPointUrl);
    final long endTime = System.currentTimeMillis();
    ShippingModesImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
    return (ShippingModesResponse) execute(shippingModesRequest, dataMap);
  }

  public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
    this.propertyReaderService = propertyReaderService;
  }

  public void setShippingModesEndPoint(String shippingModesEndPoint) {
    this.shippingModesEndPoint = shippingModesEndPoint;
  }

  @Activate
  public void activate(final Config config) {
    shippingModesEndPoint = config.endPoint();
  }

  @ObjectClassDefinition(name = "Shipping Modes Configurations", description = "Shipping Modes Configuration for WCS Vendor")
  public @interface Config {

    @AttributeDefinition(name = "Shipping Modes Endpoint", description = "Please Enter the Shipping Modes End point in the format"
        + "http://domain/restendpoint/${storeId}/shippingModesService")
    String endPoint() default "https://mdev.americangirl.com/wcs/resources/store/STORE_ID/cart/shipping_modes?updateCookies=true&storeId=STORE_ID&responseFormat=json";

  }
}
