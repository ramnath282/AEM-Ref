package com.mattel.ecomm.core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mattel.ecomm.core.pojos.BundleComponentUIResponse;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import com.mattel.ecomm.coreservices.core.pojos.PDPProductComponent;
import com.mattel.ecomm.coreservices.core.pojos.Price;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;

@RunWith(PowerMockRunner.class)
public class BundleComponentUIAdapterTest {

  PDPProductComponent component;

  @Before
  public void setUp() {
    component = new PDPProductComponent();
  }

  @Test
  public void testBundleComponentUIAdapter() throws JsonProcessingException, IOException {
    BundleComponentUIResponse bundleComponentUIResponse = new BundleComponentUIResponse();
    createPDPProductResponse();
    bundleComponentUIResponse = BundleComponentUIAdapter.transformComponentToSignleSKU(component);
    Assert.assertNotNull(bundleComponentUIResponse);
  }

  public void createPDPProductResponse() throws JsonProcessingException, IOException {
    component.setProductType("BundleBean");
    component.setParentPartnumber("06BUN12");
    component.setPartNumber("GBJ42");
    component.setName("Paradise Palms Swimsuit for Bitty Baby&#174; Dolls");
    component.setLanguage("en");
    component.setBuyable("1");
    Map<String, Price> priceMap = new HashMap<>();
    Price price = new Price();
    price.setCurrency("USD");
    price.setValue("20.00");
    price.setUsage("Display");
    price.setDescription("L");
    priceMap.put("listPrice", price);
    component.setPrice(priceMap);
    component.setAffirmIneligible("Y");
    component.setRelatedSizingChart("popup/little-girl-size-chart");
    List<ProductAssociation> associations = new ArrayList<ProductAssociation>();
    ProductAssociation productAssociation = new ProductAssociation();
    productAssociation.setName("Paradise Palms Swimsuit for Bitty Baby&#174; Dolls");
    productAssociation.setAssociationType("MINI_CART");
    associations.add(productAssociation);
    component.setAssociations(associations);
    component.setIsRetailInventoryCheckEnabled("Y");
    component.setThumbnail("GBJ42_Sweet_Spring_Outfit_Bitty_Baby_Dolls_1");
    component.setImageLink("https://mattel.scene7.com/is/image/Mattel/GBJ42_Viewer");
    component.setPrimaryBundleSku("Y");
    List<ChildProduct> childProducts = new ArrayList<>();
    ChildProduct childProduct = new ChildProduct();
    childProduct.setRegion("US");
    childProduct.setBuyable("1");
    childProduct.setFullimage("https://mattel.scene7.com/is/image/Mattel/GBJ42_Viewer");
    Map<String, Object> definingAttributes = new HashMap<>();
    definingAttributes.put("ClothingSize", "value1");
    definingAttributes.put("key2", "value");
    childProduct.setDefiningAttributes(definingAttributes);
    childProducts.add(childProduct);
    ChildProduct childProduct1 = new ChildProduct();
    childProducts.add(childProduct1);
    component.setChildProducts(childProducts);

  }

}
