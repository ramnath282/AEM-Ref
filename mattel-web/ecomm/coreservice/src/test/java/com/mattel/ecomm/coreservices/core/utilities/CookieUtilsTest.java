package com.mattel.ecomm.coreservices.core.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;

import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.pojos.CookiePojo;

public class CookieUtilsTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetCookie() {
        final CookiePojo cookiePojo = new CookiePojo();
        Cookie cookie;

        cookiePojo.setCookieName("JSESSIONID");
        cookiePojo.setCookieValue("0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq");
        cookiePojo.setCookiePath("/");
        cookiePojo.setDomain(".mattel.com");
        // cookiePojo.setMaxAge(12345678);
        cookiePojo.setSecure(true);
        cookiePojo.setHttpOnly(true);
        cookie = CookieUtils.getCookie(cookiePojo);

        Assert.assertNotNull(cookie);
        Assert.assertEquals("JSESSIONID", cookie.getName());
        Assert.assertEquals("0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq", cookie.getValue());
        Assert.assertEquals("/", cookie.getPath());
        Assert.assertEquals(".mattel.com", cookie.getDomain());
        // Assert.assertEquals(12345678, cookie.getMaxAge());
        Assert.assertTrue(cookie.getSecure());
        Assert.assertEquals("; HttpOnly;", cookie.getComment());
    }

    @Test
    public void testGetCookiePojo() {
        final NameValuePair nameValuePair = new NameValuePair() {
            @Override
            public String getValue() {
                return "12345678";
            }

            @Override
            public String getName() {
                return "Max-Age";
            }
        };
        final HeaderElement headerElement = new HeaderElement() {
            @Override
            public String getName() {
                return "JSESSIONID";
            }

            @Override
            public NameValuePair getParameter(final int arg0) {
                return nameValuePair;
            }

            @Override
            public NameValuePair getParameterByName(final String arg0) {
                return nameValuePair;
            }

            @Override
            public int getParameterCount() {
                return 1;
            }

            @Override
            public NameValuePair[] getParameters() {
                return new NameValuePair[] { nameValuePair };
            }

            @Override
            public String getValue() {
                return "0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq";
            }
        };
        CookiePojo cookiePojo;

        cookiePojo = CookieUtils.getCookiePojo(headerElement, ".mattel.com");
        Assert.assertNotNull(cookiePojo);
        Assert.assertEquals("JSESSIONID", cookiePojo.getCookieName());
        Assert.assertEquals("0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq", cookiePojo.getCookieValue());
        Assert.assertNull(cookiePojo.getCookiePath());
        Assert.assertFalse(cookiePojo.isSecure());
        // Assert.assertEquals(12345678, cookiePojo.getMaxAge());
        Assert.assertEquals(".mattel.com", cookiePojo.getDomain());
        Assert.assertFalse(cookiePojo.isHttpOnly());
    }

    @Test
    public void testGetRequestCookieList() {
        final Cookie cookie = new Cookie("JSESSIONID", "0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq");

        // cookie.setMaxAge(12345678);
        cookie.setSecure(true);
        cookie.setDomain(".mattel.com");
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setComment("; HttpOnly;");
        Assert.assertEquals("JSESSIONID=\"0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq\"; Path=\"/\"; Domain=\".mattel.com\"",
                CookieUtils.getRequestCookieList(new Cookie[] { cookie }, ServiceCookieMapping.DEFAULT.getCookieNames())
                        .get(0));
    }

    @Test
    public void testBuildWCSRequestCookies() throws Exception {
        final Cookie cookie = new Cookie("JSESSIONID", "0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq");
        final List<String> wcsCookieList = CookieUtils.buildWCSRequestCookies(new Cookie[] { cookie },
                ServiceCookieMapping.DEFAULT.getCookieNames());

        Assert.assertNotNull(wcsCookieList);
        Assert.assertEquals(1, wcsCookieList.size());
        Assert.assertEquals(cookie.getName() + "=" + cookie.getValue(), wcsCookieList.get(0));
    }

    @Test
    public void testConstructCookieList() throws Exception {
        final BasicClientCookie basicClientCookie1 = new BasicClientCookie("JSESSIONID",
                "0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq");
        final BasicClientCookie basicClientCookie2 = new BasicClientCookie("WC_SESSION_ESTABLISHED", "true");
        final BasicClientCookie basicClientCookie3 = new BasicClientCookie("WC_PERSISTENT",
                "kA6%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651");
        final BasicClientCookie basicClientCookie4 = new BasicClientCookie("WC_AUTHENTICATION_11450726",
                "11450726%2Ciq3xEXbdEdFZIDrGRgy0MgFhKq0%3D");
        final BasicClientCookie basicClientCookie5 = new BasicClientCookie("WC_ACTIVEPOINTER", "-1%2C10651");
        final BasicClientCookie basicClientCookie6 = new BasicClientCookie("WC_USERACTIVITY_11450726", "test");
        final List<org.apache.http.cookie.Cookie> cookies = new ArrayList<>(Arrays.asList(basicClientCookie1,
                basicClientCookie2, basicClientCookie3, basicClientCookie4, basicClientCookie5, basicClientCookie6));
        final List<Cookie> outputCookies;

        basicClientCookie1.setPath("/wcs/resources");
        basicClientCookie1
                .setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)));
        basicClientCookie1.setAttribute("expires", basicClientCookie1.getExpiryDate().toString());
        basicClientCookie1.setAttribute("HttpOnly", "");
        basicClientCookie1.setSecure(true);
        basicClientCookie3.setExpiryDate(
                new Date(System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)));
        basicClientCookie3.setAttribute("expires", basicClientCookie3.getExpiryDate().toString());
        basicClientCookie4.setSecure(true);

        cookies.forEach((c) -> ((BasicClientCookie)c).setDomain("wcs-dev"));
        outputCookies = CookieUtils.constructCookieList(ServiceCookieMapping.DEFAULT.getCookieNames(), "mattel-dev", cookies);

        Assert.assertNotNull(outputCookies);
        Assert.assertEquals(6, outputCookies.size());
        Assert.assertEquals("JSESSIONID", outputCookies.get(0).getName());
        Assert.assertEquals("0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq", outputCookies.get(0).getValue());
        Assert.assertEquals("/", outputCookies.get(0).getPath());
        Assert.assertEquals("mattel-dev", outputCookies.get(0).getDomain());
        Assert.assertTrue(outputCookies.get(0).getSecure());
        Assert.assertEquals("; HttpOnly;", outputCookies.get(0).getComment());
        Assert.assertTrue(outputCookies.get(0).getMaxAge() > 0);
        Assert.assertEquals("WC_SESSION_ESTABLISHED", outputCookies.get(1).getName());
        Assert.assertEquals("true", outputCookies.get(1).getValue());
        Assert.assertNull(outputCookies.get(1).getPath());
    }

    @Test
    public void testConstructCookieList2() throws Exception {
        final BasicClientCookie basicClientCookie1 = new BasicClientCookie("JSESSIONID",
                "0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq");
        final BasicClientCookie basicClientCookie2 = new BasicClientCookie("WC_SESSION_ESTABLISHED", "true");
        final BasicClientCookie basicClientCookie3 = new BasicClientCookie("WC_PERSISTENT",
                "kA6%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651");
        final BasicClientCookie basicClientCookie4 = new BasicClientCookie("WC_AUTHENTICATION_11450726",
                "11450726%2Ciq3xEXbdEdFZIDrGRgy0MgFhKq0%3D");
        final BasicClientCookie basicClientCookie5 = new BasicClientCookie("WC_ACTIVEPOINTER", "-1%2C10651");
        final BasicClientCookie basicClientCookie6 = new BasicClientCookie("WC_USERACTIVITY_11450726", "test");
        final List<org.apache.http.cookie.Cookie> cookies = new ArrayList<>(Arrays.asList(basicClientCookie1,
                basicClientCookie2, basicClientCookie3, basicClientCookie4, basicClientCookie5, basicClientCookie6));
        final List<Cookie> outputCookies;

        basicClientCookie1.setPath("/wcs/resources");
        basicClientCookie1
                .setExpiryDate(new Date(System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)));
        basicClientCookie1.setAttribute("expires", basicClientCookie1.getExpiryDate().toString());
        basicClientCookie1.setAttribute("HttpOnly", "");
        basicClientCookie1.setSecure(true);
        basicClientCookie3.setExpiryDate(
                new Date(System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)));
        basicClientCookie3.setAttribute("expires", basicClientCookie3.getExpiryDate().toString());
        basicClientCookie4.setSecure(true);

        cookies.forEach((c) -> ((BasicClientCookie)c).setDomain("wcs-dev"));
        outputCookies = CookieUtils.constructCookieList(ServiceCookieMapping.DEFAULT.getCookieNames(), "localhost", cookies);

        Assert.assertNotNull(outputCookies);
        Assert.assertEquals(6, outputCookies.size());
        Assert.assertEquals("JSESSIONID", outputCookies.get(0).getName());
        Assert.assertEquals("0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq", outputCookies.get(0).getValue());
        Assert.assertEquals("/", outputCookies.get(0).getPath());
        Assert.assertEquals("localhost", outputCookies.get(0).getDomain());
        Assert.assertFalse(outputCookies.get(0).getSecure());
        Assert.assertEquals("; HttpOnly;", outputCookies.get(0).getComment());
        Assert.assertTrue(outputCookies.get(0).getMaxAge() > 0);
        Assert.assertEquals("WC_SESSION_ESTABLISHED", outputCookies.get(1).getName());
        Assert.assertEquals("true", outputCookies.get(1).getValue());
        Assert.assertNull(outputCookies.get(1).getPath());
    }

    @Test
    public void testReplaceCookieFromList() throws Exception {
        final Cookie oldCookie = new Cookie("JSESSIONID", "0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq");
        final Cookie newCookie = new Cookie("JSESSIONID", "0000Zp-RTYF6j4iYMwCVf_brFbR:1b7o56gah");
        final List<Cookie> cookies = new ArrayList<>();

        cookies.add(oldCookie);
        CookieUtils.replaceCookieFromList(oldCookie, newCookie, cookies);
        Assert.assertTrue(cookies.contains(newCookie));
        Assert.assertFalse(cookies.contains(oldCookie));
    }

    @Test
    public void testCheckIfGuestUser1() throws Exception {
      final Cookie cookie1 = new Cookie("MATTEL_WELCOME_MSG", "test");
      final Cookie cookie2 = new Cookie("WC_AUTHENTICATION_313123", "test");

      Assert.assertFalse(CookieUtils.checkIfGuestUser(new Cookie [] {cookie1, cookie2}));
    }

    @Test
    public void testCheckIfGuestUser2() throws Exception {
      final Cookie cookie1 = new Cookie("MATTEL_WELCOME_MSG", "test");
      final Cookie cookie2 = new Cookie("WC_AUTHENTICATION_-1002", "fsdfdsfsd");

      Assert.assertFalse(CookieUtils.checkIfGuestUser(new Cookie [] {cookie1, cookie2}));
    }

    @Test
    public void testCheckIfGuestUser3() throws Exception {
      final Cookie cookie1 = new Cookie("JSESSIONID", "test");
      final Cookie cookie2 = new Cookie("WC_AUTHENTICATION_-1002", "fsdfdsfsd");

      Assert.assertFalse(CookieUtils.checkIfGuestUser(new Cookie [] {cookie1, cookie2}));
    }

    @Test
    public void testCheckIfGuestUser4() throws Exception {
      final Cookie cookie1 = new Cookie("JSESSIONID", "test");
      final Cookie cookie2 = new Cookie("WC_AUTHENTICATION_3131231", "fsdfdsfsd");

      Assert.assertTrue(CookieUtils.checkIfGuestUser(new Cookie [] {cookie1, cookie2}));
    }
}
