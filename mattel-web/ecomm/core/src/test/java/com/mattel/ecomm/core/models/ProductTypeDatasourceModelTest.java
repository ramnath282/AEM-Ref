package com.mattel.ecomm.core.models;

import java.util.HashSet;
import java.util.Set;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.interfaces.GetProductTypeService;

@RunWith(PowerMockRunner.class)
public class ProductTypeDatasourceModelTest {

  @InjectMocks
  ProductTypeDatasourceModel productTypeDatasourceModel;
  @Mock
  SlingHttpServletRequest slingHttpServletRequest;
  @Mock
  private GetProductTypeService getProductType;
  @Mock
  Resource resource;
  @Mock
  Page page;
  @Mock
  ResourceResolver resourceResolver;
  String requestPath = "/content/Ag/retail";
  @Mock
  private ValueMap pageProps;

  @Before
  public void setUp() throws Exception {
    MemberModifier.field(ProductTypeDatasourceModel.class, "getProductType")
        .set(productTypeDatasourceModel, getProductType);
    MemberModifier.field(ProductTypeDatasourceModel.class, "slingHttpServletRequest")
        .set(productTypeDatasourceModel, slingHttpServletRequest);
    Mockito.when(slingHttpServletRequest.getPathInfo()).thenReturn(requestPath);
    Mockito.when(slingHttpServletRequest.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
    Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
    Mockito.when(page.getProperties()).thenReturn(pageProps);
    Mockito.when(pageProps.get("clientid", String.class)).thenReturn("AG");
    Set<String> productTypes = new HashSet<>();
    productTypes.add("DOLL");
    productTypes.add("Clothes");
    Mockito.when(getProductType.getProductTypeDatasource("AG")).thenReturn(productTypes);
  }

  @Test
  public void testInit() {
    productTypeDatasourceModel.init();
  }
}
