package com.mattel.play.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
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
public class CharacterLandingServletTest {
	CharacterLandingServlet characterLandingServlet;
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
	Page homePage;
	TileGalleryAndLandingService tileGalleryAndLandingService;
	List<String> tagList = new ArrayList<>();
	PrintWriter printWriter;
	@Before
	public void setUp () throws PathNotFoundException, RepositoryException, IOException {
		characterLandingServlet = new CharacterLandingServlet();
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		resource = Mockito.mock(Resource.class);
		resolver = Mockito.mock(ResourceResolver.class);
		pageManager = Mockito.mock(PageManager.class);
		property = Mockito.mock(Property.class);
		page = Mockito.mock(Page.class);
		tag = Mockito.mock(Tag.class);
		node = Mockito.mock(Node.class);
		homePage = Mockito.mock(Page.class);
		PowerMockito.mockStatic(PropertyReaderUtils.class);
		PowerMockito.mockStatic(StringUtils.class);
		tileGalleryAndLandingService = Mockito.mock(TileGalleryAndLandingService.class);
		printWriter = Mockito.mock(PrintWriter.class);
		characterLandingServlet.setLandingPageNode("");
		categoryTags = new Tag[2];
		categoryTags [0] = tag;
		categoryTags [1] = tag;
		tilePojo.setAlwaysEnglish("yes");
		tagList.add("hello");
		tilePojo.setTileTags(tagList);
		
		characterList.add(tilePojo);
		characterLandingServlet.setCharacterList(characterList);
		characterLandingServlet.setTileGalleryAndLandingService(tileGalleryAndLandingService);
		Mockito.when(request.getParameter("currentPath")).thenReturn("currentPath");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("currentPath")).thenReturn(resource);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
		Mockito.when(page.getTags()).thenReturn(categoryTags);
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(node.getProperty("orderCharacter")).thenReturn(property);
		Mockito.when(node.getProperty("orderCharacter").toString()).thenReturn("automatic");
		Mockito.when(node.hasProperty("displayCharacters")).thenReturn(true);
		Mockito.when(node.getProperty("displayCharacters")).thenReturn(property);
		Mockito.when(node.getProperty("displayCharacters").getString()).thenReturn("displayCharacters");
		Mockito.when(node.getProperty("columnLayout")).thenReturn(property);
		Mockito.when( tileGalleryAndLandingService.getTilesByDate("", "characters",  null, resolver,true)).thenReturn(characterList);
		Mockito.when(node.getProperty("columnLayout").getString()).thenReturn("automatic");
		Mockito.when(page.getPath()).thenReturn("");
		Mockito.when(PropertyReaderUtils.getCharacterPath()).thenReturn("");
		Mockito.when(homePage.getPath() + PropertyReaderUtils.getCharacterPath()).thenReturn("");
		Mockito.when(tag.getTagID()).thenReturn("hello");
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		Mockito.when(page.getParent()).thenReturn(page);
		Mockito.when(page.getPath()).thenReturn("");
		Mockito.when(StringUtils.isNoneEmpty(page.getPath())).thenReturn(true);
		Mockito.when(PropertyReaderUtils.getCharacterLandingPath()).thenReturn("");
	}
	@Test
	public void doGet() throws ServletException, IOException, ValueFormatException, PathNotFoundException, RepositoryException {
		characterLandingServlet.doGet(request, response);
	}
}
