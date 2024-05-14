package com.mattel.play.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.sling.api.resource.LoginException;
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
import com.mattel.play.core.pojos.VideoTile;
import com.mattel.play.core.services.VideoGalleryService;

public class VideoLandingServletTest {
	VideoLandingServlet videoLandingServlet;
	SlingHttpServletRequest request; 
	SlingHttpServletResponse response;
	Resource resource;
	ResourceResolver resolver;
	PageManager pageManager;
	Page page;
	Tag[] categoryTags;
	Tag tag;
	Node node;
	Property property;
	List<VideoTile> characterList = new ArrayList<>();
	VideoTile tilePojo = new VideoTile();
	List<String> tagList = new ArrayList<>();
	PrintWriter printWriter;
	ValueMap valueMap;
	NodeIterator categories;
	TagManager tagManager;
	Value value;
	List<ProductTilePojo> productsList = new ArrayList<>();
	ProductTilePojo productTilePojo = new ProductTilePojo();
	VideoGalleryService videoGalleryService;
	@Before
	public void setUp() throws RepositoryException, IOException, LoginException {
		videoLandingServlet = new VideoLandingServlet();
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		resource = Mockito.mock(Resource.class);
		resolver = Mockito.mock(ResourceResolver.class);
		pageManager = Mockito.mock(PageManager.class);
		property = Mockito.mock(Property.class);
		page = Mockito.mock(Page.class);
		tag = Mockito.mock(Tag.class);
		node = Mockito.mock(Node.class);
		printWriter = Mockito.mock(PrintWriter.class);
		valueMap = Mockito.mock(ValueMap.class);
		categories = Mockito.mock(NodeIterator.class);
		tagManager = Mockito.mock(TagManager.class);
		value = Mockito.mock(Value.class);
		videoGalleryService = Mockito.mock(VideoGalleryService.class);
		categoryTags = new Tag[2];
		categoryTags [0] = tag;
		categoryTags [1] = tag;
		tilePojo.setAlwaysEnglish("yes");
		tagList.add("hello");
		tilePojo.setAlwaysEnglish("");
		
		characterList.add(tilePojo);
		productsList.add(productTilePojo);
		videoLandingServlet.setVideoGalleryService(videoGalleryService);
		
		Mockito.when(request.getParameter("currentPath")).thenReturn("");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(videoGalleryService.getVideoList("", "","")).thenReturn(characterList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
		
		
		
	}
	
	@Test
	public void doGet() throws ServletException, IOException, ValueFormatException, PathNotFoundException, RepositoryException {
		videoLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("orderVideo")).thenReturn(true);
		Mockito.when(node.getProperty("orderVideo")).thenReturn(property);
		Mockito.when(node.getProperty("orderVideo").getString()).thenReturn("orderVideo");
		videoLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("ooyalaPlayerId")).thenReturn(true);
		Mockito.when(node.getProperty("ooyalaPlayerId")).thenReturn(property);
		Mockito.when(node.getProperty("ooyalaPlayerId").getString()).thenReturn("ooyalaPlayerId");
		videoLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("allLabel")).thenReturn(true);
		Mockito.when(node.getProperty("allLabel")).thenReturn(property);
		Mockito.when(node.getProperty("allLabel").getString()).thenReturn("allLabel");
		videoLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("categoryDisplay")).thenReturn(true);
		Mockito.when(node.getProperty("categoryDisplay")).thenReturn(property);
		Mockito.when(node.getProperty("categoryDisplay").getString()).thenReturn("categoryDisplay");
		videoLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("columnLayout")).thenReturn(true);
		Mockito.when(node.getProperty("columnLayout")).thenReturn(property);
		Mockito.when(node.getProperty("columnLayout").getString()).thenReturn("columnLayout");
		videoLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("disableAutoplay")).thenReturn(true);
		Mockito.when(node.getProperty("disableAutoplay")).thenReturn(property);
		Mockito.when(node.getProperty("disableAutoplay").getString()).thenReturn("disableAutoplay");
		videoLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("nameAlign")).thenReturn(true);
		Mockito.when(node.getProperty("nameAlign")).thenReturn(property);
		Mockito.when(node.getProperty("nameAlign").getString()).thenReturn("nameAlign");
		videoLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("onLoadLimit")).thenReturn(true);
		Mockito.when(node.getProperty("onLoadLimit")).thenReturn(property);
		Mockito.when(node.getProperty("onLoadLimit").getString()).thenReturn("onLoadLimit");
		videoLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("relVideoTitle")).thenReturn(true);
		Mockito.when(node.getProperty("relVideoTitle")).thenReturn(property);
		Mockito.when(node.getProperty("relVideoTitle").getString()).thenReturn("relVideoTitle");
		videoLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("sectionAltTitle")).thenReturn(true);
		Mockito.when(node.getProperty("sectionAltTitle")).thenReturn(property);
		Mockito.when(node.getProperty("sectionAltTitle").getString()).thenReturn("sectionAltTitle");
		videoLandingServlet.doGet(request, response);
	}
	
}
