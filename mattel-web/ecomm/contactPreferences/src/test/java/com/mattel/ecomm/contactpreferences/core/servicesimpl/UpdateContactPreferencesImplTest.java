package com.mattel.ecomm.contactpreferences.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.pojos.UpdateContactPreferencesResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class UpdateContactPreferencesImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xEmailPreference/xUpdatePreference?updateCookies=true";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @InjectMocks
    private UpdateContactPreferencesImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUpdateContactPreferences() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("update_contact_preferences_req.json")) {
            final UpdateContactPreferencesImpl.Config config = Mockito.mock(UpdateContactPreferencesImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            UpdateContactPreferencesResponse updateContactPreferencesResponse;
            mockWebServer = new MockWebServer();

            mockResponse.setResponseCode(HttpStatus.SC_OK);
            mockResponse.setBody("{" + "\"Response\"" + ":" + "\"success\"" + "}");
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
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            updateContactPreferencesResponse = impl.updateContactPreferences(requestMap);
            Assert.assertNotNull(updateContactPreferencesResponse);
            Assert.assertEquals(String.valueOf(HttpStatus.SC_OK), updateContactPreferencesResponse.getResponse());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testUpdateContactPreferencesForWCSError() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("update_contact_preferences_req.json")) {
            final UpdateContactPreferencesImpl.Config config = Mockito.mock(UpdateContactPreferencesImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            UpdateContactPreferencesResponse updateContactPreferencesResponse;
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
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            updateContactPreferencesResponse = impl.updateContactPreferences(requestMap);
            Assert.assertNotNull(updateContactPreferencesResponse);
            Assert.assertEquals(String.valueOf(HttpStatus.SC_OK), updateContactPreferencesResponse.getResponse());
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
    public void testUpdateContactPreferencesForWCSDown() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("update_contact_preferences_req.json")) {
            final UpdateContactPreferencesImpl.Config config = Mockito.mock(UpdateContactPreferencesImpl.Config.class);
            final Map<String, Object> requestMap = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            UpdateContactPreferencesResponse updateContactPreferencesResponse;

            cookie.setComment("HttpOnly");
            cookie.setDomain("pattern");
            cookie.setMaxAge(2);
            cookie.setPath("uri");
            cookie.setSecure(true);
            cookie.setValue("newValue");
            cookie.setVersion(1);
            cookieObj[0] = cookie;
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(8080)));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            updateContactPreferencesResponse = impl.updateContactPreferences(requestMap);
            Assert.assertNotNull(updateContactPreferencesResponse);
            Assert.assertEquals(String.valueOf(HttpStatus.SC_OK), updateContactPreferencesResponse.getResponse());
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        }
    }

    @Test(expected = ServiceException.class)
    public void testUpdateContactPreferencesForInvalidRequest() throws IOException, ServiceException {
        try {
            final UpdateContactPreferencesImpl.Config config = Mockito.mock(UpdateContactPreferencesImpl.Config.class);
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
            requestMap.put(Constant.REQUEST_BODY, "");
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(8080)));
            impl.activate(config);
            impl.updateContactPreferences(requestMap);
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
