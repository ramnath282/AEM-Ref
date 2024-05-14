package com.mattel.global.core.services;

import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.adobe.agl.impl.Assert;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.utils.PropertyReaderUtils;
import com.mattel.global.core.utils.PropertyUtils;
import com.mattel.global.core.utils.SiteMapGeneratorUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {PropertyUtils.class,PropertyReaderUtils.class,HttpClients.class,IOUtils.class,DocumentBuilderFactory.class} )
public class SiteMapServiceTest {
	SiteMapService siteMapService;	
	SiteMapGeneratorUtils siteMapGeneratorUtils;
	PropertyReaderUtils propertyReaderUtils;	
	ResourceResolverFactory resourceResolverFactory;
	PropertyUtils propertyUtils;
	String path;
	String siteRootPath;
	String siteDomain;
	ResourceResolver resourceResolver;
	String[] countryMapping = { "en-us:us", "de-de:de", "en-gb:gb", "fr-fr:fr", "it-it:it", "es-es:es" };
	String checkText = "<?xml version=\"1.0\"?><urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"><url><loc>https://mattel-sites-dev64.adobecqms.net/content/fisher-price/de/de-de/home</loc></url><url><loc>https://mattel-sites-dev64.adobecqms.net/content/fisher-price/de/de-de/home/explore</loc></url></urlset>";
	Resource resource;
	String productsPath;
	Node node;
	NodeIterator porductNodes;
	Property property;
	Session session;
	ValueFactory factory;
	Binary binary;
	InputStream is;
	String [] brandDetails;
	String[] brandsMapping;
	HashMap<String,String> map;
	CloseableHttpClient closeableHttpClient;
	CloseableHttpResponse httpResponse;
	StatusLine statusLine;
	HttpEntity httpEntity;	
	Replicator replicator;
	DocumentBuilderFactory docBuilderFactory;
	
	@Before
	public void setUp() throws IOException, RepositoryException, ParserConfigurationException, SAXException {
		siteMapService = new SiteMapService();
		siteMapGeneratorUtils = Mockito.mock(SiteMapGeneratorUtils.class);
		propertyReaderUtils = Mockito.mock(PropertyReaderUtils.class);	
		resourceResolverFactory = Mockito.mock(ResourceResolverFactory.class);
		siteMapService.setSiteMapGeneratorUtils(siteMapGeneratorUtils);
		siteMapService.setPropertyReaderUtils(propertyReaderUtils);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		docBuilderFactory = Mockito.mock(DocumentBuilderFactory.class);
		PowerMockito.mockStatic(PropertyUtils.class);
		PowerMockito.mockStatic(HttpClients.class);
		PowerMockito.mockStatic(IOUtils.class);
		PowerMockito.mockStatic(DocumentBuilderFactory.class);
		path = "/content/dam/fp-dam/fisher-price/sitemap/sitemap_en-us.xml";
		siteRootPath = "/content/fisher-price/";
		siteDomain = "https://mattel-sites-dev64.adobecqms.net";
		productsPath = "/var/commerce/products/fisher-price/en-us";
		resource = Mockito.mock(Resource.class);
		node = Mockito.mock(Node.class);
		property = Mockito.mock(Property.class);
		porductNodes = Mockito.mock(NodeIterator.class);
		session = Mockito.mock(Session.class);
		factory = Mockito.mock(ValueFactory.class);
		binary = Mockito.mock(Binary.class);
		is = Mockito.mock(InputStream.class);
		statusLine = Mockito.mock(StatusLine.class);
		httpEntity = Mockito.mock(HttpEntity.class);
		closeableHttpClient = Mockito.mock(CloseableHttpClient.class);
		httpResponse = Mockito.mock(CloseableHttpResponse.class);
		replicator = Mockito.mock(Replicator.class);
		map = new HashMap<String,String>();
		map.put(Constant.BRAND,"corp");		
		map.put(Constant.ROOT_SITE_MAP_PATH,"/content/dam/corporate-sites/sitemap/rootsitemap.xml");
		map.put(Constant.SITE_ROOT_PATH,"/content/mattel/mattel-corporate/");	
		map.put(Constant.SITE_DOMAIN,"http://localhost:4502/");
		map.put(Constant.SHORT_SITE_DOMAIN,"http://localhost:4502/");
		map.put(Constant.DATA_PATH,productsPath);	
		brandDetails = new String[]{"corp=/content/dam/corporate-sites/sitemap/rootsitemap.xml_/content/mattel/mattel-corporate/_http://localhost:4502/_http://localhost:4502/"};
		brandsMapping = new String[]{"corp:/content/corporate-sites/mattel/"};
		Mockito.when(HttpClients.createDefault()).thenReturn(closeableHttpClient);
		Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
		Mockito.when(statusLine.getStatusCode()).thenReturn(200);		
		Mockito.when(closeableHttpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
		Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
		Mockito.when(httpEntity.getContent()).thenReturn(is);		
		Mockito.when(IOUtils.toString((is),StandardCharsets.UTF_8)).thenReturn(checkText);	
		Mockito.when(siteMapGeneratorUtils.getBrandSiteMapDetails()).thenReturn(brandDetails);
		Mockito.when(PropertyUtils.getBrandDetails(brandDetails[0])).thenReturn(map);
		Mockito.when(propertyReaderUtils.getBrandMappings()).thenReturn(brandsMapping);	
		Mockito.when(PropertyUtils.getValueFromKeyMappings(brandsMapping, "corp")).thenReturn("/content/corporate-sites/mattel/");	
		Mockito.when(siteMapGeneratorUtils.getCountryMapping()).thenReturn(countryMapping);
		Mockito.when(resourceResolver.getResource(productsPath)).thenReturn(resource);
		Mockito.when(resourceResolver.getResource(path)).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(node.getNodes()).thenReturn(porductNodes);
		Mockito.when(porductNodes.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(porductNodes.nextNode()).thenReturn(node);
		Mockito.when(node.hasProperty("productURL")).thenReturn(true);
		Mockito.when(node.getProperty("productURL")).thenReturn(property);
		Mockito.when(node.getProperty("productURL").getString()).thenReturn("/en-us/product/productOne");
		Mockito.when(session.getNode("/content/dam/fp-dam/fisher-price/sitemap/sitemap_en-us.xml/jcr:content/renditions/original/jcr:content")).thenReturn(node);
		Mockito.when(session.getValueFactory()).thenReturn(factory);	
		Mockito.when(factory.createBinary(is)).thenReturn(binary);
		Mockito.when(DocumentBuilderFactory.newInstance()).thenReturn(docBuilderFactory);
		Mockito.doNothing().when(docBuilderFactory).setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		
		DocumentBuilder docBuilder = Mockito.mock(DocumentBuilder.class); 
		Mockito.when(docBuilderFactory.newDocumentBuilder()).thenReturn(docBuilder);
		Document doc = Mockito.mock(Document.class);
		Mockito.when(docBuilder.parse(Mockito.any(InputSource.class))).thenReturn(doc);
		Element url = Mockito.mock(Element.class);
		Mockito.when(doc.createElement(Mockito.anyString())).thenReturn(url);
		Text text = Mockito.mock(Text.class);
		Mockito.when(doc.createTextNode("http://localhost:4502//en-us/product/productOne")).thenReturn(text);
		
		try {
	        Mockito.doNothing().when(replicator).replicate(session, ReplicationActionType.ACTIVATE,
	            path);
	      } catch (final ReplicationException e) {
	        Assert.fail(e);
	      }
		
	}

	@Test(expected = NullPointerException.class)
	public void updateSiteMapFiles()
			throws IOException, RepositoryException, ParserConfigurationException, SAXException, TransformerException, TransformerFactoryConfigurationError, ReplicationException {
			siteMapService.updateSiteMapFiles(map,path,resourceResolver, session);
		
	}

	@Test
	public void isError() {
		siteMapService.isError(200);
	}

	@Test
	public void notIsError() {
		siteMapService.isError(20);
	}

	@Test(expected = NullPointerException.class)
	public void modifySiteMap() throws RepositoryException, ParserConfigurationException, SAXException,
			IOException, TransformerFactoryConfigurationError, TransformerException, ReplicationException {
		siteMapService.modifySiteMap(map,path, checkText, resourceResolver,session);
	}

}
