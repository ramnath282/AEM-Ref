package com.mattel.ecomm.coreservices.core.interfaces;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.ErrorResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BaseServiceTest {
  BaseService impl;

  @Before
  public void setUp() throws Exception {
    impl = (baseRequest, dataMap) -> new BaseResponse();
  }

  @Test(expected = ServiceException.class)
  public void testGetResponseValueAsJsonForError1() throws Exception {
    try {
      impl.getResponseValueAsJson(null);
    } catch (final ServiceException se) {
      Assert.assertEquals("1004", se.getErrorKey());
      throw se;
    }
  }

  @Test(expected = ServiceException.class)
  public void testGetResponseValueAsJsonForError2() throws Exception {
    try {
      impl.getResponseValueAsJson(new Object());
    } catch (final ServiceException se) {
      Assert.assertEquals("1003", se.getErrorKey());
      throw se;
    }
  }

  @Test
  public void testGetResponseValueAsJson() throws Exception {
    Assert.assertNotNull(impl.getResponseValueAsJson(new BaseResponse()));
  }

  @Test
  public void testIsErrorForSuccess1() throws Exception {
    Assert.assertFalse(impl.isError(java.net.HttpURLConnection.HTTP_OK));
  }

  @Test
  public void testIsErrorForSuccess2() throws Exception {
    Assert.assertFalse(impl.isError(java.net.HttpURLConnection.HTTP_CREATED));
  }

  @Test
  public void testIsErrorForSuccess3() throws Exception {
    Assert.assertFalse(impl.isError(java.net.HttpURLConnection.HTTP_NO_CONTENT));
  }

  @Test
  public void testIsErrorForFailure3() throws Exception {
    Assert.assertTrue(impl.isError(java.net.HttpURLConnection.HTTP_BAD_REQUEST));
  }

  @Test
  public void testIsErrorForFailure2() throws Exception {
    Assert.assertTrue(impl.isError(java.net.HttpURLConnection.HTTP_UNAUTHORIZED));
  }

  @Test
  public void testIsErrorForFailure() throws Exception {
    Assert.assertTrue(impl.isError(java.net.HttpURLConnection.HTTP_INTERNAL_ERROR));
  }

  @Test(expected = ServiceException.class)
  public void testGeneralExceptionHandlingInt() throws Exception {
    try {
      impl.generalExceptionHandling(java.net.HttpURLConnection.HTTP_INTERNAL_ERROR);
    } catch (final ServiceException se) {
      Assert.assertEquals(String.valueOf(java.net.HttpURLConnection.HTTP_INTERNAL_ERROR),
          se.getErrorKey());
      throw se;
    }
  }

  @Test(expected = ServiceException.class)
  public void testGeneralExceptionHandlingIntBaseResponse() throws Exception {
    try {
      impl.generalExceptionHandling(java.net.HttpURLConnection.HTTP_BAD_GATEWAY,
          new BaseResponse());
    } catch (final ServiceException se) {
      Assert.assertEquals(String.valueOf(java.net.HttpURLConnection.HTTP_BAD_GATEWAY),
          se.getErrorKey());
      throw se;
    }
  }

  @Test
  public void testAddLangIdToRequestUri() throws Exception {
    final Map<String, Object> requestMap = new HashMap<>();
    final String uri = "http://endpoint?" + Constant.LANG_ID + "=" + Constant.LANG_ID_PLACEHOLDER;

    requestMap.put(Constant.LANG_ID, "11");
    Assert.assertEquals("http://endpoint?" + Constant.LANG_ID + "=" + "11",
        impl.addLangIdToRequestUri(requestMap, uri));
  }

  @Test
  public void testAddLangIdToRequestUriForDefValue() throws Exception {
    final Map<String, Object> requestMap = new HashMap<>();
    final String uri = "http://endpoint?" + Constant.LANG_ID + "=" + Constant.LANG_ID_PLACEHOLDER;

    Assert.assertEquals("http://endpoint?" + Constant.LANG_ID + "=" + "",
        impl.addLangIdToRequestUri(requestMap, uri));
  }

  @Test
  public void testGetDefaultConnectTimeout() throws Exception {
    final PropertyReaderService propertyReaderService = Mockito.mock(PropertyReaderService.class);

    Mockito.when(propertyReaderService.getHttpServiceDefConnectTimeout("ag_en")).thenReturn(15000);
    Assert.assertEquals(15000, (int) impl.getDefaultConnectTimeout(propertyReaderService, "ag_en"));
  }

  @Test
  public void testCreateCustomClient() throws Exception {
    final Map<String, Object> dataMap = new HashMap<>();

    dataMap.put(Constant.DEF_CONNECT_TIMEOUT, new Integer(-1));
    Assert.assertNotNull(impl.createCustomClient(dataMap));
  }

  @Test
  public void testLogServiceErrors() throws Exception {
    final BaseResponse br = new BaseResponse();
    final ErrorResponse er = new ErrorResponse();

    er.setErrorKey("_ERR_COOKIE_EXPIRED");
    er.setErrorMessage("User cookie expired");
    br.setErrors(Arrays.asList(er));
    impl.logServiceErrors(br);
  }

  @Test
  public void testLogServiceNoErrors() throws Exception {
    final BaseResponse br = new BaseResponse();

    br.setErrors(Arrays.asList());
    impl.logServiceErrors(br);
  }

  @Test
  public void testIsServiceErrors() throws Exception {
    final BaseResponse br = new BaseResponse();
    final ErrorResponse er = new ErrorResponse();

    er.setErrorKey("_ERR_COOKIE_EXPIRED");
    er.setErrorMessage("User cookie expired");
    br.setErrors(Arrays.asList(er));
    Assert.assertTrue(impl.isServiceError(br));
  }
  
  @Test
  public void testIsServiceErrorFalse1() throws Exception {
    final BaseResponse br = new BaseResponse();

    br.setErrors(Arrays.asList());
    Assert.assertFalse(impl.isServiceError(br));
  }

  @Test
  public void testIsServiceErrorFalse2() throws Exception {
    final BaseResponse br = new BaseResponse();

    Assert.assertFalse(impl.isServiceError(br));
  }

  @Test
  public void testMapDomain() throws Exception {
    final Map<String, Object> dataMap = new HashMap<>();
    final String domain = "www.americangirl.com";

    impl.mapDomain(dataMap, domain);
    Assert.assertEquals(domain, dataMap.get("domain"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testMapRequestCookies() throws Exception {
    final Cookie cookie1 = new Cookie("JSESSIONID", "xyz");
    final Cookie cookie2 = new Cookie("WC_PERSISTENT", "abc");
    final Map<String, Object> dataMap = new HashMap<>();
    final Map<String, Object> requestMap = new HashMap<>();
    List<String> outputCookies;

    requestMap.put(Constant.REQUEST_COOKIES_KEY, new Cookie[] { cookie1, cookie2 });
    impl.mapRequestCookies(requestMap, dataMap);

    Assert.assertArrayEquals(ServiceCookieMapping.DEFAULT.getCookieNames(),
        (String[]) dataMap.get(Constant.VALID_COOKIE_NAMES_KEY));
    outputCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
    Assert.assertNotNull(outputCookies);
    Assert.assertTrue(outputCookies.contains("JSESSIONID=xyz"));
    Assert.assertTrue(outputCookies.contains("WC_PERSISTENT=abc"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testMapCommonRequestVariables() throws Exception {
    final String domain = "www.americangirl.com";
    final Cookie cookie1 = new Cookie("JSESSIONID", "xyz");
    final Cookie cookie2 = new Cookie("WC_PERSISTENT", "abc");
    final Map<String, Object> dataMap = new HashMap<>();
    final Map<String, Object> requestMap = new HashMap<>();
    List<String> outputCookies;

    requestMap.put(Constant.REQUEST_COOKIES_KEY, new Cookie[] { cookie1, cookie2 });
    impl.mapCommonRequestVariables(requestMap, dataMap, domain);
    Assert.assertEquals(domain, dataMap.get("domain"));
    Assert.assertArrayEquals(ServiceCookieMapping.DEFAULT.getCookieNames(),
        (String[]) dataMap.get(Constant.VALID_COOKIE_NAMES_KEY));
    outputCookies = (List<String>) dataMap.get(Constant.REQUEST_COOKIES_KEY);
    Assert.assertNotNull(outputCookies);
    Assert.assertTrue(outputCookies.contains("JSESSIONID=xyz"));
    Assert.assertTrue(outputCookies.contains("WC_PERSISTENT=abc"));
  }

  @Test
  public void testFetchResponsCookies() throws Exception {
    final BasicClientCookie cookie1 = new BasicClientCookie("JSESSIONID", "xyz");
    final BasicClientCookie cookie2 = new BasicClientCookie("WC_PERSISTENT", "abc");
    final HttpClientContext httpClientContext = new HttpClientContext();
    final Map<String, Object> dataMap = new HashMap<>();
    final CookieStore cookieStore = new CookieStore() {
      List<org.apache.http.cookie.Cookie> cookies = new ArrayList<>();

      @Override
      public List<org.apache.http.cookie.Cookie> getCookies() {
        return cookies;
      }

      @Override
      public boolean clearExpired(Date arg0) {
        return false;
      }

      @Override
      public void clear() {
        cookies.clear();
      }

      @Override
      public void addCookie(org.apache.http.cookie.Cookie arg0) {
        cookies.add(arg0);
      }
    };

    cookieStore.addCookie(cookie1);
    cookieStore.addCookie(cookie2);
    httpClientContext.setCookieStore(cookieStore);
    dataMap.put(Constant.VALID_COOKIE_NAMES_KEY, ServiceCookieMapping.DEFAULT.getCookieNames());
    dataMap.put("domain", "www.americangirl.com");
    final List<Cookie> cookies = impl.fetchResponsCookies(dataMap, httpClientContext);
    Assert.assertNotNull(cookies);
    Assert.assertEquals(2, cookies.size());
  }
}
