package com.mattel.ecomm.orderhistory.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.pojos.OrderHistoryDetailResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class OrderHistoryDetailImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/orderHistoryDetailsDataBean/getOrderHistoryDetail?updateCookies=true&orderId=ORDER_ID";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @InjectMocks
    private OrderHistoryDetailImpl impl;

    @Before
    public void setUp() throws Exception {
        mockWebServer = new MockWebServer();
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer = null;
    }

    @Test
    public void testGetOrderHistoryDetail() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("order_history_detail.json")) {
            final OrderHistoryDetailImpl.Config config = Mockito.mock(OrderHistoryDetailImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "23123321");
            final Cookie[] cookieObj = new Cookie[1];

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
            requestMap.put(Constant.REQUEST_ORDERID, "1403451001");
            requestMap.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            OrderHistoryDetailResponse orderHistoryDetailResponse = impl.getOrderHistoryDetail(requestMap);
            Assert.assertNotNull(orderHistoryDetailResponse);
        }
    }

    private void addCookies(final MockResponse mockResponse) {
        mockResponse.addHeader("Set-Cookie",
                "JSESSIONID=0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq; path=/; domain=localhost; HttpOnly; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
        mockResponse.addHeader("Set-Cookie",
                "WC_SESSION_ESTABLISHED=true; path=/; domain=localhost; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
    }
}
