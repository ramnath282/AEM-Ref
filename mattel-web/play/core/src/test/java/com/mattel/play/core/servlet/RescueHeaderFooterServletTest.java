package com.mattel.play.core.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ HttpClients.class, PlayHelper.class, PropertyReaderUtils.class, IOUtils.class})
public class RescueHeaderFooterServletTest {

	@InjectMocks
	RescueHeaderFooterServlet rescueHeaderFooterServlet;
	
	@Mock
	private SlingHttpServletRequest request; 
	@Mock
	private SlingHttpServletResponse response;
	
	
	@Test
	public void testDoGetWithError() throws ServletException, IOException{
		PowerMockito.mockStatic(HttpClients.class);
		PowerMockito.mockStatic(PlayHelper.class);
		PowerMockito.mockStatic(PropertyReaderUtils.class);
		PowerMockito.mockStatic(IOUtils.class);
		
		CloseableHttpClient client = Mockito.mock(CloseableHttpClient.class);
		Mockito.when(HttpClients.createDefault()).thenReturn(client);
		Mockito.when(request.getParameter("currentPagePath")).thenReturn("/test/pagepath/rescue-heroes");
		Mockito.when(PlayHelper.getPageLocale(Mockito.anyString())).thenReturn("en-in");
		Mockito.when(request.getParameter("requestFor")).thenReturn("requestFor");
		Mockito.when(PropertyReaderUtils.getRescueHeaderFooterURL()).thenReturn("getRescueHeaderFooterURL");
		
		CloseableHttpResponse httpResponse = Mockito.mock(CloseableHttpResponse.class);
		Mockito.when(client.execute(Mockito.any())).thenReturn(httpResponse);
		StatusLine statusLine = Mockito.mock(StatusLine.class);
		Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
		Mockito.when(statusLine.getStatusCode()).thenReturn(501);
		HttpEntity responseEntity = Mockito.mock(HttpEntity.class);
		Mockito.when(httpResponse.getEntity()).thenReturn(responseEntity);
		InputStream responseContent = Mockito.mock(InputStream.class);
		Mockito.when(responseEntity.getContent()).thenReturn(responseContent);
		PrintWriter writer = Mockito.mock(PrintWriter.class);
		Mockito.when(response.getWriter()).thenReturn(writer);
		rescueHeaderFooterServlet.doGet(request, response);
	}
	
	@Test
	public void testDoGetWithoutError() throws ServletException, IOException{
		PowerMockito.mockStatic(HttpClients.class);
		PowerMockito.mockStatic(PlayHelper.class);
		PowerMockito.mockStatic(PropertyReaderUtils.class);
		PowerMockito.mockStatic(IOUtils.class);
		
		CloseableHttpClient client = Mockito.mock(CloseableHttpClient.class);
		Mockito.when(HttpClients.createDefault()).thenReturn(client);
		Mockito.when(request.getParameter("currentPagePath")).thenReturn("/test/pagepath/rescue-heroes");
		Mockito.when(PlayHelper.getPageLocale(Mockito.anyString())).thenReturn("en-in");
		Mockito.when(request.getParameter("requestFor")).thenReturn("requestFor");
		Mockito.when(PropertyReaderUtils.getRescueHeaderFooterURL()).thenReturn("getRescueHeaderFooterURL");
		
		CloseableHttpResponse httpResponse = Mockito.mock(CloseableHttpResponse.class);
		Mockito.when(client.execute(Mockito.any())).thenReturn(httpResponse);
		StatusLine statusLine = Mockito.mock(StatusLine.class);
		Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
		Mockito.when(statusLine.getStatusCode()).thenReturn(400);
		HttpEntity responseEntity = Mockito.mock(HttpEntity.class);
		Mockito.when(httpResponse.getEntity()).thenReturn(responseEntity);
		InputStream responseContent = Mockito.mock(InputStream.class);
		Mockito.when(responseEntity.getContent()).thenReturn(responseContent);
		Mockito.when(IOUtils.toString(responseContent, StandardCharsets.UTF_8)).thenReturn("");
		PrintWriter writer = Mockito.mock(PrintWriter.class);
		Mockito.when(response.getWriter()).thenReturn(writer);
		rescueHeaderFooterServlet.doGet(request, response);
	}
	
}
