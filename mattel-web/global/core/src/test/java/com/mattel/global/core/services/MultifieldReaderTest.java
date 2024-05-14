package com.mattel.global.core.services;

import java.util.LinkedHashMap;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import static org.mockito.Mockito.when;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Test;
import org.mockito.Mockito;

public class MultifieldReaderTest {
	
	@Test
	public void test(){
		try{
		MultifieldReader multifieldReader = new MultifieldReader();
		ResourceResolverFactory resourceResolverFactory = Mockito.mock(ResourceResolverFactory.class);
		ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
	      Node childNode = Mockito.mock(Node.class);
	      Resource resource = Mockito.mock(Resource.class);
	      ValueMap valueMap = Mockito.mock(ValueMap.class);
	      LinkedHashMap<String, ValueMap> linkedHashMap = Mockito.mock(LinkedHashMap.class);
	      NodeIterator nodeIterator = Mockito.mock(NodeIterator.class);
	      
	      
	      when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
	      when(childNode.getNodes()).thenReturn(nodeIterator);
	      when(nodeIterator.hasNext()).thenReturn(true, false);
	      when(nodeIterator.nextNode()).thenReturn(childNode);
	      when(childNode.getPath()).thenReturn("Node Path");
	      when(childNode.getName()).thenReturn("Node Name");
	      when(resourceResolver.getResource(childNode.getPath())).thenReturn(resource);
	      when(resource.getValueMap()).thenReturn(valueMap);
	      when(linkedHashMap.put(childNode.getName(), resource.getValueMap())).thenReturn(valueMap);
	      multifieldReader.setResourceResolverFactory(resourceResolverFactory);
	      multifieldReader.propertyReader(childNode);
		}
		catch (Exception exception){
			
		}
		
	}

}
