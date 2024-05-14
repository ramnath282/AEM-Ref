package com.mattel.fisherprice.core.utils;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Test;
import org.mockito.Mockito;

public class JcrResourceRemoveTest {
  @Test(expected = NullPointerException.class)
  public void testRemoveNodeRecursivelyDummy() throws Exception {
    final Node node = Mockito.mock(Node.class);

    Mockito.when(node.getPath()).thenReturn("/var/products/fisher-price/en-us/product_FDXX7");
    JcrResourceRemove.removeNodeRecursively(node, 4);
  }

  @Test
  public void testRemoveResource() throws Exception {
    final ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    final Resource resource = Mockito.mock(Resource.class);
    final Session session = Mockito.mock(Session.class);
    final String path = "/var/products/fisher-price/en-us/product_GBN16";

    Mockito.when(resourceResolver.getResource(path)).thenReturn(resource);
    Mockito.doNothing().when(session).removeItem(path);
    JcrResourceRemove.removeResource(path, session, resourceResolver);
  }

  @Test
  public void testRemoveResourceNotFound() throws Exception {
    final ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    final Session session = Mockito.mock(Session.class);
    final String path = "/var/products/fisher-price/en-us/product_GBN16";

    Mockito.when(resourceResolver.getResource(path)).thenReturn(null);
    JcrResourceRemove.removeResource(path, session, resourceResolver);
  }
}
