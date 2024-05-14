package com.mattel.ecomm.productinterest.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.pojos.ProductInterestResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class ProductInterestImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xpersonalInterest/xProductInterest?updateCookies=true&storeId=STORE_ID&profileName=xProductInterest&responseFormat=json";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @Mock
    private MetricsService metrics;
    @InjectMocks
    private ProductInterestImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetProductInterest() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("get_product_interest_response.json")) {
            final ProductInterestImpl.Config config = Mockito.mock(ProductInterestImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            ProductInterestResponse productInterestResponse;
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
            Mockito.when(metrics.timer(Mockito.anyString())).thenReturn(timer);
            Mockito.when(timer.time()).thenReturn(context);
            Mockito.when(context.stop()).thenReturn(1000L);
            impl.activate(config);
            productInterestResponse = impl.getProductInterest(requestHeader);

            Assert.assertNotNull(productInterestResponse);
            Assert.assertEquals(
                    "AG_GIRL_OF_THE_YEAR:Girl of the Year|AG_TRULY_ME:Truly Me|AG_ADDY:Addy|AG_JOSEPHINA:Jose"
                            + "fina|AG_JULIE:Julie|AG_KAYA:Kaya|AG_KIT:Kit|AG_MARYELLEN:Maryellen|AG_MELODY:Melody|AG_REBECCA:Rebe"
                            + "cca|AG_SAMANTHA:Samantha|AG_SOUVENIR:Souvenir|AG_BITTY_BABY:Bitty Baby|AG_WELLIE_WISHERS:WellieWishe"
                            + "rs|PARTY:Birthday parties|HAIR:Doll Hair Salon|RESTO:Cafe/Bistro|EVENT:Store events & programs|Ameri"
                            + "can Girl Atlanta:ATLANTA RETAIL|American Girl Boston:BOSTON RETAIL|American Girl Charlotte:CHARLOTTE"
                            + " RETAIL|American Girl Place Chicago:CHICAGO RETAIL|American Girl Columbus:COLUMBUS RETAIL|American Gi"
                            + "rl Dallas:DALLAS RETAIL|American Girl Denver:DENVER RETAIL|American Girl Houston:HOUSTON RETAIL|Amer"
                            + "ican Girl Place Los Angeles:LOS ANGELES RETAIL|American Girl Miami:MIAMI RETAIL|American Girl Minneap"
                            + "olis:MINNEAPOLIS RETAIL|American Girl Nashville:NASHVILLE RETAIL|American Girl Place New York:NEW YOR"
                            + "K RETAIL|American Girl Orlando:ORLANDO RETAIL|American Girl San Fransisco:SAN FRANCISCO RETAIL|Americ"
                            + "an Girl Seattle:SEATTLE RETAIL|American Girl Scottsdale:SCOTTSDALE RETAIL| American Girl Washington, "
                            + "D.C.:WASHINGTON DC RETAIL|American Girl Kansas City:KANSAS CITY RETAIL|",
                    productInterestResponse.getUserExistingProductInterest());
            Assert.assertEquals("CHICAGO RETAIL|AG_KAYA|HAIR",
                    productInterestResponse.getUserPreviousProductInterest());
            Assert.assertArrayEquals(new String[] { "PARTY:Birthday parties", "HAIR:Doll Hair Salon",
                    "RESTO:Cafe/Bistro", "EVENT:Store events & programs" },
                    productInterestResponse.getLocationInterestList());
            Assert.assertArrayEquals(
                    new String[] { "AG_GIRL_OF_THE_YEAR:Girl of the Year", "AG_TRULY_ME:Truly Me", "AG_ADDY:Addy",
                            "AG_JOSEPHINA:Josefina", "AG_JULIE:Julie", "AG_KAYA:Kaya", "AG_KIT:Kit",
                            "AG_MARYELLEN:Maryellen", "AG_MELODY:Melody", "AG_REBECCA:Rebecca", "AG_SAMANTHA:Samantha",
                            "AG_SOUVENIR:Souvenir", "AG_BITTY_BABY:Bitty Baby", "AG_WELLIE_WISHERS:WellieWishers" },
                    productInterestResponse.getProductInterestList());
            Assert.assertTrue(19 == productInterestResponse.getEventInterestList().length);
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testGetProductInterestForWCSError() throws IOException, ServiceException {
        try {
            final ProductInterestImpl.Config config = Mockito.mock(ProductInterestImpl.Config.class);
            final Timer timer = Mockito.mock(Timer.class);
            final Timer.Context context = Mockito.mock(Timer.Context.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
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
            impl.getProductInterest(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), serviceException.getErrorKey());
            Assert.assertEquals("Generic Error Ocuured", serviceException.getErrorMessage());
            throw serviceException;
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected=ServiceException.class)
    public void testGetProductInterestForWCSDown() throws IOException, ServiceException {
        final ProductInterestImpl.Config config = Mockito.mock(ProductInterestImpl.Config.class);
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
        impl.getProductInterest(requestHeader);
    }

    private void addCookies(final MockResponse mockResponse) {
        mockResponse.addHeader("Set-Cookie",
                "JSESSIONID=0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq; path=/; domain=localhost; HttpOnly; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
        mockResponse.addHeader("Set-Cookie",
                "WC_SESSION_ESTABLISHED=true; path=/; domain=localhost; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
    }
}
