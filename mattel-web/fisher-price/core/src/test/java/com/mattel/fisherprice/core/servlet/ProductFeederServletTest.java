package com.mattel.fisherprice.core.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.fisherprice.core.services.GetResourceResolver;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ URLConnection.class, InputStream.class, InputStreamReader.class, BufferedReader.class, URL.class})
public class ProductFeederServletTest {

	@InjectMocks
	private ProductFeederServlet productFeederServlet;
	@Mock
	private GetResourceResolver getResourceResolver;	 
	@Mock
	private SlingHttpServletRequest slingHttpServletRequest;  
	@Mock
	private SlingHttpServletResponse singHttpServletResponse;
	
	@Mock
	private URLConnection urlConnection;
	
	@Mock
	private InputStream inputStream;
	
	@Mock
	private InputStreamReader inputStreamReader;
	@Mock
	private BufferedReader bufferedReader;
	@Mock
	private URL url;
	@Mock
	ResourceResolver resourceResolver;
	@Before
	public void setup() throws Exception {
	}
	@Test
	@SuppressWarnings("unused")
	public void doGetFeed() throws Exception {
		 final ProductFeederServlet.Config config = Mockito.mock(ProductFeederServlet.Config.class);
		 Mockito.when(config.salsifyEndPointUrl()).thenReturn("https://storage.googleapis.com/test_fp_com/fp_aem_en_US_full.json");
		 Mockito.when(config.damRootPath()).thenReturn("/content/dam/fp-dam/fisher-price/product-feed/");
		 Mockito.when(slingHttpServletRequest.getParameter("feed")).thenReturn("full");
		 Mockito.when(slingHttpServletRequest.getParameter("language")).thenReturn("en_us");
         PowerMockito.whenNew(URL.class).withArguments(Mockito.anyString()).thenReturn(url);
         Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
		 productFeederServlet.activate(config);
		productFeederServlet.doGet(slingHttpServletRequest, singHttpServletResponse);
	}
	
	@Test
	public void doGetDelta() throws IOException, ServletException {
		 final ProductFeederServlet.Config config = Mockito.mock(ProductFeederServlet.Config.class);
		 Mockito.when(config.salsifyEndPointUrl()).thenReturn("https://storage.googleapis.com/test_fp_com/fp_aem_en_US_full.json");
		 Mockito.when(config.damRootPath()).thenReturn("/content/dam/fp-dam/fisher-price/product-feed/");
		 Mockito.when(slingHttpServletRequest.getParameter("feed")).thenReturn("delta");
		 Mockito.when(slingHttpServletRequest.getParameter("language")).thenReturn("en_us");
		 productFeederServlet.activate(config);
		productFeederServlet.doGet(slingHttpServletRequest, singHttpServletResponse);
	}
	@Test
	public void doGetFull() throws IOException, ServletException {
		 final ProductFeederServlet.Config config = Mockito.mock(ProductFeederServlet.Config.class);
		 Mockito.when(config.salsifyEndPointUrl()).thenReturn("https://storage.googleapis.com/test_fp_com/fp_aem_en_US_full.json");
		 Mockito.when(config.damRootPath()).thenReturn("/content/dam/fp-dam/fisher-price/product-feed/");
		 Mockito.when(slingHttpServletRequest.getParameter("feed")).thenReturn("full");
		 Mockito.when(slingHttpServletRequest.getParameter("language")).thenReturn("en_us");
		 productFeederServlet.activate(config);
		productFeederServlet.doGet(slingHttpServletRequest, singHttpServletResponse);
	}
}
