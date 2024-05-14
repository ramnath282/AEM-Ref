
package com.mattel.ecomm.core.pojos.shopify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GTSummaryUIResponseTest {

  private GTSummaryUIResponse impl = null;
  private Map<String, Object> attributes = null;
  private GTAddonPojo gthearingaiddetails = new GTAddonPojo();
  private GTAddonPojo gtearpiercingdetails = new GTAddonPojo();
  private GTAddonPojo gtboxdetails = new GTAddonPojo();
  private GTAddonPojo gtenvelopedetails = new GTAddonPojo();
  private GTAddonPojo gtletterdetails = new GTAddonPojo();
  private List<GTAddonPojo> gtnondisplayableitems;
  private List<GTAddonPojo> gtdisplayableitems;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    impl = new GTSummaryUIResponse();
    attributes = Mockito.mock(Map.class);
    impl.setBuyable("dummy_string");
    impl.setProductStatus("dummy_string");
    impl.setLanguage("dummy_string");
    impl.setAvailability("dummy_string");
    impl.setSeoMetaDescription("dummy_string");
    impl.setImageLink("dummy_string");
    impl.setSeoPageTitle("dummy_string");
    impl.setSeoImageAltText("dummy_string");
    impl.setThumbnail("dummy_string");
    impl.setInvStatus("dummy_string");
    impl.setAuxDescription1("dummy_string");
    impl.setSeoUrlKeyword("dummy_string");
    impl.setProductType("dummy_string");
    impl.setName("dummy_string");
    impl.setFullimage("dummy_string");
    impl.setPartNumber("dummy_string");
    impl.setAttributes(attributes);
    impl.setRegion("dummy_string");
    impl.setGtHearingAidDetails(gthearingaiddetails);
    impl.setGtEarPiercingDetails(gtearpiercingdetails);
    impl.setGtBoxDetails(gtboxdetails);
    impl.setGtEnvelopeDetails(gtenvelopedetails);
    impl.setGtLetterDetails(gtletterdetails);
    gtnondisplayableitems = new ArrayList<GTAddonPojo>();
    impl.setGtNonDisplayableItems(gtnondisplayableitems);
    gtdisplayableitems = new ArrayList<GTAddonPojo>();
    impl.setGtDisplayableItems(gtdisplayableitems);
    impl.toString();
  }

  @Test
  public void testGetGtNonDisplayableItems() throws Exception {
    Assert.assertEquals(gtnondisplayableitems, impl.getGtNonDisplayableItems());
  }

  @Test
  public void testGetName() throws Exception {
    Assert.assertEquals("dummy_string", impl.getName());
  }

  @Test
  public void testGetBuyable() throws Exception {
    Assert.assertEquals("dummy_string", impl.getBuyable());
  }

  @Test
  public void testGetSeo_imageAltText() throws Exception {
    Assert.assertEquals("dummy_string", impl.getSeoImageAltText());
  }

  @Test
  public void testGetThumbnail() throws Exception {
    Assert.assertEquals("dummy_string", impl.getThumbnail());
  }

  @Test
  public void testGetGtEnvelopeDetails() throws Exception {
    Assert.assertEquals(gtenvelopedetails, impl.getGtEnvelopeDetails());
  }

  @Test
  public void testGetAvailability() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAvailability());
  }

  @Test
  public void testGetSeo_metaDescription() throws Exception {
    Assert.assertEquals("dummy_string", impl.getSeoMetaDescription());
  }

  @Test
  public void testGetSeo_urlKeyword() throws Exception {
    Assert.assertEquals("dummy_string", impl.getSeoUrlKeyword());
  }

  @Test
  public void testGetGtDisplayableItems() throws Exception {
    Assert.assertEquals(gtdisplayableitems, impl.getGtDisplayableItems());
  }

  @Test
  public void testGetProduct_status() throws Exception {
    Assert.assertEquals("dummy_string", impl.getProductStatus());
  }

  @Test
  public void testGetInvStatus() throws Exception {
    Assert.assertEquals("dummy_string", impl.getInvStatus());
  }

  @Test
  public void testGetLanguage() throws Exception {
    Assert.assertEquals("dummy_string", impl.getLanguage());
  }

  @Test
  public void testGetAttributes() throws Exception {
    Assert.assertEquals(attributes, impl.getAttributes());
  }

  @Test
  public void testGetProduct_type() throws Exception {
    Assert.assertEquals("dummy_string", impl.getProductType());
  }

  @Test
  public void testGetSeo_PageTitle() throws Exception {
    Assert.assertEquals("dummy_string", impl.getSeoPageTitle());
  }

  @Test
  public void testGetImageLink() throws Exception {
    Assert.assertEquals("dummy_string", impl.getImageLink());
  }

  @Test
  public void testGetAuxDescription1() throws Exception {
    Assert.assertEquals("dummy_string", impl.getAuxDescription1());
  }

  @Test
  public void testGetGtEarPiercingDetails() throws Exception {
    Assert.assertEquals(gtearpiercingdetails, impl.getGtEarPiercingDetails());
  }

  @Test
  public void testGetGtHearingAidDetails() throws Exception {
    Assert.assertEquals(gthearingaiddetails, impl.getGtHearingAidDetails());
  }

  @Test
  public void testGetFullimage() throws Exception {
    Assert.assertEquals("dummy_string", impl.getFullimage());
  }

  @Test
  public void testGetPartNumber() throws Exception {
    Assert.assertEquals("dummy_string", impl.getPartNumber());
  }

  @Test
  public void testGetRegion() throws Exception {
    Assert.assertEquals("dummy_string", impl.getRegion());
  }

  @Test
  public void testGetGtBoxDetails() throws Exception {
    Assert.assertEquals(gtboxdetails, impl.getGtBoxDetails());
  }

  @Test
  public void testGetGtLetterDetails() throws Exception {
    Assert.assertEquals(gtletterdetails, impl.getGtLetterDetails());
  }

  @Test
  public void testObj() throws Exception {
    Assert.assertNotNull(impl);
  }

}
