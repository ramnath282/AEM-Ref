package com.mattel.ecomm.core.authentication;

import static org.junit.Assert.fail;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.auth.core.spi.AuthenticationInfo;
import org.apache.sling.jcr.api.SlingRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.adobe.granite.crypto.CryptoSupport;
import com.mattel.ecomm.core.authentication.AGAuthenticationHandlerImpl.Config;
import com.mattel.ecomm.core.services.ProxyService;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(PowerMockRunner.class)
public class AGAuthenticationHandlerImplTest {

  @InjectMocks
  AGAuthenticationHandlerImpl authHandler;
  @Mock
  Config config;
  @Mock
  PropertyReaderService propertyReaderService;
  @Mock
  ResourceResolverFactory resolverFactory;
  @Mock
  private SlingRepository repository;
  @Mock
  private ProxyService proxyService;
  @Mock
  private CryptoSupport cryptoSupport;

  String loginpage = "/content/AG/retail/login";
  String dummyUserId = "userId";
  String encryptCode = "passcode";
  @Mock
  private HttpServletResponse httpServletResponse;
  @Mock
  private HttpServletRequest httpServletRequest;
  private String resourceURI = "/content/AG/en/retail/books.html";
  @Mock
  private ResourceResolver resourceResolver;
  @Mock
  private Resource resource;
  @Mock
  private SlingRepository paramSlingRepository;

  @Before
  public void setUp() throws Exception {
    Mockito.when(config.failurePage()).thenReturn(loginpage);
    Mockito.when(config.dummyUser()).thenReturn(dummyUserId);
    Mockito.when(config.encryptCode()).thenReturn(encryptCode);
    Mockito.when(httpServletRequest.getRequestURI()).thenReturn(resourceURI);
    Mockito.when(resolverFactory.getServiceResourceResolver(Mockito.any()))
        .thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
    Mockito.when(resourceResolver.isLive()).thenReturn(true);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testExtractCredentials() {
    Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(null);
    Mockito.when(httpServletRequest.getCookies()).thenReturn(this.addCookiesList());
    AuthenticationInfo authInfo = authHandler.extractCredentials(httpServletRequest,
        httpServletResponse);
  }

  @Test
  public void testExtractCredentials_withoutValidCookies() {
    Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(null);
    Mockito.when(httpServletRequest.getCookies()).thenReturn(null);
    authHandler.extractCredentials(httpServletRequest, httpServletResponse);
  }

  @Test
  public void testExtractCredentials_withIOException() throws IOException {
    Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(null);
    Mockito.when(httpServletRequest.getCookies()).thenReturn(null);
    PowerMockito.doThrow(new IOException()).when(httpServletResponse).flushBuffer();
    authHandler.extractCredentials(httpServletRequest, httpServletResponse);
  }

  @Test
  public void testRequestCredentials() {
    boolean flag = authHandler.requestCredentials(httpServletRequest, httpServletResponse);
    Assert.assertEquals(false, flag);
  }

  @Test
  public void testDropCredentials() {
    authHandler.dropCredentials(httpServletRequest, httpServletResponse);
  }

  @Test
  public void testBindRepository() {
    authHandler.bindRepository(paramSlingRepository);
  }

  @Test
  public void testUnbindRepository() {
    authHandler.unbindRepository(paramSlingRepository);
  }

  @Test
  public void testActivate() {
    authHandler.activate(config);
  }

  private Cookie[] addCookiesList() {
    final Cookie cookie1 = new Cookie("JSESSIONID", "213123132");
    final Cookie cookie2 = new Cookie("WC_SESSION_ESTABLISHED", "true");
    final Cookie cookie3 = new Cookie("WC_USERACTIVITY_", "true");
    Cookie[] cookies = { cookie1, cookie2, cookie3 };
    return cookies;
  }

}
