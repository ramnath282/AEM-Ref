package com.mattel.ag.retail.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CTS. Service for getting all the card properties..
 */

@Component(service = CardReaderService.class, immediate = true)
public class CardReaderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CardReaderService.class);

	@Reference
	ResourceResolverFactory resolverFactory;

	public List<String> resourcePath(String path) {
		List<String> resourcePath = new LinkedList<>();
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		ResourceResolver resourceResolver = null;
		

		try {
			resourceResolver=resolverFactory.getServiceResourceResolver(map);
			if (null != resourceResolver) {
				Resource resourceColumnOne = resourceResolver.resolve(path + "/columnOne");
				Resource resourceColumnTwo = resourceResolver.resolve(path + "/columnTwo");
				Resource resourceColumnThree = resourceResolver.resolve(path + "/columnThree");
				List<String> column1 = allresourcePath(resourceColumnOne);
				List<String> column2 = allresourcePath(resourceColumnTwo);
				List<String> column3 = allresourcePath(resourceColumnThree);
				Integer masterListLength = getListLength(column1.size(), column2.size(), column3.size());
				for (int i = 0; i < masterListLength; i++) {
					if (i < column1.size()) {
						resourcePath.add(column1.get(i));
					}
					if (i < column2.size()) {
						resourcePath.add(column2.get(i));
					}
					if (i < column3.size()) {
						resourcePath.add(column3.get(i));
					}
				}
			}
		}catch (LoginException e) {
			LOGGER.error("Exception caused :{}", e);
		} finally {
			if (resourceResolver != null && resourceResolver.isLive()) {
				resourceResolver.close();
			}
		}

		return resourcePath;
	}

	/**
	 *
	 * @param resource
	 * @return List
	 */
	private List<String> allresourcePath(Resource resource) {
		List<String> resourcePath = new ArrayList<>();
		Iterator<Resource> resources = resource.getChildren().iterator();
		while (resources.hasNext()) {
			Resource currentResource = resources.next();
			resourcePath.add(currentResource.getPath());
		}

		return resourcePath;
	}

	/**
	 * Generic Method for getting master list size.
	 *
	 * @param columnOneSize
	 * @param columnTwoSize
	 * @param column3Size
	 * @return Integer.
	 */
	private Integer getListLength(int columnOneSize, int columnTwoSize, int column3Size) {
		LOGGER.info("Get List Length Method Starts");
		Integer masterListLength = null;
		if (columnOneSize >= columnTwoSize && columnOneSize >= column3Size) {
			masterListLength = columnOneSize;
		} else if (columnTwoSize >= column3Size) {
			masterListLength = columnTwoSize;
		} else {
			masterListLength = column3Size;
		}
		LOGGER.debug("Master List Length {}", masterListLength);
		LOGGER.info("End of Get List Length Method");
		return masterListLength;
	}

	public void setResolverFactory(ResourceResolverFactory resolverFactory) {
		this.resolverFactory = resolverFactory;
	}

}
