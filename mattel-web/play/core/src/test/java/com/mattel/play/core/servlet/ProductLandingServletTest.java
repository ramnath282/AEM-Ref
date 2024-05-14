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
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.pojos.ProductTilePojo;
import com.mattel.play.core.pojos.TilePojo;
import com.mattel.play.core.services.ProductGalleryAndLandingService;
import com.mattel.play.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StringUtils.class, PropertyReaderUtils.class})
public class ProductLandingServletTest {
	ProductLandingServlet productLandingServlet;
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
	List<String> tagList = new ArrayList<>();
	PrintWriter printWriter;
	ValueMap valueMap;
	NodeIterator categories;
	TagManager tagManager;
	Value value;
	List<ProductTilePojo> productsList = new ArrayList<>();
	ProductTilePojo productTilePojo = new ProductTilePojo();
	ProductGalleryAndLandingService productGalleryAndLandingService;
	@Before
	public void setUp () throws RepositoryException, IOException {
		productLandingServlet = new ProductLandingServlet();
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
		printWriter = Mockito.mock(PrintWriter.class);
		valueMap = Mockito.mock(ValueMap.class);
		categories = Mockito.mock(NodeIterator.class);
		tagManager = Mockito.mock(TagManager.class);
		value = Mockito.mock(Value.class);
		productGalleryAndLandingService = Mockito.mock(ProductGalleryAndLandingService.class);
		categoryTags = new Tag[2];
		categoryTags [0] = tag;
		categoryTags [1] = tag;
		tilePojo.setAlwaysEnglish("yes");
		tagList.add("hello");
		tilePojo.setTileTags(tagList);
		
		characterList.add(tilePojo);
		productTilePojo.setAlwaysEnglish("alwaysEnglish");
		productsList.add(productTilePojo);
		productLandingServlet.setProductGalleryAndLandingService(productGalleryAndLandingService);
		productLandingServlet.setProductThumbnailGridNode("");
		
		Mockito.when(request.getParameter("currentPagePath")).thenReturn("");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
		Mockito.when(resolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		Mockito.when(request.getParameter("category")).thenReturn("category");
		Mockito.when(tagManager.resolve(request.getParameter("category"))).thenReturn(tag);
		Mockito.when(page.getContentResource()).thenReturn(resource);
		Mockito.when(page.getContentResource().getResourceType()).thenReturn("");
		Mockito.when(page.getParent()).thenReturn(page);
		Mockito.when(StringUtils.isNoneEmpty(page.getPath())).thenReturn(true);
		Mockito.when(resolver.getResource(page.getPath() + "/jcr:content/root")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(node.getNodes()).thenReturn(categories);
		Mockito.when(categories.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(categories.nextNode()).thenReturn(node);
		Mockito.when(node.hasProperty("sling:resourceType")).thenReturn(true);
		Mockito.when(node.getProperty("sling:resourceType")).thenReturn(property);
		Mockito.when(node.getProperty("sling:resourceType").getValue()).thenReturn(value);
		Mockito.when(node.getProperty("sling:resourceType").getValue().toString()).thenReturn("cq/experience-fragments/editor/components/experiencefragment");
		Mockito.when(StringUtils.isNotBlank("")).thenReturn(true);
		Mockito.when(resolver.getResource("" + "/jcr:content/root")).thenReturn(resource);
		Mockito.when(node.hasNode("productthumbnailgrid")).thenReturn(true);
		Mockito.when(node.getNode("productthumbnailgrid")).thenReturn(node);
		Mockito.when(node.getNode("productthumbnailgrid").getPath()).thenReturn("");
		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		Mockito.when(valueMap.get("orderProduct", String.class)).thenReturn("automatic");
		Mockito.when(valueMap.get("columnConfig", String.class)).thenReturn("columnConfig");
		Mockito.when(valueMap.get("displayProducts", String.class)).thenReturn("displayProducts");
		Mockito.when(valueMap.get("allLabel", String.class)).thenReturn("allLabel");
		Mockito.when(valueMap.get("pages", String.class)).thenReturn("pages");
		Mockito.when(valueMap.get("titleAlign", String.class)).thenReturn("titleAlign");
		Mockito.when(valueMap.get("sectionAltTitle", String.class)).thenReturn("sectionAltTitle");
		Mockito.when(valueMap.get("alwaysEnglish", String.class)).thenReturn("alwaysEnglish");
		/*Mockito.when(page.getPath()).thenReturn("hello");
		Mockito.when(PropertyReaderUtils.getProductPath()).thenReturn("");
		Mockito.when(page.getPath() + PropertyReaderUtils.getProductPath()).thenReturn("");
		*/
		Mockito.when(productGalleryAndLandingService.getTilesByDate("", true)).thenReturn(productsList);
		Mockito.when(response.getWriter()).thenReturn(printWriter);
		
	}
	@Test
	public void doGet() throws ServletException, IOException, ValueFormatException, PathNotFoundException, RepositoryException {
		productLandingServlet.doGet(request, response);
	}
	@Test
	public void setProperty () throws ServletException, IOException, RepositoryException {
		productLandingServlet.doGet(request, response);
		Mockito.when(node.hasProperty("fragmentPath")).thenReturn(true);
		Mockito.when(node.getProperty("fragmentPath")).thenReturn(property);
		Mockito.when(node.getProperty("fragmentPath").getValue()).thenReturn(value);
		Mockito.when(node.getProperty("fragmentPath").getValue().toString()).thenReturn("cq/experience-fragments/editor/components/experiencefragment");
	}
}
