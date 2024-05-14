package com.mattel.ecomm.core.servlets;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Node;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class ProductBadgeTest {
	
	@InjectMocks
	ProductBadge productBadge;
	@Mock
    Resource resource;
	@Mock
	ResourceResolver resolver;
	@Mock
	MultifieldReader multifieldReader;
	@Mock
	ProductService productService;
	@Mock
    private SlingHttpServletResponse response;
	@Mock
	private SlingHttpServletRequest request;
	PageManager pageManager;
	Page page;
	String currentPagePath = "/content/fisher-price/us/en-us/home/product/productfinder/prod-drf13";
	Page homePage;
	Iterator<Page> rootPageIterator;
	@Mock
	PageFilter pageFilter;
	Node productBadgeNode;
	Map<String, ValueMap> multifieldProperty;
	Map.Entry<String, ValueMap> entry;
	ValueMap valMap;
	RequestPathInfo requestPathInfo;
	ValueMap productProperties;
	PrintWriter printWriter;
	String badgeTempValue = "sessionStorageValue";
	
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		MemberModifier.field(ProductBadge.class, "productService").set(productBadge,
				productService);
		pageManager = Mockito.mock(PageManager.class);
		page = Mockito.mock(Page.class);
		homePage = Mockito.mock(Page.class);
		rootPageIterator = Mockito.mock(Iterator.class);
		MemberModifier.field(ProductBadge.class, "multifieldReader").set(productBadge,
				multifieldReader);
		multifieldProperty = new HashMap<>();
		entry = Mockito.mock(Map.Entry.class);
		valMap = Mockito.mock(ValueMap.class);
		multifieldProperty.put("", valMap);
		productBadgeNode = Mockito.mock(Node.class);
		requestPathInfo = Mockito.mock(RequestPathInfo.class);
		productProperties = Mockito.mock(ValueMap.class);
		printWriter = Mockito.mock(PrintWriter.class);
		Mockito.when(request.getParameter("currentPath")).thenReturn(currentPagePath);
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("/content/fisher-price/us/en-us/home/product/productfinder")).thenReturn(resource);
		Mockito.when(resource.getPath()).thenReturn(currentPagePath);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage("/content/fisher-price/us/en-us/home/product/productfinder")).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
		Mockito.when(page.getContentResource()).thenReturn(resource);
		Mockito.when(page.getContentResource().getChild("productBadge")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(productBadgeNode);
		Mockito.when(multifieldReader.propertyReader(productBadgeNode)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue()).thenReturn(valMap);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(request.getRequestURI()).thenReturn(currentPagePath + ".html");
		Mockito.when(request.getRequestPathInfo().getSelectorString()).thenReturn("prod-drf13");
		Mockito.when(page.getPath()).thenReturn("/content/fisher-price/us/en-us/home/product/productfinder/prod-drf13");
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage("/content/fisher-price/us/en-us/home/product/productfinder/prod-drf13")).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(2)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(2).hasChild("language-masters")).thenReturn(true);
		Mockito.when(page.getAbsoluteParent(2).getName()).thenReturn("fisher-price");
		Mockito.when(productService.productProperties("/var/commerce/products/fisher-price/en-us/product_DRF13", resource)).thenReturn(productProperties);
	//	Mockito.when(parentProductPage.getProperties()).thenReturn(productProperties);
		Mockito.when(productProperties.get("badge", String.class)).thenReturn(badgeTempValue);
		Mockito.when(page.getAbsoluteParent(4)).thenReturn(homePage);
		//Mockito.when(page.listChildren()).thenReturn(rootPageIterator);
		Mockito.when(page.listChildren(pageFilter, false)).thenReturn(rootPageIterator);
		Mockito.when(rootPageIterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
	}

	@Test
	public void testDoGetSlingHttpServletRequestSlingHttpServletResponse() throws ServletException, IOException{
		Map<String,String> map = new HashMap<>();
		map.put("sessionStorageValue", "sessionStorageValue");
		Mockito.when(request.getParameterMap()).thenReturn(map);
		assertTrue(map.containsKey("sessionStorageValue"));
		Mockito.when(request.getParameter("sessionStorageValue")).thenReturn("sessionStorageValue");
		Mockito.when(entry.getValue().get("badgeTitle", String.class)).thenReturn("sessionStorageValue");
		productBadge.doGet(request, response);
	}
	
	@Test
	public void testSessionStorageValueNull() throws ServletException, IOException{
		currentPagePath = "/content/fisher-price/us/en-us/home/product/gnanendra";
		Mockito.when(request.getParameter("plp")).thenReturn("");
		Mockito.when(resolver.getResource(currentPagePath)).thenReturn(resource);
		Mockito.when(request.getParameter("currentPath")).thenReturn(currentPagePath);
		Mockito.when(resource.getPath()).thenReturn(currentPagePath);
		Mockito.when(pageManager.getPage(currentPagePath)).thenReturn(page);
		productBadge.doGet(request, response);
	}
	
}
