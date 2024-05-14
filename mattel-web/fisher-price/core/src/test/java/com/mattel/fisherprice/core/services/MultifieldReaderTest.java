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
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MultifieldReaderTest {

	MultifieldReader multifieldReader;
	ResourceResolverFactory resourceResolverFactory;
	Map<String, Object> map = new HashMap<>();
	Node childNodes;
	Resource resource;

	ResourceResolver resourceResolver = null;
	LinkedHashMap<String, ValueMap> propertiesMap = new LinkedHashMap<>();
	ValueMap valueMap;
	NodeIterator nodeIterator;
	Resource valueMapResource;
	ResourceResolver resolver;

	@Before
	public void setUp() throws LoginException, RepositoryException {
		multifieldReader = new MultifieldReader();
		resourceResolverFactory = Mockito.mock(ResourceResolverFactory.class);
		childNodes = Mockito.mock(Node.class);
		resolver = Mockito.mock(ResourceResolver.class);
		valueMap = Mockito.mock(ValueMap.class);
		nodeIterator = Mockito.mock(NodeIterator.class);
		valueMapResource = Mockito.mock(Resource.class);
		multifieldReader.setResourceResolverFactory(resourceResolverFactory);
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		map.put(ResourceResolverFactory.USER, "fisherpriceserviceuser");
		Mockito.when(resourceResolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
		Mockito.when(childNodes.getNodes()).thenReturn(nodeIterator);
		Mockito.when(nodeIterator.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(nodeIterator.nextNode()).thenReturn(childNodes);
		Mockito.when(resolver.getResource(childNodes.getPath())).thenReturn(resource);
		/*
		 * Mockito.when(resource.getValueMap()).thenReturn(valueMap); valueMap = new
		 * ValueMapDecorator(new HashMap<>()); valueMap.putAll(resource.getValueMap());
		 */
		if (null != resolver && resolver.isLive()) {
			resolver.close();
		}

	}

	@Test
	public void propertyReader() {
		multifieldReader.propertyReader(childNodes);
	}

	@Test
	public void checkIfClientLibExists() throws RepositoryException {

		Node rootClientLibNode;
		resource = Mockito.mock(Resource.class);
		Mockito.when(resolver.getResource("/apps/mattel/ecomm/fisher-price/clientlibs")).thenReturn(resource);
		rootClientLibNode = Mockito.mock(Node.class);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(rootClientLibNode);
		Mockito.when(rootClientLibNode.getNodes()).thenReturn(nodeIterator);
		multifieldReader.checkIfClientLibExists("clientLibCategory");

	}

}
