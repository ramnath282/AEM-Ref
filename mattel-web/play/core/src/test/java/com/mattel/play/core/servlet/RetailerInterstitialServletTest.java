package com.mattel.play.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.pojos.ProductTilePojo;
import com.mattel.play.core.pojos.RetailerPojo;
import com.mattel.play.core.services.MultifieldReader;
import com.mattel.play.core.services.TileGalleryAndLandingService;

public class RetailerInterstitialServletTest {
	
	RetailerInterstitialServlet retailerInterstitialServlet;
	SlingHttpServletRequest request; 
	SlingHttpServletResponse response;
	Resource resource;
	ResourceResolver resolver;
	PageManager pageManager;
	Page page;
	MultifieldReader multifieldReader;
	Tag[] categoryTags;
	Tag tag;
	Node node;
	Property property;
	List<RetailerPojo> retailerDetailsList = new ArrayList<>();
	RetailerPojo tilePojo = new RetailerPojo();
	List<String> tagList = new ArrayList<>();
	PrintWriter printWriter;
	ValueMap valueMap;
	NodeIterator categories;
	TagManager tagManager;
	Value value;
	Map<String, ValueMap> multifieldProperty;
	List<ProductTilePojo> productsList = new ArrayList<>();
	ProductTilePojo productTilePojo = new ProductTilePojo();
	TileGalleryAndLandingService tileGalleryAndLandingService;
	@Before
	public void setUp() throws IOException {
		retailerInterstitialServlet = new RetailerInterstitialServlet();
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		resource = Mockito.mock(Resource.class);
		resolver = Mockito.mock(ResourceResolver.class);
		pageManager = Mockito.mock(PageManager.class);
		property = Mockito.mock(Property.class);
		page = Mockito.mock(Page.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		tag = Mockito.mock(Tag.class);
		node = Mockito.mock(Node.class);
		printWriter = Mockito.mock(PrintWriter.class);
		valueMap = Mockito.mock(ValueMap.class);
		categories = Mockito.mock(NodeIterator.class);
		tagManager = Mockito.mock(TagManager.class);
		value = Mockito.mock(Value.class);
		tileGalleryAndLandingService = Mockito.mock(TileGalleryAndLandingService.class);
		multifieldProperty = new HashMap<String, ValueMap>();
		multifieldProperty.put("", valueMap);
		multifieldProperty.put("", valueMap);
		multifieldProperty.put("", valueMap);
		categoryTags = new Tag[2];
		categoryTags [0] = tag;
		categoryTags [1] = tag;
		tilePojo.setRetailerLogoSrc("abc");
		tagList.add("hello");
		tilePojo.setRetailerTarget("abc");
		tilePojo.setRetailerUrl("abc");
		tilePojo.setRetailLogoAlt("abc");
		retailerDetailsList.add(tilePojo);
		retailerInterstitialServlet.setMultifieldReader(multifieldReader);
		retailerInterstitialServlet.setRetailerDetailsList(retailerDetailsList);;
		retailerInterstitialServlet.setRetailerResource(resource);
		retailerInterstitialServlet.setTileGalleryAndLandingService(tileGalleryAndLandingService);
		
		Mockito.when(request.getParameter("pagePath")).thenReturn("/jcr:content/pagePath.html");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("/jcr:content/pagePath")).thenReturn(resource);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
		Mockito.when(page.getPath()).thenReturn("pagePath/jcr:content");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(request.getResourceResolver().getResource("/jcr:content/pagePath/jcr:content/productdetail/retailerDetailList")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(multifieldReader.propertyReader(node)).thenReturn(multifieldProperty);
		Mockito.when(tileGalleryAndLandingService.fetchRetailerDetails(multifieldProperty,resource)).thenReturn(retailerDetailsList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
	}
	@Test
	public void doGet() throws ServletException, IOException, ValueFormatException, PathNotFoundException, RepositoryException {
		retailerInterstitialServlet.doGet(request, response);
	}

}
