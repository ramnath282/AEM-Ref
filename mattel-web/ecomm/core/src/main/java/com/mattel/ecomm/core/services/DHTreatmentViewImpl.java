package com.mattel.ecomm.core.services;

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
import com.mattel.ecomm.coreservices.core.interfaces.DHTreatmentViewService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.DHTreatmentViewResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = DHTreatmentViewService.class)
@Designate(ocd = DHTreatmentViewImpl.Config.class)
public class DHTreatmentViewImpl implements DHTreatmentViewService {

    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
    private static final String EXECUTE_SERVICE = "execute";
    private static final Logger LOGGER = LoggerFactory.getLogger(DHTreatmentViewImpl.class);
    private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(DHTreatmentViewResponse.class);
    @Reference
    PropertyReaderService propertyReaderService;
    private String getDHServiceDataEndpoint;

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        DHTreatmentViewImpl.LOGGER.info("{} - start", DHTreatmentViewImpl.EXECUTE_SERVICE);
        final long startTime = System.currentTimeMillis();
        try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
                    dataMap.get(DHTreatmentViewImpl.END_POINT_URL_KEY).toString(), requestCookies, null,
                    httpClientContext);
            final int status = httpResponse.getStatusLine().getStatusCode();
            DHTreatmentViewImpl.LOGGER.debug("Response status is {}", status);
            if (null != httpResponse.getEntity() && !isError(status)) {
                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    DHTreatmentViewResponse dhTreatmentViewResponse;
                    dhTreatmentViewResponse = DHTreatmentViewImpl.RESP_READER.readValue(inputStream);
                    if (null != cookieResheader && cookieResheader.length > 0) {
                        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                        final String domain = (String) dataMap.get("domain");
                        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                                httpClientContext.getCookieStore().getCookies());
                        dhTreatmentViewResponse.setCookieList(cookieList);
                    }
                    DHTreatmentViewImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, DHTreatmentViewImpl.EXECUTE_SERVICE,
                            (System.currentTimeMillis() - startTime));
                    return dhTreatmentViewResponse;
                }
            } else {
                final long endTime = System.currentTimeMillis();
                DHTreatmentViewImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, DHTreatmentViewImpl.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status);
            }
        } catch (final IOException io) {
            final long endTime = System.currentTimeMillis();
            DHTreatmentViewImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, DHTreatmentViewImpl.EXECUTE_SERVICE,
                    endTime - startTime);
            DHTreatmentViewImpl.LOGGER.error("Encountered error:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }
        final long endTime = System.currentTimeMillis();
        DHTreatmentViewImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, DHTreatmentViewImpl.EXECUTE_SERVICE,
                endTime - startTime);
        return null;
    }

    @Override
    public DHTreatmentViewResponse fetch(Map<String, Object> requestMap) throws ServiceException {
        DHTreatmentViewImpl.LOGGER.info("fetch - start");
        final long startTime = System.currentTimeMillis();
        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
        final String storekey = (String) requestMap.get(Constant.STORE_KEY);
        final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        final String storeId = propertyReaderService.getStoreId(storekey);
        final String domain = propertyReaderService.getCookieDomain(domainKey);
        final Map<String, Object> dataMap = new HashMap<>();
        String endPointUrl = getDHServiceDataEndpoint.replaceAll(DHTreatmentViewImpl.STORE_ID_PLACEHOLDER, storeId);
        DHTreatmentViewImpl.LOGGER.debug("endPointUrl is {}", endPointUrl);
        final long endTime;
        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put("domain", domain);
        dataMap.put(DHTreatmentViewImpl.END_POINT_URL_KEY, endPointUrl);
        dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
        endTime = System.currentTimeMillis();
        DHTreatmentViewImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "DHCategoryViewImpl", endTime - startTime);
        return (DHTreatmentViewResponse) execute(null, dataMap);
    }

    @ObjectClassDefinition(name = "DH Category view configurations", description = "DH Category view configurations")
    public @interface Config {
        @AttributeDefinition(name = "DH Category View EndPoint", description = "Please Enter the Doll Hospital Category View End point in the format"
                + "http://domain/restendpoint/${storeId}/loginservice")
        String endPoint() default "https://mqa.services.commerce.mattel.com/search/resources/store/STORE_ID/productview/byCategory/88601";
    }

    @Activate
    public void activate(final Config config) {
        getDHServiceDataEndpoint = config.endPoint();
    }

}
