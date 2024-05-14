package com.mattel.ecomm.productinterest.core.servicesimpl;

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
import org.apache.sling.commons.metrics.MetricsService;
import org.apache.sling.commons.metrics.Timer;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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
import com.mattel.ecomm.coreservices.core.interfaces.ProductInterest;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ProductInterestResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = ProductInterest.class)
@Designate(ocd = ProductInterestImpl.Config.class)
public class ProductInterestImpl implements ProductInterest {
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductInterestImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(ProductInterestResponse.class);
    @Reference
    PropertyReaderService propertyReaderService;
    @Reference
    private MetricsService metricsService;
    private Timer wcsTimer;
    private Timer aemTimer;

    private String productInterestEndPoint;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        final String methodName = "execute";
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
            final List<String> reqCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final Timer.Context wcsContext = wcsTimer.time();
            final HttpResponse httpResponse;
            
            try {
            final long wcsExecutionStartTime = System.currentTimeMillis();
            httpResponse = HttpRequestHandler.get(httpClient,
                    dataMap.get(ProductInterestImpl.END_POINT_URL_KEY).toString(), reqCookies, null, httpClientContext);
            final long wcsExecutionEndTime = System.currentTimeMillis();
            ProductInterestImpl.LOGGER.debug("WCS Execution Time for product Interest is {}",
                    wcsExecutionEndTime - wcsExecutionStartTime);
            } finally {
            wcsContext.stop();
            }

            final int status = httpResponse.getStatusLine().getStatusCode();
            ProductInterestImpl.LOGGER.debug("Status is {}", status);
            final InputStream inputStream = httpResponse.getEntity().getContent();
            ProductInterestResponse productInterestResponse = null;
            final boolean isError = isError(status);
            if (!isError) {
                final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                final String domain = (String) dataMap.get("domain");
                ProductInterestImpl.LOGGER.debug("Cookies are {}", cookieResheader);
                final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                        httpClientContext.getCookieStore().getCookies());
                productInterestResponse = ProductInterestImpl.RESP_READER.readValue(inputStream);
                productInterestResponse.setCookieList(cookieList);
                ProductInterestImpl.LOGGER.debug("Product Interest Response is {}", productInterestResponse);
            } else {
                final long endTime = System.currentTimeMillis();
                ProductInterestImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Product Interest execute",
                        endTime - startTime);
                generalExceptionHandling(status);
            }
            final long endTime = System.currentTimeMillis();
            ProductInterestImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            return productInterestResponse;
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();
            ProductInterestImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            ProductInterestImpl.LOGGER.error("IO Exception Occured:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }
    }

    @Override
    public ProductInterestResponse getProductInterest(Map<String, Object> requestMap) throws ServiceException {
        final Timer.Context aemContext = aemTimer.time();
        
        try {
        final long startTime = System.currentTimeMillis();
        final String methodName = "getProductInterest";
        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
        final String storekey = (String) requestMap.get(Constant.STORE_KEY);
        final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        final String storeId = propertyReaderService.getStoreId(storekey);
        final String domain = propertyReaderService.getCookieDomain(domainKey);
        final String endPointUrl = productInterestEndPoint.replaceAll(ProductInterestImpl.STORE_ID_PLACEHOLDER,
                storeId);
        final Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put("domain", domain);
        dataMap.put(ProductInterestImpl.END_POINT_URL_KEY, endPointUrl);
        final long endTime = System.currentTimeMillis();
        ProductInterestImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
        return (ProductInterestResponse) execute(null, dataMap);
        } finally {
            aemContext.stop();
        }
    }

    @Activate
    public void activate(final Config config) {
        productInterestEndPoint = config.endPoint();
        long currentTimeInMillis = System.currentTimeMillis();
        aemTimer = metricsService.timer("productinterest-aem-" + currentTimeInMillis);
        wcsTimer = metricsService.timer("productinterest-wcs-" + currentTimeInMillis);
    }

    @Deactivate
    public void deactivate () {
        aemTimer = null;
        wcsTimer = null;
    }

    @ObjectClassDefinition(name = "Product Interest Configurations", description = "Product Interest Configuration for WCS Vendor")
    public @interface Config {

        @AttributeDefinition(name = "Product Interest Endpoint", description = "Please Enter the Product Interest End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xpersonalInterest/xProductInterest?updateCookies=true&storeId=STORE_ID&profileName=xProductInterest&responseFormat=json";

    }
}
