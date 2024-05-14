package com.mattel.ecomm.login.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.interfaces.EmployeeValidation;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.EmployeeValidationResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = EmployeeValidation.class)
@Designate(ocd = EmployeeValidationImpl.Config.class)
public class EmployeeValidationImpl implements EmployeeValidation {

	private static final String END_POINT_URL_KEY = "endPointUrl";
	private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
	private static final String EMP_ID_PLACEHOLDER = "EMP_ID";
	private static final String CATALOG_ID_PLACEHOLDER = "CATALOG_ID";
	private static final String USER_ID_PLACEHOLDER = "USER_ID";
	private static final String EXECUTE_SERVICE = "execute";
	private static final String EMPLOYEE_VALIDATION_SERVICE = "Employee Validation Service";
	@Reference
	PropertyReaderService propertyReaderService;
	private String employeeValidationEndpoint;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeValidationImpl.class);
	private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(EmployeeValidationResponse.class);

	@Override
	public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
		 final long startTime = System.currentTimeMillis();

	        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
	            @SuppressWarnings("unchecked")
				final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
	            final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
	            final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
	                    dataMap.get(EmployeeValidationImpl.END_POINT_URL_KEY).toString(), requestCookies, null, httpClientContext);
	            final int status = httpResponse.getStatusLine().getStatusCode();

	            EmployeeValidationImpl.LOGGER.debug("Response status is {}", status);

	            if (null != httpResponse.getEntity() && !isError(status)) {
	                try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
	                    final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

	                    EmployeeValidationResponse employeeValidationResponse;
	                    employeeValidationResponse = RESP_READER.readValue(inputStream);
	                    if (null != cookieResheader && cookieResheader.length > 0) {
	                        final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
	                        final String domain = (String) dataMap.get("domain");
	                        final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());
	                        employeeValidationResponse.setCookieList(cookieList);
	                    }

	                    EmployeeValidationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, EmployeeValidationImpl.EXECUTE_SERVICE,
	                            (System.currentTimeMillis() - startTime));
	                    return employeeValidationResponse;
	                }
	            } else {
	                final long endTime = System.currentTimeMillis();

	                EmployeeValidationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, EmployeeValidationImpl.EXECUTE_SERVICE,
	                        endTime - startTime);
	                generalExceptionHandling(status);
	            }
	        } catch (final IOException io) {
	            final long endTime = System.currentTimeMillis();

	            EmployeeValidationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, EmployeeValidationImpl.EXECUTE_SERVICE,
	                    endTime - startTime);
	            EmployeeValidationImpl.LOGGER.error("Encountered error:", io);
	            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
	        }

	        final long endTime = System.currentTimeMillis();
	        EmployeeValidationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, EmployeeValidationImpl.EXECUTE_SERVICE,
	                endTime - startTime);
	        return null;
	}

	@Override
	public EmployeeValidationResponse validateEmployee(Map<String, Object> requestMap) throws ServiceException {
		LOGGER.info("{} - Start", EmployeeValidationImpl.EMPLOYEE_VALIDATION_SERVICE);
		final long startTime = System.currentTimeMillis();
		final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
		final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
		final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
		final String storekey = (String) requestMap.get(Constant.STORE_KEY);
		final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
		final String storeId = propertyReaderService.getStoreId(storekey);
		final String domain = propertyReaderService.getCookieDomain(domainKey);
		final String employeeId = (String) requestMap.get("employeeId");
		final String catalogId = (String) requestMap.get("catalogId");
		final String userId = (String) requestMap.get("userId");
		final Map<String, Object> dataMap = new HashMap<>();
		String endPointUrl = employeeValidationEndpoint.replaceAll(EmployeeValidationImpl.STORE_ID_PLACEHOLDER,
				storeId);
		endPointUrl = endPointUrl.replaceAll(EmployeeValidationImpl.EMP_ID_PLACEHOLDER, employeeId);
		endPointUrl = endPointUrl.replaceAll(EmployeeValidationImpl.CATALOG_ID_PLACEHOLDER, catalogId);
		endPointUrl = endPointUrl.replaceAll(EmployeeValidationImpl.USER_ID_PLACEHOLDER, userId);
		LOGGER.debug("Request Endpoint : {}", endPointUrl);
		final long endTime;

		dataMap.put("validCookieNames", cookieNames);
		dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
		dataMap.put("domain", domain);
		dataMap.put(EmployeeValidationImpl.END_POINT_URL_KEY, endPointUrl);
		endTime = System.currentTimeMillis();

		EmployeeValidationImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
				EmployeeValidationImpl.EMPLOYEE_VALIDATION_SERVICE, endTime - startTime);
		return (EmployeeValidationResponse) execute(null, dataMap);
	}

	@ObjectClassDefinition(name = "WCS Employee Validation Configurations", description = "Employee Validation Configuration for WCS Vendor")
	public @interface Config {
		@AttributeDefinition(name = "Employee Validation End Point", description = "Please Enter the Employee Validation End point in the format"
				+ "http://domain/restendpoint/STORE_ID/serviceendpoint")
		String endPoint() default "https://api.sdn.mattel.com/QA/store/STORE_ID/EmpVerification/verify?employeeId=EMP_ID&responseFormat=json&updateCookies=true&catalogId=CATALOG_ID&userId=USER_ID&api_key=avxa9d9tt35fpn2a8hbuhjj9";
	}

	@Activate
	public void activate(final Config config) {
		employeeValidationEndpoint = config.endPoint();
	}

}
