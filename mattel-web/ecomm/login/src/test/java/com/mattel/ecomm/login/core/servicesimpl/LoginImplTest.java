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
import org.apache.sling.commons.metrics.MetricsService;
import org.apache.sling.commons.metrics.Timer;
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
public class LoginImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/loginidentity?responseFormat=json&updateCookies=true";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @Mock
    private MetricsService metrics;
    @InjectMocks
    private LoginImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLoginForUser() throws IOException, ServiceException {
        try (InputStream is1 = getClass().getResourceAsStream("logon_request.json");
                InputStream is2 = getClass().getResourceAsStream("logon_response.json")) {
            final LoginImpl.Config config = Mockito.mock(LoginImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            LoginResponse loginResponse;
            List<Cookie> cookies;
            mockWebServer = new MockWebServer();

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
            requestMap.put(Constant.REQUEST_COOKIES_KEY, new Cookie[] {cookie});
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            loginResponse = impl.login(requestMap);

            Assert.assertNotNull(loginResponse);
            Assert.assertEquals("11179722", loginResponse.getUserId());
            Assert.assertEquals(
                    "10458722%2CIDQ8EG7sjjqdcRnqLwqytYM35y17ToG8bjVA54FnXosIcazcJYTSqerQajlXdUaZD8w5%2Fs91rrDvavPHQP1eEo"
                            + "zT6Td3kCz4PVrksWUOTA0ubZ%2F%2FVLXbdLpVu%2BdobZ4%2FpOD0psBdf04FPzxKFrLKxgKptgBdXFH5ZOd%2FA5xCLmsmd"
                            + "xUH9gUea4gqMDikgu5F948yzE4le8GRmkTT7aDpBQ%3D%3D",
                    loginResponse.getWcToken());
            Assert.assertEquals("1544610620251-5", loginResponse.getPersonalizationID());
            Assert.assertEquals("10458722%2CTBkLUkmBAKoVRvs6SD8OZVO4n4g%3D", loginResponse.getWcTrustedToken());
            Assert.assertEquals("multipassURL", loginResponse.getMultipassURL());
            Assert.assertEquals("identityAccessToken", loginResponse.getIdentityAccessToken());
            Assert.assertEquals("12345", loginResponse.getShopifyCustomerId());
            cookies = loginResponse.getCookieList();

            Assert.assertNotNull(cookies);
            Assert.assertEquals(7, cookies.size());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testLoginForWCSError() throws IOException, ServiceException {
        try (InputStream is1 = getClass().getResourceAsStream("logon_request.json")) {
            final LoginImpl.Config config = Mockito.mock(LoginImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            mockWebServer = new MockWebServer();

            mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            impl.login(requestMap);
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
    public void testLoginForWCSDown() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("logon_request.json")) {
            final LoginImpl.Config config = Mockito.mock(LoginImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            final Map<String, Object> requestMap = new HashMap<>();

            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(9433)));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            impl.login(requestMap);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("Io Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        }
    }

    @Test(expected = ServiceException.class)
    public void testLoginForInvalidRequest() throws IOException, ServiceException {
        try {
            final LoginImpl.Config config = Mockito.mock(LoginImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            final Map<String, Object> requestMap = new HashMap<>();

            requestMap.put(Constant.REQUEST_BODY, "");
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(9433)));
            Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            impl.login(requestMap);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception thrown from message body", serviceException.getErrorMessage());
            throw serviceException;
        }
    }

    @Test(expected = ServiceException.class)
    public void testLoginForInvalidResponse() throws IOException, ServiceException {
        try (InputStream is1 = getClass().getResourceAsStream("logon_request.json")) {
            final LoginImpl.Config config = Mockito.mock(LoginImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            mockWebServer = new MockWebServer();

            mockResponse.setBody("[]");
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            impl.login(requestMap);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("Io Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    private void addCookies(final MockResponse mockResponse) {
        mockResponse.addHeader("Set-Cookie",
                "JSESSIONID=0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq; HTTPOnly; Expires=Fri, 29-Mar-29 08:59:37 GMT; Path=/wcs/resources; Secure; HttpOnly");
        mockResponse.addHeader("Set-Cookie",
                "WC_SESSION_ESTABLISHED=true; Path=/");
        mockResponse.addHeader("Set-Cookie",
                "WC_PERSISTENT=kA6%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651; Expires=Sat, 27-Apr-29 08:59:37 GMT; Path=/");
        mockResponse.addHeader("Set-Cookie",
                "WC_AUTHENTICATION_11450726=11450726%2Ciq3xEXbdEdFZIDrGRgy0MgFhKq0%3D; Path=/; Secure");
        mockResponse.addHeader("Set-Cookie",
                "WC_USERACTIVITY_11450726=11450726%2C10651%2C0%2Cnull%2C1553763578033%2C1553765378033%2Cnull%2Cnull%2Cnull%2Cnull%2CWy4MaAIrtdL1bVl2fh%2FCkuOgn"
                + "iRYRp1uBeh5Z0o50vraeWryQQ29slOuW8BL0CI%2Bj9ZVte%2FLl4fURl3eAMPq%2By9LUX2ilEfVUNNtEDPnaQHypvZGuDPI3kVnJ7xUhvQaqdcgrzdAe2SLGI1H82y0kv6o00zr3eDhvSXuTSa2yG%2Fdi1NxogyMHZ2hzvCzJ23HhMsqLUwvA%2BPskVSt8%2F7%2Br%2BClIfKRfsHcUWkZ9r%2FbAk4%3D; Path=/");
        mockResponse.addHeader("Set-Cookie",
                "WC_ACTIVEPOINTER=-1%2C10651; Path=/");
    }
}
