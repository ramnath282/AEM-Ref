package com.mattel.ecomm.addressinfo.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class AddDefaultAddressImplTest {
	private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xperson/updateperson?storeId=STORE_ID&updateCookies=true&responseFormat=json";
	private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @InjectMocks
    private AddDefaultAddressImpl impl;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testForAddAddress() throws IOException, ServiceException{
    	 try (InputStream is1 = getClass().getResourceAsStream("add_defaultaddress_request.json");
    			 InputStream is2 = getClass().getResourceAsStream("add_defaultaddress_response.json")) {
    		 final AddDefaultAddressImpl.Config config = Mockito.mock(AddDefaultAddressImpl.Config.class);
    		 final MockResponse mockResponse = new MockResponse();
             final Map<String, Object> requestHeader = new HashMap<>();
             final Cookie cookie = new Cookie("JSESSIONID", "213123132");
             final Cookie[] cookieObj = new Cookie[1];
             BaseResponse baseResponse;
             mockWebServer = new MockWebServer();
             mockResponse.setResponseCode(HttpStatus.SC_OK);
             mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
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
             requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
             requestHeader.put(Constant.STORE_KEY, "AG");
             requestHeader.put(Constant.DOMAIN_KEY, "AG");
             requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
             Mockito.when(config.endPoint())
                     .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
             Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
             Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
             impl.activate(config);
             baseResponse = impl.addDefaultAddress(requestHeader);
             Assert.assertNotNull(baseResponse);
             Assert.assertNull(baseResponse.getErrors());
    	 } finally {
             if (mockWebServer != null)
                 mockWebServer.shutdown();
         }
    }
    
    @Test(expected = ServiceException.class)
    public void testAddDefaultAddressForWCSDown() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("add_defaultaddress_request.json")) {
            final AddDefaultAddressImpl.Config config = Mockito.mock(AddDefaultAddressImpl.Config.class);
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];

            cookie.setComment("HttpOnly");
            cookie.setDomain("pattern");
            cookie.setMaxAge(2);
            cookie.setPath("uri");
            cookie.setSecure(true);
            cookie.setValue("newValue");
            cookie.setVersion(1);
            cookieObj[0] = cookie;
            requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(8080)));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.addDefaultAddress(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("Io Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        }
    }
    
    @Test(expected = ServiceException.class)
    public void testAddDefaultAddressForWCSError() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("add_defaultaddress_request.json")) {
            final AddDefaultAddressImpl.Config config = Mockito.mock(AddDefaultAddressImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];

            mockWebServer = new MockWebServer();
            mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
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
            requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.addDefaultAddress(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), serviceException.getErrorKey());
            Assert.assertEquals("Generic Error Ocuured", serviceException.getErrorMessage());
            throw serviceException;
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }
    
    @Test(expected = ServiceException.class)
    public void testAddDefaultAddressForInvalidRequest() throws IOException, ServiceException {
        try {
            final AddDefaultAddressImpl.Config config = Mockito.mock(AddDefaultAddressImpl.Config.class);
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];

            cookie.setComment("HttpOnly");
            cookie.setDomain("pattern");
            cookie.setMaxAge(2);
            cookie.setPath("uri");
            cookie.setSecure(true);
            cookie.setValue("newValue");
            cookie.setVersion(1);
            cookieObj[0] = cookie;
            requestHeader.put(Constant.REQUEST_BODY, "");
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(8080)));
            impl.activate(config);
            impl.addDefaultAddress(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception thrown from message body", serviceException.getErrorMessage());
            throw serviceException;
        }
    }
    
    private void addCookies(final MockResponse mockResponse) {
        mockResponse.addHeader("Set-Cookie",
                "JSESSIONID=0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq; path=/; domain=localhost; HttpOnly; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
        mockResponse.addHeader("Set-Cookie",
                "WC_SESSION_ESTABLISHED=true; path=/; domain=localhost; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
    }
}
