package com.mattel.informational.core.services;

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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class MultifieldReaderTest {

	@InjectMocks
	private MultifieldReader multifieldReader;

	@Mock
	ResourceResolverFactory resourceResolverFactory;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Node childNodes;
	
	@Mock
	NodeIterator nodeIterator;
	
	@Mock
	Resource resource;
	
	@Mock
	ValueMap valueMap;

	@Before
	public void setup() throws LoginException, RepositoryException {
		Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		nodeIterator = Mockito.mock(NodeIterator.class);
		Mockito.when(childNodes.getNodes()).thenReturn(nodeIterator);
		Mockito.when(nodeIterator.hasNext()).thenReturn(true, false);
		Node itemNode = Mockito.mock(Node.class);
		Mockito.when(nodeIterator.nextNode()).thenReturn(itemNode);
		Mockito.when(itemNode.getName()).thenReturn("itemnode");
		Mockito.when(itemNode.getPath()).thenReturn("itemnodepath");
		resource = Mockito.mock(Resource.class);
		Mockito.when(resourceResolver.getResource("itemnodepath")).thenReturn(resource);
		valueMap.put("categories", "category");
		Mockito.when(resource.getValueMap()).thenReturn(valueMap);
	}

	@Test
	public void testPropertyReader() {
		multifieldReader.propertyReader(childNodes);
	}
}
