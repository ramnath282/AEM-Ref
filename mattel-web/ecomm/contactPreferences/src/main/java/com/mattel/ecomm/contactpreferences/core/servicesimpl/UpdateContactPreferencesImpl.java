package com.mattel.ecomm.contactpreferences.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.UpdateContactPreferences;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.UpdateContactPreferencesRequest;
import com.mattel.ecomm.coreservices.core.pojos.UpdateContactPreferencesResponse;
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
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = UpdateContactPreferences.class)
@Designate(ocd = UpdateContactPreferencesImpl.Config.class)
public class UpdateContactPreferencesImpl implements UpdateContactPreferences {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateContactPreferencesImpl.class);
	private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(UpdateContactPreferencesResponse.class);
	private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(UpdateContactPreferencesRequest.class);

	@Reference
	PropertyReaderService propertyReaderService;
	private String updateContactPreferences;

	@Override
	public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
			throws ServiceException {
		final long startTime = System.currentTimeMillis();
		LOGGER.info("UpdateContactPreferencesImpl execute - start");

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			final UpdateContactPreferencesRequest updateContactPreferencesRequest = (UpdateContactPreferencesRequest) baseRequest;
			@SuppressWarnings("unchecked")
			final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
			final Map<String, String> requestHeaders = new HashMap<>();
			HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
			final HttpResponse httpResponse;
			final int status;

			requestHeaders.put("Accept", "application/json");
			requestHeaders.put("Content-type", "application/json");
			httpResponse = HttpRequestHandler.put(httpClient, dataMap.get(Constant.END_POINT_URL_KEY).toString(),
					requestCookies, requestHeaders, updateContactPreferencesRequest, httpClientContext);

			status = httpResponse.getStatusLine().getStatusCode();
			LOGGER.debug("Response status is {}", status);
			if (null != httpResponse.getEntity() && !isError(status)) {
				try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
					UpdateContactPreferencesResponse updateContactPreferencesResponse = new UpdateContactPreferencesResponse();
					final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

					if (null != cookieResheader && cookieResheader.length > 0) {
						final String[] cookieNames = (String[]) dataMap.get("validCookieNames");
						final String domain = (String) dataMap.get("domain");
						final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, httpClientContext.getCookieStore().getCookies());
						updateContactPreferencesResponse = RESP_READER.readValue(inputStream);
						updateContactPreferencesResponse.setResponse(Integer.toString(status));
						updateContactPreferencesResponse.setCookieList(cookieList);
					}
					final long endTime = System.currentTimeMillis();
					LOGGER.debug(Constant.EXECUTION_TIME_LOG, "execute ", endTime - startTime);
					return updateContactPreferencesResponse;
				}
			} else {
				final long endTime = System.currentTimeMillis();
				LOGGER.debug(Constant.EXECUTION_TIME_LOG, "execute", endTime - startTime);
				generalExceptionHandling(status);
			}
		} catch (IOException io) {
			final long endTime = System.currentTimeMillis();
			LOGGER.debug(Constant.EXECUTION_TIME_LOG, "ioexception", endTime - startTime);
			LOGGER.error("Encountered error:", io);
			throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
		}
		final long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, "end execute", endTime - startTime);
		return null;
	}

	@Override
	public UpdateContactPreferencesResponse updateContactPreferences(Map<String, Object> requestMap)
			throws ServiceException {
		final long startTime = System.currentTimeMillis();
		LOGGER.info("UpdateContactPreferencesImpl updateContactPreferences - start");
		try {
			UpdateContactPreferencesRequest updateContactPreferencesRequest = REQ_READER.readValue(
					requestMap.get(Constant.REQUEST_BODY).toString());
			final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
			final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
			final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
			final String storekey = (String) requestMap.get(Constant.STORE_KEY);
			final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
			final String storeId = propertyReaderService.getStoreId(storekey);
			final String domain = propertyReaderService.getCookieDomain(domainKey);
			final Map<String, Object> dataMap = new HashMap<>();
			final String endPointUrl = updateContactPreferences.replaceAll("STORE_ID", storeId);
			final long endTime;

			dataMap.put("validCookieNames", cookieNames);
			dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
			dataMap.put("domain", domain);
			dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
			endTime = System.currentTimeMillis();
			LOGGER.debug(Constant.EXECUTION_TIME_LOG, "updateContactPreferences", endTime - startTime);
			return (UpdateContactPreferencesResponse) execute(updateContactPreferencesRequest, dataMap);
		} catch (final IOException e) {
			final long endTime = System.currentTimeMillis();
			LOGGER.debug(Constant.EXECUTION_TIME_LOG, "updateContactPreferences IO Exception", endTime - startTime);
			UpdateContactPreferencesImpl.LOGGER.error("Error encountered: {}", e);
			throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
		}
	}

	@ObjectClassDefinition(name = "WCS updateContactPreferences Configurations", description = "updateContactPreferences Configuration for WCS Vendor")
	public @interface Config {
		@AttributeDefinition(name = "updateContactPreferences End Point", description = "Please Enter the updateContactPreferences End point in the format"
				+ "http://domain/restendpoint/${storeId}/xEmailPreference/xUpdatePreference")
		String endPoint() default "https://mdev.services.commerce.mattel.com/wcs/resources/store/10651/xEmailPreference/xUpdatePreference?updateCookies=true";
	}

	@Activate
	public void activate(final Config config) {
		updateContactPreferences = config.endPoint();
	}
}
