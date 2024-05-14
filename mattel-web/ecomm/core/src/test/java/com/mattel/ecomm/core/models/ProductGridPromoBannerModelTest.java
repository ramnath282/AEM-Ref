package com.mattel.ecomm.core.models;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;


@RunWith(PowerMockRunner.class)
public class ProductGridPromoBannerModelTest {

  @Mock
  Resource resource;

  @InjectMocks
  ProductGridPromoBannerModel productGridPromoBannerModel;
  @Mock
  private Node node;
  @Mock
  Session session;
  @Before
  public void setUp() throws Exception {
    MemberModifier.field(ProductGridPromoBannerModel.class, "resource").set(productGridPromoBannerModel, resource);
    Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
    Mockito.when(node.hasProperty("bannerId")).thenReturn(true);
  }


  @Test
  public void testInit_withBannerId() {
    productGridPromoBannerModel.init();
  }
  @Test
  public void testInit_withOutBannerId() throws RepositoryException {
    Mockito.when(node.getSession()).thenReturn(session);
    Mockito.when(node.hasProperty("bannerId")).thenReturn(false);
    productGridPromoBannerModel.init();
  }
  @Test
  public void testInit_withOutRepositoryException() throws RepositoryException {
    Mockito.when(node.hasProperty("bannerId")).thenThrow(RepositoryException.class);
    productGridPromoBannerModel.init();
  }

}
