package com.mattel.ecomm.dolls.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.pojos.Doll;
import com.mattel.ecomm.coreservices.core.pojos.ErrorResponse;
import com.mattel.ecomm.coreservices.core.pojos.RegisteredDollsResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class RegisteredDollsImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/DollRegister/getDollData?storeId=STORE_ID&responseFormat=json&updateCookies=true";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @InjectMocks
    private RegisteredDollsImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFetchForSuccess() throws ServiceException, IOException {
        try (InputStream is = getClass().getResourceAsStream("registered_dolls_response.json")) {
            final RegisteredDollsImpl.Config config = Mockito.mock(RegisteredDollsImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            RegisteredDollsResponse registeredDollsResponse;
            Doll doll;

            mockWebServer = new MockWebServer();
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
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            registeredDollsResponse = impl.fetch(requestHeader);

            Assert.assertNotNull(registeredDollsResponse);
            Assert.assertNotNull(registeredDollsResponse.getDolls());
            Assert.assertEquals(2, registeredDollsResponse.getDolls().size());
            doll = registeredDollsResponse.getDolls().get(0);

            Assert.assertEquals("1111", doll.getProductID());
            Assert.assertEquals("Amazon", doll.getStore());
            Assert.assertEquals("Truly Me", doll.getCharacter());
            Assert.assertEquals("08/01/1984", doll.getPurchasedDate());
            Assert.assertEquals("myself", doll.getBoughtFor());
            Assert.assertEquals("The doll looks beautiful", doll.getReason());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test
    public void testFetchForMissingParam() throws ServiceException, IOException {
        try (InputStream is = getClass().getResourceAsStream("registered_dolls_missing_param.json")) {
            final RegisteredDollsImpl.Config config = Mockito.mock(RegisteredDollsImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            RegisteredDollsResponse registeredDollsResponse;
            ErrorResponse errorResponse;

            mockWebServer = new MockWebServer();
            mockResponse.setBody(IOUtils.toString(is, StandardCharsets.UTF_8));
            mockResponse.setResponseCode(HttpStatus.SC_OK);
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
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("4324");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            registeredDollsResponse = impl.fetch(requestHeader);

            Assert.assertNotNull(registeredDollsResponse);
            Assert.assertNull(registeredDollsResponse.getDolls());
            Assert.assertNotNull(registeredDollsResponse.getErrors());
            Assert.assertEquals(1, registeredDollsResponse.getErrors().size());
            errorResponse = registeredDollsResponse.getErrors().get(0);

            Assert.assertEquals("_ERR_MISSING_PARAM", errorResponse.getErrorCode());
            Assert.assertEquals("_ERR_MISSING_PARAM", errorResponse.getErrorKey());
            Assert.assertEquals("Operation details are missing", errorResponse.getErrorMessage());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testFetchForWCSError() throws ServiceException, IOException {
        try {
            final RegisteredDollsImpl.Config config = Mockito.mock(RegisteredDollsImpl.Config.class);
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
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.fetch(requestHeader);
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
    public void testFetchForInvalidResponse() throws ServiceException, IOException {
        try {
            final RegisteredDollsImpl.Config config = Mockito.mock(RegisteredDollsImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];

            mockWebServer = new MockWebServer();
            mockResponse.setBody("[]");
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
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.fetch(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testUpdateCardDetailsForWCSDown() throws IOException, ServiceException {
        try {
            final RegisteredDollsImpl.Config config = Mockito.mock(RegisteredDollsImpl.Config.class);
            final Map<String, Object> requestMap = new HashMap<>();
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
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(8080)));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.fetch(requestMap);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
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
