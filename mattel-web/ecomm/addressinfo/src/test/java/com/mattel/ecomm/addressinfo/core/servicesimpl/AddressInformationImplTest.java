package com.mattel.ecomm.addressinfo.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.Address;
import com.mattel.ecomm.coreservices.core.pojos.AddressInfoResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class AddressInformationImplTest {

    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/defaultuserprofile/getUserInfo?updateCookies=true";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @Mock
    private MetricsService metrics;
    @InjectMocks
    private AddressInformationImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAddressInfoForSuccess() throws ServiceException, IOException {
        try (InputStream is = getClass().getResourceAsStream("address_info_response.json")) {
            final AddressInformationImpl.Config config = Mockito.mock(AddressInformationImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            AddressInfoResponse addressInfoResponse;
            Address address;

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
            Mockito.when(metrics.timer(ArgumentMatchers.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            addressInfoResponse = impl.getAddressInfo(requestHeader);

            Assert.assertNotNull(addressInfoResponse);
            Assert.assertNotNull(addressInfoResponse.getAddress());
            Assert.assertEquals(1, addressInfoResponse.getAddress().size());
            address = addressInfoResponse.getAddress().get(0);

            Assert.assertEquals("John", address.getFirstName());
            Assert.assertEquals("Doe", address.getLastName());
            Assert.assertEquals("Plymouth meeting", address.getCity());
            Assert.assertEquals("IN", address.getCountry());
            Assert.assertEquals("TestState", address.getState());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testGetAddressInfoForWCSError() throws ServiceException, IOException {
        try {
            final AddressInformationImpl.Config config = Mockito.mock(AddressInformationImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
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
            Mockito.when(metrics.timer(ArgumentMatchers.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            impl.getAddressInfo(requestHeader);
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
    public void testGetAddressInfoForInvalidResponse() throws ServiceException, IOException {
        try {
            final AddressInformationImpl.Config config = Mockito.mock(AddressInformationImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
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
            Mockito.when(metrics.timer(ArgumentMatchers.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            impl.getAddressInfo(requestHeader);
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
                "JSESSIONID=0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq; path=/; domain=localhost; HttpOnly; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
        mockResponse.addHeader("Set-Cookie",
                "WC_SESSION_ESTABLISHED=true; path=/; domain=localhost; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
    }

}