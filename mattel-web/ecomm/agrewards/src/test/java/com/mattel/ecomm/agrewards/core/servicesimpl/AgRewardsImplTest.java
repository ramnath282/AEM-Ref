package com.mattel.ecomm.agrewards.core.servicesimpl;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.AgRewardsResponse;
import com.mattel.ecomm.coreservices.core.pojos.LoyaltyDetails;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class AgRewardsImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xloyalty/xloyaltyRewards?storeId=STORE_ID&responseFormat=json";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @Mock
    private MetricsService metrics;
    @InjectMocks
    private AgRewardsImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetAgReward() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("ag_rewards_response.json")) {
            final AgRewardsImpl.Config config = Mockito.mock(AgRewardsImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            AgRewardsResponse agRewardsResponse;
            LoyaltyDetails loyaltyDetails;
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
            Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            agRewardsResponse = impl.getAgReward(requestHeader);

            Assert.assertNotNull(agRewardsResponse);
            loyaltyDetails = agRewardsResponse.getLoyaltyDetails();

            Assert.assertNotNull(loyaltyDetails);
            Assert.assertEquals("Silver", loyaltyDetails.getLoyaltySegment());
            Assert.assertEquals("JAN10@GMAIL.COM", loyaltyDetails.getEmailAddress());
            Assert.assertEquals("Active", loyaltyDetails.getLoyaltyStatus());
            Assert.assertEquals(36422320, loyaltyDetails.getLoyaltyNumber());
            Assert.assertEquals(0, loyaltyDetails.getLoyaltyPoints());
            Assert.assertEquals(0, loyaltyDetails.getLoyaltySourceChannel());
            Assert.assertEquals(0, loyaltyDetails.getLoyaltyPointsToNextTier());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testGetAgRewardForWCSError() throws IOException, ServiceException {
        try {
            final AgRewardsImpl.Config config = Mockito.mock(AgRewardsImpl.Config.class);
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
            Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            impl.getAgReward(requestHeader);
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
    public void testGetAgRewardForInvalidResponse() throws IOException, ServiceException {
        try {
            final AgRewardsImpl.Config config = Mockito.mock(AgRewardsImpl.Config.class);
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
            Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            impl.getAgReward(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        }  finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected=ServiceException.class)
    public void testGetAgRewardForWCSDown() throws IOException, ServiceException {
        final AgRewardsImpl.Config config = Mockito.mock(AgRewardsImpl.Config.class);
        final Timer timer = Mockito.mock(Timer.class);
        final Timer.Context context = Mockito.mock(Timer.Context.class);
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
        requestHeader.put(Constant.STORE_KEY, "AG");
        requestHeader.put(Constant.DOMAIN_KEY, "AG");
        requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
        Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(8080)));
        Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
        Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
        Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
        Mockito.when(timer.time()).thenReturn(context);
        Mockito.when(context.stop()).thenReturn(1000L);
        impl.activate(config);
        impl.getAgReward(requestHeader);
    }
}
