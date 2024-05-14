package com.mattel.ecomm.core.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.interfaces.MarketingContentProviderService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;

@RunWith(PowerMockRunner.class)
public class ProductQuickViewServiceImplTest {

  @InjectMocks
  ProductQuickViewServiceImpl productQuickViewServiceImpl;

  @Mock
  ProductAvailability productAvailability;

  @Mock
  MarketingContentProviderService marketingContentProviderService;

  Map<String, Object> requestMap;

  Map<String, Object> responseMap;

  @Before
  public void init() throws ServiceException {
    responseMap = new HashMap<>();
    requestMap = new HashMap<>();
    requestMap.put(Constant.PART_NUMBER, "1234");
    requestMap.put(Constant.STORE_KEY, "AG");

    ProductServiceResponse pdpResponse = Mockito.mock(ProductServiceResponse.class);
    Mockito.when(productAvailability.fetch(requestMap)).thenReturn(pdpResponse);

    Product shopifyProduct = Mockito.mock(Product.class);
    Mockito.when(pdpResponse.getProduct()).thenReturn(shopifyProduct);

    @SuppressWarnings("unchecked")
    List<String> experienceFragmentPath = Mockito.mock(List.class);
    Mockito.when(marketingContentProviderService.getContentFromTags("AG", "1234", "default"))
        .thenReturn(experienceFragmentPath);
  }

  @Test
  public void testGetQuickViewProductData() throws ServiceException {
    productQuickViewServiceImpl.getQuickViewProductData(requestMap, responseMap);
  }
}
