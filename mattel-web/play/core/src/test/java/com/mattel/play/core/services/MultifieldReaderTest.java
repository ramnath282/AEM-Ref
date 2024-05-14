package com.mattel.play.core.services;

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
	ResourceResolver resolver = null;
	LinkedHashMap<String, ValueMap> propertiesMap = new LinkedHashMap<>();
	ValueMap valueMap;
	NodeIterator nodeIterator;
	Resource valueMapResource;

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
		map.put(ResourceResolverFactory.USER, "playserviceuser");
		Mockito.when(resourceResolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
		Mockito.when(childNodes.getNodes()).thenReturn(nodeIterator);
		Mockito.when(nodeIterator.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(nodeIterator.nextNode()).thenReturn(childNodes);
		Mockito.when(resolver.getResource(childNodes.getPath())).thenReturn(valueMapResource);
		Mockito.when(valueMapResource.getValueMap()).thenReturn(valueMap);
		if (null != resolver && resolver.isLive()) {
			resolver.close();
		}

	}

	@Test
	public void propertyReader() {
		multifieldReader.propertyReader(childNodes);
	}
}
