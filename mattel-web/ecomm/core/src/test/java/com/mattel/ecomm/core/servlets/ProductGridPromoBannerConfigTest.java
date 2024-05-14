package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.interfaces.ProductGridPromoBannerService;

@RunWith(PowerMockRunner.class)
public class ProductGridPromoBannerConfigTest {

	ProductGridPromoBannerConfig productGridPromoBannerConfig;
	@Mock
	SlingHttpServletRequest request;
	@Mock
	SlingHttpServletResponse response;
	private String CURRENTPAGEPATH="/content/ag/en/shop/categories/dolls/doll-care";
	@Mock
	ProductGridPromoBannerService _productGridPromoBannerService;
	@Mock
	PrintWriter _writer;
	@Before
	public void setUp() throws RepositoryException, JSONException, IllegalArgumentException, IllegalAccessException, IOException {
		 productGridPromoBannerConfig=new ProductGridPromoBannerConfig();
		 JSONObject jsonObject=new JSONObject();
		 jsonObject.put("testString", "testString");
		 MemberModifier.field(ProductGridPromoBannerConfig.class, "productGridPromoBannerService").set(productGridPromoBannerConfig, _productGridPromoBannerService);
		 Mockito.when(_productGridPromoBannerService.getProductGridPromoBannerJson(CURRENTPAGEPATH)).thenReturn(jsonObject);
		 Mockito.when(request.getParameter("currentPagePath")).thenReturn(CURRENTPAGEPATH);
		 Mockito.when(response.getWriter()).thenReturn(_writer);
	}

	@Test
	public void doGetTest() throws IOException {
		productGridPromoBannerConfig.doGet(request, response);
	}
	@Test
	public void doGetTest_withEmptyPagePath() throws IOException {
		Mockito.when(request.getParameter("currentPagePath")).thenReturn("");
		productGridPromoBannerConfig.doGet(request, response);
	}

}
