package com.mattel.play.core.model;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
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

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StringUtils.class})
public class ProductGridModelTest {
	ProductGridModel productGridModel;
	SlingHttpServletRequest request;
	Resource resource;
	String pagePath = "";
	ResourceResolver resolver;
	PageManager pageManager;
	Page currentPage;
	NodeIterator iter;
	Node currPageRootNode;
	Property property;
	Value value;
	ValueMap valueMap;
	
	@Before
	public void setUp() throws RepositoryException {
		productGridModel = new ProductGridModel();
		request = Mockito.mock(SlingHttpServletRequest.class);
		resource = Mockito.mock(Resource.class);
		resolver = Mockito.mock(ResourceResolver.class);
		pageManager = Mockito.mock(PageManager.class);
		currentPage = Mockito.mock(Page.class);
		iter = Mockito.mock(NodeIterator.class);
		currPageRootNode = Mockito.mock(Node.class);
		property = Mockito.mock(Property.class);
		value = Mockito.mock(Value.class);
		valueMap = Mockito.mock(ValueMap.class);
		PowerMockito.mockStatic(StringUtils.class);
		productGridModel.setPagePath(pagePath);
		productGridModel.setRequest(request);
		productGridModel.setProductThumbnailGridNodePath("");
		Mockito.when(request.getResource()).thenReturn(resource);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage(pagePath)).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(5)).thenReturn(currentPage);
		Mockito.when(resource.getResourceResolver().getResource("nullnull/jcr:content/root")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(currPageRootNode);
		Mockito.when(currPageRootNode.getNodes()).thenReturn(iter);
		Mockito.when(iter.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(iter.nextNode()).thenReturn(currPageRootNode);
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(valueMap);
		Mockito.when(StringUtils.isNotBlank("")).thenReturn(true);
		Mockito.when(resolver.getResource("" + "/jcr:content/root/")).thenReturn(resource);
		Mockito.when(currPageRootNode.hasNode("productthumbnailgrid")).thenReturn(true);
		Mockito.when(currPageRootNode.getNode("productthumbnailgrid")).thenReturn(currPageRootNode);
		Mockito.when(currPageRootNode.getNode("productthumbnailgrid").getPath()).thenReturn("");
	}
	@Test
	public void init() {
		productGridModel.init();
	}
	@Test
	public void whenConstants() throws RepositoryException {
		Mockito.when(currPageRootNode.hasProperty("sling:resourceType")).thenReturn(true);
		Mockito.when(currPageRootNode.getProperty("sling:resourceType")).thenReturn(property);
		Mockito.when(currPageRootNode.getProperty("sling:resourceType").getValue()).thenReturn(value);
		Mockito.when(currPageRootNode.getProperty("sling:resourceType").getValue().toString()).thenReturn("cq/experience-fragments/editor/components/experiencefragment");
		productGridModel.init();
		Mockito.when(currPageRootNode.hasProperty("fragmentPath")).thenReturn(true);
		Mockito.when(currPageRootNode.getProperty("fragmentPath")).thenReturn(property);
		Mockito.when(currPageRootNode.getProperty("fragmentPath").getValue()).thenReturn(value);
		Mockito.when(currPageRootNode.getProperty("fragmentPath").getValue().toString()).thenReturn("fragmentPath");
		
	}
	@Test
	public void getLandingBackOption() {
		productGridModel.getLandingBackOption();
	}
	@Test
	public void setLandingBackOption() {
		productGridModel.setLandingBackOption("");
	}
	@Test
	public void getLandingBgReference() {
		productGridModel.getLandingBgReference();
	}
	@Test
	public void setLandingBgReference() {
		productGridModel.setLandingBgReference("");
	}
	@Test
	public void getLandingBgColor() {
		productGridModel.getLandingBgColor();
	}
	@Test
	public void setLandingBgColor() {
		productGridModel.setLandingBgColor("");
	}
}
