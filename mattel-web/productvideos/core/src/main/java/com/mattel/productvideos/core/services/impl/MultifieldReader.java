package com.mattel.productvideos.core.services.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS. Multifield Reader Service to read the fields and store it in
 *         Map.
 */

@Component(service = MultifieldReader.class, immediate = true)
@Designate(ocd = MultifieldReader.Config.class)
public class MultifieldReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(MultifieldReader.class);
	private static final String NODEPATH = "nodePath";

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	/**
	 * This returns the map.
	 *
	 * @param childNodes
	 *            parameter
	 * @return map
	 */
	public Map<String, ValueMap> propertyReader(Node childNodes) {
		LOGGER.info("propertyReader start");
		Map<String, Object> map = new HashMap<>();
		NodeIterator nodeIterator = null;
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		ResourceResolver resolver = null;
		LinkedHashMap<String, ValueMap> propertiesMap = new LinkedHashMap<>();
		try {
			if (null != resourceResolverFactory) {
				resolver = resourceResolverFactory.getServiceResourceResolver(map);
			}
			if (null != childNodes) {
				nodeIterator = childNodes.getNodes();
			}
			iterate(resolver, nodeIterator, propertiesMap);
			LOGGER.debug("map values set: {}", propertiesMap);
		} catch (LoginException | RepositoryException e) {
			LOGGER.error("Exception Caused in Multifield Reader Service {}", e);
		} finally {
			LOGGER.debug("propertiesMap ++++++++++++: {}", propertiesMap);
			LOGGER.info("start of finally in setArticleImagePath() Method");
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
			LOGGER.info("End of finall in setArticleImagePath() Method");

		}

		LOGGER.info("propertyReader end");
		return propertiesMap;
	}

	public Map<String, ValueMap> propertyReader(Node childNodes, ResourceResolver resolver) {
		LOGGER.info("propertyReader start");
		NodeIterator nodeIterator = null;
		LinkedHashMap<String, ValueMap> propertiesMap = new LinkedHashMap<>();
		try {
			if (null != childNodes) {
				nodeIterator = childNodes.getNodes();
			}
			iterate(resolver, nodeIterator, propertiesMap);
			LOGGER.debug("map values set: {}", propertiesMap);
		} catch (RepositoryException e) {
			LOGGER.error("Exception Caused in Multifield Reader Service {}", e);
		} 
		
		LOGGER.info("propertyReader end");
		return propertiesMap;
	}

	private void iterate(ResourceResolver resolver, NodeIterator nodeIterator,
			LinkedHashMap<String, ValueMap> propertiesMap) throws RepositoryException {
		if (null != nodeIterator && null != resolver) {
			while (nodeIterator.hasNext()) {
				Node itemNode = nodeIterator.nextNode();
				LOGGER.debug("Node Names are {}", itemNode.getName());
				Resource resource = resolver.getResource(itemNode.getPath());
				if (resource != null) {
					LOGGER.debug("valuemap: {}", resource.getValueMap());
					ValueMap valueMap = new ValueMapDecorator(new HashMap<>());
					valueMap.putAll(resource.getValueMap());
					valueMap.put(MultifieldReader.NODEPATH, itemNode.getPath());
					propertiesMap.put(itemNode.getName(), valueMap);
				}
			}
		}
	}

	@ObjectClassDefinition(name = "Multifield reader service")
	public @interface Config {

	}

	public void setResourceResolverFactory(ResourceResolverFactory resourceResolverFactory) {
		this.resourceResolverFactory = resourceResolverFactory;
	}

}
