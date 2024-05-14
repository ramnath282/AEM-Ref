package com.mattel.ecomm.core.models;

import com.mattel.ecomm.coreservices.core.constants.Constant;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BasePageModelTest {
  @Mock
  private SlingHttpServletRequest request;
  @InjectMocks
  private BasePageModel basePageModel;

  @Test
  public void testIsDisableClientLibs() throws Exception {
    final RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);

    Mockito.when(requestPathInfo.getSelectors())
        .thenReturn(new String[] { Constant.CLIENT_LIBRARY_SELECTOR });
    Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    Assert.assertTrue(basePageModel.isDisableClientLibs());
  }

  @Test
  public void testIsDisableClientLibs1() throws Exception {
    final RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);

    Mockito.when(requestPathInfo.getSelectors())
        .thenReturn(new String[] { "abcd" });
    Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    Assert.assertFalse(basePageModel.isDisableClientLibs());
  }

  @Test
  public void testIsDisableClientLibs2() throws Exception {
    final RequestPathInfo requestPathInfo = Mockito.mock(RequestPathInfo.class);

    Mockito.when(requestPathInfo.getSelectors())
        .thenReturn(new String[] { });
    Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    Assert.assertFalse(basePageModel.isDisableClientLibs());
  }
}
