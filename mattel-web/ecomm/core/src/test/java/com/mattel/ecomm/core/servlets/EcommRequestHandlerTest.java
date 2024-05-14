package com.mattel.ecomm.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.Cookie;

import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.services.ProxyService;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ IOUtils.class })
public class EcommRequestHandlerTest {

  @InjectMocks
  EcommRequestHandler ecommRequestHandler;
  @Mock
  private SlingHttpServletResponse response;
  @Mock
  private SlingHttpServletRequest request;
  @Mock
  private RequestPathInfo pathInfo;
  private Enumeration<String> names;
  @Mock
  ProxyService proxyService;
  @Mock
  private PrintWriter writer;
  @Mock
  BufferedReader reader;
  @Mock
  IOUtils ioUtils;

  @Before
  public void setUp() throws Exception {

    Vector<String> elements = new Vector<String>();
    elements.add(Constant.REQUEST_STOREID);
    elements.add(Constant.REQUEST_DOMAIN_ID);
    names = elements.elements();
    String[] selectors = { Constant.WEB_SELECTOR, Constant.ADD_GIFT_CARD_TO_CART_SERVICE };
    Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
    Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);
    Mockito.when(request.getParameter(Constant.REQUEST_STOREID)).thenReturn("AG");
    Mockito.when(request.getParameter(Constant.REQUEST_DOMAIN_ID)).thenReturn("AG");
    Mockito.when(request.getParameter(Constant.REQUEST_ORDERID)).thenReturn("AG");
    Mockito.when(request.getReader()).thenReturn(reader);
    PowerMockito.mockStatic(IOUtils.class);
    Mockito.when(IOUtils.toString(reader)).thenReturn("testRequestBody");
    Mockito.when(request.getCookies()).thenReturn(this.addCookies());
    Mockito.when(request.getParameterNames()).thenReturn(names);
    MemberModifier.field(EcommRequestHandler.class, "proxyService").set(ecommRequestHandler,
        proxyService);
    Map<String, Object> responseMap = new HashMap<String, Object>();
    responseMap.put(Constant.RESPONSE_COOKIES_KEY, this.addCookiesList());
    responseMap.put(Constant.RESPONSE_BODY, "testResponse");
    Mockito.when(proxyService.makeServiceCalls(Mockito.any(), Mockito.any()))
        .thenReturn(responseMap);
    Mockito.when(response.getWriter()).thenReturn(writer);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testDoGetSlingHttpServletRequestSlingHttpServletResponse() throws IOException {
    ecommRequestHandler.doGet(request, response);
  }

  @Test
  public void testDoPostSlingHttpServletRequestSlingHttpServletResponse() throws IOException {
    ecommRequestHandler.doPost(request, response);
  }

  @Test
  public void testDoPutSlingHttpServletRequestSlingHttpServletResponse() throws IOException {
    ecommRequestHandler.doPut(request, response);
  }

  @Test
  public void testDoDeleteSlingHttpServletRequestSlingHttpServletResponse() throws IOException {
    ecommRequestHandler.doDelete(request, response);
  }

  private Cookie[] addCookies() {
    final Cookie cookie1 = new Cookie("JSESSIONID", "213123132");
    final Cookie cookie2 = new Cookie("WC_SESSION_ESTABLISHED", "true");
    Cookie[] cookies = { cookie1, cookie2 };
    return cookies;
  }

  private List<Cookie> addCookiesList() {
    final Cookie cookie1 = new Cookie("JSESSIONID", "213123132");
    final Cookie cookie2 = new Cookie("WC_SESSION_ESTABLISHED", "true");
    List<Cookie> cookies = new ArrayList<Cookie>();
    cookies.add(cookie1);
    cookies.add(cookie2);
    return cookies;
  }
}
