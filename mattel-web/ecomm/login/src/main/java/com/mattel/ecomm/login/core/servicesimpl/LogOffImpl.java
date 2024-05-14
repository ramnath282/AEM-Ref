package com.mattel.ecomm.login.core.servicesimpl;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.LogOff;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.LogOffResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import org.apache.commons.lang3.ArrayUtils;
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

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component(service = LogOff.class)
@Designate(ocd = LogOffImpl.Config.class)
public class LogOffImpl implements LogOff {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogOffImpl.class);
    private String logOffEndPoint;
    @Reference
    private PropertyReaderService propertyReaderService;

    @Override
    public LogOffResponse logOff(Map<String, Object> requestMap) throws ServiceException {
        long startTime = System.currentTimeMillis();
        LOGGER.info("Start of Log off Method");
        String storeKey = (String) requestMap.get(Constant.STORE_KEY);
        String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
        String storeId = propertyReaderService.getStoreId(storeKey);
        String domain = propertyReaderService.getCookieDomain(domainKey);
        String endPointUrl = logOffEndPoint.replaceAll("STORE_ID",storeId);
        LOGGER.debug("End Point URL is {}", endPointUrl);
        final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
        final String[] cookieNames = ArrayUtils.addAll(ServiceCookieMapping.DEFAULT.getCookieNames(),ServiceCookieMapping.LOGOUT.getCookieNames());
        final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("validCookieNames", cookieNames);
        dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
        dataMap.put("domain", domain);
        dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
        dataMap.put("requestCookie",requestCookieObjects);
        dataMap.put("validCookies",cookieNames);
        dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storeKey));
        long endTime = System.currentTimeMillis();
        LOGGER.debug(Constant.EXECUTION_TIME_LOG,"Log Off method", endTime-startTime);
        return (LogOffResponse) execute(null,dataMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
        final long startTime = System.currentTimeMillis();
        final String methodName = "Execute method of logoff";
        try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
            final List<String> reqCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
            final HttpResponse httpResponse;
            httpResponse = HttpRequestHandler.delete(httpClient,dataMap.get(Constant.END_POINT_URL_KEY).toString(),
                    reqCookies,null,httpClientContext);
            final int status = httpResponse.getStatusLine().getStatusCode();
            final List<Cookie> finalCookieList = new ArrayList<>();
            LOGGER.debug("Status of Log Off Service is {}", status);
            final boolean isError = isError(status);
            if (!isError) {
                final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
                final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
                final String domain = (String) dataMap.get("domain");
                final Cookie[] requestCookies = (Cookie[])dataMap.get("requestCookie");
                final String[] validCookieName = (String[]) dataMap.get("validCookies");
                for (Cookie cookie: requestCookies) {
                    if (CookieUtils.checkValidServiceCookie(cookie.getName(),validCookieName)){
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        cookie.setDomain(domain);
                        finalCookieList.add(cookie);
                    }
                }

                LOGGER.debug("Request Cookies are {}", requestCookies);
                LogOffResponse logOffResponse = new LogOffResponse();
                logOffResponse.setStatusCode(status);
                LOGGER.debug("Cookie are {}", cookieResheader);
                final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
                        httpClientContext.getCookieStore().getCookies());

                logOffResponse.setCookieList(buildResponseCookies(finalCookieList,cookieList));
                long endTime = System.currentTimeMillis();
                LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                logServiceErrors(logOffResponse);
                return logOffResponse;
            }else  {
                long endTime = System.currentTimeMillis();
                LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
                generalExceptionHandling(status, httpResponse.getEntity());
            }

        } catch (IOException io) {
            long endTime = System.currentTimeMillis();
            LOGGER.error("IO Exception {}", io);
            LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
        }

        return null;
    }
    private List<Cookie> buildResponseCookies(List<Cookie> finalCookieList, List<Cookie> cookieList) {
        for (Cookie cookie: cookieList) {
            if (!cookie.getName().contains("WC_USERACTIVITY") && !cookie.getName().contains("WC_AUTHENTICATION")) {
                finalCookieList.add(cookie);
            } else {
                String [] userCookieSplit = cookie.getName().split("_");
                if (userCookieSplit[2].equals("-1002")){
                    finalCookieList.add(cookie);
                }

            }
        }
        return finalCookieList;
    }
    @ObjectClassDefinition(name = "WCS LogOff Service Configuration")
    public @interface Config{
        @AttributeDefinition(name = "Log Off End Point",description = "Please Enter the LogOff End point in the format" +
                "http://domain/restendpoint/STORE_ID/serviceendpoint" )
        String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/loginidentity/@self?&responseFormat=json&updateCookies=true&storeId=STORE_ID";
    }
    @Activate
    public void activate(Config config) {
        logOffEndPoint = config.endPoint();
    }
}
