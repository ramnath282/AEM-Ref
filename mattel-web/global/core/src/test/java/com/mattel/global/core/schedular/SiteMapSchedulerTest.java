package com.mattel.global.core.schedular;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;

import javax.jcr.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.scheduler.Scheduler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.schedulars.SiteMapScheduler;
import com.mattel.global.core.services.GetResourceResolver;
import com.mattel.global.core.services.SiteMapService;
import com.mattel.global.core.utils.PropertyUtils;
import com.mattel.global.core.utils.SiteMapGeneratorUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {PropertyUtils.class})
public class SiteMapSchedulerTest {
	

	@InjectMocks
	SiteMapScheduler siteMapSchedular; 
	
	@Mock
    Scheduler scheduler;
	
	@Mock
	GetResourceResolver getResourceResolver;
	
	@Mock
	SiteMapGeneratorUtils siteMapGeneratorUtils;
	
	@Mock
	SiteMapService siteMapService;		
	
	String [] brandDetails;
	
	@Mock
	Resource resource;
	
	@Mock 
	Asset asset;
	
	@Mock
	Session session;
	
	@Mock
	Rendition rendition;
	
	@Mock
	Document document;
	
	@Mock
	NodeList nodeList;	
	
	Node node;
	
	@Before
	public void setup() throws ParserConfigurationException, SAXException, IOException {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put(Constant.BRAND,"corp");		
		map.put(Constant.ROOT_SITE_MAP_PATH,"/content/dam/corporate-sites/sitemap/rootsitemap.xml");
		map.put(Constant.SITE_ROOT_PATH,"/content/mattel/mattel-corporate/");	
		map.put(Constant.SITE_DOMAIN,"http://localhost:4502/");
		map.put(Constant.SHORT_SITE_DOMAIN,"http://localhost:4502/");
		map.put(Constant.DATA_PATH,"/content/dam/mattel/mattel-corporate/");
		String xmlText = "<?xml version=\"1.0\"?><urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"><url><loc>https://mattel-sites-dev64.adobecqms.net/content/fisher-price/de/de-de/home</loc><path>/var/commerce/products/fisher-price/de-de</path></url></urlset>";
		brandDetails = new String[]{"corp=/content/dam/corporate-sites/sitemap/rootsitemap.xml_/content/mattel/mattel-corporate/_http://localhost:4502/_http://localhost:4502/"};
		PowerMockito.mockStatic(PropertyUtils.class);
		Mockito.when(siteMapGeneratorUtils.getBrandSiteMapDetails()).thenReturn(brandDetails);
		Mockito.when(PropertyUtils.getBrandDetails(brandDetails[0])).thenReturn(map);
		final ResourceResolver rr = Mockito.mock(ResourceResolver.class);
		Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(rr);
		Mockito.when(rr.getResource(map.get("rootSitemapPath").toString())).thenReturn(resource);
		Mockito.when(resource.adaptTo(Asset.class)).thenReturn(asset);
		Mockito.when(rr.adaptTo(Session.class)).thenReturn(session);
		Mockito.when(asset.getOriginal()).thenReturn(rendition);
		final InputStream inputstream = new ByteArrayInputStream(xmlText.getBytes());
		Mockito.when(rendition.getStream()).thenReturn(inputstream);	
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(new InputSource(new StringReader(xmlText)));
		doc.getDocumentElement().normalize();
		
		try {
			Mockito.when(siteMapService.prepareXmlDocument(xmlText)).thenReturn(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {			
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void testRun() {
		siteMapSchedular.run();
	}
	
	@Test
	@Ignore
	public void testException() throws ParserConfigurationException, SAXException, IOException {
		String xmlText = "<?xml version=\"1.0\"?><urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"><url><loc>https://mattel-sites-dev64.adobecqms.net/content/fisher-price/de/de-de/home</loc><path>/var/commerce/products/fisher-price/de-de</path></url></urlset>";
		Mockito.when(siteMapService.prepareXmlDocument(xmlText)).thenReturn(document);	
		Mockito.when(document.getElementsByTagName("url")).thenThrow(SAXException.class);
		siteMapSchedular.run();
	}
}
