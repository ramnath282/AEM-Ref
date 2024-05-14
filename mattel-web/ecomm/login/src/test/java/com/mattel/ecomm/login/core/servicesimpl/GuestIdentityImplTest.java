package com.mattel.ecomm.login.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
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
import com.mattel.ecomm.coreservices.core.pojos.LoginResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class GuestIdentityImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/guestidentity?storeId=STORE_ID&updateCookies=true&responseFormat=json";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @InjectMocks
    private GuestIdentityImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGuestIdentity() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("logon_response.json")) {
            final GuestIdentityImpl.Config config = Mockito.mock(GuestIdentityImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            List<Cookie> cookies;
            addCookies(mockResponse);
            final Map<String, Object> requestHeader = new HashMap<>();
            LoginResponse guestIdentityResponse;
            mockWebServer = new MockWebServer();
            addCookies(mockResponse);
            mockResponse.setResponseCode(HttpStatus.SC_OK);
            mockResponse.setBody(IOUtils.toString(is, StandardCharsets.UTF_8));
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            guestIdentityResponse = impl.guestIdentity(requestHeader);
            Assert.assertNotNull(guestIdentityResponse);
            Assert.assertEquals("11179722", guestIdentityResponse.getUserId());
            cookies = guestIdentityResponse.getCookieList();

            Assert.assertNotNull(cookies);
            Assert.assertEquals(6, cookies.size());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testGuestIdentity_withWCSError() throws IOException, ServiceException {
        try {
            final GuestIdentityImpl.Config config = Mockito.mock(GuestIdentityImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            mockWebServer = new MockWebServer();
            addCookies(mockResponse);
            mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.guestIdentity(requestHeader);
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
    public void testGuestIdentity_withWCSDown() throws IOException, ServiceException {
        try {
            final GuestIdentityImpl.Config config = Mockito.mock(GuestIdentityImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            mockWebServer = new MockWebServer();
            mockWebServer.enqueue(mockResponse);
            addCookies(mockResponse);
            mockWebServer.start();
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.guestIdentity(requestHeader);
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
