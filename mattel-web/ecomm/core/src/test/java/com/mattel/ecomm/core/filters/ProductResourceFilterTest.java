package com.mattel.ecomm.core.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;

import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(PowerMockRunner.class)
public class ProductResourceFilterTest {

	  @InjectMocks
	  ProductResourceFilter productResourceFilter;	
	  @Mock
	  FilterConfig filterConfig;  
	  @Mock
	  SlingHttpServletRequest slingHttpServletRequest;  
	  @Mock
	  SlingHttpServletResponse singHttpServletResponse;
	  @Mock
	  FilterChain filterChain;
	  @Mock
	  private RequestDispatcher requestDispatcher;
	  @Mock
	  ResourceResolver resourceResolver;
	  @Mock
	  RequestPathInfo requestPathInfo;
	  @Mock
	  PageManager pageManager;
	  @Mock
	  Page page;
	  @Mock
	  Page absoluteParent;
	  @Mock
	  Resource selectorResource;
	  @Mock
	  PropertyReaderService propertyReaderService;	  

	  @Before
	  public void setup() throws Exception {
		  StringBuffer requestUrlSb = new StringBuffer();
		  requestUrlSb.append("/content/fisher-price/home/shop/product/productfinder/abc-fxc66.html");
		  Mockito.when(slingHttpServletRequest.getRequestURL()).thenReturn(requestUrlSb);	
		  Mockito.when(slingHttpServletRequest.getResourceResolver()).thenReturn(resourceResolver);
		  String[] selectors = new String[1];
		  selectors[0] = "en-us";
		  Mockito.when(slingHttpServletRequest.getRequestPathInfo()).thenReturn(requestPathInfo);
		  Mockito.when(requestPathInfo.getSelectors()).thenReturn(selectors);
		  Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		  Mockito.when(requestPathInfo.getResourcePath()).thenReturn("/content/fisher-price/home/shop/product/productfinder/abc-fxc66.en-us.html");
		  Mockito.when(pageManager.getContainingPage(Mockito.anyString())).thenReturn(page);
		  Mockito.when(page.getAbsoluteParent(5)).thenReturn(absoluteParent);
		  Mockito.when(absoluteParent.getPath()).thenReturn("/content/fisher-price/home/shop/product");	  
		  Mockito.when(slingHttpServletRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
	  }
	  
	  @Test
	  public void doFilterTestForStaticPDP() throws IOException, ServletException {

		  Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(selectorResource);	
		  productResourceFilter.init(filterConfig);
		  productResourceFilter.doFilter(slingHttpServletRequest, singHttpServletResponse, filterChain);  
		  productResourceFilter.destroy();
	  }
	  
	  @Test
	  public void doFilterTestForDynamicPDP() throws IOException, ServletException {
		  
		  Mockito.when(resourceResolver.getResource("/content/fisher-price/home/shop/product/abc-fxc66")).thenReturn(null);
		  Mockito.when(resourceResolver.getResource("/var/commerce/products/fisher-price/en-us/product_FXC66")).thenReturn(selectorResource);
		  Mockito.when(propertyReaderService.getProductPath(Mockito.anyString())).thenReturn("/var/commerce/products/fisher-price/en-us");
		  productResourceFilter.init(filterConfig);
		  productResourceFilter.doFilter(slingHttpServletRequest, singHttpServletResponse, filterChain);  
		  productResourceFilter.destroy();
	  }
	  
	  @Test
	  public void doFilterTestForErronousUrl() throws IOException, ServletException {
		  
		  Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(null);
		  productResourceFilter.init(filterConfig);
		  productResourceFilter.doFilter(slingHttpServletRequest, singHttpServletResponse, filterChain);  
		  productResourceFilter.destroy();
	  }

}
