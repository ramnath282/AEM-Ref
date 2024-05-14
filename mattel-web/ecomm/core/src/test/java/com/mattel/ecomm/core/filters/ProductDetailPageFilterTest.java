package com.mattel.ecomm.core.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import com.mattel.ecomm.core.interfaces.GetTemplateTypeService;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(PowerMockRunner.class)
public class ProductDetailPageFilterTest {

  @InjectMocks
  ProductDetailPageFilter productDetailPageFilter;
  @Mock
  SlingHttpServletRequest slingHttpServletRequest;
  @Mock
  SlingHttpServletResponse response;
  @Mock
  FilterChain chain;
  @Mock
  RequestPathInfo requestPathInfo;
  @Mock
  GetProductTypeService getProductType;
  @Mock
  GetTemplateTypeService getTemplateType;
  @Mock
  private RequestDispatcher reqDispatcher;
  @Mock
  PropertyReaderService propertyReaderService;
  @Mock
  FilterConfig filterConfig;
  @Test
  public void doFilterTest() throws IOException, ServletException {
    
    final String[] selector = new String[3];
    selector[0] = "web";
    selector[1] = "addressinfo";
    String value = "/content/ag/retail/doll.webmodule.addtocart";
    Mockito.when(slingHttpServletRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getSelectors()).thenReturn(selector);
    Mockito.when(requestPathInfo.getResourcePath()).thenReturn(value);
    Map<String, String> propertiesMap=new HashMap<>();
    propertiesMap.put("product_type", "doll");
    propertiesMap.put("partNumber", "1256");
    Mockito.when(getProductType.getProductType(Mockito.anyString(),Mockito.anyString())).thenReturn(propertiesMap);
    Mockito.when(getTemplateType.getTemplatePath(Mockito.any(),Mockito.any())).thenReturn(value);
    Mockito.when(propertyReaderService.getSiteErrorPage(Mockito.any())).thenReturn("/content/ag/en/error");
    Mockito.when(slingHttpServletRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(reqDispatcher);
    productDetailPageFilter.init(filterConfig);
    productDetailPageFilter.doFilter(slingHttpServletRequest, response, chain);
    productDetailPageFilter.destroy();
  }

}
