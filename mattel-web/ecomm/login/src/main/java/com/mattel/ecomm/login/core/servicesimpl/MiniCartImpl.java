package com.mattel.ecomm.login.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.Minicart;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.MiniCartResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import org.apache.commons.lang.ArrayUtils;
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
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = Minicart.class)
@Designate(ocd = MiniCartImpl.Config.class)
public class MiniCartImpl implements Minicart {

	private static final Logger LOGGER = LoggerFactory.getLogger(MiniCartImpl.class);
	private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
	private static final String END_POINT_URL_KEY = "endPointUrl";
	private static final String EXECUTE_SERVICE = "execute";
	private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(MiniCartResponse.class);
	private String getMiniCartDataEndpoint;
	@Reference
	PropertyReaderService propertyReaderService;

	@SuppressWarnings("unchecked")
	@Override
	public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
		final long startTime = System.currentTimeMillis();

  try (CloseableHttpClient client = createCustomClient(dataMap)) {
			final List<String> requestCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
			final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
			final HttpResponse httpResponse = HttpRequestHandler.get(client,
					dataMap.get(MiniCartImpl.END_POINT_URL_KEY).toString(), requestCookies, null, httpClientContext);
			final int status = httpResponse.getStatusLine().getStatusCode();

			MiniCartImpl.LOGGER.debug("Response status is {}", status);

			if (null != httpResponse.getEntity() && !isError(status)) {
				try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
					final Header[] cookieResheader = httpResponse.getHeaders("Set-Cookie");

					MiniCartResponse miniCartResponse;
					miniCartResponse = RESP_READER.readValue(inputStream);
					if (null != cookieResheader && cookieResheader.length > 0) {
						final String[] cookieNames = (String[]) ArrayUtils.addAll(ServiceCookieMapping.DEFAULT.getCookieNames(), ServiceCookieMapping.MINICART.getCookieNames());
						final String domain = (String) dataMap.get("domain");
						final List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain,
								httpClientContext.getCookieStore().getCookies());
						miniCartResponse.setCookieList(cookieList);
					}

					MiniCartImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, MiniCartImpl.EXECUTE_SERVICE,
							(System.currentTimeMillis() - startTime));
					logServiceErrors(miniCartResponse);
					return miniCartResponse;
				}
			} else {
				final long endTime = System.currentTimeMillis();

				MiniCartImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, MiniCartImpl.EXECUTE_SERVICE,
						endTime - startTime);
				generalExceptionHandling(status, httpResponse.getEntity());
			}
		} catch (final IOException io) {
			final long endTime = System.currentTimeMillis();

			MiniCartImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, MiniCartImpl.EXECUTE_SERVICE, endTime - startTime);
			MiniCartImpl.LOGGER.error("Encountered error:", io);
			throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
		}

		final long endTime = System.currentTimeMillis();
		MiniCartImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, MiniCartImpl.EXECUTE_SERVICE, endTime - startTime);
		return null;
	}

	@Override
	public MiniCartResponse fetch(Map<String, Object> requestMap) throws ServiceException {
		final long startTime = System.currentTimeMillis();
		final Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
		final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
		final List<String> reqCookies = CookieUtils.buildWCSRequestCookies(requestCookieObjects, cookieNames);
		final String storekey = (String) requestMap.get(Constant.STORE_KEY);
		final String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
		final String storeId = propertyReaderService.getStoreId(storekey);
		final String domain = propertyReaderService.getCookieDomain(domainKey);
		final Map<String, Object> dataMap = new HashMap<>();
		String endPointUrl = getMiniCartDataEndpoint.replaceAll(MiniCartImpl.STORE_ID_PLACEHOLDER, storeId);
		endPointUrl = addLangIdToRequestUri(requestMap, endPointUrl);
		final long endTime;
		MiniCartImpl.LOGGER.debug("Valid Cookie Names : {} ", cookieNames);
		dataMap.put("validCookieNames", cookieNames);
		dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
		dataMap.put("domain", domain);
		dataMap.put(MiniCartImpl.END_POINT_URL_KEY, endPointUrl);
    dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
		endTime = System.currentTimeMillis();

		MiniCartImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Fetch", endTime - startTime);
		return (MiniCartResponse) execute(null, dataMap);
	}

	@ObjectClassDefinition(name = "WCS Minicart Configurations", description = "Minicart Configuration for WCS Vendor")
	public @interface Config {
		@AttributeDefinition(name = "MiniCart End Point", description = "Please Enter the MiniCart End point in the format"
				+ "http://domain/restendpoint/${storeId}/xheaderinfo/headerContext")
		String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xheaderinfo/headerContext?&responseFormat=json&updateCookies=true&langId=LANG_ID";
	}

	@Activate
	public void activate(final Config config) {
		getMiniCartDataEndpoint = config.endPoint();
		LOGGER.debug("Endpoint : {} ", getMiniCartDataEndpoint);
	}
}
