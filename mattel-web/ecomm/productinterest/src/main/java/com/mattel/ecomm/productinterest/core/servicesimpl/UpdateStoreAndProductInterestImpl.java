package com.mattel.ecomm.productinterest.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateStoreAndProductInterest;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.UpdateStoreAndProductInterestRequest;
import com.mattel.ecomm.coreservices.core.pojos.UpdateStoreAndProductInterestResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
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

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component(service = UpdateStoreAndProductInterest.class)
@Designate(ocd = UpdateStoreAndProductInterestImpl.Config.class)
public class UpdateStoreAndProductInterestImpl implements UpdateStoreAndProductInterest {
    @Reference
    PropertyReaderService propertyReaderService;
    private String updateProductInterestEndPoint;
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateStoreAndProductInterestImpl.class);
    private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(UpdateStoreAndProductInterestRequest.class);
    @Override
    public UpdateStoreAndProductInterestResponse updateInterests(Map<String, Object> requestMap) throws ServiceException {
        try {
            UpdateStoreAndProductInterestRequest updateStoreAndProductInterestRequest = REQ_READER.readValue(
                    requestMap.get(Constant.REQUEST_BODY).toString());
            Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
            String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
            List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
            String storekey = (String) requestMap.get(Constant.STORE_KEY);
            String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
            String storeId = propertyReaderService.getStoreId(storekey);
            String domain = propertyReaderService.getCookieDomain(domainKey);
            final String updateInterestEndPoint = updateProductInterestEndPoint.replaceAll("STORE_ID", storeId);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("validCookieNames", cookieNames);
            dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
            dataMap.put("domain", domain);
            dataMap.put(END_POINT_URL_KEY, updateInterestEndPoint);
            return (UpdateStoreAndProductInterestResponse) execute(updateStoreAndProductInterestRequest, dataMap);
        } catch (final IOException e) {
            LOGGER.error("IO Exception occured {}", e);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            UpdateStoreAndProductInterestRequest updateStoreAndProductInterestRequest = (UpdateStoreAndProductInterestRequest) baseRequest;
            final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final Map<String, String> requestHeaders = new HashMap<>();
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse;
            final int status;
            requestHeaders.put("Accept", "application/json");
            requestHeaders.put("Content-type", "application/json");
            httpResponse = HttpRequestHandler.put(httpClient, dataMap.get(END_POINT_URL_KEY).toString(), requestCookies, requestHeaders, updateStoreAndProductInterestRequest, httpClientContext);
            status = httpResponse.getStatusLine().getStatusCode();
            LOGGER.debug("Status Code from Update Product Interest is {}", status);
            if (!isError(status)){
                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                    UpdateStoreAndProductInterestResponse updateStoreAndProductInterestResponse = new UpdateStoreAndProductInterestResponse();
                    updateStoreAndProductInterestResponse.setUpdateStatus(status);
                    LOGGER.debug("Update Product Interest Response is {}",updateStoreAndProductInterestResponse);
                    if (null != cookieResheader && cookieResheader.length > 0) {
                        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                        final String domain = (String) dataMap.get("domain");
                        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());

                        updateStoreAndProductInterestResponse.setCookieList(cookieList);
                    }
                    return updateStoreAndProductInterestResponse;


            } else {
                generalExceptionHandling(status);
            }
            return null;
        } catch (IOException io) {
            LOGGER.error("IO Exception Occured {}", io);
            throw new ServiceException(Constant.IO_ERROR_KEY,"Io Exception from Method Body");
        }
    }
    @ObjectClassDefinition(name = "Update Store And Product Interests Configurations", description = "All configurations for " +
            "Updating store and product interest for WCS vendors goes here")
    public @interface Config{
        @AttributeDefinition(name = "WCS Update Store And Product Interest End point", description = "End Point Configuration for" +
                "WCS Update Store and Product Interest")
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xpersonalInterest/xUpdateInterests?" +
                "storeId=STORE_ID&responseFormat=json&updateCookies=true";

    }
    @Activate
    public void activate(Config config) {
        updateProductInterestEndPoint = config.endPoint();
    }
}
