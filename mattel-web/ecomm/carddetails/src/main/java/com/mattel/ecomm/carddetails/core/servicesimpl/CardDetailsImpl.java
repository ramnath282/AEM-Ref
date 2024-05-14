package com.mattel.ecomm.carddetails.core.servicesimpl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.CardDetails;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.CardDetailsRequest;
import com.mattel.ecomm.coreservices.core.pojos.CardDetailsResponse;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
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

@Component(service = CardDetails.class)
@Designate(ocd = CardDetailsImpl.Config.class)
public class CardDetailsImpl implements CardDetails {
    private static final String END_POINT_URL_KEY = "endPointUrl";
    private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
	private static final Logger LOGGER = LoggerFactory.getLogger(CardDetailsImpl.class);
	private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(CardDetailsResponse.class);
	@Reference
	PropertyReaderService propertyReaderService;

	private String cardDetailsEndPoint;

	@SuppressWarnings("unchecked")
	@Override
	public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
			throws ServiceException {
		long startTime = System.currentTimeMillis();
		String methodName = "execute";
		try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet getMethod = new HttpGet(dataMap.get(CardDetailsImpl.END_POINT_URL_KEY).toString());
			List<String> reqCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
			if (!reqCookies.isEmpty()) {
				reqCookies.forEach(reqCookie -> {
					getMethod.addHeader("Cookie", reqCookie);
					LOGGER.debug("String value of Cookie object added in request header is {}", reqCookie);
				});
			}

			HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
			HttpResponse httpResponse = httpClient.execute(getMethod, httpClientContext);
			int status = httpResponse.getStatusLine().getStatusCode();
			LOGGER.debug("Status is {}", status);
			Header responseHeaderAttribute = getMethod.getFirstHeader("content-encoding");
			String responseEncodingType = null;
			if (null != responseHeaderAttribute) {
				responseEncodingType = responseHeaderAttribute.getValue();
				LOGGER.debug("Content encoding type {}", responseEncodingType);
			}
			InputStream inputStream = httpResponse.getEntity().getContent();
			CardDetailsResponse cardDetailResponse = null;
			boolean isError = isError(status);
			if (!isError) {
				String[] cookieNames = (String[]) dataMap.get("validCookieNames");
				String domain = (String) dataMap.get("domain");
				List<Cookie> cookieList = CookieUtils.constructCookieList(cookieNames, domain, 
				    httpClientContext.getCookieStore().getCookies());
				cardDetailResponse = RESP_READER.readValue(inputStream);
				cardDetailResponse.setCookieList(cookieList);
			} else {
                final long endTime = System.currentTimeMillis();

                CardDetailsImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, Constant.EXECUTE_SERVICE,
                        endTime - startTime);
                generalExceptionHandling(status);
            }
			long endTime = System.currentTimeMillis();
			LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
			return cardDetailResponse;
		} catch (IOException io) {
			long endTime = System.currentTimeMillis();
			LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
			LOGGER.error("IO Exception Occured:", io);
            throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
		}
	}

	@Override
	public CardDetailsResponse getCardDetails(Map<String, Object> requestMap) throws ServiceException {
		long startTime = System.currentTimeMillis();
		String methodName = "getCardDetails";
		CardDetailsRequest cardDetailsRequest = new CardDetailsRequest();
		Cookie[] requestCookieObjects = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
		String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
		List<String> reqCookies = CookieUtils.getRequestCookieList(requestCookieObjects, cookieNames);
		String storekey = (String) requestMap.get(Constant.STORE_KEY);
		String domainKey = (String) requestMap.get(Constant.DOMAIN_KEY);
		String creditCardId = (String) requestMap.get(Constant.CREDITCARD_ID);
		String userId = (String) requestMap.get(Constant.REQUEST_USERID);
		String storeId = propertyReaderService.getStoreId(storekey);
		String domain = propertyReaderService.getCookieDomain(domainKey);
        String endPointUrl = cardDetailsEndPoint.replaceAll(CardDetailsImpl.STORE_ID_PLACEHOLDER, storeId);
        endPointUrl = endPointUrl.replaceAll("CREDITCARD_ID", creditCardId);
        endPointUrl = endPointUrl.replaceAll("USER_ID", userId);
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("validCookieNames", cookieNames);
		dataMap.put(Constant.REQUEST_COOKIES_KEY, reqCookies);
		dataMap.put("domain", domain);
        dataMap.put(CardDetailsImpl.END_POINT_URL_KEY, endPointUrl);
		long endTime = System.currentTimeMillis();
		LOGGER.debug(Constant.EXECUTION_TIME_LOG, methodName, endTime - startTime);
		return (CardDetailsResponse) execute(cardDetailsRequest, dataMap);

	}

	@Activate
	public void activate(final Config config) {
		cardDetailsEndPoint = config.endPoint();
	}

	@ObjectClassDefinition(name = "Card Details Endpoint Configurations", description = "Card Details Endpoint Configuration for WCS Vendor")
	public @interface Config {

		@AttributeDefinition(name = "Card Details Endpoint", description = "Please Enter the Card Details Endpoint in the format"
				+ "http://domain/restendpoint/${storeId}/loginservice")
		String endPoint() default "https://mdev.americangirl.com/wcs/resources/store/STORE_ID/xcreditcard/xCreditCard?updateCookies=true&storeId=STORE_ID&creditCardId=CREDITCARD_ID&profileName=xCreditCard&userId=USER_ID&responseFormat=json";

	}

	public void setPropertyReaderService(PropertyReaderService propertyReaderService) {
		this.propertyReaderService = propertyReaderService;
	}

	public void setCardDetailsEndPoint(String cardDetailsEndPoint) {
		this.cardDetailsEndPoint = cardDetailsEndPoint;
	}
}
