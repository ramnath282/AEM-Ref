package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Association;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(MockitoJUnitRunner.class)
public class ProductAddonImplTest {

  @InjectMocks
  private ProductAddonImpl productAddonImpl;

  @Mock
  PropertyReaderService propertyReaderService;

  @Mock
  ProductAvailability productAvailability;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void testFetch_withvalidpartnumber() throws LoginException, ServiceException {
    Map<String, Object> requestMap = new HashMap<>();
    requestMap.put(Constant.PART_NUMBER, "1234");
    ProductServiceResponse productServiceResponse = Mockito.mock(ProductServiceResponse.class);
    Mockito.when(productAvailability.fetch(requestMap)).thenReturn(productServiceResponse);

    Product product = Mockito.mock(Product.class);
    Association association = new Association();
    association.setAssociation_type("ADDONSERVICES");

    List<Association> associations = new ArrayList<>();
    associations.add(association);

    Mockito.when(productServiceResponse.getProduct()).thenReturn(product);
    Mockito.when(product.getAssociations()).thenReturn(associations);

    JSONObject response = Mockito.mock(JSONObject.class);
    Mockito.when(productAddonImpl.fetch(requestMap)).thenReturn(response);
    Assert.assertNotNull(productAddonImpl.fetch(requestMap));
  }

  @Test(expected = ServiceException.class)
  public void testFetch_withemptypartnumber() throws ServiceException {
    Map<String, Object> requestMap = new HashMap<>();
    requestMap.put(Constant.PART_NUMBER, "");
    try {
      productAddonImpl.fetch(requestMap);
    } catch (ServiceException ex) {
      Assert.assertNotNull(ex.getErrorKey());
      throw ex;
    }
  }

}
