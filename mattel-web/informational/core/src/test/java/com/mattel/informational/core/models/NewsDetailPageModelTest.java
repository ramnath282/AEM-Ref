package com.mattel.informational.core.models;

import static org.junit.Assert.assertEquals;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.informational.core.pojos.ItemListPojo;
import com.mattel.informational.core.services.NewsDetailMetadataService;

@RunWith(PowerMockRunner.class)
public class NewsDetailPageModelTest {
	@InjectMocks
	private NewsDetailPageModel newsDetailPageModel;
	@Mock
	NewsDetailMetadataService newsDetailMetadataService;
	@Mock
	Resource resource;
	@Mock
	Resource homePageResource;
	@Mock
	SlingHttpServletRequest request;
	@Mock
	RequestPathInfo requestPathInfo;
	@Mock
	ResourceResolver resourceResolver;

	@Test
	public void testInit() throws IllegalArgumentException, IllegalAccessException {
	  String[] selectors = {"selector1","selector2"};
      ItemListPojo itemListPojo = new ItemListPojo(); 
      itemListPojo.setDescription("this is test description");
      itemListPojo.setTitle("Test Title");
      ValueMap valueMap = Mockito.mock(ValueMap.class);
      valueMap.put("globalSeoTitle", "globalSeoTitle");
      valueMap.put("seoTitle", "seoTitle");       
      MemberModifier.field(NewsDetailPageModel.class, "newsDetailMetadataService").set(newsDetailPageModel, newsDetailMetadataService);
      MemberModifier.field(NewsDetailPageModel.class, "resource").set(newsDetailPageModel, resource);
      MemberModifier.field(NewsDetailPageModel.class, "request").set(newsDetailPageModel, request);
      Mockito.when(resource.getPath()).thenReturn("/content/mattel/mattel-corporate/us/en-us/home/news/news-detail");
      Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
      Mockito.when(requestPathInfo.getSelectors()).thenReturn(selectors);
      Mockito.when(newsDetailMetadataService.getFragmentDetails(selectors, "corp")).thenReturn(itemListPojo);
      Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
      Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(homePageResource);
	  newsDetailPageModel.init();
	}
	
	@Test
    public void testInit_1() throws IllegalArgumentException, IllegalAccessException {
      String[] selectors = {"selector1","selector2"};
      ItemListPojo itemListPojo = null; 
      ValueMap valueMap = Mockito.mock(ValueMap.class);
      valueMap.put("globalSeoTitle", "globalSeoTitle");
      valueMap.put("seoTitle", "seoTitle");       
      MemberModifier.field(NewsDetailPageModel.class, "newsDetailMetadataService").set(newsDetailPageModel, newsDetailMetadataService);
      MemberModifier.field(NewsDetailPageModel.class, "resource").set(newsDetailPageModel, resource);
      MemberModifier.field(NewsDetailPageModel.class, "request").set(newsDetailPageModel, request);
      Mockito.when(resource.getPath()).thenReturn("/content/mattel/mattel-corporate/us/en-us/home/news/news-detail");
      Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
      Mockito.when(requestPathInfo.getSelectors()).thenReturn(selectors);
      Mockito.when(newsDetailMetadataService.getFragmentDetails(selectors, "corp")).thenReturn(itemListPojo);
      Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
      Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(homePageResource);
      newsDetailPageModel.init();
    }
	
	@Test
    public void testInit_2() throws IllegalArgumentException, IllegalAccessException {
      String[] selectors = {"selector1","selector2"};
      ItemListPojo itemListPojo = new ItemListPojo(); 
      itemListPojo.setDescription("this is test description");
      itemListPojo.setTitle("");
      ValueMap valueMap = Mockito.mock(ValueMap.class);
      valueMap.put("globalSeoTitle", "globalSeoTitle");
      valueMap.put("seoTitle", "seoTitle");       
      MemberModifier.field(NewsDetailPageModel.class, "newsDetailMetadataService").set(newsDetailPageModel, newsDetailMetadataService);
      MemberModifier.field(NewsDetailPageModel.class, "resource").set(newsDetailPageModel, resource);
      MemberModifier.field(NewsDetailPageModel.class, "request").set(newsDetailPageModel, request);
      Mockito.when(resource.getPath()).thenReturn("/content/mattel/mattel-corporate/us/en-us/home/news/news-detail");
      Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
      Mockito.when(requestPathInfo.getSelectors()).thenReturn(selectors);
      Mockito.when(newsDetailMetadataService.getFragmentDetails(selectors, "corp")).thenReturn(itemListPojo);
      Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
      Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(homePageResource);
      newsDetailPageModel.init();
    }
	
	@Test
    public void testInit_3() throws IllegalArgumentException, IllegalAccessException {
      String[] selectors = {"selector1","selector2"};
      ItemListPojo itemListPojo = new ItemListPojo(); 
      itemListPojo.setDescription("this is test description");
      itemListPojo.setTitle("Test Title");
      ValueMap valueMap = Mockito.mock(ValueMap.class);
      valueMap.put("globalSeoTitle", "globalSeoTitle");
      valueMap.put("seoTitle", "seoTitle");       
      MemberModifier.field(NewsDetailPageModel.class, "newsDetailMetadataService").set(newsDetailPageModel, newsDetailMetadataService);
      MemberModifier.field(NewsDetailPageModel.class, "resource").set(newsDetailPageModel, resource);
      MemberModifier.field(NewsDetailPageModel.class, "request").set(newsDetailPageModel, request);
      Mockito.when(resource.getPath()).thenReturn("/content/mattel/mattel-corporate/us/en-us/home/news/news-detail");
      Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
      Mockito.when(requestPathInfo.getSelectors()).thenReturn(null);
      Mockito.when(newsDetailMetadataService.getFragmentDetails(selectors, "corp")).thenReturn(itemListPojo);
      Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
      Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(homePageResource);
      newsDetailPageModel.init();
    }

	@Test
	public void testToVerifyAllPropertiesModel() throws IllegalAccessException {
		newsDetailPageModel.setResource(resource);
		newsDetailPageModel.setDescription("this is test description");
		assertEquals("this is test description", newsDetailPageModel.getDescription());
	}
}
