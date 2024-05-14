package com.mattel.ecomm.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.interfaces.CategoryPagesDetailsService;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EcommConfigurationUtils.class })
public class GetCategoryDetailsTest {

	private String ROOTCATGORYPAGEPATH = "/content/ag/en/shop/dolls";

	GetCategoryDetails getCategoryDetails;
	@Mock
	SlingHttpServletRequest request;
	@Mock
	SlingHttpServletResponse response;
	@Mock
	EcommConfigurationUtils ecommConfigurationUtils;
	@Mock
	ResourceResolver _resourceResolver;
	@Mock
	Resource _resource;
	@Mock
	Page _page;
	@Mock
	PrintWriter _writer;
	@Mock
    CategoryPagesDetailsService _categoryPagesDetailsService;
	@Before
	public void setUp() throws RepositoryException, JSONException, IllegalArgumentException, IllegalAccessException, IOException {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("testString", "testString");
		getCategoryDetails=new GetCategoryDetails();
		PowerMockito.mockStatic(EcommConfigurationUtils.class);
		Mockito.when(EcommConfigurationUtils.getRootCatgoryPagePath()).thenReturn(ROOTCATGORYPAGEPATH);
		Mockito.when(request.getResourceResolver()).thenReturn(_resourceResolver);
		Mockito.when(_resourceResolver.getResource(Mockito.anyString())).thenReturn(_resource);
		Mockito.when(_resource.adaptTo(Page.class)).thenReturn(_page);
		Mockito.when(_page.getPath()).thenReturn(ROOTCATGORYPAGEPATH);
		MemberModifier.field(GetCategoryDetails.class, "categoryPagesDetailsService").set(getCategoryDetails, _categoryPagesDetailsService);
		Mockito.when(response.getWriter()).thenReturn(_writer);
		Mockito.when(_categoryPagesDetailsService.getCategoryPagesJson(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(jsonObject);
		
	}

	@Test
	public void doGetTest() throws IOException {
		getCategoryDetails.doGet(request, response);
	}
	@Test
	public void doGetTest_withEmptyPath() throws IOException {
		Mockito.when(EcommConfigurationUtils.getRootCatgoryPagePath()).thenReturn("");
		getCategoryDetails.doGet(request, response);
	}
	@Test
	public void doGetTest_withNullResource() throws IOException {
		Mockito.when(_resourceResolver.getResource(Mockito.anyString())).thenReturn(null);
		getCategoryDetails.doGet(request, response);
	}
	@Test
	public void doGetTest_withNullPage() throws IOException {
		Mockito.when(_resource.adaptTo(Page.class)).thenReturn(null);
		getCategoryDetails.doGet(request, response);
	}

}
