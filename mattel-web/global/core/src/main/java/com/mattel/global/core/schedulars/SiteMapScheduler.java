package com.mattel.global.core.schedulars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.day.cq.dam.api.Asset;
import com.day.cq.replication.ReplicationException;
import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.services.GetResourceResolver;
import com.mattel.global.core.services.SiteMapService;
import com.mattel.global.core.utils.PropertyUtils;
import com.mattel.global.core.utils.SiteMapGeneratorUtils;

@Designate(ocd = SiteMapScheduler.Config.class)
@Component(service = Runnable.class, immediate = true)
public class SiteMapScheduler implements Runnable {
	
	@ObjectClassDefinition(name = "Global Sitemap Scheduler", description = "A Scheduler to generate the sitemap dialy")
	public @interface Config {
		@AttributeDefinition(name = "Cron-job expression", description = "Expression stands for sec min hour monthDay month weekday year")
		String scheduler_expression() default "0 0 0 1/1 * ? *";

		@AttributeDefinition(name = "Concurrent task", description = "Whether or not to schedule this task concurrently")
		boolean scheduler_concurrent() default false;
	}

	@Reference
	private GetResourceResolver getResourceResolver;
	@Reference
	SiteMapGeneratorUtils siteMapGeneratorUtils;
	@Reference
	SiteMapService siteMapService;		
	Session session;	
	String [] brandSiteMapConfigs;	
	Map<String,String> map;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SiteMapScheduler.class);
	
	@Activate
	protected void activate(final Config config) {
		/**
		 * Have kept this method empty as we need it
		 */
	}

	@Override
	public void run() {
		LOGGER.info("SiteMapScheduler init method started");
		try (ResourceResolver resourceResolver = getResourceResolver.getResourceResolver()) {
			LOGGER.debug("serviceuser of resourceResolver object is {}", resourceResolver.getUserID());
			brandSiteMapConfigs = siteMapGeneratorUtils.getBrandSiteMapDetails();
			LOGGER.debug("brandSiteMapConfigs is {}", brandSiteMapConfigs);
			for(String brandSiteMapConfig : brandSiteMapConfigs) {				
				map = PropertyUtils.getBrandDetails(brandSiteMapConfig);
				LOGGER.debug("map is {}", map);
				if (Objects.nonNull(resourceResolver) && ! map.isEmpty()) {	
					LOGGER.debug("resourceResolver is not null in run() method for SiteMapScheduler class");
					LOGGER.debug("Sitemap Scheduler {}:{}", map.get(Constant.ROOT_SITE_MAP_PATH),  map.get(Constant.SITE_ROOT_PATH));
					Resource rootPathResource = resourceResolver.getResource(map.get("rootSitemapPath"));
					if (Objects.nonNull(rootPathResource)) {
						LOGGER.debug("rootPathResource is not null in run() method for SiteMapScheduler class");
						updateSiteMaps(rootPathResource, resourceResolver);
					}
				}
			}			
		} catch (ParserConfigurationException | SAXException | IOException | RepositoryException | TransformerException | TransformerFactoryConfigurationError | ReplicationException e) {
			LOGGER.error("Exception Occurred: {}", e.getMessage());
		}finally {
			Optional.ofNullable(session).ifPresent(s -> {
				if(s.isLive()) {
					s.logout();
					LOGGER.debug("Session Logged Out");
				}
			});
		}
		LOGGER.info("SiteMapScheduler end method");
	}	

	/**
	 * Reading list of locals that needs sitemap generation, iterating the list and update jcr:data for each local with latest/updated sitemap.xml
	 * 
	 * @param rootPathResource
	 * @param resourceResolver 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws RepositoryException
	 * @throws TransformerException
	 * @throws TransformerFactoryConfigurationError
	 * @throws ReplicationException
	 */
	private void updateSiteMaps(Resource rootPathResource, ResourceResolver resourceResolver)
			throws ParserConfigurationException, SAXException, IOException, RepositoryException, TransformerException,
			TransformerFactoryConfigurationError, ReplicationException {
		LOGGER.info("updateSiteMaps method started");
		LOGGER.debug("Sitemap Scheduler {}", rootPathResource.getPath());	
		Asset asset = rootPathResource.adaptTo(Asset.class);
		session = resourceResolver.adaptTo(Session.class);
		if (Objects.nonNull(asset)) {	
			LOGGER.debug("session is not null in run() updateSiteMaps for SiteMapScheduler class");	
			try (InputStream in = asset.getOriginal().getStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
				LOGGER.debug("InputStream in is {}", in);
				LOGGER.debug("reader is {}", reader);
				String checkText = reader.lines().collect(Collectors.joining(System.lineSeparator()));
				LOGGER.debug("SiteMap Locale URL{}", checkText);
				Document doc = siteMapService.prepareXmlDocument(checkText);
				LOGGER.debug("doc is {}", doc);
				NodeList locales = doc.getElementsByTagName("url");
				int localesCount = locales.getLength();
				LOGGER.debug("localesCount is {}", localesCount);
				for (int i = 0; i < localesCount; i++) {
					org.w3c.dom.Node currentLocale = locales.item(i);
					if (currentLocale.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
						Element locElement = (Element) currentLocale;
						LOGGER.debug("locElement is {}", locElement);
						NodeList urlList = locElement.getElementsByTagName("loc");
						LOGGER.debug("urlList is {}", urlList);
						Element urlElement = (Element) urlList.item(0);
						LOGGER.debug("urlElement is {}", urlElement);
						NodeList loctextList = urlElement.getChildNodes();
						LOGGER.debug("loctextList is {}", loctextList);
						String path = loctextList.item(0).getNodeValue().trim();
						LOGGER.debug("path is {}", path);
						NodeList productPathNodes = locElement.getElementsByTagName("path");
						if(Objects.nonNull(productPathNodes) && productPathNodes.getLength()>0) {
							String dataPath	= productPathNodes.item(0).getTextContent();
						LOGGER.debug("dataPath is {}", dataPath);
						map.put(Constant.DATA_PATH, dataPath);
						}
						if(StringUtils.isNotBlank(path)) {
							siteMapService.updateSiteMapFiles(map,path,resourceResolver, session);
						}						
					}
				}
			} 
		}
	}

}
