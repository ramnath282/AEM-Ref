package com.mattel.fisherprice.core.services;

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

@Component(service = MultifieldReader.class, immediate = true)
@Designate(ocd = MultifieldReader.Config.class)
public class MultifieldReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(MultifieldReader.class);

	@Reference
	ResourceResolverFactory resourceResolverFactory;

	/**
	 * This returns the map.
	 *
	 * @param childNodes
	 *            parameter
	 * @return map
	 */
	public Map<String, ValueMap> propertyReader(Node childNodes) {
		LOGGER.debug("start of propertyReader");
		Map<String, Object> map = new HashMap<>();
		NodeIterator nodeIterator = null;
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		map.put(ResourceResolverFactory.USER, "fisherpriceserviceuser");
		ResourceResolver resolver = null;
		LinkedHashMap<String, ValueMap> propertiesMap = new LinkedHashMap<>();

		try {
			if (null != resourceResolverFactory) {
				resolver = resourceResolverFactory.getServiceResourceResolver(map);
			}
			if (null != childNodes) {
				nodeIterator = childNodes.getNodes();
			}
			if (null != nodeIterator && null != resolver) {
				while (nodeIterator.hasNext()) {
					Node itemNode = nodeIterator.nextNode();
					LOGGER.debug("Node Names are {}", itemNode.getName());
					Resource resource = resolver.getResource(itemNode.getPath());
					if (resource != null) {
						ValueMap valueMap = new ValueMapDecorator(new HashMap<>());
						valueMap.putAll(resource.getValueMap());
						propertiesMap.put(itemNode.getName(), valueMap);
					}
				}
			}
		} catch (LoginException | RepositoryException e) {
			LOGGER.error("Exception Caused in Multifield Reader Service {}", e);
		} finally {
			LOGGER.debug("start of finally in propertyReader() Method");
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
			LOGGER.debug("End of finally in propertyReader() Method");

		}

		LOGGER.info("End of propertyReader");
		return propertiesMap;
	}

	public boolean checkIfClientLibExists(String clientLibCategory) {
		LOGGER.debug("start of propertyReader");
		boolean clinetLibExists = false;
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		map.put(ResourceResolverFactory.USER, "fisherpriceserviceuser");
		ResourceResolver resourceResolver = null;
		Map<String, ValueMap> propertiesMap = new LinkedHashMap<>();
		NodeIterator nodeIterator = null;
		try {
			if (null != resourceResolverFactory) {
				resourceResolver = resourceResolverFactory.getServiceResourceResolver(map);
				Resource resource = resourceResolver.getResource("/apps/mattel/ecomm/fisher-price/clientlibs");
				if (resource != null) {
					Node rootClientLibNode = resource.adaptTo(Node.class);
					if (null != rootClientLibNode) {
						nodeIterator = rootClientLibNode.getNodes();
					}
					clinetLibExists = iterateClienlibs(nodeIterator, propertiesMap, resourceResolver,
							clientLibCategory);
				}
			}

		} catch (LoginException | RepositoryException e) {
			LOGGER.error("Exception Caused in Multifield Reader Service {}", e);
		} finally {
			LOGGER.debug("start of finally in propertyReader() Method");
			if (resourceResolver != null && resourceResolver.isLive()) {
				resourceResolver.close();
			}
			LOGGER.debug("End of finally in propertyReader() Method");

		}

		LOGGER.debug("End of propertyReader");
		return clinetLibExists;
	}

	/**
	 * 
	 * @param nodeIterator
	 * @param propertiesMap
	 * @param resourceResolver
	 * @param clientLibCategory
	 * @return
	 * @throws RepositoryException
	 */
	private boolean iterateClienlibs(NodeIterator nodeIterator, Map<String, ValueMap> propertiesMap,
			ResourceResolver resourceResolver, String clientLibCategory) throws RepositoryException {
		if (null != nodeIterator && null != resourceResolver) {
			while (nodeIterator.hasNext()) {
				Node itemNode = nodeIterator.nextNode();
				LOGGER.debug("Node Names are {}", itemNode.getName());
				Resource resource = resourceResolver.getResource(itemNode.getPath());
				if (resource != null) {
					ValueMap valueMap = new ValueMapDecorator(new HashMap<>());
					valueMap.putAll(resource.getValueMap());
					propertiesMap.put(itemNode.getName(), valueMap);
				}
			}
			return checkClientLibs(propertiesMap, clientLibCategory);
		}
		return false;
	}

	/**
	 * 
	 * @param propertiesMap
	 * @param clientLibCategory
	 * @return
	 */
	private boolean checkClientLibs(Map<String, ValueMap> propertiesMap, String clientLibCategory) {
		for (Map.Entry<String, ValueMap> entry : propertiesMap.entrySet()) {
			String[] currentCategories = entry.getValue().get("categories", String[].class);
			if (currentCategories != null) {
				for (String str : currentCategories) {
					if (str.equals(clientLibCategory)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@ObjectClassDefinition(name = "Multifield reader service")
	public @interface Config {

	}

	public void setResourceResolverFactory(ResourceResolverFactory resourceResolverFactory) {
		this.resourceResolverFactory = resourceResolverFactory;
	}
}
