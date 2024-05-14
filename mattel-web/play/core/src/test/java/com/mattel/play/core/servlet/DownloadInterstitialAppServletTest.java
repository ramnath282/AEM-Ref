package com.mattel.play.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.play.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StringUtils.class, PropertyReaderUtils.class})
public class DownloadInterstitialAppServletTest {
	DownloadInterstitialAppServlet downloadInterstitialAppServlet;
	SlingHttpServletRequest request; 
	SlingHttpServletResponse response;
	Resource resource;
	ResourceResolver resolver;
	ResourceResolverFactory resolverFactory;
	Node node;
	NodeIterator itr;
	Property property;
	PrintWriter printWriter;
	
	@Before
	public void setUp() throws RepositoryException, IOException {
		downloadInterstitialAppServlet = new DownloadInterstitialAppServlet();
		request = Mockito.mock(SlingHttpServletRequest.class);
		response = Mockito.mock(SlingHttpServletResponse.class);
		resource = Mockito.mock(Resource.class);
		resolver = Mockito.mock(ResourceResolver.class);
		PowerMockito.mockStatic(PropertyReaderUtils.class);
		node = Mockito.mock(Node.class);
		resolverFactory = Mockito.mock(ResourceResolverFactory.class);
		itr = Mockito.mock(NodeIterator.class);
		property = Mockito.mock(Property.class);
		printWriter = Mockito.mock(PrintWriter.class);
		
		
		Mockito.when(request.getParameter("downloadPath")).thenReturn("downloadPath");
		Mockito.when(PropertyReaderUtils.getDownloadInterstitialApp()).thenReturn("");
		Mockito.when(request.getParameter("downloadId")).thenReturn("downloadId_10_1_5_5");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("downloadPath")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(node.getNodes()).thenReturn(itr);
		Mockito.when(itr.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(itr.getPosition()).thenReturn(10L);
		Mockito.when( itr.nextNode()).thenReturn(node);
		Mockito.when(node.getProperty("interstitialLogo")).thenReturn(property);
		Mockito.when(node.getProperty("interstitialLogo").getString()).thenReturn("");
		Mockito.when(node.getProperty("interstitialUrl")).thenReturn(property);
		Mockito.when(node.getProperty("interstitialUrl").getString()).thenReturn("");
		Mockito.when(node.getProperty("interstitialLogoAlt")).thenReturn(property);
		Mockito.when(node.getProperty("interstitialLogoAlt").getString()).thenReturn("");
		Mockito.when(node.getProperty("interstitialTarget")).thenReturn(property);
		Mockito.when(node.getProperty("interstitialTarget").getString()).thenReturn("");
		Mockito.when(response.getWriter()).thenReturn(printWriter);
	}
	@Test
	public void doGet() throws ServletException, IOException, ValueFormatException, PathNotFoundException, RepositoryException {
		downloadInterstitialAppServlet.doGet(request, response);
	}
	
	@Test
	public void doGetCatchNullPointerException() throws IOException, ServletException{
		
		Mockito.when(response.getWriter()).thenThrow(IOException.class);
		downloadInterstitialAppServlet.doGet(request, response);
	}
}
