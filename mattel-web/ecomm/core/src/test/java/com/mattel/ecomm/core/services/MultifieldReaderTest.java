package com.mattel.ecomm.core.services;

import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MultifieldReaderTest {

  @InjectMocks
  MultifieldReader multiFieldReader;
  @Mock
  ResourceResolverFactory resourceResolverFactory;
  @Mock
  Node node;
  @Mock
  private ResourceResolver resourceResolver;
  @Mock
  private NodeIterator nodeIterator;
  private String nodeName = "Dummy Node Name";
  private String nodePath = "/content/ag/en/shop/home/forgot-password/jcr:content/root/forgotpassword";
  @Mock
  private Resource resource;
  @Mock
  private ValueMap valueMap;

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testPropertyReader() {

  }

  @Test
  public void testMultiFieldReader() throws LoginException, RepositoryException {
    Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any()))
        .thenReturn(resourceResolver);
    Mockito.when(resourceResolver.isLive()).thenReturn(true);
    Mockito.when(node.getNodes()).thenReturn(nodeIterator);
    Mockito.when(nodeIterator.hasNext()).thenReturn(true, false);
    Mockito.when(nodeIterator.nextNode()).thenReturn(node);
    Mockito.when(node.getPath()).thenReturn(nodePath);
    Mockito.when(resource.getValueMap()).thenReturn(valueMap);
    Mockito.when(node.getName()).thenReturn(nodeName);
    Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
    multiFieldReader.setResourceResolverFactory(resourceResolverFactory);
    Map<String, ValueMap> propertyReaderMap = multiFieldReader.propertyReader(node);
    Assert.assertNotNull(propertyReaderMap);
    Assert.assertEquals(propertyReaderMap.get(this.nodeName), this.valueMap);
  }
  @Test
  public void testMultiFieldReader_withLoginException() throws LoginException, RepositoryException  {
    Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any()))
    .thenReturn(resourceResolver);
  Mockito.when(resourceResolver.isLive()).thenReturn(true);
  Mockito.when(node.getNodes()).thenThrow(RepositoryException.class);
  multiFieldReader.propertyReader(node);
  }
}
