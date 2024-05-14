package com.mattel.play.core.servlet;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.wcm.api.Page;

public class ChildPagesTest {
	ChildPages childPages;
	SlingHttpServletRequest request;
	SlingHttpServletResponse response;
	ResourceResolver resolver;
	Resource resource;
	Page page;
	Iterator<Page> rootPageIterator;
	ValueMap valueMap;
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		childPages = new ChildPages();
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		resolver = Mockito.mock(ResourceResolver.class);
		resource = Mockito.mock(Resource.class);
		page = Mockito.mock(Page.class);
		rootPageIterator = Mockito.mock(Iterator.class);
		valueMap = Mockito.mock(ValueMap.class);
		
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(request.getRequestURI()).thenReturn("/_cq_dialog.html/content/ag/en/home/play/");
		Mockito.when(resolver.getResource("/content/ag/en/home")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
		Mockito.when(page.listChildren(null, false)).thenReturn(rootPageIterator);
		Mockito.when(rootPageIterator.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(rootPageIterator.next()).thenReturn(page);
		Mockito.when(page.getProperties()).thenReturn(valueMap);
		Mockito.when(valueMap.get("jcr:title", String.class)).thenReturn("");
	}
	@Test
	public void doGet() throws ServletException, IOException {
		childPages.doGet(request, response);
	}
}
