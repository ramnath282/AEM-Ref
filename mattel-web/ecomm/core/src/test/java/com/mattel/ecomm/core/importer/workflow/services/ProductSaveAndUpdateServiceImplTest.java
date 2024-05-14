package com.mattel.ecomm.core.importer.workflow.services;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.commons.jcr.JcrUtil;
import com.day.crx.JcrConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.ecomm.core.pojos.ProductJsonNode;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ JcrUtil.class })
public class ProductSaveAndUpdateServiceImplTest {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private ProductSaveAndUpdateServiceImpl impl;

  @Before
  public void setUp() throws Exception {
    impl = new ProductSaveAndUpdateServiceImpl();
  }

  @Test
  public void testSaveForUpdate() throws Exception {
    final ProductSaveAndUpdateServiceImpl.Config config = Mockito
        .mock(ProductSaveAndUpdateServiceImpl.Config.class);
    final ResourceResolver resolver = Mockito.mock(ResourceResolver.class);
    final Resource resource = Mockito.mock(Resource.class);
    final Node node = Mockito.mock(Node.class);
    final Property property = Mockito.mock(Property.class);
    final Session session = Mockito.mock(Session.class);
    final JsonNode jsonNode = ProductSaveAndUpdateServiceImplTest.OBJECT_MAPPER.createObjectNode()
        .put("product_type", "ItemBean").put("pdpLink", "blaire-doll-book-gbl30")
        .put("partNumber", "gbl30").put("productName", "Willa Doll");
    final ProductJsonNode productJsonNode = new ProductJsonNode("blaire-doll-book-gbl30", jsonNode);

    productJsonNode.setProperty("product_type", "ItemBean");
    productJsonNode.setProperty("pdpLink", "blaire-doll-book-gbl30");
    productJsonNode.setProperty("partNumber", "gbl30");
    productJsonNode.setProperty("productName", "Willa Doll");
    Mockito.when(config.parentNodePath()).thenReturn("/var/commerce/products/ag/en/");
    Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
    Mockito.when(node.setProperty("product_type", "ItemBean")).thenReturn(property);
    Mockito.when(node.setProperty("pdpLink", "blaire-doll-book-gbl30")).thenReturn(property);
    Mockito.when(node.setProperty("partNumber", "gbl30")).thenReturn(property);
    Mockito.when(node.setProperty("productName", "Willa Doll")).thenReturn(property);
    Mockito.when(resolver.getResource("/var/commerce/products/ag/en/" + "blaire-doll-book-gbl30"))
        .thenReturn(resource);
    impl.activate(config);
    Assert.assertEquals(node, impl.save(productJsonNode, session, resolver));
  }

  @Test(expected = RepositoryException.class)
  public void testSaveForFailedUpdate() throws Exception {
    final ProductSaveAndUpdateServiceImpl.Config config = Mockito
        .mock(ProductSaveAndUpdateServiceImpl.Config.class);
    final ResourceResolver resolver = Mockito.mock(ResourceResolver.class);
    final Resource resource = Mockito.mock(Resource.class);
    final Session session = Mockito.mock(Session.class);
    final JsonNode jsonNode = ProductSaveAndUpdateServiceImplTest.OBJECT_MAPPER.createObjectNode()
        .put("product_type", "ItemBean").put("pdpLink", "blaire-doll-book-gbl30")
        .put("partNumber", "gbl30").put("productName", "Willa Doll");
    final ProductJsonNode productJsonNode = new ProductJsonNode("blaire-doll-book-gbl30", jsonNode);

    productJsonNode.setProperty("product_type", "ItemBean");
    productJsonNode.setProperty("pdpLink", "blaire-doll-book-gbl30");
    productJsonNode.setProperty("partNumber", "gbl30");
    productJsonNode.setProperty("productName", "Willa Doll");
    Mockito.when(config.parentNodePath()).thenReturn("/var/commerce/products/ag/en/");
    Mockito.when(resource.adaptTo(Node.class)).thenReturn(null);
    Mockito.when(resolver.getResource("/var/commerce/products/ag/en/" + "blaire-doll-book-gbl30"))
        .thenReturn(resource);
    impl.activate(config);
    impl.save(productJsonNode, session, resolver);
  }

  @Test
  public void testSaveForNewNode() throws Exception {
    final ProductSaveAndUpdateServiceImpl.Config config = Mockito
        .mock(ProductSaveAndUpdateServiceImpl.Config.class);
    final ResourceResolver resolver = Mockito.mock(ResourceResolver.class);
    final Resource resource = Mockito.mock(Resource.class);
    final Node node = Mockito.mock(Node.class);
    final Property property = Mockito.mock(Property.class);
    final Session session = Mockito.mock(Session.class);
    final JsonNode jsonNode = ProductSaveAndUpdateServiceImplTest.OBJECT_MAPPER.createObjectNode()
        .put("product_type", "ItemBean").put("pdpLink", "blaire-doll-book-gbl30")
        .put("partNumber", "gbl30").put("productName", "Willa Doll");
    final ProductJsonNode productJsonNode = new ProductJsonNode("blaire-doll-book-gbl30", jsonNode);

    productJsonNode.setProperty("product_type", "ItemBean");
    productJsonNode.setProperty("pdpLink", "blaire-doll-book-gbl30");
    productJsonNode.setProperty("partNumber", "gbl30");
    productJsonNode.setProperty("productName", "Willa Doll");
    Mockito.when(config.parentNodePath()).thenReturn("/var/commerce/products/ag/en/");
    Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
    Mockito.when(node.setProperty("product_type", "ItemBean")).thenReturn(property);
    Mockito.when(node.setProperty("pdpLink", "blaire-doll-book-gbl30")).thenReturn(property);
    Mockito.when(node.setProperty("partNumber", "gbl30")).thenReturn(property);
    Mockito.when(node.setProperty("productName", "Willa Doll")).thenReturn(property);
    PowerMockito.mockStatic(JcrUtil.class);
    Mockito.when(JcrUtil.createPath("/var/commerce/products/ag/en/" + "blaire-doll-book-gbl30",
        JcrConstants.NT_UNSTRUCTURED, session)).thenReturn(node);
    Mockito.when(resolver.getResource("/var/commerce/products/ag/en/" + "blaire-doll-book-gbl30"))
        .thenReturn(null);
    impl.activate(config);
    Assert.assertEquals(node, impl.save(productJsonNode, session, resolver));
  }
}
