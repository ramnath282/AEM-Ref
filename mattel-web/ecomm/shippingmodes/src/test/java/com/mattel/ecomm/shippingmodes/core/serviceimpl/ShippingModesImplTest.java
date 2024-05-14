package com.mattel.ecomm.shippingmodes.core.serviceimpl;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.ShippingModesResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;
import com.mattel.ecomm.shippingmodes.core.servicesimpl.ShippingModesImpl;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class ShippingModesImplTest {

	private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xheaderinfo/headerContext?&responseFormat=json&updateCookies=true";

	private MockWebServer mockWebServer;

	ShippingModesResponse shippingModesResponse;

	@Mock
	private PropertyReaderService propertyReaderService;

	@InjectMocks
	private ShippingModesImpl shippingModesImpl;

	@Test
	public void testGetShippingModes() throws IOException, ServiceException {
		try (InputStream is = getClass().getResourceAsStream("get_shippingMode_response.json")) {
			final ShippingModesImpl.Config config = Mockito.mock(ShippingModesImpl.Config.class);
			final MockResponse mockResponse = new MockResponse();
			final Map<String, Object> requestMap = new HashMap<>();
			final Cookie cookie = new Cookie("JSESSIONID", "213123132");
			final Cookie[] cookieObj = new Cookie[1];
			mockWebServer = new MockWebServer();
			mockResponse.setResponseCode(HttpStatus.SC_OK);
			mockResponse.setBody(IOUtils.toString(is, StandardCharsets.UTF_8));
			addCookies(mockResponse);
			mockWebServer.enqueue(mockResponse);
			mockWebServer.start();
			cookie.setComment("HttpOnly");
			cookie.setDomain("pattern");
			cookie.setMaxAge(2);
			cookie.setPath("uri");
			cookie.setSecure(true);
			cookie.setValue("newValue");
			cookie.setVersion(1);
			cookieObj[0] = cookie;
			requestMap.put(Constant.STORE_KEY, "AG");
			requestMap.put(Constant.DOMAIN_KEY, "AG");
			requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
			Mockito.when(config.endPoint())
					.thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
			Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
			Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
			shippingModesImpl.activate(config);
			shippingModesResponse = shippingModesImpl.getShippingModes(requestMap);
			Assert.assertNotNull(shippingModesResponse);
			//Assert.assertEquals();
			//Assert.assertEquals();
		} finally {
			if (mockWebServer != null)
				mockWebServer.shutdown();
		}
	}
	@Test(expected=ServiceException.class)
	public void testGetShippingModes_withWCSError() throws IOException, ServiceException {
		try{
			final ShippingModesImpl.Config config = Mockito.mock(ShippingModesImpl.Config.class);
			final MockResponse mockResponse = new MockResponse();
			final Map<String, Object> requestMap = new HashMap<>();
			final Cookie cookie = new Cookie("JSESSIONID", "213123132");
			final Cookie[] cookieObj = new Cookie[1];
			mockWebServer = new MockWebServer();
			mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);			
			addCookies(mockResponse);
			mockWebServer.enqueue(mockResponse);
			mockWebServer.start();
			cookie.setComment("HttpOnly");
			cookie.setDomain("pattern");
			cookie.setMaxAge(2);
			cookie.setPath("uri");
			cookie.setSecure(true);
			cookie.setValue("newValue");
			cookie.setVersion(1);
			cookieObj[0] = cookie;
			requestMap.put(Constant.STORE_KEY, "AG");
			requestMap.put(Constant.DOMAIN_KEY, "AG");
			requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
			Mockito.when(config.endPoint())
					.thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
			Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
			Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
			shippingModesImpl.activate(config);
			shippingModesImpl.getShippingModes(requestMap);
		} catch (final ServiceException serviceException) {
			Assert.assertEquals(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), serviceException.getErrorKey());
			Assert.assertEquals("Generic Error Ocuured", serviceException.getErrorMessage());
			throw serviceException;
		} finally {
			if (mockWebServer != null)
				mockWebServer.shutdown();
		}
	}
	@Test(expected=ServiceException.class)
	public void testGetShippingModes_withWCSDown() throws IOException, ServiceException {
		try{
			final ShippingModesImpl.Config config = Mockito.mock(ShippingModesImpl.Config.class);
			final MockResponse mockResponse = new MockResponse();
			final Map<String, Object> requestMap = new HashMap<>();
			final Cookie cookie = new Cookie("JSESSIONID", "213123132");
			final Cookie[] cookieObj = new Cookie[1];
			mockWebServer = new MockWebServer();		
			addCookies(mockResponse);
			mockWebServer.enqueue(mockResponse);
			mockWebServer.start();
			cookie.setComment("HttpOnly");
			cookie.setDomain("pattern");
			cookie.setMaxAge(2);
			cookie.setPath("uri");
			cookie.setSecure(true);
			cookie.setValue("newValue");
			cookie.setVersion(1);
			cookieObj[0] = cookie;
			requestMap.put(Constant.STORE_KEY, "AG");
			requestMap.put(Constant.DOMAIN_KEY, "AG");
			requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
			Mockito.when(config.endPoint())
					.thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
			Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
			Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
			shippingModesImpl.activate(config);
			shippingModesImpl.getShippingModes(requestMap);
		} catch (final ServiceException serviceException) {
			Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
			Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
			throw serviceException;
		} finally {
			if (mockWebServer != null)
				mockWebServer.shutdown();
		}
	}

	private void addCookies(final MockResponse mockResponse) {
		mockResponse.addHeader("Set-Cookie",
				"JSESSIONID=0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq; HTTPOnly; Expires=Fri, 29-Mar-29 08:59:37 GMT; Path=/wcs/resources; Secure; HttpOnly");
		mockResponse.addHeader("Set-Cookie", "WC_SESSION_ESTABLISHED=true; Path=/");
		mockResponse.addHeader("Set-Cookie",
				"WC_PERSISTENT=kA6%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651; Expires=Sat, 27-Apr-29 08:59:37 GMT; Path=/");
		mockResponse.addHeader("Set-Cookie",
				"WC_AUTHENTICATION_11450726=11450726%2Ciq3xEXbdEdFZIDrGRgy0MgFhKq0%3D; Path=/; Secure");
		mockResponse.addHeader("Set-Cookie",
				"WC_USERACTIVITY_11450726=11450726%2C10651%2C0%2Cnull%2C1553763578033%2C1553765378033%2Cnull%2Cnull%2Cnull%2Cnull%2CWy4MaAIrtdL1bVl2fh%2FCkuOgn"
						+ "iRYRp1uBeh5Z0o50vraeWryQQ29slOuW8BL0CI%2Bj9ZVte%2FLl4fURl3eAMPq%2By9LUX2ilEfVUNNtEDPnaQHypvZGuDPI3kVnJ7xUhvQaqdcgrzdAe2SLGI1H82y0kv6o00zr3eDhvSXuTSa2yG%2Fdi1NxogyMHZ2hzvCzJ23HhMsqLUwvA%2BPskVSt8%2F7%2Br%2BClIfKRfsHcUWkZ9r%2FbAk4%3D; Path=/");
		mockResponse.addHeader("Set-Cookie", "WC_ACTIVEPOINTER=-1%2C10651; Path=/");
	}
}
