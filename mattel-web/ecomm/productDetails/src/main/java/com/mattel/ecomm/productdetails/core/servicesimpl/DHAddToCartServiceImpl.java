package com.mattel.ecomm.productdetails.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.mattel.ecomm.coreservices.core.interfaces.DHAddToCartService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.DHAddCartResponse;
import com.mattel.ecomm.coreservices.core.pojos.DHAddToCartRequest;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = DHAddToCartService.class)
@Designate(ocd = DHAddToCartServiceImpl.Config.class)
public class DHAddToCartServiceImpl implements DHAddToCartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DHAddToCartServiceImpl.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(DHAddToCartRequest.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(DHAddCartResponse.class);
    @Reference
    PropertyReaderService propertyReaderService;
    
    private String dhAddToCartServiceEndpoint;

    @SuppressWarnings("unchecked")
    @Override
    public DHAddCartResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        LOGGER.info("DHAddToCartService execute start");
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
            final DHAddToCartRequest dhAddToCartRequest = (DHAddToCartRequest) baseRequest;
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final Map<String, String> requestHeaders = new HashMap<>();
            final HttpResponse httpResponse;
            final int status;

            requestHeaders.put("Accept", Constant.CONTENT_TYPE_APPLICATION_JSON);
            requestHeaders.put("Content-type", Constant.CONTENT_TYPE_APPLICATION_JSON);
            httpResponse = HttpRequestHandler.post(httpClient, dataMap.get(Constant.END_POINT_URL_KEY).toString(),
                    requestCookies, requestHeaders, dhAddToCartRequest, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();

            DHAddToCartServiceImpl.LOGGER.debug("Response status is {}", status);

            if (!isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    final DHAddCartResponse dhAddCartResponse = DHAddToCartServiceImpl.RESP_READER
                            .readValue(inputStream);

                    if (null != cookieResheader && cookieResheader.length > 0) {
                        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                        final String domain = (String) dataMap.get("domain");
                        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                                httpClientContext.getCookieStore().getCookies());

                        dhAddCartResponse.setCookieList(cookieList);
                    }

                    DHAddToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                            System.currentTimeMillis() - startTime);
                    return dhAddCartResponse;
                }
            } else {
                DHAddToCartServiceImpl.LOGGER.error("Recieved response with error status: {} in {}", status,
                        Constant.EXECUTE_SERVICE);
                DHAddToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                        System.currentTimeMillis() - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            DHAddToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                    endTime - startTime);
            DHAddToCartServiceImpl.LOGGER.error("Encountered error:", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        return null;
    }

    @Override
    public DHAddCartResponse addToCart(Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        DHAddToCartServiceImpl.LOGGER.info("DHAddToCartService addToCart start");
        try {
            final DHAddToCartRequest dhAddToCartRequest = DHAddToCartServiceImpl.REQ_READER
                    .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
            final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
            final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            final String endPointUrl = dhAddToCartServiceEndpoint.replaceAll(Constant.STORE_ID_PLACEHOLDER, storeId);
            final Map<String, Object> dataMap = new HashMap<>();
            final long endTime;

            dataMap.put("validCookieNames", cookieNames);
            dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
            dataMap.put("domain", domain);
            dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
            dataMap.put(Constant.DEF_CONNECT_TIMEOUT,
                    getDefaultConnectTimeout(propertyReaderService, storekey));
            endTime = System.currentTimeMillis();

            DHAddToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "DH Add To Cart service",
                    endTime - startTime);
            DHAddToCartServiceImpl.LOGGER.info("DHAddToCartService addToCart End");

            return execute(dhAddToCartRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            DHAddToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "DH Add To Cart service",
                    endTime - startTime);
            DHAddToCartServiceImpl.LOGGER.error("Error encountered: ", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

    @ObjectClassDefinition(name = "DH Add to Cart Service Configurations", description = "DH Add to Cart Service Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "DH Add to Cart Service End Point", description = "Please Enter the Add to Cart Service End point in the format"
                + "http://domain/wcs/resources/store/10651/xupdatecart/addItemWithAddOns?responseFormat=json&updateCookies=true&storeId=STORE_ID")
        String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/10651/xupdatecart/addItemWithAddOns?responseFormat=json&updateCookies=true&storeId=STORE_ID";
    }

    @Activate
    public void activate(final Config config) {
        dhAddToCartServiceEndpoint = config.endPoint();
    }
}