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
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.interfaces.CategoryPagesDetailsService;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EcommConfigurationUtils.class })
public class GetFPCategoryDetailsTest {

	private String ROOTCATGORYPAGEPATH = "/content/fisher-price/us/en-us/home";
	private String PAGEPATH = "/content/fisher-price/us/en-us/home/shop/category/baby-gear";

	GetFPCategoryDetails getFPCategoryDetails;
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
	PageManager _pageManager;
	@Mock
	Page _page;
	@Mock
	Page _rootCategoryPage;
	@Mock
	PrintWriter _writer;
	@Mock
    CategoryPagesDetailsService _categoryPagesDetailsService;
	@Before
	public void setUp() throws RepositoryException, JSONException, IllegalArgumentException, IllegalAccessException, IOException {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("testString", "testString");
		getFPCategoryDetails=new GetFPCategoryDetails();
		PowerMockito.mockStatic(EcommConfigurationUtils.class);
		Mockito.when(EcommConfigurationUtils.getRootCatgoryPagePath()).thenReturn(ROOTCATGORYPAGEPATH);
		Mockito.when(request.getParameter("pagePath")).thenReturn(PAGEPATH);
		Mockito.when(request.getResourceResolver()).thenReturn(_resourceResolver);
		Mockito.when(_resourceResolver.adaptTo(PageManager.class)).thenReturn(_pageManager);
		Mockito.when(_pageManager.getPage(PAGEPATH)).thenReturn(_page);
		Mockito.when(_rootCategoryPage.getPath()).thenReturn(ROOTCATGORYPAGEPATH);
		Mockito.when(_page.getAbsoluteParent(5)).thenReturn(_rootCategoryPage);
		MemberModifier.field(GetFPCategoryDetails.class, "categoryPagesDetailsService").set(getFPCategoryDetails, _categoryPagesDetailsService);
		Mockito.when(response.getWriter()).thenReturn(_writer);
		Mockito.when(_categoryPagesDetailsService.getCategoryPagesJson(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(jsonObject);
		
	}

	@Test
	public void doGetTest() throws IOException {
		getFPCategoryDetails.doGet(request, response);
	}
	@Test
	public void doGetTest_withEmptyPath() throws IOException {
		Mockito.when(EcommConfigurationUtils.getRootCatgoryPagePath()).thenReturn("");
		getFPCategoryDetails.doGet(request, response);
	}
	@Test
	public void doGetTest_withNullResource() throws IOException {
		Mockito.when(_resourceResolver.getResource(Mockito.anyString())).thenReturn(null);
		getFPCategoryDetails.doGet(request, response);
	}
	@Test
	public void doGetTest_withNullPage() throws IOException {
		Mockito.when(_resource.adaptTo(Page.class)).thenReturn(null);
		getFPCategoryDetails.doGet(request, response);
	}

}
