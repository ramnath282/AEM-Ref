package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PDPProductComponentTest {

  PDPProductComponent pdpProductComponent;

  @Before
  public void setUp() {
    pdpProductComponent = new PDPProductComponent();
    pdpProductComponent.setBuyable("1");
    pdpProductComponent.setImageLink("ImageLink");
    pdpProductComponent.setThumbnail("GBL30_Blaire_Doll_and_Book_1");
    pdpProductComponent.setProductType("ItemBean");
    pdpProductComponent.setParentPartnumber("");
    pdpProductComponent.setFullimage("GBL30_Viewer");
    pdpProductComponent.setPartNumber("GBL30");
    pdpProductComponent.setLanguage("en");
    pdpProductComponent.setRegion("US");
    pdpProductComponent.setName("GBL30_Blaire_Doll_and_Book_1");
    pdpProductComponent.setRelatedSizingChart("popup/ag-girl-size-chart");
    pdpProductComponent.setIsRetailInventoryCheckEnabled("true");
    pdpProductComponent.setPrimaryBundleSku("");
    Map<String, Price> price = new HashMap<String, Price>();
    pdpProductComponent.setPrice(price);
    pdpProductComponent.setAffirmIneligible("Y");
    List<ProductAssociation> associations = new ArrayList<>();
    pdpProductComponent.setAssociations(associations);
    List<ChildProduct> childProducts = new ArrayList<>();
    pdpProductComponent.setChildProducts(childProducts);
  }

  @Test
  public void testPDPProductComponent() {
    Assert.assertNotNull(pdpProductComponent.getChildProducts());
    Assert.assertNotNull(pdpProductComponent.getAssociations());
    Assert.assertEquals("Y", pdpProductComponent.getAffirmIneligible());
    Assert.assertEquals("1", pdpProductComponent.getBuyable());
    Assert.assertEquals("ImageLink", pdpProductComponent.getImageLink());
    Assert.assertNotNull(pdpProductComponent.getThumbnail());
    Assert.assertNotNull(pdpProductComponent.getParentPartnumber());
    Assert.assertEquals("ItemBean", pdpProductComponent.getProductType());
    Assert.assertEquals("GBL30_Viewer", pdpProductComponent.getFullimage());
    Assert.assertEquals("GBL30", pdpProductComponent.getPartNumber());
    Assert.assertEquals("en", pdpProductComponent.getLanguage());
    Assert.assertEquals("US", pdpProductComponent.getRegion());
    Assert.assertEquals("GBL30_Blaire_Doll_and_Book_1", pdpProductComponent.getName());
    Assert.assertEquals("popup/ag-girl-size-chart", pdpProductComponent.getRelatedSizingChart());
    Assert.assertEquals("true", pdpProductComponent.getIsRetailInventoryCheckEnabled());
    Assert.assertNotNull(pdpProductComponent.getPrice());
    Assert.assertNotNull(pdpProductComponent.getPrimaryBundleSku());
    Assert.assertNotNull(pdpProductComponent.toString());

  }

}
