package com.mattel.ag.retail.core.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.NodeIterator;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CardReaderServiceTest {
	
	CardReaderService cardReaderService;
	ResourceResolverFactory resolverFactory;
	ResourceResolver resourceResolver;
	Resource resource,childResource,columnTwoResource;
	NodeIterator nodeIterator;
	String path = "/content/ag/en/retail/jcr:content/root";
	
	@Before
	public void setUp(){
		cardReaderService = new CardReaderService();
		resolverFactory = Mockito.mock(ResourceResolverFactory.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		resource = Mockito.mock(Resource.class);
		childResource = Mockito.mock(Resource.class);
		columnTwoResource = Mockito.mock(Resource.class);
		cardReaderService.setResolverFactory(resolverFactory);
		try {
			Mockito.when(resolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
		} catch (LoginException e) {
			e.printStackTrace();
		}
		
		when(resourceResolver.resolve(Mockito.anyString())).thenReturn(resource);
		
	}
	
	@Test
	public void testResourcePath(){
	    List<Resource> resourceList = new ArrayList<>();
	    resourceList.add(childResource);
	    Mockito.when( resource.getChildren()).thenReturn(resourceList);
	    Mockito.when(childResource.getPath()).thenReturn("childPath");
	    Mockito.when(resourceResolver.isLive()).thenReturn(true);
	    List<String> resourcePathList = cardReaderService.resourcePath(path);
        Assert.assertNotNull(resourcePathList);
        Assert.assertEquals(3, resourcePathList.size());
        
	}
	
    @Test
    public void testResourcePathWithChildResource() {
        when(resourceResolver.resolve(path + "/columnTwo")).thenReturn(columnTwoResource);
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(childResource);
        Mockito.when(resource.getChildren()).thenReturn(resourceList);
        Mockito.when(childResource.getPath()).thenReturn("childPath");
        List<Resource> columnResourceList = new ArrayList<>();
        columnResourceList.add(childResource);
        columnResourceList.add(childResource);
        Mockito.when(columnTwoResource.getChildren()).thenReturn(columnResourceList);
        Mockito.when(resourceResolver.isLive()).thenReturn(true);
        List<String> resourcePathList = cardReaderService.resourcePath(path);
        Assert.assertNotNull(resourcePathList);
        Assert.assertEquals(4, resourcePathList.size());

    }

}
