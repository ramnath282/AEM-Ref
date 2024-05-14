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
import javax.jcr.ValueFormatException;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.pojos.TilePojo;
import com.mattel.play.core.services.TileGalleryAndLandingService;
import com.mattel.play.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StringUtils.class, PropertyReaderUtils.class})
public class GameLandingServletTest {
	GameLandingServlet gameLandingServlet;
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
	List<TilePojo> characterList = new ArrayList<>();
	TilePojo tilePojo = new TilePojo();
	TileGalleryAndLandingService tileGalleryAndLandingService;
	List<String> tagList = new ArrayList<>();
	PrintWriter printWriter;
	ValueMap valueMap;
	NodeIterator categories;
	@Before
	public void setUp() throws PathNotFoundException, RepositoryException, IOException {
		gameLandingServlet = new GameLandingServlet();
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		resource = Mockito.mock(Resource.class);
		resolver = Mockito.mock(ResourceResolver.class);
		pageManager = Mockito.mock(PageManager.class);
		property = Mockito.mock(Property.class);
		page = Mockito.mock(Page.class);
		tag = Mockito.mock(Tag.class);
		node = Mockito.mock(Node.class);
		PowerMockito.mockStatic(PropertyReaderUtils.class);
		PowerMockito.mockStatic(StringUtils.class);
		tileGalleryAndLandingService = Mockito.mock(TileGalleryAndLandingService.class);
		printWriter = Mockito.mock(PrintWriter.class);
		valueMap = Mockito.mock(ValueMap.class);
		categories = Mockito.mock(NodeIterator.class);
		categoryTags = new Tag[2];
		categoryTags [0] = tag;
		categoryTags [1] = tag;
		tilePojo.setAlwaysEnglish("yes");
		tagList.add("hello");
		tilePojo.setTileTags(tagList);
		
		characterList.add(tilePojo);
		gameLandingServlet.setTileGalleryAndLandingService(tileGalleryAndLandingService);
		Mockito.when(request.getParameter("currentPath")).thenReturn("currentPath");
		Mockito.when(request.getParameter("currentPagePath")).thenReturn("currentPagePath");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("currentPath")).thenReturn(resource);
		Mockito.when(resolver.getResource("currentPagePath")).thenReturn(resource);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		Mockito.when(valueMap.get("orderGame", String.class)).thenReturn("automatic");
		Mockito.when(valueMap.get("displayGames", String.class)).thenReturn("displayGames");
		Mockito.when(valueMap.get("allLabel", String.class)).thenReturn("allLabel");
		Mockito.when(valueMap.get("aligntitle", String.class)).thenReturn("aligntitle");
		Mockito.when(valueMap.get("sectionAltTitle", String.class)).thenReturn("sectionAltTitle");
		Mockito.when(page.getPath()).thenReturn("");
		Mockito.when(PropertyReaderUtils.getCharacterPath()).thenReturn("");
		Mockito.when(page.getPath() + PropertyReaderUtils.getGamePath()).thenReturn("");
		Mockito.when( tileGalleryAndLandingService.getTilesByDate("", "games",  null, resolver,true)).thenReturn(characterList);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(node.hasNode("categoryDetail")).thenReturn(true);
		Mockito.when(node.getNode("categoryDetail")).thenReturn(node);
		Mockito.when(node.getNodes()).thenReturn(categories);
		Mockito.when(categories.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(categories.nextNode()).thenReturn(node);
		Mockito.when(node.getProperty("category")).thenReturn(property);
		Mockito.when(node.getProperty("category").getString()).thenReturn("category");
		Mockito.when(node.getProperty("category") != null
						? node.getProperty("category").getString()
						: "").thenReturn("category");
		Mockito.when(pageManager.getPage("category")).thenReturn(page);
		Mockito.when(page.getTitle()).thenReturn("title");
		Mockito.when(page.getTags()).thenReturn(categoryTags);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
	}
	@Test
	public void doGet() throws ServletException, IOException, ValueFormatException, PathNotFoundException, RepositoryException {
		gameLandingServlet.doGet(request, response);
	}
}
