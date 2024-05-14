package com.mattel.ecomm.contactpreferences.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.interfaces.ContactPreferences;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ContactPreferencesRequest;
import com.mattel.ecomm.coreservices.core.pojos.ContactPreferencesResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

/**
 * @author CTS
 *
 */
@Component(service = ContactPreferences.class)
@Designate(ocd = ContactPreferencesImpl.Config.class)
public class ContactPreferencesImpl implements ContactPreferences {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactPreferencesImpl.class);
	private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(ContactPreferencesResponse.class);
	private static final String END_POINT_URL_KEY = "endPointUrl";

	@Reference
	PropertyReaderService propertyReaderService;

	private String contactPreferencesEndPoint;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mattel.ecomm.coreservices.core.interfaces.BaseService#execute(com.
	 * mattel.ecomm.coreservices.core.pojos.BaseRequest,
	 * com.mattel.ecomm.coreservices.core.pojos.BaseResponse, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
		long startTime = System.currentTimeMillis();
		LOGGER.info("ContactPreferencesImpl Execute Start");

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
			final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
			final HttpResponse httpResponse = HttpRequestHandler.get(httpClient,
	                    dataMap.get(ContactPreferencesImpl.END_POINT_URL_KEY).toString(), requestCookies, null, httpClientContext);
			
			
			int status = httpResponse.getStatusLine().getStatusCode();
			LOGGER.debug("Status is {}", status);

			InputStream inputStream = httpResponse.getEntity().getContent();
			ContactPreferencesResponse contactPreferencesResponse = null;
			boolean isError = isError(status);
			if (!isError) {
				Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");
				String[] cookieNames = (String[]) dataMap.get("validCookieNames");
				String domain = (String) dataMap.get("domain");
				LOGGER.debug("Cookies are {}", cookieResheader);
				List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());
				contactPreferencesResponse = RESP_READER.readValue(inputStream);
				contactPreferencesResponse.setCookieList(cookieList);
			} else {
				long endTime = System.currentTimeMillis();
				LOGGER.debug(Constant.EXECUTION_TIME_LOG, "ContactPreferencesImpl execute", endTime - startTime);
				generalExceptionHandling(status);
			}
			long endTime = System.currentTimeMillis();
			LOGGER.debug(Constant.EXECUTION_TIME_LOG, "execute ", endTime - startTime);
			LOGGER.debug("ContactPreferencesImpl Responce : {} ", contactPreferencesResponse);
			return contactPreferencesResponse;
		} catch (IOException io) {
			long endTime = System.currentTimeMillis();
			LOGGER.debug(Constant.EXECUTION_TIME_LOG, "execute IOException", endTime - startTime);
			LOGGER.error("IO Exception Occured:", io);
			throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mattel.ecomm.coreservices.core.interfaces.ContactPreferences#
	 * getContactPreferences(java.util.Map)
	 */
	@Override
	public ContactPreferencesResponse getContactPreferences(Map<String, Object> requestMap) throws ServiceException {
		long startTime = System.currentTimeMillis();
		LOGGER.info("ContactPreferencesImpl getContactPreferences Start");

		ContactPreferencesRequest contactPreferencesRequest = new ContactPreferencesRequest();

		Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
		String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
		List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
		String storekey = (String) requestMap.get(Constant.STORE_KEY);
		String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
		String storeId = propertyReaderService.getStoreId(storekey);
		String domain = propertyReaderService.getCookieDomain(domainKey);

		final String endPointUrl = contactPreferencesEndPoint.replaceAll("STORE_ID", storeId);
		
		LOGGER.debug("WCS endPointUrl : {}", endPointUrl);
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put(ContactPreferencesImpl.END_POINT_URL_KEY, endPointUrl);
		dataMap.put("validCookieNames", cookieNames);
		dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
		dataMap.put("domain", domain);
		dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);

		long endTime = System.currentTimeMillis();
		LOGGER.info(Constant.EXECUTION_TIME_LOG, "execute", endTime - startTime);
		return (ContactPreferencesResponse) execute(contactPreferencesRequest, dataMap);
	}

	@ObjectClassDefinition(name = "WCS ContactPreferences Configurations", description = "ContactPreferences Configuration for WCS Vendor")
	public @interface Config {
		@AttributeDefinition(name = "ContactPreferences End Point", description = "Please Enter the ContactPreferences End point in the format"
				+ "http://domain/restendpoint/${storeId}/xEmailPreference/xGetEmailPreference")
		String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xEmailPreference/xGetEmailPreference?responseFormat=json";
	}

	@Activate
	public void activate(final Config config) {
		contactPreferencesEndPoint = config.endPoint();
	}

}
