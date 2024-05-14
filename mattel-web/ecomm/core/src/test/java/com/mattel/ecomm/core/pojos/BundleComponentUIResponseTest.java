package com.mattel.ecomm.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import com.mattel.ecomm.coreservices.core.pojos.Price;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;

@RunWith(PowerMockRunner.class)
public class BundleComponentUIResponseTest {

  BundleComponentUIResponse bundleComponentUIResponse;

  @Before
  public void setUp() throws Exception {
    bundleComponentUIResponse = new BundleComponentUIResponse();
    bundleComponentUIResponse.setBuyable("1");
    bundleComponentUIResponse.setImageLink("https://images.mattel.com/scene7/FLY25_Viewer");
    bundleComponentUIResponse.setThumbnail("FLY25_Sunny_Day_Dress_Girls_1");
    bundleComponentUIResponse.setProductType("BundleBean");
    bundleComponentUIResponse.setParentPartnumber("FLY25");
    bundleComponentUIResponse.setFullimage("FLY25_Viewer");
    bundleComponentUIResponse.setPartNumber("FLY25.COR.14");
    bundleComponentUIResponse.setLanguage("en_US");
    bundleComponentUIResponse.setRegion("US");
    bundleComponentUIResponse.setName("en");
    bundleComponentUIResponse.setIsRetailInventoryCheckEnabled(true);
    bundleComponentUIResponse.setSizeChartLink("SizeChartLink");
    bundleComponentUIResponse.setDefiningAttribute("");
    Price price = new Price();
    bundleComponentUIResponse.setListPrice(price);
    ProductAssociation productAssociation = new ProductAssociation();
    List<ProductAssociation> productAssociationList = new ArrayList<>();
    productAssociationList.add(productAssociation);
    bundleComponentUIResponse.setAssociations(productAssociationList);
    ChildProduct childProduct = new ChildProduct();
    List<ChildProduct> childProducts = new ArrayList<>();
    childProducts.add(childProduct);
    bundleComponentUIResponse.setChildProducts(childProducts);
    bundleComponentUIResponse.setHasAddOns("addOns");
    bundleComponentUIResponse.setAffirmIneligible("Y");
    bundleComponentUIResponse.setIsDynamicKitPrimaryComponent("true");
  }

  @Test
  public void testGetMethods() {
    Assert.assertEquals("1", bundleComponentUIResponse.getBuyable());
    Assert.assertEquals("https://images.mattel.com/scene7/FLY25_Viewer",
        bundleComponentUIResponse.getImageLink());
    Assert.assertEquals("FLY25_Sunny_Day_Dress_Girls_1", bundleComponentUIResponse.getThumbnail());
    Assert.assertEquals("BundleBean", bundleComponentUIResponse.getProductType());
    Assert.assertEquals("FLY25", bundleComponentUIResponse.getParentPartnumber());
    Assert.assertEquals("FLY25.COR.14", bundleComponentUIResponse.getPartNumber());
    Assert.assertEquals("FLY25_Viewer", bundleComponentUIResponse.getFullimage());
    Assert.assertEquals("en_US", bundleComponentUIResponse.getLanguage());
    Assert.assertEquals("en", bundleComponentUIResponse.getName());
    Assert.assertEquals("SizeChartLink", bundleComponentUIResponse.getSizeChartLink());
    Assert.assertEquals("", bundleComponentUIResponse.getDefiningAttribute());
    Assert.assertNotNull(bundleComponentUIResponse.getListPrice());
    Assert.assertNotNull(bundleComponentUIResponse.getAssociations());
    Assert.assertNotNull(bundleComponentUIResponse.getChildProducts());
    Assert.assertNotNull(bundleComponentUIResponse.toString());
    Assert.assertNotNull(bundleComponentUIResponse.getRegion());
    Assert.assertEquals("addOns", bundleComponentUIResponse.isHasAddOns());
    Assert.assertEquals("Y", bundleComponentUIResponse.getAffirmIneligible());
    Assert.assertEquals("true", bundleComponentUIResponse.getIsDynamicKitPrimaryComponent());
    Assert.assertTrue(bundleComponentUIResponse.getIsRetailInventoryCheckEnabled());
    
  }

}
