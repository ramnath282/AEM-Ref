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
import org.apache.http.impl.client.HttpClients;
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

import com.mattel.ecomm.coreservices.core.interfaces.AddTrunkToCartService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.TrunkCartResponse;
import com.mattel.ecomm.coreservices.core.pojos.AddTrunkToCartRequest;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = AddTrunkToCartService.class)
@Designate(ocd = AddTrunkToCartServiceImpl.Config.class)
public class AddTrunkToCartServiceImpl implements AddTrunkToCartService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddTrunkToCartServiceImpl.class);
    private static final ObjectReader REQ_READER = ResourceMapper
            .getReaderInstance(AddTrunkToCartRequest.class);
    private static final ObjectReader RESP_READER = ResourceMapper
            .getReaderInstance(TrunkCartResponse.class);
   @Reference
    PropertyReaderService propertyReaderService;
    private String addTrunkToCartServiceEndpoint;

    @SuppressWarnings("unchecked")
    @Override
    public TrunkCartResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
            throws ServiceException {
        final long startTime = System.currentTimeMillis();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            final AddTrunkToCartRequest addTrunkToCartRequest = (AddTrunkToCartRequest) baseRequest;
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final Map<String, String> requestHeaders = new HashMap<>();
            final HttpResponse httpResponse;
            final int status;

            requestHeaders.put("Accept", Constant.CONTENT_TYPE_APPLICATION_JSON);
            requestHeaders.put("Content-type", Constant.CONTENT_TYPE_APPLICATION_JSON);
            httpResponse = HttpRequestHandler.post(httpClient, dataMap.get(Constant.END_POINT_URL_KEY).toString(),
                    requestCookies, requestHeaders, addTrunkToCartRequest, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();

            AddTrunkToCartServiceImpl.LOGGER.debug("Response status is {}", status);

            if (!isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    final TrunkCartResponse trunkCartResponse = AddTrunkToCartServiceImpl.RESP_READER
                            .readValue(inputStream);

                    if (null != cookieResheader && cookieResheader.length > 0) {
                        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                        final String domain = (String) dataMap.get("domain");
                        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                                httpClientContext.getCookieStore().getCookies());

                        trunkCartResponse.setCookieList(cookieList);
                    }

                    AddTrunkToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                            System.currentTimeMillis() - startTime);
                    return trunkCartResponse;
                }
            } else {
                AddTrunkToCartServiceImpl.LOGGER.error("Recieved response with error status: {} in {}", status,
                        Constant.EXECUTE_SERVICE);
                AddTrunkToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                        System.currentTimeMillis() - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            AddTrunkToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                    endTime - startTime);
            AddTrunkToCartServiceImpl.LOGGER.error("Encountered error:", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        return null;
    }

    @Override
    public TrunkCartResponse addToCart(Map<String, Object> requestMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
		 AddTrunkToCartServiceImpl.LOGGER.info("Start of addToCart {}", getClass().getSimpleName());
        try {
            final AddTrunkToCartRequest addTrunkToCartRequest = AddTrunkToCartServiceImpl.REQ_READER
                    .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
            final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
            final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
            final String storekey = (String) requestMap.get(Constant.STORE_KEY);
            final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            final String storeId = propertyReaderService.getStoreId(storekey);
            final String domain = propertyReaderService.getCookieDomain(domainKey);
            final String endPointUrl = addTrunkToCartServiceEndpoint.replaceAll(Constant.STORE_ID_PLACEHOLDER,
                    storeId);
            final Map<String, Object> dataMap = new HashMap<>();
            final long endTime;

            dataMap.put("validCookieNames", cookieNames);
            dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
            dataMap.put("domain", domain);
            dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
            endTime = System.currentTimeMillis();

            AddTrunkToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    "Trunk Add To Cart service", endTime - startTime);
            return execute(addTrunkToCartRequest, dataMap);
        } catch (final IOException e) {
            final long endTime = System.currentTimeMillis();

            AddTrunkToCartServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
                    "Trunk Add To Cart service", endTime - startTime);
            AddTrunkToCartServiceImpl.LOGGER.error("Error encountered: ", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }
    }

   @ObjectClassDefinition(name = "WCS Add Trunk to Cart Service Configurations", description = "Add Trunk to Cart Service Configuration for WCS Vendor")
    public @interface Config {
        @AttributeDefinition(name = "Add Trunk to Cart Service End Point", description = "Please Enter the Add Trunk to Cart Service End point in the format"
                + "http://domain/restendpoint/${storeId}//xcart/@self/mattel_add_configuration_to_cart")
        String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xcart/@self/mattel_add_configuration_to_cart?storeId=STORE_ID&responseFormat=json&updateCookies=true";
    }

    @Activate
    public void activate(final Config config) {
        addTrunkToCartServiceEndpoint = config.endPoint();
    }
}
