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
import com.mattel.ecomm.coreservices.core.pojos.ContactPreferencesResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class ContactPreferencesImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xEmailPreference/xGetEmailPreference?responseFormat=json";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @InjectMocks
    private ContactPreferencesImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetContactPreferencesForSuccess() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("get_contact_preferences_response.json")) {
            final ContactPreferencesImpl.Config config = Mockito.mock(ContactPreferencesImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            ContactPreferencesResponse contactPreferencesResponse;
            mockWebServer = new MockWebServer();

            mockResponse.setBody(IOUtils.toString(is, StandardCharsets.UTF_8));
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
            contactPreferencesResponse = impl.getContactPreferences(requestHeader);

            Assert.assertNotNull(contactPreferencesResponse);
            Assert.assertEquals(
                    ",SHARED~ALL,PROMOTIONAL~ADDRESS,SHARED~ALL,GLOBAL_PROMOTIONAL~EMAIL,PRODUCT_OPT~EMAIL,PROMOTIONS_OPT~EMAIL,SWEEPSTAKES_OPT~EMAIL,SURVEYS_OPT~EMAIL,CONTENT_OPT~EMAIL,AG_STORES_OPT~EMAIL,,",
                    contactPreferencesResponse.getUserExistingContactPref());
            Assert.assertEquals("1", contactPreferencesResponse.getUserContactPrefFRQ());
            Assert.assertEquals("SHARED~ALL", contactPreferencesResponse.getUserContactPrefRTL());
            Assert.assertEquals("GLOBAL_PROMOTIONAL~EMAIL", contactPreferencesResponse.getUserContactPrefGL());
            Assert.assertEquals("LOYALTY_MKTG_OPT~EMAIL,LOYALTY_CERT_OPT~EMAIL",
                    contactPreferencesResponse.getUserContactPrefLOY());
            Assert.assertEquals(
                    "PRODUCT_OPT~EMAIL,PROMOTIONS_OPT~EMAIL,SWEEPSTAKES_OPT~EMAIL,SURVEYS_OPT~EMAIL,CONTENT_OPT~EMAIL,AG_STORES_OPT~EMAIL",
                    contactPreferencesResponse.getUserContactPrefCAT());
            // TODO add test cases for other fields
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testGetContactPreferencesForWCSError() throws IOException, ServiceException {
        try {
            final ContactPreferencesImpl.Config config = Mockito.mock(ContactPreferencesImpl.Config.class);
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
            impl.getContactPreferences(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), serviceException.getErrorKey());
            Assert.assertEquals("Generic Error Ocuured", serviceException.getErrorMessage());
            throw serviceException;
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }
}
