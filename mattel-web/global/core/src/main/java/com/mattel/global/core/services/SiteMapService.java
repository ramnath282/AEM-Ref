package com.mattel.global.core.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.ValueFormatException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.utils.SiteMapGeneratorUtils;
import com.mattel.global.core.utils.PropertyUtils;
import com.mattel.global.core.utils.PropertyReaderUtils;

@Component(service = SiteMapService.class, immediate = true)
public class SiteMapService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapService.class);
	private static final List<String> BYPASS_NODE_TYPES = Arrays.asList("rep:ACL", "rep:acl");
	@Reference
	SiteMapGeneratorUtils siteMapGeneratorUtils;
	@Reference
	PropertyReaderUtils propertyReaderUtils;
	@Reference
	private Replicator replicator;

	/**
	 * Reading sitemap.xml for AEM physical pages
	 * Added not null, empty condition to avoid getting SAXException
	 *
	 * @param path
	 * @param map
	 * @param resourceResolver
	 * @param session
	 * @throws IOException
	 * @throws RepositoryException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws TransformerFactoryConfigurationError
	 * @throws ReplicationException
	 */
	public void updateSiteMapFiles(Map<String, String> map, String path, ResourceResolver resourceResolver, Session session)
			throws IOException, RepositoryException, ParserConfigurationException, SAXException, TransformerException, TransformerFactoryConfigurationError, ReplicationException {
		LOGGER.info("Start of updateSiteMapFiles method");
		String locale = path.substring(path.indexOf('_') + 1, path.indexOf('.'));
		LOGGER.debug("locale is {}", locale);
		String country = getPageLocaleFromMappings(path);
		LOGGER.debug("country is {}", country);
		String brandBaseContentPath = PropertyUtils.getValueFromKeyMappings(propertyReaderUtils.getBrandMappings(), map.get(Constant.BRAND));
		LOGGER.debug("brandBaseContentPath is {}", brandBaseContentPath);
		String siteRootPath = map.get(Constant.SITE_ROOT_PATH);
		LOGGER.debug("siteRootPath is {}", siteRootPath);
		String siteDomain = map.get(Constant.SITE_DOMAIN);
		LOGGER.debug("siteDomain is {}", siteDomain);
		String contentPath = brandBaseContentPath + country + Constant.SLASH + locale + siteRootPath;
		LOGGER.debug("contentPath is {}", contentPath);
		int contentPathStrLen = contentPath.length();
		LOGGER.debug("contentPathStrLen is {}", contentPathStrLen);
		contentPath = contentPath.charAt(contentPathStrLen-1)=='/' ? contentPath.substring(0, contentPathStrLen-1) : contentPath;
		LOGGER.debug("updated contentPath is {}", contentPath);
		String uri = siteDomain + contentPath + Constant.SITEMAP_DOT_XML;
		LOGGER.debug("uri is {}", uri);
		HttpGet getMethod = new HttpGet(uri);
		try (CloseableHttpClient client = HttpClients.createDefault();) {
			HttpResponse httpResponse = client.execute(getMethod);
			LOGGER.debug("httpResponse is {}", httpResponse);
			int status = httpResponse.getStatusLine().getStatusCode();
			LOGGER.debug("status is {}", status);
			boolean isError = isError(status);
			LOGGER.debug("isError is {}", isError);
			if (!isError) {
				try (InputStream inputStream = httpResponse.getEntity().getContent()) {
					LOGGER.debug("inputStream is {}", inputStream);
					String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
					LOGGER.debug("result : {}", result);
					if (StringUtils.isNotBlank(result)) {
						modifySiteMap(map, path, result, resourceResolver, session);
					}
				}
			}
		}
		LOGGER.info("End of updateSiteMapFiles method");
	}

	/**
	 * After adding products xml path to AEM physical pages generated xml (existing xml), updating jcr:data with the latest xml & publish it for every Locale
	 *
	 * @param path
	 * @param result
	 * @param resourceResolver
	 * @param map
	 * @param session
	 * @throws RepositoryException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 * @throws ReplicationException
	 */
	public void modifySiteMap(Map<String, String> map, String path, String result, ResourceResolver resourceResolver, Session session) throws RepositoryException, ParserConfigurationException, SAXException,
	IOException, TransformerFactoryConfigurationError, TransformerException, ReplicationException {
		LOGGER.info("Start modifySiteMap method");
		String stemapXmlData = fetchCompleteData(map, resourceResolver, result);
		LOGGER.debug("stemapXmlData is {}", stemapXmlData);
		Resource localeSitemapResource = resourceResolver.getResource(path);
		if (Objects.nonNull(localeSitemapResource) && Objects.nonNull(session) && Objects.nonNull(stemapXmlData)) {
			LOGGER.debug("localeSitemapResource,stemapXmlData and session are non null in modifySiteMap() method for SiteMapService class");
			Node originalNode = session.getNode(path + Constant.SITEMAP_JCR_DATA);
			LOGGER.debug("originalNode is {}", originalNode);
			ValueFactory factory = session.getValueFactory();
			LOGGER.debug("factory is {}", factory);
			try (InputStream is = new ByteArrayInputStream(stemapXmlData.getBytes(StandardCharsets.UTF_8))) {
				LOGGER.debug("InputStream is {}", is);
				Binary binary = factory.createBinary(is);
				LOGGER.debug("binary is {}", binary);
				Value value = factory.createValue(binary);
				LOGGER.debug("value is {}", value);
				originalNode.setProperty("jcr:data", value);
				session.save();
				replicator.replicate(session, ReplicationActionType.ACTIVATE, path);
				LOGGER.debug("Replication for locale {}", path);
			}
		}
		LOGGER.info("End modifySiteMap method");
	}

	/**
	 * Adding products to respective locale like en-us, it-it
	 *
	 * @param map
	 * @param resourceResolver
	 * @param result
	 * @return
	 * @throws RepositoryException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	private String fetchCompleteData(Map<String, String> map, ResourceResolver resourceResolver, String result) throws RepositoryException, ParserConfigurationException, SAXException, IOException,
	TransformerFactoryConfigurationError, TransformerException {
		LOGGER.info("Start fetchCompleteData method");
		String completeSiteMapDataStr = StringUtils.EMPTY;
		try {
			Document resultDocWithSites = prepareXmlDocument(result);
			LOGGER.debug("resultDocWithSitesWithSites is {}", resultDocWithSites);
			completeSiteMapDataStr = checkForProductData(resourceResolver, map, resultDocWithSites);
			LOGGER.debug("completeSiteMapDataStr is {}", completeSiteMapDataStr);
		} catch(Exception e) {
			LOGGER.error("Exception occurred in fetchCompleteData() method for SiteMapService class", e);
		}
		LOGGER.info("End fetchCompleteData method");			
		return completeSiteMapDataStr;
	}

	/**
	 * Checking if the productsPath and the resource for that path exists. If so then calls addProductData methods to populate Products data
	 *
	 * @param map
	 * @param resourceResolver
	 * @param result
	 * @return
	 * @throws PathNotFoundException 
	 * @throws ValueFormatException 
	 * @throws RepositoryException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	private String checkForProductData(ResourceResolver resourceResolver, Map<String, String> map, Document resultDocWithSites)
			throws RepositoryException, TransformerException {
		LOGGER.info("Start of checkForProductData method");
		String brandName = map.get(Constant.BRAND);
		LOGGER.debug("brandName is {}", brandName);
		String productsPath = map.get(Constant.DATA_PATH);
		LOGGER.debug("productsPath is {}", productsPath);
		String shortUrlSiteDomain = map.get(Constant.SHORT_SITE_DOMAIN);
		LOGGER.debug("shortUrlSiteDomain {}", shortUrlSiteDomain);
		String productResult = StringUtils.EMPTY;
		try {
			if(Objects.nonNull(productsPath)) {
				Resource productsResource = resourceResolver.getResource(productsPath);
				if (Objects.nonNull(productsResource)) {
					LOGGER.debug("productsResource is non-null in addProductData() method for SiteMapService class");
					Node productsNode = productsResource.adaptTo(Node.class);
					if (Objects.nonNull(productsNode)) {
						LOGGER.debug("productsNode is non-null and its string representation is {}", productsNode);
						addProductData(productsNode, resultDocWithSites, shortUrlSiteDomain, brandName);
					}
				}
			}
			TransformerFactory factory = TransformerFactory.newInstance();
			factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			Transformer transformer = factory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter newXml = new StringWriter();
			transformer.transform(new DOMSource(resultDocWithSites), new StreamResult(newXml));
			productResult =  newXml.getBuffer().toString();			
		} catch(Exception e) {
			LOGGER.error("Exception occurred in fetchCompleteData() method for SiteMapService class", e);
		}
		LOGGER.info("End of checkForProductData method");
		return productResult;
	}

	/**
	 * Adding products to respective locale like en-us, it-it..
	 *
	 * @param Node
	 * @param Document
	 * @param String
	 * @param String
	 * @return
	 * @throws RepositoryException
	 */
	private void addProductData(Node productsNode, Document resultDocWithSites, String shortUrlSiteDomain, String brandName) throws RepositoryException {
		LOGGER.info("Start of addProductData method");
		String uri = null;
		try {
			NodeIterator porductNodes = productsNode.getNodes();
			Element root = resultDocWithSites.getDocumentElement();
			LOGGER.debug("root is {}", root);
			while (porductNodes.hasNext()) {
				Node childNode = porductNodes.nextNode();
				boolean bypassNodeOnType = checkNodeBypass(childNode);
				LOGGER.debug("childNode {}", childNode);
				
				if (bypassNodeOnType) {
				  LOGGER.debug("Bypassing childNode {}, as it belongs to restricted type", childNode);
				  continue;
				}
				
				if (childNode.hasProperty("productURL")) {
					uri = childNode.getProperty("productURL").getString();
					LOGGER.debug("for PRODUCT URL - uri {}", uri);
				} else if (StringUtils.equals(brandName, "corp")) {
					uri = Constant.NEWS.concat(childNode.getName());
					LOGGER.debug("for brand CORP - uri {}", uri);
				}
				if (StringUtils.isNotEmpty(uri)) {
					Element url = resultDocWithSites.createElement("url");
					LOGGER.debug("url is {}", url);
					Element loc = resultDocWithSites.createElement("loc");
					LOGGER.debug("loc is {}", loc);
					loc.appendChild(resultDocWithSites.createTextNode(shortUrlSiteDomain + uri));
					url.appendChild(loc);
					LOGGER.debug("updated url is {}", url);
					Element changefreq = resultDocWithSites.createElement("changefreq");
					LOGGER.debug("changefreq is {}", changefreq);
					changefreq.appendChild(resultDocWithSites.createTextNode("daily"));
					LOGGER.debug("updated changefreq is {}", changefreq);
					url.appendChild(changefreq);
					Element priority = resultDocWithSites.createElement("priority");
					LOGGER.debug("priority is {}", priority);
					priority.appendChild(resultDocWithSites.createTextNode("0.9"));
					LOGGER.debug("updated priority is {}", priority);
					url.appendChild(priority);
					LOGGER.debug("final url is {}", url);
					root.appendChild(url);
					LOGGER.debug("final root is {}", root);
				}
			}			
		} catch(Exception e) {
			LOGGER.error("Exception occurred in addProductData() method for SiteMapService class", e);
		}
		LOGGER.info("End of addProductData method");
	}

	/**
	 * Bypasses node if node type is present in {@link #BYPASS_NODE_TYPES}
	 * @param childNode
	 *           The node under check.
	 * @return True if node is to be bypassed, otherwise false.
	 */
  private Boolean checkNodeBypass(Node childNode) {
    return Optional.ofNullable(childNode).map(node -> {
        try {
          if (node.hasProperty(JcrConstants.JCR_PRIMARYTYPE))  {
            Value type = node.getProperty(JcrConstants.JCR_PRIMARYTYPE).getValue();
            
            if (Objects.nonNull(type) && BYPASS_NODE_TYPES.contains(type.getString())) {
              return true;
            }
          }
        } catch (Exception e) {
          LOGGER.error("Unable to determine node type", e);
        }
      
        return false;
    }).orElse(false);
  }

	/**
	 * checking response status
	 *
	 * @param status
	 * @return
	 */
	public boolean isError(int status) {
		if (status == 200 || status == 201 || status == 204 || status == 400 || status == 401) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * getting locale for en-us, it will be us
	 *
	 * @param currentPagePath
	 * @return
	 */
	private String getPageLocaleFromMappings(String currentPagePath) {
		LOGGER.info("Start getPageLocaleFromMappings method");
		String[] countryMappings = siteMapGeneratorUtils.getCountryMapping();
		LOGGER.debug("countryMappings is {}", countryMappings);
		if (Objects.nonNull(countryMappings) && countryMappings.length > 0) {
			for (String mapping : countryMappings) {
				if (mapping.contains(":") && mapping.split(":").length > 1) {
					String locale = mapping.split(":")[0];
					LOGGER.debug("locale is {}", locale);
					String country = mapping.split(":")[1];
					LOGGER.debug("country is {}", country);
					if (currentPagePath.contains("_" + locale)) {
						LOGGER.debug("currentPagePath contains _locale");
						return country;
					}
				}
			}
		}
		LOGGER.info("End getPageLocaleFromMappings method");
		return StringUtils.EMPTY;
	}


	/**
	 * preparing xml document from the input string
	 *
	 * @param checkText
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document prepareXmlDocument(String checkText)
			throws ParserConfigurationException, SAXException, IOException {
		LOGGER.info("Start prepareXmlDocument method");
		Document doc = null;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new InputSource(new StringReader(checkText)));
			LOGGER.debug("doc is {}", doc);
			doc.getDocumentElement().normalize();
			LOGGER.debug("noramlized doc is {}", doc);
		} catch(Exception e) {
			LOGGER.error("Exception occurred in prepareXmlDocument() method for SiteMapService class", e);
		}
		LOGGER.info("End prepareXmlDocument method");
		return doc;
	}

	public void setSiteMapGeneratorUtils(SiteMapGeneratorUtils siteMapGeneratorUtils) {
		this.siteMapGeneratorUtils = siteMapGeneratorUtils;
	}

	public void setPropertyReaderUtils(PropertyReaderUtils propertyReaderUtils) {
		this.propertyReaderUtils = propertyReaderUtils;
	}
}
