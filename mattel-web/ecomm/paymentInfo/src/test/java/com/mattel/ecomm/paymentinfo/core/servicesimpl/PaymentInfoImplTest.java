package com.mattel.ecomm.paymentinfo.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.pojos.Address;
import com.mattel.ecomm.coreservices.core.pojos.Card;
import com.mattel.ecomm.coreservices.core.pojos.PaymentInfoResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class PaymentInfoImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xcreditcard/getPaymentInfo?updateCookies=true&storeId=STORE_ID&responseFormat=json";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @Mock
    private MetricsService metrics;
    @InjectMocks
    private PaymentInfoImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = ServiceException.class)
    public void testGetPaymentInformationForInvalidResponsePayload() throws ServiceException, IOException {
        try {
            final PaymentInfoImpl.Config config = Mockito.mock(PaymentInfoImpl.Config.class);
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
            impl.getPaymentInformation(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test
    public void testGetPaymentInformationForSuccess() throws ServiceException, IOException {
        try (InputStream is = getClass().getResourceAsStream("request.json")) {
            final PaymentInfoImpl.Config config = Mockito.mock(PaymentInfoImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            PaymentInfoResponse paymentInfoResponse;
            List<Card> cards;
            Card card;
            Address address;
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
            requestHeader.put(Constant.REQUEST_BODY, "{}");
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
            paymentInfoResponse = impl.getPaymentInformation(requestHeader);

            Assert.assertNotNull(paymentInfoResponse);
            cards = paymentInfoResponse.getCards();

            Assert.assertNotNull(cards);
            Assert.assertFalse(cards.isEmpty());
            Assert.assertEquals(4, cards.size());
            card = cards.get(0);

            Assert.assertNotNull(card);
            Assert.assertEquals("XXXXXXXXXXXX1111", card.getMaskAccount());
            Assert.assertEquals("11173722", card.getUsersId());
            Assert.assertEquals("04", card.getExpMonth());
            Assert.assertEquals("609001", card.getCreditCardId());
            Assert.assertEquals("VISA", card.getCardType());
            Assert.assertEquals("0", card.getDefaultFlag());
            Assert.assertEquals("25530532", card.getAddressId());
            Assert.assertEquals("2024", card.getExpYear());
            Assert.assertEquals("test12", card.getCardName());
            address = card.getAddress();

            Assert.assertNotNull(address);
            Assert.assertEquals("25530532", address.getAddressId());
            Assert.assertEquals("GA", address.getState());
            Assert.assertEquals("US", address.getCountry());
            //Assert.assertEquals("30303", address.getZipCode());
            Assert.assertEquals("Test", address.getFirstName());
            Assert.assertEquals("Test", address.getLastName());
            Assert.assertEquals("Atlanta", address.getCity());
            Assert.assertNotNull(address.getAddressLine());
            Assert.assertEquals(3, address.getAddressLine().length);
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test
    public void testGetPaymentInformationForSuccessWithEmptyRequestCookie() throws ServiceException, IOException {
        try (InputStream is = getClass().getResourceAsStream("request.json")) {
            final PaymentInfoImpl.Config config = Mockito.mock(PaymentInfoImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            PaymentInfoResponse paymentInfoResponse;
            List<Card> cards;
            Card card;
            Address address;
            mockWebServer = new MockWebServer();

            mockResponse.setBody(IOUtils.toString(is, StandardCharsets.UTF_8));
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, null);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            paymentInfoResponse = impl.getPaymentInformation(requestHeader);

            Assert.assertNotNull(paymentInfoResponse);
            cards = paymentInfoResponse.getCards();

            Assert.assertNotNull(cards);
            Assert.assertFalse(cards.isEmpty());
            Assert.assertEquals(4, cards.size());
            card = cards.get(0);

            Assert.assertNotNull(card);
            Assert.assertEquals("XXXXXXXXXXXX1111", card.getMaskAccount());
            Assert.assertEquals("11173722", card.getUsersId());
            Assert.assertEquals("04", card.getExpMonth());
            Assert.assertEquals("609001", card.getCreditCardId());
            Assert.assertEquals("VISA", card.getCardType());
            Assert.assertEquals("0", card.getDefaultFlag());
            Assert.assertEquals("25530532", card.getAddressId());
            Assert.assertEquals("2024", card.getExpYear());
            Assert.assertEquals("test12", card.getCardName());
            address = card.getAddress();

            Assert.assertNotNull(address);
            Assert.assertEquals("25530532", address.getAddressId());
            Assert.assertEquals("GA", address.getState());
            Assert.assertEquals("US", address.getCountry());
            //Assert.assertEquals("30303", address.getZipCode());
            Assert.assertEquals("Test", address.getFirstName());
            Assert.assertEquals("Test", address.getLastName());
            Assert.assertEquals("Atlanta", address.getCity());
            Assert.assertNotNull(address.getAddressLine());
            Assert.assertEquals(3, address.getAddressLine().length);
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected=ServiceException.class)
    public void testGetPaymentInformationForWcsError() throws ServiceException, IOException {
        try (InputStream is = getClass().getResourceAsStream("request.json")) {
            final PaymentInfoImpl.Config config = Mockito.mock(PaymentInfoImpl.Config.class);
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
            requestHeader.put(Constant.REQUEST_BODY, "{}");
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
            impl.getPaymentInformation(requestHeader);
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
    public void testGetPaymentInformationForWcsServiceDown() throws ServiceException, IOException {
        final PaymentInfoImpl.Config config = Mockito.mock(PaymentInfoImpl.Config.class);
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
        requestHeader.put(Constant.REQUEST_BODY, "{}");
        requestHeader.put(Constant.STORE_KEY, "AG");
        requestHeader.put(Constant.DOMAIN_KEY, "AG");
        requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
        Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", "8080"));
        Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
        Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
        Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
        Mockito.when(timer.time()).thenReturn(context);
        Mockito.when(context.stop()).thenReturn(1000L);
        impl.activate(config);

        try {
            impl.getPaymentInformation(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        }
    }
}
