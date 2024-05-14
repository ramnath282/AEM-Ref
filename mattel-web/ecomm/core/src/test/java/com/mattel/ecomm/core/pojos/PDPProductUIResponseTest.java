package com.mattel.ecomm.core.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.pojos.ChildProduct;
import com.mattel.ecomm.coreservices.core.pojos.Price;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;

@RunWith(MockitoJUnitRunner.class)
public class PDPProductUIResponseTest {

  private PDPProductUIResponse productUIResponse = null;
  private Price price = null;

  @Before
  public void setUp() throws Exception {
    final Map<String, String> additionalDescriptiveAttributes = new HashMap<>();
    productUIResponse = new PDPProductUIResponse();
    price = new Price();
    productUIResponse.setAvailability("available");
    productUIResponse.setSkuName("skuName");
    productUIResponse.setBackordarableText("backordarableText");
    productUIResponse.setImageLink("/content/dam/image.png");
    productUIResponse.setMarketingAge("marketingAge");
    productUIResponse.setPartNumber("partNumber");
    productUIResponse.setProductType("product_type");
    productUIResponse.setProductCalloutAttribute("productCalloutAttribute");
    productUIResponse.setProductReviewCount("productReviewCount");
    productUIResponse.setProductReviewEnabled("productReviewEnabled");
    productUIResponse.setProductReviewRating("productReviewRating");
    productUIResponse.setSeoMetaDescription("seo_metaDescription");
    price.setUsage("8%");
    price.setCurrency("100$");
    price.setDescription("Backorderable");
    price.setValue("50.0");
    productUIResponse.setListPrice(price);
    productUIResponse.setSafetyMessage("safetyMessage");
    productUIResponse.setProductStatus("available");
    productUIResponse.setDefiningAttribute("definingAttribute");
    productUIResponse.setRetailInventoryCheckEnabled(true);
    productUIResponse.setCustomerSegment("customerSegment");
    productUIResponse.setHasAddOns("addOns");
    List<String> experienceFragmentPath = new ArrayList<String>();
    productUIResponse.setExperienceFragmentPath(experienceFragmentPath);
    productUIResponse.setSizeChartLink("sizeChartLink");
    final Map<String, String[]> storeMap = new HashMap<>();
    String[] valList = new String[2];
    storeMap.put("key", valList);
    productUIResponse.setStoreMap(storeMap);
    additionalDescriptiveAttributes.put(Constant.DISABLE_QUICK_VIEW, "true");
    productUIResponse.setAdditionalDescriptiveAttributes(additionalDescriptiveAttributes);
  }

  @Test
  public void testGetPartNumber() {
    Assert.assertEquals("partNumber", productUIResponse.getPartNumber());
  }

  @Test
  public void testGetProductCalloutAttribute() {
    Assert.assertEquals("productCalloutAttribute", productUIResponse.getProductCalloutAttribute());
  }

  @Test
  public void testGetSkuName() {
    Assert.assertEquals("skuName", productUIResponse.getSkuName());
  }

  @Test
  public void testGetMarketingAge() {
    Assert.assertEquals("marketingAge", productUIResponse.getMarketingAge());
  }

  @Test
  public void testGetAvailability() {
    Assert.assertEquals("available", productUIResponse.getAvailability());
  }

  @Test
  public void testGetBackordarableText() {
    Assert.assertEquals("backordarableText", productUIResponse.getBackordarableText());
  }

  @Test
  public void testGetProductReviewCount() {
    Assert.assertEquals("productReviewCount", productUIResponse.getProductReviewCount());
  }

  @Test
  public void testGetProductReviewRating() {
    Assert.assertEquals("productReviewRating", productUIResponse.getProductReviewRating());
  }

  @Test
  public void testGetProductReviewEnabled() {
    Assert.assertEquals("productReviewEnabled", productUIResponse.getProductReviewEnabled());
  }

  @Test
  public void testGetProduct_type() {
    Assert.assertEquals("product_type", productUIResponse.getProductType());
  }

  @Test
  public void testGetAssociationList() {
    Assert.assertEquals("backordarableText", productUIResponse.getBackordarableText());
  }

  @Test
  public void testGetStoreMap() {
    Assert.assertNotNull(productUIResponse.getStoreMap().get("key"));
  }

  @Test
  public void testGetImageLink() {
    Assert.assertEquals("/content/dam/image.png", productUIResponse.getImageLink());
  }

  @Test
  public void testGetSafetyMessage() {
    Assert.assertEquals("safetyMessage", productUIResponse.getSafetyMessage());
  }

  @Test
  public void testGetListPrice() {
    Assert.assertNotNull(productUIResponse.getListPrice());
    Assert.assertEquals("100$", productUIResponse.getListPrice().getCurrency());
    Assert.assertEquals("Backorderable", productUIResponse.getListPrice().getDescription());
    Assert.assertEquals("8%", productUIResponse.getListPrice().getUsage());
    Assert.assertEquals("50.0", productUIResponse.getListPrice().getValue());
  }

  @Test
  public void testGetProductStatus() {
    Assert.assertEquals("available", productUIResponse.getProductStatus());
  }

  @Test
  public void testGetDefiningAttribute() {
    Assert.assertEquals("definingAttribute", productUIResponse.getDefiningAttribute());
  }

  @Test
  public void testGetRetailInventoryCheckEnabled() {
    Assert.assertTrue(productUIResponse.isRetailInventoryCheckEnabled());
  }

  @Test
  public void testGetCustomerSegment() {
    Assert.assertEquals("customerSegment", productUIResponse.getCustomerSegment());
  }

  @Test
  public void testGetSeoMetadescription() {
    Assert.assertEquals("seo_metaDescription", productUIResponse.getSeoMetaDescription());
  }

  @Test
  public void testIsHasAddOns() {
    Assert.assertEquals("addOns", productUIResponse.isHasAddOns());
  }

  @Test
  public void getExperienceFragmentPath() {
    Assert.assertNotNull(productUIResponse.getExperienceFragmentPath());
  }

  @Test
  public void getSizeChartLink() {
    Assert.assertNotNull(productUIResponse.getSizeChartLink());
  }

  @Test
  public void getChildProducts() {
    List<ChildProduct> childProductsList = new ArrayList<ChildProduct>();
    productUIResponse.setChildProducts(childProductsList);
    Assert.assertNotNull(productUIResponse.getChildProducts());
  }

  @Test
  public void getDesciptiveAttributes() {
    Map<String, Object> desciptiveAttributes = new HashMap<String, Object>();
    productUIResponse.setDesciptiveAttributes(desciptiveAttributes);
    Assert.assertNotNull(productUIResponse.getDesciptiveAttributes());
  }

  @Test
  public void getAssociationList() {
    List<ProductAssociation> associationList = new ArrayList<ProductAssociation>();
    productUIResponse.setAssociationList(associationList);
    Assert.assertNotNull(productUIResponse.getAssociationList());
  }

  @Test
  public void getComponents() {
    List<BundleComponentUIResponse> componentsList = new ArrayList<BundleComponentUIResponse>();
    productUIResponse.setComponents(componentsList);
    Assert.assertNotNull(productUIResponse.getComponents());
  }

}
